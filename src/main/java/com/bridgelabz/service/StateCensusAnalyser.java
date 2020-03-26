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

    //Reading and printing data from csv file
    public int loadCensusCsvData(String SAMPLE_CSV_PATH) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_PATH));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
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
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
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

    //Sorting in a  JSON formats
    public String getStateWiseSortedCensusData() throws CSVBuilderException {
        if (stateCensusRecords == null || stateCensusRecords.size() == 0)
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        Comparator<CSVStateCensus> censusCSVComparator = Comparator.comparing(csvStateCensus -> csvStateCensus.getState());
        this.sort(censusCSVComparator);
        String sortedStateCensusJson = new Gson().toJson(stateCensusRecords);
        return sortedStateCensusJson;
    }

    //Sorting CSV State  census data
    public void sort(Comparator<CSVStateCensus> censusCSVComparator) {
        for (int iterate = 0; iterate < stateCensusRecords.size() - 1; iterate++) {
            for (int Inneriterate = 0; Inneriterate < stateCensusRecords.size() - iterate - 1; Inneriterate++) {
                CSVStateCensus census1 = stateCensusRecords.get(Inneriterate);
                CSVStateCensus census2 = stateCensusRecords.get(Inneriterate + 1);
                if (censusCSVComparator.compare(census1, census2) > 0) {
                    stateCensusRecords.set(Inneriterate, census2);
                    stateCensusRecords.set(Inneriterate + 1, census1);
                }
            }
        }
    }
}