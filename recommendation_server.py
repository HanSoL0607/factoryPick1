#!/usr/bin/env python
# coding: utf-8

# In[2]:


# 서버용 import
# pip install Flask
from flask import Flask, request, jsonify, session

app = Flask(__name__)


# In[3]:


industry_codes = {
    '음식료': [10, 11, 12],
    '섬유의복': [13, 14, 15],
    '목재종이': [16, 17, 18],
    '석유화학': [19, 20, 21, 22],
    '비금속': [23],
    '철강': [24],
    '기계': [25, 29],
    '전기전자': [26, 27, 28],
    '운송장비': [30, 31],
    '기타': [32, 33, 34]
}


# In[4]:


import pandas as pd
import numpy as np
from sklearn.preprocessing import OneHotEncoder
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.preprocessing import MinMaxScaler
from googletrans import Translator
import string
import folium
import json


# In[5]:


# 산업단지 기본 데이터 불러오기
df_park = pd.read_excel('Data/COMPLEX_BASIC.xlsx')
df_concentration_all = pd.read_excel('Data/업종집적도_결과.xlsx')
df_similarity = pd.read_excel('모든공장유사도용0621.xlsx', nrows=97615)
df_trans = pd.read_excel('Data/COMPLEX_TRANS.xlsx')
df_worker = pd.read_excel('Data/노동력.xlsx')

# GloVe 모델 로드
# 원자재, 생산품 유사도용
glove_txt_file_path = 'glove.6B.300d.txt'

# 전체 불러오기 (메모리 문제)(속도를 버리고 정확도를 얻음) => ***VS CODE에서 돌리기***
def load_all_glove_vectors(glove_txt_file_path):
    glove_dict = {}
    with open(glove_txt_file_path, 'r', encoding='utf-8') as f:
        for line in f:
            values = line.split()
            word = values[0]
            vector = np.array(values[1:], dtype='float32')
            glove_dict[word] = vector

    return glove_dict

# GloVe 벡터 로드 시도
try:
    glove_dict = load_all_glove_vectors(glove_txt_file_path)
    print("GloVe 벡터 로드 완료")
except Exception as e:
    print(f"GloVe 벡터 로드 중 오류 발생: {e}")



# glove 벡터 가져오기(문장 단위)
def get_glove_vector(sentence, glove_dict):
    # 문장을 단어단위로 쪼개기
    words = sentence.split()

    # 벡터 담을 리스트
    vectors = []

    # 단어 훑고 glove 가져오기
    for word in words:
        vector = glove_dict.get(word)
        if vector is not None:
            vectors.append(vector)

    # 벡터 평균 계산
    if len(vectors) > 0:
        # Compute the average of the vectors
        avg_vector = np.mean(vectors, axis=0)
        return avg_vector
    else:
        return None

# 유사도 계산
def calculate_similarity(user_input_sentence, file_data_sentence, glove_dict):
    # 두 벡터 가져오기
    vector1 = get_glove_vector(user_input_sentence, glove_dict)    # 유저 입력값 벡터
    vector2 = get_glove_vector(file_data_sentence, glove_dict)    # 파일의 데이터 벡터

    # 벡터중 하나라도 존재하지 않는 경우 유사도 0
    if vector1 is None or vector2 is None:
        return None

    # 유사도 계산
    cosine_similarity = np.dot(vector1, vector2) / (np.linalg.norm(vector1) * np.linalg.norm(vector2))
    return cosine_similarity


# 사용자의 입력값 한글 -> 영어 번역
def translate_to_english(text, dest='en', src='auto'):
    translator = Translator()
    try:
        translation = translator.translate(text, dest=dest, src=src)
        return translation.text
    except Exception as e:
        print(f"Error translating text '{text}': {e}")
        return None  # 오류 발생 시 None 반환

    # 예외 처리 추가 예시
def translate_with_fallback(text):
    translated_text = translate_to_english(text)
    if translated_text is None:
        print(f"Translation failed for text: '{text}' <-")
        return ''  # 또는 처리할 수 있는 기본 값
    return translated_text


# # 한꺼번에 처리하는 로직

# In[4]:


