package net.etfbl.ip.urban_motion.service;

import net.etfbl.ip.urban_motion.model.Car;
import net.etfbl.ip.urban_motion.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarById(String id) {
        return carRepository.findById(id);
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public void deleteCar(String id) {
        carRepository.deleteById(id);
    }


    public Car updateCar(String id, Car carDetails) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));

        if (carDetails.getManufacturer() != null) {
            existingCar.setManufacturer(carDetails.getManufacturer());
        }
        if (carDetails.getModel() != null) {
            existingCar.setModel(carDetails.getModel());
        }
        if (carDetails.getPurchasePrice() != null) {
            existingCar.setPurchasePrice(carDetails.getPurchasePrice());
        }
        if (carDetails.getVehicleState() != null) {
            existingCar.setVehicleState(carDetails.getVehicleState());
        }
        if (carDetails.getPicture() != null) {
            existingCar.setPicture(carDetails.getPicture());
        }
        if (carDetails.getPurchaseDate() != null) {
            existingCar.setPurchaseDate(carDetails.getPurchaseDate());
        }
        if (carDetails.getDescription() != null) {
            existingCar.setDescription(carDetails.getDescription());
        }
        if (carDetails.getMapX() != null) {
            existingCar.setMapX(carDetails.getMapX());
        }
        if (carDetails.getMapY() != null) {
            existingCar.setMapY(carDetails.getMapY());
        }
        return carRepository.save(existingCar);
    }

    public List<Car> getCarsByManufacturerId(Long manufacturerId) {
        return carRepository.findByManufacturerId(manufacturerId);
    }

    public List<Car> getCarsByVehicleState(String vehicleState) {
        return carRepository.findByVehicleState(vehicleState);
    }
}