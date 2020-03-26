package com.bridgelabz.service;

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
import java.util.Comparator;
import java.util.List;

public class StateCensusAnalyser {
    List<CSVStateCensus> stateCensusRecords = null;
    List<CSVState> stateCensusCodesRecord = null;
    ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();

    //Reading and printing data from csv file
    public int loadCensusCsvData(String SAMPLE_CSV_PATH) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_PATH));) {
            stateCensusRecords = csvBuilder.getCSVFileList(reader, CSVStateCensus.class);
            return stateCensusRecords.size();
        } catch (IOException e) {
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.ENTERED_WRONG_FILE, e.getMessage());
        } catch (RuntimeException e) {
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.getMessage());
        }
    }

    public int loadSateCodeCsvData(String CSV_PATH) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(CSV_PATH));) {
            // ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            stateCensusCodesRecord = csvBuilder.getCSVFileList(reader, CSVState.class);
            return stateCensusCodesRecord.size();
        } catch (IOException e) {
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.ENTERED_WRONG_FILE, e.getMessage());
        } catch (RuntimeException e) {
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.getMessage());
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
        if (stateCensusRecords == null || stateCensusRecords.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.NO_CENSUS_DATA, "Data empty");
        Comparator<CSVStateCensus> censusCSVComparator = Comparator.comparing(csvStateCensus -> csvStateCensus.getState());
        this.sort(censusCSVComparator, stateCensusCodesRecord);
        String sortedStateCensusJson = new Gson().toJson(stateCensusRecords);
        return sortedStateCensusJson;
    }

    //Sorting in a JSON formats to State Code Data
    public String getStateWiseSortedCodeData() throws CSVBuilderException {
        if (stateCensusCodesRecord == null || stateCensusCodesRecord.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.NO_CENSUS_DATA, "Data empty");
        Comparator<CSVState> stateCodeCSVCoparator = Comparator.comparing(csvState -> csvState.getStateCode());
        this.sort(stateCodeCSVCoparator, stateCensusCodesRecord);
        String sortedStateCodeJson = new Gson().toJson(stateCensusCodesRecord);
        return sortedStateCodeJson;
    }

    //Sort method csv file for generic
    private <E> void sort(Comparator<E> censusCSVComparator, List censusRecords) {
        for (int iterate = 0; iterate < censusRecords.size() - 1; iterate++) {
            for (int Inneriterate = 0; Inneriterate < censusRecords.size() - iterate - 1; Inneriterate++) {
                E census1 = (E) censusRecords.get(Inneriterate);
                E census2 = (E) censusRecords.get(Inneriterate + 1);
                if (censusCSVComparator.compare(census1, census2) > 0) {
                    censusRecords.set(Inneriterate, census2);
                    censusRecords.set(Inneriterate + 1, census1);
                }
            }
        }
    }
}