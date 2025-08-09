package net.etfbl.ip.urban_motion.repository;

import net.etfbl.ip.urban_motion.model.Rental;
import net.etfbl.ip.urban_motion.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findAllByVehicleId(String vehicleId);
    void deleteByVehicleId(String vehicleId);

    List<Rental> findByDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<Rental> findByVehicleIn(List<? extends Vehicle> vehicles);
}
