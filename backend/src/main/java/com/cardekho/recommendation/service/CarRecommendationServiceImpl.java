package com.cardekho.recommendation.service;

import com.cardekho.recommendation.datasource.InMemoryCarDataSource;
import com.cardekho.recommendation.dto.CarRecommendationRequest;
import com.cardekho.recommendation.dto.CarRecommendationResponse;
import com.cardekho.recommendation.model.Car;
import com.cardekho.recommendation.model.Priority;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

@Service
public class CarRecommendationServiceImpl implements CarRecommendationService {

    private static final int MAX_RECOMMENDATIONS = 3;
    private static final double BUDGET_WEIGHT = 45.0;
    private static final double FUEL_TYPE_WEIGHT = 20.0;
    private static final double SEATING_CAPACITY_WEIGHT = 15.0;
    private static final double MILEAGE_WEIGHT = 10.0;
    private static final double SAFETY_WEIGHT = 10.0;
    private static final double MAX_MILEAGE = 32.0;
    private static final double MAX_SAFETY_RATING = 5.0;

    private final InMemoryCarDataSource carDataSource;

    public CarRecommendationServiceImpl(InMemoryCarDataSource carDataSource) {
        this.carDataSource = carDataSource;
    }

    @Override
    public List<CarRecommendationResponse> recommendCars(CarRecommendationRequest request) {
        return carDataSource.findAll().stream()
                .filter(car -> isWithinBudget(car, request.budget()))
                .map(car -> toRecommendationResponse(car, request, calculateScore(car, request)))
                .sorted(Comparator.comparing(CarRecommendationResponse::score).reversed())
                .limit(MAX_RECOMMENDATIONS)
                .toList();
    }

    private CarRecommendationResponse toRecommendationResponse(
            Car car,
            CarRecommendationRequest request,
            double recommendationScore
    ) {
        return new CarRecommendationResponse(
                car.id(),
                car.brand(),
                car.model(),
                car.price(),
                car.fuelType(),
                car.mileage(),
                car.safetyRating(),
                car.seatingCapacity(),
                car.transmission(),
                recommendationScore,
                buildExplanation(car, request)
        );
    }

    private boolean isWithinBudget(Car car, BigDecimal budget) {
        return car.price().compareTo(budget) <= 0;
    }

    private double calculateScore(Car car, CarRecommendationRequest request) {
        double score = calculateBudgetScore(car, request.budget())
                + calculateFuelTypeScore(car, request)
                + calculateSeatingCapacityScore(car, request)
                + calculateMileageScore(car, request.priority())
                + calculateSafetyScore(car, request.priority());

        return BigDecimal.valueOf(score)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private double calculateBudgetScore(Car car, BigDecimal budget) {
        double budgetUsage = car.price().doubleValue() / budget.doubleValue();
        return BUDGET_WEIGHT * (0.8 + (budgetUsage * 0.2));
    }

    private double calculateFuelTypeScore(Car car, CarRecommendationRequest request) {
        return car.fuelType() == request.fuelType() ? FUEL_TYPE_WEIGHT : 0.0;
    }

    private double calculateSeatingCapacityScore(Car car, CarRecommendationRequest request) {
        if (car.seatingCapacity() >= request.familySize()) {
            return SEATING_CAPACITY_WEIGHT;
        }

        return SEATING_CAPACITY_WEIGHT * ((double) car.seatingCapacity() / request.familySize());
    }

    private double calculateMileageScore(Car car, Priority priority) {
        double priorityMultiplier = priority == Priority.MILEAGE ? 1.5 : 1.0;
        double normalizedMileage = Math.min(car.mileage() / MAX_MILEAGE, 1.0);

        return MILEAGE_WEIGHT * priorityMultiplier * normalizedMileage;
    }

    private double calculateSafetyScore(Car car, Priority priority) {
        double priorityMultiplier = priority == Priority.SAFETY ? 1.5 : 1.0;
        double normalizedSafetyRating = Math.min(car.safetyRating() / MAX_SAFETY_RATING, 1.0);

        return SAFETY_WEIGHT * priorityMultiplier * normalizedSafetyRating;
    }

    private String buildExplanation(Car car, CarRecommendationRequest request) {
        String budgetReason = "fits within the requested budget";
        String fuelReason = car.fuelType() == request.fuelType()
                ? "matches the preferred fuel type"
                : "does not match the preferred fuel type";
        String seatingReason = car.seatingCapacity() >= request.familySize()
                ? "has enough seating capacity for the family size"
                : "has limited seating capacity for the family size";
        String priorityReason = switch (request.priority()) {
            case MILEAGE -> "gets an extra boost for strong mileage";
            case SAFETY -> "gets an extra boost for safety rating";
            case PERFORMANCE -> "is ranked using the balanced budget, fuel, seating, mileage, and safety score";
        };

        return "%s %s, %s, %s, and %s.".formatted(
                car.brand(),
                car.model(),
                budgetReason,
                fuelReason,
                seatingReason + "; " + priorityReason
        );
    }
}
