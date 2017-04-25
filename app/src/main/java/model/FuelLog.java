package model;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Fuel Log Model Class!
 */

public class FuelLog extends RealmObject {


    private int id;
    private int carId;
    private Date date;
    private double fuelUnits;
    private double fuelPrice;
    private double odometerValue;
    private String additionalInformation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getFuelUnits() {
        return fuelUnits;
    }

    public void setFuelUnits(double fuelUnits) {
        this.fuelUnits = fuelUnits;
    }

    public double getFuelPrice() {
        return fuelPrice;
    }

    public void setFuelPrice(double fuelPrice) {
        this.fuelPrice = fuelPrice;
    }

    public double getOdometerValue() {
        return odometerValue;
    }

    public void setOdometerValue(double odometerValue) {
        this.odometerValue = odometerValue;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

}
