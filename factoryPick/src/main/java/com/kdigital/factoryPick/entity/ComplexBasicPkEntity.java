package com.kdigital.factoryPick.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "complex_basic_pk")
public class ComplexBasicPkEntity {

    @Id
    @Column(name = "complex_name", length = 200, nullable = false)
    private String complexName;  // Primary Key

    @Column(name = "province", length = 50)
    private String province;  // Province

    @Column(name = "land_price")
    private int landPrice;  // Land Price

    @Column(name = "complex_group", length = 20, nullable = false)
    private String complexGroup;  // Complex Group
}
