package com.cardekho.recommendation.model;

import java.math.BigDecimal;

public record Car(
        String id,
        String brand,
        String model,
        BigDecimal price,
        FuelType fuelType,
        double mileage,
        double safetyRating,
        int seatingCapacity,
        Transmission transmission
) {
}
