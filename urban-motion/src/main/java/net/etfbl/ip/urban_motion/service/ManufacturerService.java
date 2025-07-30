package net.etfbl.ip.urban_motion.service;

import net.etfbl.ip.urban_motion.model.Manufacturer;
import net.etfbl.ip.urban_motion.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    public Optional<Manufacturer> getManufacturerById(Long id) {
        return manufacturerRepository.findById(id);
    }

    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public void deleteManufacturer(Long id) {
        manufacturerRepository.deleteById(id);
    }

    public Manufacturer updateManufacturer(Long id, Manufacturer manufacturerDetails) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found with id: " + id));

        manufacturer.setName(manufacturerDetails.getName());
        manufacturer.setState(manufacturerDetails.getState());
        manufacturer.setAddress(manufacturerDetails.getAddress());
        manufacturer.setPhone(manufacturerDetails.getPhone());
        manufacturer.setFax(manufacturerDetails.getFax());
        manufacturer.setMail(manufacturerDetails.getMail());

        return manufacturerRepository.save(manufacturer);
    }
}