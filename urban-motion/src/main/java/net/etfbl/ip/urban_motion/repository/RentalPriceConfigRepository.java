package net.etfbl.ip.urban_motion.repository;

import net.etfbl.ip.urban_motion.model.RentalPriceConfig;
import net.etfbl.ip.urban_motion.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalPriceConfigRepository extends JpaRepository<RentalPriceConfig, VehicleType> {
}
