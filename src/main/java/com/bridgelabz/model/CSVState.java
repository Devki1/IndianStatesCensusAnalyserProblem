package com.bridgelabz.model;

import com.opencsv.bean.CsvBindByName;

public class CSVState {
    //Binding the column name in CsvBindByName Class
    @CsvBindByName(column = "SrNo")
    public String SrNo;
    @CsvBindByName(column = "StateName")
    public String StateName;
    @CsvBindByName(column = "TIN")
    public String TIN;
    @CsvBindByName(column = "StateCode")
    public String StateCode;

    //Getter and setter method to encapsulate
    public String getSrNo() {
        return SrNo;
    }

    public void setSrNo(String srNo) {
        SrNo = srNo;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public String getStateCode() {
        return StateCode;
    }

    public void setStateCode(String stateCode) {
        StateCode = stateCode;
    }
}