@app.route('/recommendation', methods=['POST'])
def recommendation():
    data = request.json
    region = data.get('region')
    transportation = data.get('transportation')
    landPrice = data.get('landPrice')
    industry = data.get('industry')
    product = data.get('product')
    rawMaterial = data.get('rawMaterial')

    
    


    # 시도 유사도 계산
    user_region_input = region
    user_regions = [region.strip() for region in user_region_input.split(',')]
    df_park['시도유사도'] = df_park['시도'].apply(lambda x: 1 if x in user_regions else 0)
    df_sido_similarity = df_park.groupby('단지명', as_index=False).agg({'시도유사도': 'max'})


    # 업종집적도 계산
    user_industry = industry
    industry_col_int = f"{user_industry}_업종집적도"
    df_concentration = df_concentration_all[['단지명', industry_col_int]]
    df_concentration = df_concentration.rename(columns={industry_col_int: "업종집적도"}).sort_values(by='업종집적도', ascending=False)


    # 토지 지가 점수 계산
    preferred_price_max = landPrice
    beta = 5

    def calculate_penalty_score(price, max_price, beta):
        if price <= max_price:
            return 1
        excess_ratio = (price - max_price) / max_price
        return 1 / np.exp(beta * excess_ratio)
    df_park['토지 개별공시지가 점수'] = df_park['산업용지 평균분양가 (원/㎡)'].apply(
        lambda x: calculate_penalty_score(x, preferred_price_max, beta)
    )
    df_land_price_score = df_park[['단지명', '토지 개별공시지가 점수']].drop_duplicates()


    # 원자재 유사도 점수 계산
    user_need_item = rawMaterial
    user_input_english = translate_to_english(user_need_item).lower()
    translated_text_need = translate_with_fallback(user_need_item)
    if translated_text_need:
        user_input_english = translated_text_need.lower()
    else:
        print("오류. 생산품이나 원자재 존재X")

    # "원자재_영어" 유사도 계산
    raw_materials_df = df_similarity[['단지명', '원자재_영어']].dropna(subset=['원자재_영어'])
    raw_materials_df['원자재_영어'] = raw_materials_df['원자재_영어'].str.lower()  # 소문자로 변환
    raw_materials_df['원자재_유사도'] = raw_materials_df['원자재_영어'].apply(
        lambda x: calculate_similarity(user_input_english, x, glove_dict)
    )

    # 유사도 0.5 이상인 경우만 가져오기 
    filtered_df_raw_materials = raw_materials_df[raw_materials_df['원자재_유사도'] >= 0.5]

    # 유사 공장 수 및 유사도 평균값 구하기 
    sim_fac_cnt_raw_materials = filtered_df_raw_materials.groupby('단지명').size().reset_index(name='유사 원자재 사용공장 수')
    mean_similarity_raw_materials = filtered_df_raw_materials.groupby('단지명')['원자재_유사도'].mean().reset_index(name='원자재 유사도 평균값')
    sim_fac_cnt_raw_materials = sim_fac_cnt_raw_materials.merge(mean_similarity_raw_materials, on='단지명')

    # 유사도와 공장 수의 가중치 비율
    weight_mean_raw_materials = 0.5
    weight_count_raw_materials = 0.5
    sim_fac_cnt_raw_materials['최종 유사도'] = (
        sim_fac_cnt_raw_materials['원자재 유사도 평균값'] * weight_mean_raw_materials +
        sim_fac_cnt_raw_materials['유사 원자재 사용공장 수'] * weight_count_raw_materials
    )

    # 0.5~1로 정규화
    min_final_sim_raw_materials = sim_fac_cnt_raw_materials['최종 유사도'].min()
    max_final_sim_raw_materials = sim_fac_cnt_raw_materials['최종 유사도'].max()
    sim_fac_cnt_raw_materials['원자재 유사도'] = 0.5 + (
        (sim_fac_cnt_raw_materials['최종 유사도'] - min_final_sim_raw_materials) /
        (max_final_sim_raw_materials - min_final_sim_raw_materials)
    ) * 0.5

    # 원자재 유사도 최종 결과
    raw_materials_result = sim_fac_cnt_raw_materials[['단지명', '원자재 유사도']].sort_values(by='원자재 유사도', ascending=False).drop_duplicates(subset='단지명')


    # 생산품 유사도 
    # 사용자의 생산품 입력
    user_input_item = product

    # 번역
    user_input_made_english = translate_to_english(user_input_item).lower()

    # 번역
    translated_text = translate_with_fallback(user_input_item)
    if translated_text:
        user_input_made_english = translated_text.lower()

    # "생산품_영어" 유사도 계산
    products_df = df_similarity[['단지명', '생산품_영어']].dropna(subset=['생산품_영어'])    # 결측치 제거
    products_df['생산품_영어'] = products_df['생산품_영어'].str.lower()  # 소문자로 변환
    products_df['생산품_유사도'] = products_df['생산품_영어'].apply(
        lambda x: calculate_similarity(user_input_made_english, x, glove_dict)
    )    # 코싸인 유사도 계산

    # 유사도 0.5 이상인 경우만 가져오기 
    filtered_df_products = products_df[products_df['생산품_유사도'] >= 0.5]

    # 유사 공장 수 및 유사도 평균값 구하기 
    sim_fac_cnt_products = filtered_df_products.groupby('단지명').size().reset_index(name='유사 생산품 제작공장 수')
    mean_similarity_products = filtered_df_products.groupby('단지명')['생산품_유사도'].mean().reset_index(name='생산품 유사도 평균값')
    sim_fac_cnt_products = sim_fac_cnt_products.merge(mean_similarity_products, on='단지명')

    # 유사도와 공장 수의 가중치 비율
    weight_mean_products = 0.5
    weight_count_products = 0.5
    sim_fac_cnt_products['최종 유사도'] = (
        sim_fac_cnt_products['생산품 유사도 평균값'] * weight_mean_products +
        sim_fac_cnt_products['유사 생산품 제작공장 수'] * weight_count_products
    )

    # 0.5~1로 정규화
    min_final_sim_products = sim_fac_cnt_products['최종 유사도'].min()
    max_final_sim_products = sim_fac_cnt_products['최종 유사도'].max()
    sim_fac_cnt_products['생산품 유사도'] = 0.5 + (
        (sim_fac_cnt_products['최종 유사도'] - min_final_sim_products) /
        (max_final_sim_products - min_final_sim_products)
    ) * 0.5

    # 생산품 유사도 결과
    products_result = sim_fac_cnt_products[['단지명', '생산품 유사도']].sort_values(by='생산품 유사도', ascending=False).drop_duplicates(subset='단지명')


    # 교통 접근성에 대한 사용자 선호도 입력

    user_priorities = {
        '고속도로 인접도': transportation.get('highwayPreference'),
        '철도역 인접도': transportation.get('railwayPreference'),
        '항만 인접도': transportation.get('portPreference'),
        '공항 인접도': transportation.get('airportPreference')
    }

    # 사용자 교통 인접 선호도 입력값에 따라 점수 생성 ex) 인접 선호도 순위(1,2,3,4) -> 인접도 점수(4,3,2,1)
    priority_scores = {key: 5 - value for key, value in user_priorities.items()}
    print("사용자 선호도 가중치:", priority_scores)

    # 페널티를 적용한 가중치 점수 계산
    df_trans['고속도로 가중치'] = df_trans['고속도로 인접도 점수'].apply(
        lambda x: x * priority_scores['고속도로 인접도'] + (x - priority_scores['고속도로 인접도']) if x < priority_scores['고속도로 인접도'] else x * priority_scores['고속도로 인접도'])

    df_trans['철도 가중치'] = df_trans['철도 인접도 점수'].apply(
        lambda x: x * priority_scores['철도역 인접도'] + (x - priority_scores['철도역 인접도']) if x < priority_scores['철도역 인접도'] else x * priority_scores['철도역 인접도'])

    df_trans['항만 가중치'] = df_trans['항만 인접도 점수'].apply(
        lambda x: x * priority_scores['항만 인접도'] + (x - priority_scores['항만 인접도']) if x < priority_scores['항만 인접도'] else x * priority_scores['항만 인접도'])

    df_trans['공항 가중치'] = df_trans['공항 인접도 점수'].apply(
        lambda x: x * priority_scores['공항 인접도'] + (x - priority_scores['공항 인접도']) if x < priority_scores['공항 인접도'] else x * priority_scores['공항 인접도'])

    # 총 가중치 점수 계산
    df_trans['총 가중치 점수'] = (
        df_trans['고속도로 가중치'] +
        df_trans['철도 가중치'] +
        df_trans['항만 가중치'] +
        df_trans['공항 가중치']
    )

    # 정규화된 총 가중치 점수 계산
    df_trans['교통 점수'] = (df_trans['총 가중치 점수'] - df_trans['총 가중치 점수'].min()) / (df_trans['총 가중치 점수'].max() - df_trans['총 가중치 점수'].min())

    # 단지명과 교통 점수로 이루어진 최종 데이터프레임 생성
    df_trans_result = df_trans[['단지명', '교통 점수']].sort_values(by='교통 점수', ascending=False).drop_duplicates(subset='단지명')

    
    # 노동력 점수
    worker_result = df_worker[['단지명', '노동력']].sort_values(by='노동력', ascending=False).drop_duplicates(subset='단지명')

    
    # 가중치 점수
    weight_region = 0.142857  # 시도 유사도 # df_sido_similarity [['단지명', '시도유사도']]
    weight_industry = 0.142857  # 업종집적도 # df_concentration [['단지명', '업종집적도']]
    weight_price_penalty = 0.142857  # 토지 개별공시지가 점수 # df_land_price_score [['단지명', '토지 개별공시지가 점수']]
    weight_raw_material = 0.142857  # 원자재 유사도 # raw_materials_result [['단지명', '원자재 유사도']]
    weight_product = 0.142857  # 생산품 유사도 # products_result [['단지명', '생산품 유사도']]
    weight_transport = 0.142857  # 교통 접근성 점수 # df_trans_result [['단지명', '교통 점수']]
    weight_pop = 0.142857  # 노동력 # worker_result [['단지명', '노동력']]


    
    # 최종 결과
    # 단지명 기준으로 데이터 병합
    merged_df = df_sido_similarity.merge(df_concentration, on='단지명', how='left')\
        .merge(df_land_price_score, on='단지명', how='left')\
        .merge(raw_materials_result, on='단지명', how='left')\
        .merge(products_result, on='단지명', how='left')\
        .merge(df_trans_result, on='단지명', how='left')\
        .merge(worker_result, on='단지명', how='left')

    # 최종 유사도 계산
    merged_df['최종 유사도'] = (
        merged_df['시도유사도'] * weight_region +
        merged_df['업종집적도'] * weight_industry +
        merged_df['토지 개별공시지가 점수'] * weight_price_penalty +
        merged_df['원자재 유사도'] * weight_raw_material +
        merged_df['생산품 유사도'] * weight_product +
        merged_df['교통 점수'] * weight_transport +
        merged_df['노동력'] * weight_pop
    )

    # NaN 값을 0으로 채우기
    merged_df.fillna(0, inplace=True)

    # 4. 필요한 컬럼만 추출 ("단지명"과 "최종 유사도")
    final_result = merged_df[['단지명', '최종 유사도']]


    # '최종 유사도' 컬럼을 기준으로 내림차순 정렬
    sorted_df = merged_df.sort_values(by='최종 유사도', ascending=False)

    # 인덱스를 새로운 컬럼으로 추가하여 순위를 매김 (기존 인덱스를 리셋하고 1부터 시작하는 순서로 설정)
    sorted_df = sorted_df.reset_index(drop=True)

    # 순위 컬럼 추가 (1부터 시작하는 순위)
    sorted_df['순위'] = sorted_df.index + 1

    # DataFrame 컬럼명을 변경하는 매핑 딕셔너리 생성 ＃ 스프링 ＪＳＯＮ 연계용
    column_mapping = {
        '순위': 'rankResult',
        '단지명': 'complexName',
        '최종 유사도': 'finalSimilarityScore',
        '시도유사도': 'regionScore',
        '업종집적도': 'industryScore',
        '토지 개별공시지가 점수': 'landPriceScore',
        '원자재 유사도': 'rawMaterialScore',
        '생산품 유사도': 'productScore',
        '교통 점수': 'transportScore',
        '노동력': 'workerScore'
    }

    # 정렬된 결과 출력 (순위 포함)
    result = sorted_df[['순위', '단지명', '최종 유사도', '시도유사도', '업종집적도', '토지 개별공시지가 점수', '원자재 유사도', '생산품 유사도', '교통 점수', '노동력']].head(10)

    # 컬럼명을 영어로 변경
    result = result.rename(columns=column_mapping)

    # 최종 결과 JSON 변환
    result_json = result.to_json(orient='records', force_ascii=False)
    
    # 파이썬 서버에서 JSON 데이터 확인
    print("서버에서 반환할 JSON 데이터: ", result_json)  # JSON 데이터를 콘솔에서 확인 ＃ 디버깅용
    
    # JSON 데이터를 그대로 반환 (jsonify는 불필요)
    return result_json


# In[ ]:


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)    # 파이썬 서버 포트 번호 5000

