package com.cardekho.recommendation.datasource;

import com.cardekho.recommendation.model.Car;
import com.cardekho.recommendation.model.FuelType;
import com.cardekho.recommendation.model.Transmission;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class InMemoryCarDataSource {

    private final List<Car> cars = List.of(
            new Car("CAR-001", "Maruti Suzuki", "Alto K10", BigDecimal.valueOf(399000), FuelType.PETROL, 24.39, 2.0, 4, Transmission.MANUAL),
            new Car("CAR-002", "Maruti Suzuki", "Swift", BigDecimal.valueOf(649000), FuelType.PETROL, 24.80, 3.0, 5, Transmission.MANUAL),
            new Car("CAR-003", "Maruti Suzuki", "Baleno", BigDecimal.valueOf(666000), FuelType.PETROL, 22.35, 3.0, 5, Transmission.AUTOMATIC),
            new Car("CAR-004", "Maruti Suzuki", "Dzire", BigDecimal.valueOf(657000), FuelType.CNG, 31.12, 5.0, 5, Transmission.MANUAL),
            new Car("CAR-005", "Maruti Suzuki", "Brezza", BigDecimal.valueOf(834000), FuelType.PETROL, 19.80, 4.0, 5, Transmission.AUTOMATIC),
            new Car("CAR-006", "Maruti Suzuki", "Ertiga", BigDecimal.valueOf(869000), FuelType.CNG, 26.11, 3.0, 7, Transmission.MANUAL),
            new Car("CAR-007", "Hyundai", "Grand i10 Nios", BigDecimal.valueOf(592000), FuelType.PETROL, 20.70, 2.0, 5, Transmission.MANUAL),
            new Car("CAR-008", "Hyundai", "i20", BigDecimal.valueOf(704000), FuelType.PETROL, 20.00, 3.0, 5, Transmission.AUTOMATIC),
            new Car("CAR-009", "Hyundai", "Exter", BigDecimal.valueOf(613000), FuelType.CNG, 27.10, 3.0, 5, Transmission.MANUAL),
            new Car("CAR-010", "Hyundai", "Venue", BigDecimal.valueOf(794000), FuelType.PETROL, 18.31, 4.0, 5, Transmission.AUTOMATIC),
            new Car("CAR-011", "Hyundai", "Creta", BigDecimal.valueOf(1100000), FuelType.DIESEL, 21.80, 3.0, 5, Transmission.AUTOMATIC),
            new Car("CAR-012", "Hyundai", "Verna", BigDecimal.valueOf(1107000), FuelType.PETROL, 20.60, 5.0, 5, Transmission.AUTOMATIC),
            new Car("CAR-013", "Tata", "Tiago", BigDecimal.valueOf(565000), FuelType.CNG, 26.49, 4.0, 5, Transmission.MANUAL),
            new Car("CAR-014", "Tata", "Altroz", BigDecimal.valueOf(665000), FuelType.DIESEL, 23.64, 5.0, 5, Transmission.MANUAL),
            new Car("CAR-015", "Tata", "Punch", BigDecimal.valueOf(613000), FuelType.PETROL, 20.09, 5.0, 5, Transmission.AUTOMATIC),
            new Car("CAR-016", "Tata", "Nexon", BigDecimal.valueOf(815000), FuelType.DIESEL, 24.08, 5.0, 5, Transmission.MANUAL),
            new Car("CAR-017", "Tata", "Harrier", BigDecimal.valueOf(1549000), FuelType.DIESEL, 16.80, 5.0, 5, Transmission.AUTOMATIC),
            new Car("CAR-018", "Tata", "Tiago EV", BigDecimal.valueOf(799000), FuelType.ELECTRIC, 24.00, 4.0, 5, Transmission.AUTOMATIC),
            new Car("CAR-019", "Mahindra", "XUV 3XO", BigDecimal.valueOf(749000), FuelType.PETROL, 20.10, 5.0, 5, Transmission.MANUAL),
            new Car("CAR-020", "Mahindra", "Bolero Neo", BigDecimal.valueOf(995000), FuelType.DIESEL, 17.29, 3.0, 7, Transmission.MANUAL),
            new Car("CAR-021", "Mahindra", "Scorpio N", BigDecimal.valueOf(1360000), FuelType.DIESEL, 15.42, 5.0, 7, Transmission.AUTOMATIC),
            new Car("CAR-022", "Mahindra", "XUV700", BigDecimal.valueOf(1399000), FuelType.DIESEL, 17.00, 5.0, 7, Transmission.AUTOMATIC),
            new Car("CAR-023", "Kia", "Sonet", BigDecimal.valueOf(799000), FuelType.DIESEL, 22.30, 3.0, 5, Transmission.AUTOMATIC),
            new Car("CAR-024", "Kia", "Seltos", BigDecimal.valueOf(1090000), FuelType.PETROL, 17.70, 3.0, 5, Transmission.AUTOMATIC),
            new Car("CAR-025", "Kia", "Carens", BigDecimal.valueOf(1052000), FuelType.DIESEL, 21.30, 3.0, 7, Transmission.MANUAL),
            new Car("CAR-026", "Toyota", "Glanza", BigDecimal.valueOf(686000), FuelType.CNG, 30.61, 4.0, 5, Transmission.MANUAL),
            new Car("CAR-027", "Toyota", "Urban Cruiser Hyryder", BigDecimal.valueOf(1114000), FuelType.HYBRID, 27.97, 4.0, 5, Transmission.AUTOMATIC),
            new Car("CAR-028", "Honda", "Amaze", BigDecimal.valueOf(716000), FuelType.PETROL, 18.60, 4.0, 5, Transmission.AUTOMATIC),
            new Car("CAR-029", "Honda", "City e:HEV", BigDecimal.valueOf(2000000), FuelType.HYBRID, 27.13, 5.0, 5, Transmission.AUTOMATIC),
            new Car("CAR-030", "MG", "Comet EV", BigDecimal.valueOf(699000), FuelType.ELECTRIC, 28.00, 3.0, 4, Transmission.AUTOMATIC)
    );

    public List<Car> findAll() {
        return cars;
    }
}
