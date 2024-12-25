document.addEventListener('DOMContentLoaded', function () {
  // 1. 지도 생성 및 옵션 설정
  const mapContainer = document.getElementById('map'); // 지도를 표시할 div
  const mapOption = {
    center: new kakao.maps.LatLng(36.2683, 127.6358), // 지도의 중심 좌표
    level: 12 // 확대 수준
  };

  // 2. 지도 생성
  const map = new kakao.maps.Map(mapContainer, mapOption);
      
  const markers = [];
  const infoWindows = [];
  let complexInfoData = [];
  let transInfoData = [];
   
  
  // 3. JSON 데이터 비동기 로드
  Promise.all([
    fetch('/property/COMPLEX_INFO(20241021).json').then(res => res.json()),
    fetch('/property/TRANS_INFO2(20241021).json').then(res => res.json()),
    fetch('/property/COMPLEX_POSITION.json').then(res => res.json())
  ]).then(([complexInfo, transInfo, complexPositionData]) => {
    complexInfoData = complexInfo.COMPLEX_INFO;
    transInfoData = transInfo.TRANS_INFO2;
    createMarkersFromJson(complexPositionData);
    setupHeartEvents(); // 하트 버튼 이벤트 설정

    // 10. URL에서 검색어를 가져와 자동 검색 실행
    const searchValue = getQueryParam('search'); // 전달된 검색어 가져오기
    if (searchValue) {
      document.getElementById('search-input').value = searchValue; // 검색어 입력 필드에 표시
      document.querySelector('.search-box button').click(); // 검색 버튼 자동 클릭
    }
  }).catch(error => console.error('데이터 로드 오류:', error));

  // 4. 마커 생성 함수
  function createMarkersFromJson(jsonData) {
    jsonData.forEach(function (item) {
      const marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(parseFloat(item.latitude), parseFloat(item.longitude)),
        title: item.complex_name
      });

      markers.push(marker);

      const infoWindow = new kakao.maps.InfoWindow({
        content: `<div id="custom-info-window">${item.complex_name}</div>`,
        zIndex: 1
      });
      infoWindows.push(infoWindow);

      kakao.maps.event.addListener(marker, 'click', function () {
        map.setCenter(marker.getPosition());
        map.setLevel(8);
        document.getElementById('complex-name-header').innerText = `${item.complex_name} 정보`;
        infoWindow.open(map, marker);

        // 테이블과 버튼 보이기
        document.querySelectorAll('#complex-info-table, #complex-name-header, #emptyheart').forEach(el => {
          el.style.display = 'inline-block';
        });
        document.getElementById('fullheart').style.display = 'none'; // 빨간 하트 숨기기
        updateTable(item.complex_name);
      });
    });

    kakao.maps.event.addListener(map, 'zoom_changed', displayVisibleMarkersInfoWindows);
    kakao.maps.event.addListener(map, 'dragend', displayVisibleMarkersInfoWindows);
  }

  // 5. 하트 버튼 이벤트 설정
  document.addEventListener('DOMContentLoaded', function () {
      setupHeartEvents(); // DOM이 완전히 로드된 후 이벤트 연결
  });

  function setupHeartEvents() {
      const closeModalButton = document.querySelector('.close');
      const emptyHeart = document.getElementById('emptyheart');
      const fullHeart = document.getElementById('fullheart');

      if (!emptyHeart || !fullHeart) {
          console.error('emptyHeart 또는 fullHeart 요소를 찾을 수 없습니다.');
          return;
      }

      async function isLoggedIn() {
          try {
              const response = await fetch('/api/user/status');
              const data = await response.json();
              return data.loggedIn;
          } catch (error) {
              console.error('로그인 상태 확인 중 오류 발생:', error);
              return false;
          }
      }

   // 경로와 검색어 저장 
      function saveCurrentPathAndSearch(complexName) {
          const currentPath = window.location.pathname + window.location.search; // 현재 경로 저장
          const searchValue = document.getElementById('search-input')?.value || ''; // 검색어
          sessionStorage.setItem('redirectPath', currentPath); // 경로 저장
          sessionStorage.setItem('searchValue', searchValue); // 검색어 저장
          sessionStorage.setItem('complexName', complexName); // 산업단지 이름 저장
      }

      function restorePathAndSearch() {
          const redirectPath = sessionStorage.getItem('redirectPath') || '/property';
          const searchValue = sessionStorage.getItem('searchValue') || '';

          sessionStorage.clear(); // 저장된 값 제거

          const fullRedirectPath = redirectPath + (searchValue ? `?search=${encodeURIComponent(searchValue)}` : '');
          window.location.href = fullRedirectPath;
      }
   // 하트 버튼 이벤트 
      emptyHeart.addEventListener('dblclick', async function () {
          const loggedIn = await isLoggedIn();
          const complexName = document.getElementById('complex-name-header').innerText.replace(' 정보', '');


          try {
              const response = await fetch('/api/like', {
                  method: 'POST',
                  headers: { 'Content-Type': 'application/json' },
                  body: JSON.stringify({ complexName })
              });

              const data = await response.json();
              if (data.success) {
                  emptyHeart.style.display = 'none';
                  fullHeart.style.display = 'inline-block';
              alert("찜 목록에 추가되었습니다!")
              }else{
            alert(data.message); 
           }
          } catch (error) {
              console.error('좋아요 처리 중 오류 발생:', error);
           
          }
      });

      fullHeart.addEventListener('dblclick', function () {
          fullHeart.style.display = 'none';
          emptyHeart.style.display = 'inline-block';
      });

      closeModalButton.addEventListener('click', function () {
          loginModal.style.display = 'none';
      });

      window.addEventListener('click', function (event) {
          if (event.target === loginModal) {
              loginModal.style.display = 'none';
          }
      });


    }



  // 6. 검색 및 자동완성 기능
  document.querySelector('.search-box button').addEventListener('click', function () {
    const searchInputValue = document.getElementById('search-input').value.trim();
    if (searchInputValue) {
      searchIndustrialComplex(searchInputValue);
    }
  });

  document.getElementById('search-input').addEventListener('keydown', function (event) {
    if (event.key === 'Enter') {
      document.getElementById('autocomplete-list').innerHTML = ''; // 목록 제거
      const searchInputValue = document.getElementById('search-input').value.trim();
      if (searchInputValue) {
        searchIndustrialComplex(searchInputValue);
      }
    }
  });

  document.getElementById('search-input').addEventListener('input', function () {
    const searchTerm = this.value.toLowerCase().trim();
    const autocompleteList = document.getElementById('autocomplete-list');

    autocompleteList.innerHTML = ''; // 기존 목록 비우기

    if (searchTerm) {
      const suggestions = complexInfoData
        .map(item => item.complex_name)
        .filter(name => name.toLowerCase().includes(searchTerm));

      suggestions.forEach(suggestion => {
        const li = document.createElement('li');
        li.textContent = suggestion;
        li.addEventListener('click', function () {
          document.getElementById('search-input').value = suggestion;
          autocompleteList.innerHTML = ''; // 선택 후 목록 제거
          searchIndustrialComplex(suggestion);
        });
        autocompleteList.appendChild(li);
      });
    }
  });

  // 7. 검색 함수
  function searchIndustrialComplex(searchTerm) {
    let found = false;
    markers.forEach((marker) => {
      if (marker.getTitle() === searchTerm) {
        found = true;
        map.setCenter(marker.getPosition());
        map.setLevel(8);
        kakao.maps.event.trigger(marker, 'click');
      }
    });

    if (!found) alert('해당 단지명을 찾을 수 없습니다.');
  }

  // 8. 마커 표시 제어
  function displayVisibleMarkersInfoWindows() {
    const bounds = map.getBounds();
    const zoomLevel = map.getLevel();

    markers.forEach((marker, index) => {
      if (bounds.contain(marker.getPosition()) && zoomLevel <= 8) {
        infoWindows[index].open(map, marker);
      } else {
        infoWindows[index].close();
      }
    });
  }

  // 9. 테이블 업데이트
  function updateTable(complexName) {
    const complexData = complexInfoData.find(item => item.complex_name === complexName);
    const transData = transInfoData.find(item => item.complex_name === complexName);
   console.log('completion_date:', complexData.completion_date);
    if (complexData && transData) {
      document.getElementById('size').innerText = `${parseInt(complexData.land_size).toLocaleString()} m²`;
      document.getElementById('completeDate').innerText = complexData.completion_date
        ? formatDate(complexData.completion_date)
        : '-';
      document.getElementById('highway').innerText = `${transData.highway_name} ${transData.highway_distance} km`;
      document.getElementById('seaport').innerText = `${transData.seaport_name} ${transData.seaport_distance} km`;
      document.getElementById('train').innerText = `${transData.station_name} ${transData.station_distance} km`;
      document.getElementById('airport').innerText = `${transData.airport_name} ${transData.airport_distance} km`;
    } else {
      alert('해당 단지에 대한 정보를 찾을 수 없습니다.');
    }
  }

  function formatDate(dateString) {
      if (!dateString) return '-'; // 날짜 문자열이 없을 경우 '-' 반환

      const date = new Date(dateString);

      // 날짜가 유효하지 않은 경우 ('Invalid Date')
      if (isNaN(date.getTime())) {
          return '-';
      }

      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0'); 
      
      return `${year}년 ${month}월 ${day}일`;
  }


  // 10. URL에서 검색어 가져오는 함수
  function getQueryParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
  }
});