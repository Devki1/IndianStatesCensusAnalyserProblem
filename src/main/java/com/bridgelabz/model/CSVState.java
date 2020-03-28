package com.bridgelabz.model;

import com.opencsv.bean.CsvBindByName;

public class CSVState extends CSVStateCensus {
    //Binding the column name in CsvBindByName Class
    @CsvBindByName(column = "SrNo")
    public Integer SrNo;
    @CsvBindByName(column = "StateName")
    public String StateName;
    @CsvBindByName(column = "TIN")
    public Integer TIN;
    @CsvBindByName(column = "StateCode")
    public String StateCode;

    //Getter and setter method to encapsulate
    public Integer getSrNo() {
        return SrNo;
    }

    public void setSrNo(Integer srNo) {
        SrNo = srNo;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public Integer getTIN() {
        return TIN;
    }

    public void setTIN(Integer TIN) {
        this.TIN = TIN;
    }

    public String getStateCode() {
        return StateCode;
    }

    public void setStateCode(String stateCode) {
        StateCode = stateCode;
    }
}
