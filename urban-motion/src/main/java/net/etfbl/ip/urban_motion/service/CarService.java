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
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));

        car.setManufacturer(carDetails.getManufacturer());
        car.setModel(carDetails.getModel());
        car.setPurchasePrice(carDetails.getPurchasePrice());
        car.setVehicleState(carDetails.getVehicleState());
        car.setPicture(carDetails.getPicture());

        car.setPurchaseDate(carDetails.getPurchaseDate());
        car.setDescription(carDetails.getDescription());

        return carRepository.save(car);
    }

    public List<Car> getCarsByManufacturerId(Long manufacturerId) {
        return carRepository.findByManufacturerId(manufacturerId);
    }

    public List<Car> getCarsByVehicleState(String vehicleState) {
        return carRepository.findByVehicleState(vehicleState);
    }
}