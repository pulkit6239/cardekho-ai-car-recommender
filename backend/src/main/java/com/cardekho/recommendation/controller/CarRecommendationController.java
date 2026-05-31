package com.cardekho.recommendation.controller;

import com.cardekho.recommendation.dto.ApiResponse;
import com.cardekho.recommendation.dto.CarRecommendationRequest;
import com.cardekho.recommendation.dto.CarRecommendationResponse;
import com.cardekho.recommendation.service.CarRecommendationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarRecommendationController {

    private final CarRecommendationService carRecommendationService;

    public CarRecommendationController(CarRecommendationService carRecommendationService) {
        this.carRecommendationService = carRecommendationService;
    }

    @PostMapping("/api/recommend")
    public ResponseEntity<ApiResponse<List<CarRecommendationResponse>>> recommend(
            @Valid @RequestBody CarRecommendationRequest request
    ) {
        List<CarRecommendationResponse> recommendations = carRecommendationService.recommendCars(request);
        return ResponseEntity.ok(ApiResponse.success(recommendations));
    }

    @PostMapping("/api/v1/recommendations/cars")
    public ResponseEntity<ApiResponse<List<CarRecommendationResponse>>> recommendCars(
            @Valid @RequestBody CarRecommendationRequest request
    ) {
        List<CarRecommendationResponse> recommendations = carRecommendationService.recommendCars(request);
        return ResponseEntity.ok(ApiResponse.success(recommendations));
    }
}
