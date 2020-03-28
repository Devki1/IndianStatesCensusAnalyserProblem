package com.bridgelabz.dao;

import com.bridgelabz.model.CSVState;
import com.bridgelabz.model.CSVStateCensus;

public class IndianCensusDAO {
    public String state;
    public Double areaInSqKm;
    public Double densityPerSqKm;
    public Double population;
    public String stateName;
    public String stateCode;
    public Integer srNo;
    public Integer tin;

    public IndianCensusDAO(CSVStateCensus csvStateCensus) {
        state = csvStateCensus.getState();
        areaInSqKm = csvStateCensus.getAreaInSqKm();
        densityPerSqKm = csvStateCensus.getDensityPerSqKm();
        population = csvStateCensus.getPopulation();
    }

    public IndianCensusDAO(CSVState csvStateCode) {
        stateName = csvStateCode.getStateName();
        stateCode = csvStateCode.getStateCode();
        srNo = csvStateCode.getSrNo();
        tin = csvStateCode.getTIN();
    }
}
