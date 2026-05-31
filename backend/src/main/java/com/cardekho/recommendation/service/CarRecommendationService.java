package com.cardekho.recommendation.service;

import com.cardekho.recommendation.dto.CarRecommendationRequest;
import com.cardekho.recommendation.dto.CarRecommendationResponse;

import java.util.List;

public interface CarRecommendationService {

    List<CarRecommendationResponse> recommendCars(CarRecommendationRequest request);
}
