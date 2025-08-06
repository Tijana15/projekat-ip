package net.etfbl.ip.urban_motion.service;

import net.etfbl.ip.urban_motion.dto.RentalDTO;
import net.etfbl.ip.urban_motion.dto.RentalSimpleDTO;
import net.etfbl.ip.urban_motion.model.Client;
import net.etfbl.ip.urban_motion.model.Rental;
import net.etfbl.ip.urban_motion.model.Vehicle;
import net.etfbl.ip.urban_motion.repository.ClientRepository;
import net.etfbl.ip.urban_motion.repository.RentalRepository;
import net.etfbl.ip.urban_motion.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final ClientRepository clientRepository;
    private final VehicleRepository vehicleRepository;

    public RentalService(RentalRepository rentalRepository, ClientRepository clientRepository, VehicleRepository vehicleRepository) {
        this.rentalRepository = rentalRepository;
        this.clientRepository = clientRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<RentalSimpleDTO> findAll() {
        return rentalRepository.findAll().stream().map(RentalSimpleDTO::new).toList();
    }

    public List<RentalSimpleDTO> findAllByVehicleId(String vehicleId) {
        return rentalRepository.findAllByVehicleId(vehicleId).stream().map(RentalSimpleDTO::new).toList();
    }

    public RentalDTO add(RentalDTO rentalDTO) {
        Client client = clientRepository.findById(rentalDTO.getClientId()).orElse(null);
        Vehicle vehicle = vehicleRepository.findById(rentalDTO.getVehicleId()).orElse(null);
        if (client == null || vehicle == null) {
            return null;
        }
        Rental rental = new Rental(rentalDTO.getDateTime(), rentalDTO.getStartX(), rentalDTO.getStartX(), rentalDTO.getEndX(), rentalDTO.getEndY(), rentalDTO.getDurationSeconds(), rentalDTO.getActive(), rentalDTO.getPrice(), client, vehicle);
        return new RentalDTO(rentalRepository.save(rental));
    }

}
