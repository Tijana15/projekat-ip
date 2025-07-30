package net.etfbl.ip.urban_motion.repository;

import net.etfbl.ip.urban_motion.model.Car;
import net.etfbl.ip.urban_motion.service.VehicleService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {
    List<Car> findByManufacturerId(Long manufacturerId);

    List<Car> findByVehicleState(String vehicleState);

    List<Car> findByModel(String model);
}
