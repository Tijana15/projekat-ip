package net.etfbl.ip.urban_motion.repository;

import net.etfbl.ip.urban_motion.model.EScooter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EScooterRepository extends JpaRepository<EScooter, String> {
    List<EScooter> findByManufacturerId(Long manufacturerId);
    List<EScooter> findByVehicleState(String vehicleState);
    List<EScooter> findByModel(String model);
    List<EScooter> findByMaxSpeedGreaterThan(Integer minSpeed);
    List<EScooter> findByMaxSpeedLessThan(Integer maxSpeed);
    List<EScooter> findByMaxSpeedBetween(Integer minSpeed, Integer maxSpeed);
}
