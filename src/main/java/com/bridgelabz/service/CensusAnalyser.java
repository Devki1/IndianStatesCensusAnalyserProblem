package com.bridgelabz.service;

import com.bridgelabz.adaptor.CensusAdapter;
import com.bridgelabz.adaptor.CensusAdapterFactory;
import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.exception.CSVBuilderException;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

public class CensusAnalyser {
    HashMap<String, CensusDAO> censusDAOHashMap = new HashMap<String, CensusDAO>();

    public enum COUNTRY {INDIA, US}

    public enum SortingMode {AREA, STATE, STATECODE, DENSITY, POPULATION}

    private COUNTRY country;

    public CensusAnalyser(COUNTRY country) {
        this.country = country;
    }

    //Generic method load csv file data
    public int loadCensusData(String... csvFilePath) throws CSVBuilderException {
        CensusAdapter censusDataLoader = CensusAdapterFactory.getCensusData(country);
        censusDAOHashMap = censusDataLoader.loadCensusData(csvFilePath);
        return censusDAOHashMap.size();
    }

    //Read file extension
    public void getFileExtension(File file) throws CSVBuilderException {
        String extension = "";
        if (file != null) {
            String name = file.getName();
            extension = name.substring(name.lastIndexOf("."));
            if (!extension.equals(".csv")) {
                throw new CSVBuilderException
                        (CSVBuilderException.Exceptiontype.ENTERED_WRONG_FILE_TYPE, "FILE_TYPE_INCORRECT");
            }
        }
    }

    //Sorting in a JSON formats to StateCensus data
    public String getSortedCensusData(SortingMode mode) throws CSVBuilderException {
        if (censusDAOHashMap == null || censusDAOHashMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.NO_CENSUS_DATA, "No data found");
        ArrayList censusDTO = censusDAOHashMap.values().stream()
                .sorted(CensusDAO.getSortComparator(mode))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        return new Gson().toJson(censusDTO);
    }

    //Sorting both population and density
    public String getDualSortByPopulationDensity() throws CSVBuilderException {
        if (censusDAOHashMap == null || censusDAOHashMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.NO_CENSUS_DATA, "No Census Data");
        ArrayList censusDTO = censusDAOHashMap.values().stream()
                .sorted(Comparator.comparing(CensusDAO::getPopulation)
                        .thenComparingDouble(CensusDAO::getPopulationDensity).reversed())
                .map(c -> c.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        return new Gson().toJson(censusDTO);
    }
}