package com.bridgelabz.service;

import com.bridgelabz.adaptor.CensusAdapter;
import com.bridgelabz.adaptor.CensusAdapterFactory;
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
    Map<String, CensusDAO> censusDAOMap = new HashMap<String, CensusDAO>();


    public enum COUNTRY {INDIA, US}

    //Generic method load csv file data
    public int loadCensusData(COUNTRY country, String... csvFilePath) throws CSVBuilderException, IOException {
        CensusAdapter censusDataLoader = CensusAdapterFactory.getCensusData(country);
        censusDAOMap = censusDataLoader.loadCensusData(csvFilePath);
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

    //Sorting in a JSON format to StateCensus data
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

    //Sorting in a JSON format to StateCodeData
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

    //Sorting csv state census data population wise
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

    //Sorting csv state census data density wise
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

    //Sorting csv State Census data wise
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

    //Sorting csv file generic method
    private <E extends CensusDAO> LinkedHashMap<String, CensusDAO> sort(Comparator censusComparator) {
        Set<Map.Entry<String, CensusDAO>> entries = censusDAOMap.entrySet();
        List<Map.Entry<String, CensusDAO>> listOfEntries =
                new ArrayList<Map.Entry<String, CensusDAO>>(entries);
        Collections.sort(listOfEntries, censusComparator);
        LinkedHashMap<String, CensusDAO> sortedByValue =
                new LinkedHashMap<String, CensusDAO>(listOfEntries.size());
        // Coping list to map
        for (Map.Entry<String, CensusDAO> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }
        return sortedByValue;
    }
}