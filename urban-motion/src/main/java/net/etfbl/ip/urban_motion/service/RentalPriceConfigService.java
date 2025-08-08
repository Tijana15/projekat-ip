package net.etfbl.ip.urban_motion.service;

import net.etfbl.ip.urban_motion.dto.UpdateRentalPriceDTO;
import net.etfbl.ip.urban_motion.model.RentalPriceConfig;
import net.etfbl.ip.urban_motion.model.VehicleType;
import net.etfbl.ip.urban_motion.repository.RentalPriceConfigRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentalPriceConfigService {
    private final RentalPriceConfigRepository rentalPriceConfigRepository;

    public RentalPriceConfigService(RentalPriceConfigRepository rentalPriceConfigRepository) {
        this.rentalPriceConfigRepository = rentalPriceConfigRepository;
    }

    public UpdateRentalPriceDTO getAll() {
        return new UpdateRentalPriceDTO(rentalPriceConfigRepository.findAll());
    }

    public List<RentalPriceConfig> update(UpdateRentalPriceDTO updateRentalPriceDTO) {
        List<RentalPriceConfig> updated = new ArrayList<>();
        if (updateRentalPriceDTO.getCarPrice() != null) {
            updated.add(rentalPriceConfigRepository.save(new RentalPriceConfig(VehicleType.CAR, updateRentalPriceDTO.getCarPrice())));
        }
        if (updateRentalPriceDTO.getEscooterPrice() != null) {
            updated.add(rentalPriceConfigRepository.save(new RentalPriceConfig(VehicleType.ESCOOTER, updateRentalPriceDTO.getEscooterPrice())));
        }
        if (updateRentalPriceDTO.getEbikePrice() != null) {
            updated.add(rentalPriceConfigRepository.save(new RentalPriceConfig(VehicleType.EBIKE, updateRentalPriceDTO.getEbikePrice())));
        }
        return updated;
    }
}
