package com.bridgelabz.service;

import com.bridgelabz.dao.IndianCensusDAO;
import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.model.CSVState;
import com.bridgelabz.model.CSVStateCensus;
import com.bridgelabz.utility.CSVBuilderFactory;
import com.bridgelabz.utility.ICSVBuilder;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class StateCensusAnalyser {

    ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
    Collection<IndianCensusDAO> CensusRecords = null;
    HashMap<String, IndianCensusDAO> censusDAOMap = new HashMap<String, IndianCensusDAO>();

    //Reading State Census Data
    public int loadStateCensusData(String csvPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvPath));) {
            // ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCensus> csvFileIterator = csvBuilder.getCSVFileIterator(reader, CSVStateCensus.class);
            while (csvFileIterator.hasNext()) {
                IndianCensusDAO indianCensusDAO = new IndianCensusDAO(csvFileIterator.next());
                this.censusDAOMap.put(indianCensusDAO.state, indianCensusDAO);
            }
            return censusDAOMap.size();
        } catch (IOException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.Exceptiontype.ENTERED_WRONG_FILE, e.getMessage());
        } catch (RuntimeException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.getMessage());
        }
    }

    //Reading the state code data from csv file
    public int loadStateCodeData(String csvPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvPath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVState> csvFileIterator = csvBuilder.getCSVFileIterator(reader, CSVState.class);
            while (csvFileIterator.hasNext()) {
                IndianCensusDAO indianCensusDAO = new IndianCensusDAO(csvFileIterator.next());
                this.censusDAOMap.put(indianCensusDAO.stateCode, indianCensusDAO);
            }
            return censusDAOMap.size();
        } catch (IOException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.Exceptiontype.ENTERED_WRONG_FILE, e.getMessage());
        } catch (RuntimeException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.getMessage());
        }
    }

    //Read file extension
    public void getFileExtension(File file) throws CSVBuilderException {
        String extension = "";
        if (file != null) {
            String name = file.getName();
            extension = name.substring(name.lastIndexOf("."));
            if (!extension.equals(".csv")) {
                throw new CSVBuilderException(CSVBuilderException.Exceptiontype.ENTERED_WRONG_FILE_TYPE, "FILE_TYPE_INCORRECT");
            }
        }
    }

    //Sorting in a  JSON formats to StateCensusData
    public String getStateWiseSortedCensusData() throws CSVBuilderException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.NO_CENSUS_DATA, "Data empty");
        Comparator<Map.Entry<String, IndianCensusDAO>> stateCensusComparator =
                Comparator.comparing(census -> census.getValue().state);
        LinkedHashMap<String, IndianCensusDAO> sortedByValue = this.sort(stateCensusComparator);
        CensusRecords = sortedByValue.values();
        String sortedStateCensusJson = new Gson().toJson(CensusRecords);
        return sortedStateCensusJson;
    }

    //Sorting in a JSON formats to State Code Data
    public String getStateWiseSortedCodeData() throws CSVBuilderException {
        if (censusDAOMap == null || censusDAOMap.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.NO_CENSUS_DATA, "Data empty");
        Comparator<Map.Entry<String, IndianCensusDAO>> stateCodeCSVComparator = Comparator.comparing(csvState -> csvState.getValue().stateCode);
        LinkedHashMap<String, IndianCensusDAO> sortedByValue = this.sort(stateCodeCSVComparator);
        CensusRecords = sortedByValue.values();
        String sortedStateCodeJson = new Gson().toJson(CensusRecords);
        return sortedStateCodeJson;
    }

    //Sort method csv file for generic
    private <E extends IndianCensusDAO> LinkedHashMap<String, IndianCensusDAO> sort(Comparator censusComparator) {
        Set<Map.Entry<String, IndianCensusDAO>> entries = censusDAOMap.entrySet();
        List<Map.Entry<String, IndianCensusDAO>> listOfEntries =
                new ArrayList<Map.Entry<String, IndianCensusDAO>>(entries);
        Collections.sort(listOfEntries, censusComparator);
        LinkedHashMap<String, IndianCensusDAO> sortedByValue =
                new LinkedHashMap<String , IndianCensusDAO>(listOfEntries.size());
        // Coping list to map
        for (Map.Entry<String, IndianCensusDAO> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }
        return sortedByValue;
    }
}