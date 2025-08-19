package net.etfbl.ip.urban_motion.service;

import net.etfbl.ip.urban_motion.model.EBike;
import net.etfbl.ip.urban_motion.repository.EBikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EBikeService {

    @Autowired
    private EBikeRepository eBikeRepository;

    public List<EBike> getAllEBikes() {
        return eBikeRepository.findAll();
    }

    public Optional<EBike> getEBikeById(String id) {
        return eBikeRepository.findById(id);
    }

    public EBike saveEBike(EBike eBike) {
        return eBikeRepository.save(eBike);
    }

    public void deleteEBike(String id) {
        eBikeRepository.deleteById(id);
    }

    public EBike updateEBike(String id, EBike eBikeDetails) {
        EBike existingEBike = eBikeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EBike not found with id: " + id));

        if (eBikeDetails.getManufacturer() != null) {
            existingEBike.setManufacturer(eBikeDetails.getManufacturer());
        }
        if (eBikeDetails.getModel() != null) {
            existingEBike.setModel(eBikeDetails.getModel());
        }
        if (eBikeDetails.getPurchasePrice() != null) {
            existingEBike.setPurchasePrice(eBikeDetails.getPurchasePrice());
        }
        if (eBikeDetails.getVehicleState() != null) {
            existingEBike.setVehicleState(eBikeDetails.getVehicleState());
        }
        if (eBikeDetails.getPicture() != null) {
            existingEBike.setPicture(eBikeDetails.getPicture());
        }
        if (eBikeDetails.getMaxRange() != null) {
            existingEBike.setMaxRange(eBikeDetails.getMaxRange());
        }
        if (eBikeDetails.getMapX() != null) {
            existingEBike.setMapX(eBikeDetails.getMapX());
        }
        if (eBikeDetails.getMapY() != null) {
            existingEBike.setMapY(eBikeDetails.getMapY());
        }

        return eBikeRepository.save(existingEBike);
    }

    public List<EBike> getEBikesByManufacturerId(Long manufacturerId) {
        return eBikeRepository.findByManufacturerId(manufacturerId);
    }

    public List<EBike> getEBikesByVehicleState(String vehicleState) {
        return eBikeRepository.findByVehicleState(vehicleState);
    }

    public List<EBike> getEBikesByMaxRangeGreaterThan(Integer minRange) {
        return eBikeRepository.findByMaxRangeGreaterThan(minRange);
    }

    public List<EBike> getEBikesByMaxRangeBetween(Integer minRange, Integer maxRange) {
        return eBikeRepository.findByMaxRangeBetween(minRange, maxRange);
    }
}