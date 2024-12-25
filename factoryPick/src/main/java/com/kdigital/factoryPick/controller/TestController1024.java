package com.kdigital.factoryPick.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController1024 {
    @GetMapping("/industrial-data")
    public List<Map<String, Object>> getIndustrialData() {
        // JSON 데이터 (원래 데이터를 여기서 반환)
        List<Map<String, Object>> industrialData = List.of(
            Map.of(
                "rankResult", 1,
                "complexName", "시화국가산업단지",
                "regionScore", 0,
                "industryScore", 1,
                "landPriceScore", 1,
                "rawMaterialScore", 1,
                "productScore", 0.820617113,
                "transportScore", 0.3666666667,
                "workerScore", 1
            )
            // 추가 데이터도 여기에 넣을 수 있습니다.
        );

        return industrialData;
    }
}
