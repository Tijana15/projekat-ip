package net.etfbl.ip.urban_motion.repository;

import net.etfbl.ip.urban_motion.model.Breakdown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BreakdownRepository extends JpaRepository<Breakdown, Long> {
    List<Breakdown> findAllByVehicleId(String vehicleId);
    void deleteAllByVehicleId(String vehicleId);
}
