package com.cardekho.recommendation.dto;

import com.cardekho.recommendation.model.FuelType;
import com.cardekho.recommendation.model.Priority;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CarRecommendationRequest(
        @NotNull(message = "budget is required")
        @DecimalMin(value = "1.0", message = "budget must be greater than 0")
        BigDecimal budget,

        @NotNull(message = "fuelType is required")
        FuelType fuelType,

        @Min(value = 1, message = "familySize must be at least 1")
        @Max(value = 10, message = "familySize must be at most 10")
        int familySize,

        @NotNull(message = "priority is required")
        Priority priority
) {
}
