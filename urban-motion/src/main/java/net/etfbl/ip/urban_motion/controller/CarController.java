package net.etfbl.ip.urban_motion.controller;

import net.etfbl.ip.urban_motion.model.Car;
import net.etfbl.ip.urban_motion.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable String id) {
        Optional<Car> car = carService.getCarById(id);
        return car.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return carService.saveCar(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable String id,
                                         @RequestBody Car carDetails) {
        try {
            Car updatedCar = carService.updateCar(id, carDetails);
            return ResponseEntity.ok(updatedCar);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable String id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/manufacturer/{manufacturerId}")
    public List<Car> getCarsByManufacturer(@PathVariable Long manufacturerId) {
        return carService.getCarsByManufacturerId(manufacturerId);
    }

    @GetMapping("/state/{vehicleState}")
    public List<Car> getCarsByState(@PathVariable String vehicleState) {
        return carService.getCarsByVehicleState(vehicleState);
    }
}