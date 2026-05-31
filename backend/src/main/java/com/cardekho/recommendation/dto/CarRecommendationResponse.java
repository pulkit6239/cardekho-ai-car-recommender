package com.cardekho.recommendation.dto;

import com.cardekho.recommendation.model.FuelType;
import com.cardekho.recommendation.model.Transmission;

import java.math.BigDecimal;

public record CarRecommendationResponse(
        String id,
        String brand,
        String model,
        BigDecimal price,
        FuelType fuelType,
        double mileage,
        double safetyRating,
        int seatingCapacity,
        Transmission transmission,
        double score,
        String explanation
) {
}
