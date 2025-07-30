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
        EBike eBike = eBikeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EBike not found with id: " + id));

        // Update Vehicle fields
        eBike.setManufacturer(eBikeDetails.getManufacturer());
        eBike.setModel(eBikeDetails.getModel());
        eBike.setPurchasePrice(eBikeDetails.getPurchasePrice());
        eBike.setVehicleState(eBikeDetails.getVehicleState());
        eBike.setPicture(eBikeDetails.getPicture());

        // Update EBike-specific fields
        eBike.setMaxRange(eBikeDetails.getMaxRange());

        return eBikeRepository.save(eBike);
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