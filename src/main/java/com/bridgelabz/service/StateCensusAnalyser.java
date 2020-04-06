package com.bridgelabz.service;

import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.utility.CensusLoader;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class StateCensusAnalyser {

    CensusLoader censusLoader = new CensusLoader();
    Collection<CensusDAO> censusRecords = null;
    HashMap<String, CensusDAO> censusDAOMap = new HashMap<String, CensusDAO>();

    public int loadCensusData(String... csvFilePath) throws CSVBuilderException {
        censusDAOMap = censusLoader.loadStateCensusData(censusDAOMap, csvFilePath);
        return censusDAOMap.size();
    }

    public int loadUSCensusData(String... csvFilePath) throws CSVBuilderException {
        censusDAOMap = censusLoader.loadUSCensusData(censusDAOMap, csvFilePath);
        return censusDAOMap.size();
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

    public String getStateWiseSortedCensusData() throws CSVBuilderException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.NO_CENSUS_DATA, "No data found");
        Comparator<Map.Entry<String, CensusDAO>> stateCensusComparator =
                Comparator.comparing(census -> census.getValue().state);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCensusComparator);
        censusRecords = sortedByValue.values();
        String sortedStateCensusJson = new Gson().toJson(censusRecords);
        return sortedStateCensusJson;
    }

    public String getStateCodeWiseSortedData() throws CSVBuilderException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.NO_CENSUS_DATA, "No data found");
        Comparator<Map.Entry<String, CensusDAO>> stateCodeCSVComparator =
                Comparator.comparing(stateCode -> stateCode.getValue().stateCode);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCodeCSVComparator);
        censusRecords = sortedByValue.values();
        String sortedStateCodeJson = new Gson().toJson(censusRecords);
        return sortedStateCodeJson;
    }

    public String getStateCensusPopulationWiseSortedData() throws CSVBuilderException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.NO_CENSUS_DATA, "No data found");
        Comparator<Map.Entry<String, CensusDAO>> stateCensusCSVComparator =
                Comparator.comparing(census -> census.getValue().population);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCensusCSVComparator);
        ArrayList<CensusDAO> sortedList = new ArrayList<CensusDAO>(sortedByValue.values());
        Collections.reverse(sortedList);
        String sortedStateCensusPopulationJson = new Gson().toJson(sortedList);
        return sortedStateCensusPopulationJson;
    }

    public String getStateCensusPopulationDensityWiseSortedData() throws CSVBuilderException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.NO_CENSUS_DATA, "No data found");
        Comparator<Map.Entry<String, CensusDAO>> stateCensusCSVComparator =
                Comparator.comparing(census -> census.getValue().densityPerSqKm);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCensusCSVComparator);
        ArrayList<CensusDAO> sortedList = new ArrayList<CensusDAO>(sortedByValue.values());
        Collections.reverse(sortedList);
        String sortedStateCensusPopulationJson = new Gson().toJson(sortedList);
        return sortedStateCensusPopulationJson;
    }

    public String getStateCensusLargestAreaWiseSortedData() throws CSVBuilderException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.NO_CENSUS_DATA, "No data found");
        Comparator<Map.Entry<String, CensusDAO>> stateCensusCSVComparator =
                Comparator.comparing(census -> census.getValue().areaInSqKm);
        LinkedHashMap<String, CensusDAO> sortedByValue = this.sort(stateCensusCSVComparator);
        ArrayList<CensusDAO> sortedList = new ArrayList<CensusDAO>(sortedByValue.values());
        Collections.reverse(sortedList);
        String sortedStateCensusPopulationJson = new Gson().toJson(sortedList);
        return sortedStateCensusPopulationJson;
    }

    private <E extends CensusDAO> LinkedHashMap<String, CensusDAO> sort(Comparator censusComparator) {
        Set<Map.Entry<String, CensusDAO>> entries = censusDAOMap.entrySet();
        List<Map.Entry<String, CensusDAO>> listOfEntries =
                new ArrayList<Map.Entry<String, CensusDAO>>(entries);
        Collections.sort(listOfEntries, censusComparator);
        LinkedHashMap<String, CensusDAO> sortedByValue =
                new LinkedHashMap<String, CensusDAO>(listOfEntries.size());
        for (Map.Entry<String, CensusDAO> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }
        return sortedByValue;
    }
}