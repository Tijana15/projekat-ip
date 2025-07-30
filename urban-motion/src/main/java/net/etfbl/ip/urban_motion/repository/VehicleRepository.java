package net.etfbl.ip.urban_motion.repository;

import net.etfbl.ip.urban_motion.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
}
