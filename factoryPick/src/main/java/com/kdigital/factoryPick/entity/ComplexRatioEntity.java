package com.kdigital.factoryPick.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.kdigital.factoryPick.dto.ComplexRatioDTO;

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
@Table(name = "complex_ratio")
public class ComplexRatioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // AUTO_INCREMENT
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "complex_name", length = 200, nullable = false)
    private String complexName;

    @Column(name = "food")
    private Double food;

    @Column(name = "clothing")
    private Double clothing;

    @Column(name = "wood")
    private Double wood;

    @Column(name = "petrochemical")
    private Double petrochemical;

    @Column(name = "non_metal")
    private Double nonMetal;

    @Column(name = "metal")
    private Double metal;

    @Column(name = "machinery")
    private Double machinery;

    @Column(name = "electronics")
    private Double electronics;

    @Column(name = "trans")
    private Double trans;

    @Column(name = "other")
    private Double other;

    @Column(name = "non_manu")
    private Double nonManu;

    // DTO -> Entity 변환
    public static ComplexRatioEntity toEntity(ComplexRatioDTO dto) {
        return ComplexRatioEntity.builder()
                .complexName(dto.getComplexName())
                .food(dto.getFood())
                .clothing(dto.getClothing())
                .wood(dto.getWood())
                .petrochemical(dto.getPetrochemical())
                .nonMetal(dto.getNonMetal())
                .metal(dto.getMetal())
                .machinery(dto.getMachinery())
                .electronics(dto.getElectronics())
                .trans(dto.getTrans())
                .other(dto.getOther())
                .nonManu(dto.getNonManu())
                .build();
    }
}
