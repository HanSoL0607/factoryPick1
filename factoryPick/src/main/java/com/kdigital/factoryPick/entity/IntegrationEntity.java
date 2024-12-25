package com.kdigital.factoryPick.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.kdigital.factoryPick.dto.IntegrationDTO;

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
@Table(name = "integration")
public class IntegrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // AUTO_INCREMENT
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "complex_name", length = 200, nullable = false)
    private String complexName;

    @Column(name = "food", nullable = false)
    private Double food;

    @Column(name = "clothing", nullable = false)
    private Double clothing;

    @Column(name = "wood", nullable = false)
    private Double wood;

    @Column(name = "petrochemical", nullable = false)
    private Double petrochemical;

    @Column(name = "non_metal", nullable = false)
    private Double nonMetal;

    @Column(name = "metal", nullable = false)
    private Double metal;

    @Column(name = "machinery", nullable = false)
    private Double machinery;

    @Column(name = "electronics", nullable = false)
    private Double electronics;

    @Column(name = "trans", nullable = false)
    private Double trans;

    @Column(name = "other", nullable = false)
    private Double other;

    @Column(name = "non_manu", nullable = false)
    private Double nonManu;

    // DTO -> Entity 변환
    public static IntegrationEntity toEntity(IntegrationDTO dto) {
        return IntegrationEntity.builder()
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
