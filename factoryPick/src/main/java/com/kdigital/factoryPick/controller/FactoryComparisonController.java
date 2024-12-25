package com.kdigital.factoryPick.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.kdigital.factoryPick.dto.ComplexInfoDTO;
import com.kdigital.factoryPick.dto.TransInfoDTO;
import com.kdigital.factoryPick.service.ComplexInfoService;
import com.kdigital.factoryPick.service.TransInfoService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/factory")
public class FactoryComparisonController {

    private final ComplexInfoService complexInfoService;
    private final TransInfoService transInfoService;

    public FactoryComparisonController(ComplexInfoService complexInfoService, TransInfoService transInfoService) {
        this.complexInfoService = complexInfoService;
        this.transInfoService = transInfoService;
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getFactoryInfo(
            @RequestParam("first") String first,
            @RequestParam("second") String second) {

        // 첫 번째 산업단지 정보 조회
        ComplexInfoDTO firstInfo = complexInfoService.getComplexInfoByName(first);
        TransInfoDTO firstTrans = transInfoService.getComplexInfoByName(first);

        // 두 번째 산업단지 정보 조회
        ComplexInfoDTO secondInfo = complexInfoService.getComplexInfoByName(second);
        TransInfoDTO secondTrans = transInfoService.getComplexInfoByName(second);

        // 데이터가 제대로 채워졌는지 확인하는 로그 추가
        System.out.println("First Factory: " + firstInfo);
        System.out.println("Second Factory: " + secondInfo);

        // 첫 번째 산업단지 교통 정보 조합
        String firstHighway = formatTransportInfo(firstTrans, "highway");
        String firstSeaport = formatTransportInfo(firstTrans, "seaport");
        String firstStation = formatTransportInfo(firstTrans, "station");
        String firstAirport = formatTransportInfo(firstTrans, "airport");

        // 두 번째 산업단지 교통 정보 조합
        String secondHighway = formatTransportInfo(secondTrans, "highway");
        String secondSeaport = formatTransportInfo(secondTrans, "seaport");
        String secondStation = formatTransportInfo(secondTrans, "station");
        String secondAirport = formatTransportInfo(secondTrans, "airport");

        // 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("first", createFactoryResponse(firstInfo, firstHighway, firstSeaport, firstStation, firstAirport));
        response.put("second", createFactoryResponse(secondInfo, secondHighway, secondSeaport, secondStation, secondAirport));

        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    private Map<String, Object> createFactoryResponse(
            ComplexInfoDTO info, String highway, String seaport, String station, String airport) {
        Map<String, Object> factoryInfo = new HashMap<>();
        factoryInfo.put("name", Optional.ofNullable(info).map(ComplexInfoDTO::getComplexName).orElse("정보 없음"));
        factoryInfo.put("size", Optional.ofNullable(info).map(i -> i.getLandSize() + "㎡").orElse("정보 없음"));
        factoryInfo.put("completionDate", Optional.ofNullable(info).map(ComplexInfoDTO::getCompletionDate).orElse("N/A"));
        factoryInfo.put("highway", highway + "km");
        factoryInfo.put("seaport", seaport + "km");
        factoryInfo.put("station", station + "km");
        factoryInfo.put("airport", airport + "km");
        return factoryInfo;
    }

    private String formatTransportInfo(TransInfoDTO transInfo, String type) {
        if (transInfo == null) return "정보 없음";

        switch (type) {
            case "highway":
                return Optional.ofNullable(transInfo.getHighwayName()).orElse("정보 없음") + " " +
                       Optional.ofNullable(transInfo.getHighwayDistance()).orElse("");
            case "seaport":
                return Optional.ofNullable(transInfo.getSeaportName()).orElse("정보 없음") + " " +
                       Optional.ofNullable(transInfo.getSeaportDistance()).orElse("");
            case "station":
                return Optional.ofNullable(transInfo.getStationName()).orElse("정보 없음") + " " +
                       Optional.ofNullable(transInfo.getStationDistance()).orElse("");
            case "airport":
                return Optional.ofNullable(transInfo.getAirportName()).orElse("정보 없음") + " " +
                       Optional.ofNullable(transInfo.getAirportDistance()).orElse("");
            default:
                return "정보 없음";
        }
    }
}