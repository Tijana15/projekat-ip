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
        EScooter eScooter = eScooterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EScooter not found with id: " + id));

        eScooter.setManufacturer(eScooterDetails.getManufacturer());
        eScooter.setModel(eScooterDetails.getModel());
        eScooter.setPurchasePrice(eScooterDetails.getPurchasePrice());
        eScooter.setVehicleState(eScooterDetails.getVehicleState());
        eScooter.setPicture(eScooterDetails.getPicture());

        eScooter.setMaxSpeed(eScooterDetails.getMaxSpeed());

        return eScooterRepository.save(eScooter);
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