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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "survey")
public class SurveyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;  // Integer -> Long으로 변경

    @Column(name = "user_email", length = 255, nullable = false)
    private String userEmail;

    @Column(name = "province_input", length = 300)
    private String provinceInput;

    @Column(name = "highway_input", nullable = false)
    private Integer highwayInput;

    @Column(name = "port_input", nullable = false)
    private Integer portInput;

    @Column(name = "train_input", nullable = false)
    private Integer trainInput;

    @Column(name = "airport_input", nullable = false)
    private Integer airportInput;

    @Column(name = "price_input", nullable = false)
    private Integer priceInput;

    @Column(name = "industry_input", nullable = false)
    private Double industryInput;

    @Column(name = "products_input", length = 100, nullable = false)
    private String productsInput;

    @Column(name = "raw_materials_input", length = 100, nullable = false)
    private String rawMaterialsInput;
}
