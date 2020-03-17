package com.bridgelabz.model;

import com.opencsv.bean.CsvBindByName;

//Binding the column name in CsvBindByName Class
public class CSVStateCensus {
    @CsvBindByName(column = "State")
    public String State;
    @CsvBindByName(column = "Population")
    public String Population;
    @CsvBindByName(column = "AreaInSqKm")
    public String AreaInSqKm;
    @CsvBindByName(column = "DensityPerSqKm")
    public String DensityPerSqKm;

    //Getter and setter method to encapsulate
    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPopulation() {
        return Population;
    }

    public void setPopulation(String population) {
        Population = population;
    }

    public String getAreaInSqKm() {
        return AreaInSqKm;
    }

    public void setAreaInSqKm(String areaInSqKm) {
        AreaInSqKm = areaInSqKm;
    }

    public String getDensityPerSqKm() {
        return DensityPerSqKm;
    }

    public void setDensityPerSqKm(String densityPerSqKm) {
        DensityPerSqKm = densityPerSqKm;
    }
}

