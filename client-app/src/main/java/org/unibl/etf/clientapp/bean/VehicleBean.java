package org.unibl.etf.clientapp.bean;

import org.unibl.etf.clientapp.dao.CarDAO;
import org.unibl.etf.clientapp.dao.EBikeDAO;
import org.unibl.etf.clientapp.dao.EScooterDAO;
import org.unibl.etf.clientapp.dto.Car;
import org.unibl.etf.clientapp.dto.EBike;
import org.unibl.etf.clientapp.dto.EScooter;

import java.util.List;

public class VehicleBean {
    public List<Car> getAvailableCars() {
        return CarDAO.getAllAvailableCars();
    }

    public List<EBike> getAvailableEBikes() {
        return EBikeDAO.getAllAvailableEBikes();
    }

    public List<EScooter> getAvailableEScooters() {
        return EScooterDAO.getAllAvailableEScooters();
    }
}
