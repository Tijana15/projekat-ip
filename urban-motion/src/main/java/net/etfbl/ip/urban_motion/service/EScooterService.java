package net.etfbl.ip.urban_motion.service;

import net.etfbl.ip.urban_motion.model.EScooter;
import net.etfbl.ip.urban_motion.repository.EScooterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EScooterService {

    @Autowired
    private EScooterRepository eScooterRepository;

    public List<EScooter> getAllEScooters() {
        return eScooterRepository.findAll();
    }

    public Optional<EScooter> getEScooterById(String id) {
        return eScooterRepository.findById(id);
    }

    public EScooter saveEScooter(EScooter eScooter) {
        return eScooterRepository.save(eScooter);
    }

    public void deleteEScooter(String id) {
        eScooterRepository.deleteById(id);
    }

    public EScooter updateEScooter(String id, EScooter eScooterDetails) {
        EScooter existingEScooter = eScooterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EScooter not found with id: " + id));

        if (eScooterDetails.getManufacturer() != null) {
            existingEScooter.setManufacturer(eScooterDetails.getManufacturer());
        }
        if (eScooterDetails.getModel() != null) {
            existingEScooter.setModel(eScooterDetails.getModel());
        }
        if (eScooterDetails.getPurchasePrice() != null) {
            existingEScooter.setPurchasePrice(eScooterDetails.getPurchasePrice());
        }
        if (eScooterDetails.getVehicleState() != null) {
            existingEScooter.setVehicleState(eScooterDetails.getVehicleState());
        }
        if (eScooterDetails.getPicture() != null) {
            existingEScooter.setPicture(eScooterDetails.getPicture());
        }
        if (eScooterDetails.getMaxSpeed() != null) {
            existingEScooter.setMaxSpeed(eScooterDetails.getMaxSpeed());
        }
        if (eScooterDetails.getMapX() != null) {
            existingEScooter.setMapX(eScooterDetails.getMapX());
        }
        if (eScooterDetails.getMapY() != null) {
            existingEScooter.setMapY(eScooterDetails.getMapY());
        }

        return eScooterRepository.save(existingEScooter);
    }

    public List<EScooter> getEScootersByManufacturerId(Long manufacturerId) {
        return eScooterRepository.findByManufacturerId(manufacturerId);
    }

    public List<EScooter> getEScootersByVehicleState(String vehicleState) {
        return eScooterRepository.findByVehicleState(vehicleState);
    }

    public List<EScooter> getEScootersByMaxSpeedGreaterThan(Integer minSpeed) {
        return eScooterRepository.findByMaxSpeedGreaterThan(minSpeed);
    }

    public List<EScooter> getEScootersByMaxSpeedBetween(Integer minSpeed, Integer maxSpeed) {
        return eScooterRepository.findByMaxSpeedBetween(minSpeed, maxSpeed);
    }

    public List<EScooter> getEScootersByMaxSpeedLessThan(Integer maxSpeed) {
        return eScooterRepository.findByMaxSpeedLessThan(maxSpeed);
    }
}