package com.kdigital.factoryPick.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "survey_result")
public class SurveyResultEntity {

	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "user_email", nullable = false)
	    private String userEmail;  // 가상의 이메일로 테스트 진행

	    @Column(name = "complex_name", nullable = false)
	    private String complexName;

	    @Column(name = "region_score")
	    private Double regionScore;

	    @Column(name = "industry_score")
	    private Double industryScore;

	    @Column(name = "land_price_score")
	    private Double landPriceScore;

	    @Column(name = "raw_material_score")
	    private Double rawMaterialScore;

	    @Column(name = "product_score")
	    private Double productScore;

	    @Column(name = "transport_score")
	    private Double transportScore;

	    @Column(name = "worker_score")
	    private Double workerScore;

	    @CreationTimestamp
	    @Column(name = "create_time", updatable = false)
	    private LocalDateTime createTime;

	    @Column(name = "rank_result", nullable = false)
	    private Integer rankResult;

	    @Column(name = "final_similarity_score")
	    private Double finalSimilarityScore;
}


