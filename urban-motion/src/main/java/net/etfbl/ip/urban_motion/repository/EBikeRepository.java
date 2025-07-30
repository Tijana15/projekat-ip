package net.etfbl.ip.urban_motion.repository;

import net.etfbl.ip.urban_motion.model.EBike;
import net.etfbl.ip.urban_motion.model.VehicleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EBikeRepository extends JpaRepository<EBike, String> {
    List<EBike> findByManufacturerId(Long manufacturerId);
    List<EBike> findByVehicleState(String vehicleState);
    List<EBike> findByModel(String model);
    List<EBike> findByMaxRangeGreaterThan(Integer minRange);
    List<EBike> findByMaxRangeBetween(Integer minRange, Integer maxRange);
    List<EBike> findByMaxRangeLessThan(Integer maxRange);
}
