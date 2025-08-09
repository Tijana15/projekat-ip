package net.etfbl.ip.urban_motion.service;

import net.etfbl.ip.urban_motion.dto.DailyRevenueDTO;
import net.etfbl.ip.urban_motion.dto.VehicleBreakdownDTO;
import net.etfbl.ip.urban_motion.dto.VehicleRevenueDTO;
import net.etfbl.ip.urban_motion.model.Car;
import net.etfbl.ip.urban_motion.model.EBike;
import net.etfbl.ip.urban_motion.model.EScooter;
import net.etfbl.ip.urban_motion.model.Rental;
import net.etfbl.ip.urban_motion.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private final RentalRepository rentalRepository;
    private final BreakdownRepository breakdownRepository;
    private final CarRepository carRepository;
    private final EBikeRepository eBikeRepository;
    private final EScooterRepository eScooterRepository;


    public StatisticsService(RentalRepository rentalRepository, BreakdownRepository breakdownRepository, CarRepository carRepository, EBikeRepository eBikeRepository, EScooterRepository eScooterRepository) {
        this.rentalRepository = rentalRepository;
        this.breakdownRepository = breakdownRepository;
        this.carRepository = carRepository;
        this.eBikeRepository = eBikeRepository;
        this.eScooterRepository = eScooterRepository;
    }

    public List<DailyRevenueDTO> getDailyRevenueForMonth(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        List<Rental> rentals = rentalRepository.findByDateTimeBetween(startDateTime, endDateTime);

        Map<LocalDate, Double> dailyRevenueMap = rentals.stream()
                .filter(rental -> rental.getPrice() != null)
                .collect(Collectors.groupingBy(
                        rental -> rental.getDateTime().toLocalDate(),
                        Collectors.summingDouble(Rental::getPrice)
                ));

        List<DailyRevenueDTO> result = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            Double revenue = dailyRevenueMap.getOrDefault(date, 0.0);
            result.add(new DailyRevenueDTO(date.toString(), revenue));
        }

        return result;
    }

    public List<VehicleBreakdownDTO> getBreakdownsByVehicleType() {
        List<VehicleBreakdownDTO> result = new ArrayList<>();

        // Kvarovi automobila
        List<Car> cars = carRepository.findAll();
        long carBreakdowns = breakdownRepository.findByVehicleIn(cars).size();
        result.add(new VehicleBreakdownDTO("CAR", carBreakdowns));

        // Kvarovi e-bicikala
        List<EBike> eBikes = eBikeRepository.findAll();
        long eBikeBreakdowns = breakdownRepository.findByVehicleIn(eBikes).size();
        result.add(new VehicleBreakdownDTO("EBIKE", eBikeBreakdowns));

        // Kvarovi e-trotineta
        List<EScooter> eScooters = eScooterRepository.findAll();
        long eScooterBreakdowns = breakdownRepository.findByVehicleIn(eScooters).size();
        result.add(new VehicleBreakdownDTO("ESCOOTER", eScooterBreakdowns));

        return result;
    }

    public List<VehicleRevenueDTO> getRevenueByVehicleType() {
        List<VehicleRevenueDTO> result = new ArrayList<>();

        // Prihod od automobila
        List<Car> cars = carRepository.findAll();
        List<Rental> carRentals = rentalRepository.findByVehicleIn(cars);
        double carRevenue = carRentals.stream()
                .filter(rental -> rental.getPrice() != null)
                .mapToDouble(Rental::getPrice)
                .sum();
        result.add(new VehicleRevenueDTO("CAR", carRevenue));

        List<EBike> eBikes = eBikeRepository.findAll();
        List<Rental> eBikeRentals = rentalRepository.findByVehicleIn(eBikes);
        double eBikeRevenue = eBikeRentals.stream()
                .filter(rental -> rental.getPrice() != null)
                .mapToDouble(Rental::getPrice)
                .sum();
        result.add(new VehicleRevenueDTO("EBIKE", eBikeRevenue));

        List<EScooter> eScooters = eScooterRepository.findAll();
        List<Rental> eScooterRentals = rentalRepository.findByVehicleIn(eScooters);
        double eScooterRevenue = eScooterRentals.stream()
                .filter(rental -> rental.getPrice() != null)
                .mapToDouble(Rental::getPrice)
                .sum();
        result.add(new VehicleRevenueDTO("ESCOOTER", eScooterRevenue));

        return result;
    }
}