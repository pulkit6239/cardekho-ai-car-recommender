# Car Recommendation System

Spring Boot REST API that recommends cars using an in-memory data source.

## Features

- Layered architecture with controller, service, DTO, model, and data source packages
- REST API for car recommendations
- In-memory catalog of 30 Indian-market cars
- Recommendations based on budget, fuel type, family size, and priority
- Returns the top 3 matching recommendations
- Score-based recommendation algorithm

## Run

```bash
mvn spring-boot:run
```

## API

`POST /api/recommend`

```json
{
  "budget": 1000000,
  "fuelType": "PETROL",
  "familySize": 5,
  "priority": "MILEAGE"
}
```

Example response:

```json
{
  "success": true,
  "data": [
    {
      "id": "CAR-016",
      "brand": "Tata",
      "model": "Nexon",
      "price": 815000,
      "fuelType": "DIESEL",
      "mileage": 24.08,
      "safetyRating": 5.0,
      "seatingCapacity": 5,
      "transmission": "MANUAL",
      "score": 92.03,
      "explanation": "Tata Nexon fits within the requested budget, matches the preferred fuel type, has enough seating capacity for the family size; gets an extra boost for safety rating."
    }
  ],
  "error": null,
  "meta": {
    "timestamp": "2026-05-31T04:55:00Z"
  }
}
```

The legacy `POST /api/v1/recommendations/cars` endpoint is also available.

Allowed `fuelType` values:

- `PETROL`
- `DIESEL`
- `CNG`
- `ELECTRIC`
- `HYBRID`

Allowed `priority` values:

- `MILEAGE`
- `SAFETY`
- `PERFORMANCE`

Car objects include:

- `id`
- `brand`
- `model`
- `price`
- `fuelType`
- `mileage`
- `safetyRating`
- `seatingCapacity`
- `transmission`

## Recommendation Scoring

Cars above the requested budget are excluded. Eligible cars receive a recommendation score and the API returns the top 3 cars sorted by score.

- Budget match: highest weight, up to `45`
- Fuel type match: up to `20`
- Seating capacity match: up to `15`
- Mileage preference: base weight `10`, boosted when priority is `MILEAGE`
- Safety preference: base weight `10`, boosted when priority is `SAFETY`
