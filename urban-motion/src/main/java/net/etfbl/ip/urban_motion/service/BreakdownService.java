package net.etfbl.ip.urban_motion.service;

import net.etfbl.ip.urban_motion.dto.BreakdownSimpleDTO;
import net.etfbl.ip.urban_motion.model.Breakdown;
import net.etfbl.ip.urban_motion.model.Vehicle;
import net.etfbl.ip.urban_motion.repository.BreakdownRepository;
import net.etfbl.ip.urban_motion.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BreakdownService {
    private final BreakdownRepository breakdownRepository;
    private final VehicleRepository vehicleRepository;

    public BreakdownService(BreakdownRepository breakdownRepository, VehicleRepository vehicleRepository) {
        this.breakdownRepository = breakdownRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<Breakdown> getAllBreakdowns() {
        return breakdownRepository.findAll();
    }

    public Optional<Breakdown> getBreakdownById(Long id) {
        return breakdownRepository.findById(id);
    }

    public List<Breakdown> getBreakdownsByVehicleId(String vehicleId) {
        return breakdownRepository.findAllByVehicleId(vehicleId);
    }

    public BreakdownSimpleDTO createBreakdown(BreakdownSimpleDTO breakdownSimpleDTO) {
        Vehicle vehicle = vehicleRepository.findById(breakdownSimpleDTO.getVehicleId()).orElse(null);
        if (vehicle == null) {
            return null;
        }
        Breakdown breakdown=breakdownRepository.save(new Breakdown(
                breakdownSimpleDTO.getBreakdownDateTime(),
                breakdownSimpleDTO.getDescription(),
                vehicle
        ));
        return new BreakdownSimpleDTO(breakdown);

    }

    public boolean deleteBreakdownById(Long id) {
        Optional<Breakdown> breakdown = breakdownRepository.findById(id);
        if (breakdown.isPresent()) {
            breakdownRepository.delete(breakdown.get());
            return true;
        }
        return false;
    }

}
