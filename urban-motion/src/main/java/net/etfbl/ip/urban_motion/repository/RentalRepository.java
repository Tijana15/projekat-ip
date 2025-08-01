package net.etfbl.ip.urban_motion.repository;

import net.etfbl.ip.urban_motion.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findAllByVehicleId(String vehicleId);
    void deleteByVehicleId(String vehicleId);
}
