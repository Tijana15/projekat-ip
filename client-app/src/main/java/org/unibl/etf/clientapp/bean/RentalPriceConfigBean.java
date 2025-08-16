package org.unibl.etf.clientapp.bean;

import org.unibl.etf.clientapp.dao.RentalPriceConfigDAO;

import java.io.Serializable;

public class RentalPriceConfigBean implements Serializable {
    public double getCarRentalPrice() {
        return RentalPriceConfigDAO.getPriceForCar();
    }

    public double getEScooterRentalPrice() {
        return RentalPriceConfigDAO.getPriceForEScooter();
    }

    public double getEBikeRentalPrice() {
        return RentalPriceConfigDAO.getPriceForEBike();
    }
}
