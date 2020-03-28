package com.bridgelabz.model;

import com.opencsv.bean.CsvBindByName;

//Binding the column name in CsvBindByName Class
public class CSVStateCensus {
    @CsvBindByName(column = "State")
    public String State;
    @CsvBindByName(column = "Population")
    public double Population;
    @CsvBindByName(column = "AreaInSqKm")
    public double AreaInSqKm;
    @CsvBindByName(column = "DensityPerSqKm")
    public double DensityPerSqKm;

    //Getter and setter method to encapsulate
    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public double getPopulation() {
        return Population;
    }

    public void setPopulation(double population) {
        Population = population;
    }

    public double getAreaInSqKm() {
        return AreaInSqKm;
    }

    public void setAreaInSqKm(double areaInSqKm) {
        AreaInSqKm = areaInSqKm;
    }

    public double getDensityPerSqKm() {
        return DensityPerSqKm;
    }

    public void setDensityPerSqKm(double densityPerSqKm) {
        DensityPerSqKm = densityPerSqKm;
    }
}
