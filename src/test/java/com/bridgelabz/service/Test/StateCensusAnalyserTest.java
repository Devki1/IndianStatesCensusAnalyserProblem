package com.bridgelabz.service.Test;

import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.model.CSVState;
import com.bridgelabz.model.CSVStateCensus;
import com.bridgelabz.service.StateCensus;
import com.bridgelabz.service.StateCensusAnalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class StateCensusAnalyserTest {
    StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
    //CSV Census file path
    private final String SIMPLE_CSV_PATH = "src/test/resources/StateCensusData.csv";
    private final String INCORRECT_CSV_PATH = "\"src/testes/resources/StateCensusData.csv";
    private final String INCORRECT_CSV_TYPE_PATH = "src/test/resources/StateCensusData (copy).pdf";
    private final String INCORRECT_DELIMITER_PATH = "src/test/resources/StateCensusData1.csv";
    private final String INCORRECT_HEADER_PATH = "src/test/resources/StateCensusData2.csv";
    //CSV State code file path
    private final String CSV_STATE_CODE_PATH = "src/test/resources/StateCode.csv";
    private final String INCORRECT_CSV_STATE_CODE_PATH = "src/tested/resources/StateCode1.csv";
    private final String INCORRECT_EXTENSION_CSV_STATE_CODE = "src/test/resources/StateCode.pdf";
    private final String INCORRECT_IN_DELIMITER_CSV_STATE_CODE_PATH = "src/test/resources/StateCodeDataCopy1.csv";
    private final String INCORRECT_IN_HEADER_CSV_STATE_CODE_PATH = "src/test/resources/StateCodeDataCopy2.csv";

    @Test
    public void givenStateCensusCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException, IOException {
        int totalRecords = stateCensusAnalyser.loadCsvData(SIMPLE_CSV_PATH,CSVStateCensus.class);
        Assert.assertEquals(29, totalRecords);
    }

    @Test
    public void givenStateCensusCsvFile_WhenFileNameIncorrect_ShouldThrowCustomException() throws IOException {
        try {
            stateCensusAnalyser.loadCsvData(INCORRECT_CSV_PATH,CSVStateCensus.class);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.Exceptiontype.ENTERED_WRONG_FILE, e.type);
        }
    }

    @Test

    public void givenStateCensusCsvFile_WhenTypeIncorrect_ShouldThrowCustomException() {
        try {
            File fileExtension = new File(INCORRECT_CSV_TYPE_PATH);
            stateCensusAnalyser.getFileExtension(fileExtension);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.Exceptiontype.ENTERED_WRONG_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenDelimiterIncorrect_ShouldThrowCustomException() throws IOException {
        try {
            stateCensusAnalyser.loadCsvData(INCORRECT_DELIMITER_PATH,CSVStateCensus.class);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenHeaderIncorrect_ShouldThrowCustomException() throws IOException {
        try {
            stateCensusAnalyser.loadCsvData(INCORRECT_HEADER_PATH,CSVStateCensus.class);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException, IOException {
        int totalRecords = stateCensusAnalyser.loadCsvData(CSV_STATE_CODE_PATH,CSVState.class);
        Assert.assertEquals(37, totalRecords);
    }

    @Test
    public void givenStateCodeCsvFile_WhenFileNameIncorrect_ShouldThrowCustomException() throws IOException {
        try {
            stateCensusAnalyser.loadCsvData(INCORRECT_CSV_STATE_CODE_PATH,CSVState.class);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.Exceptiontype.ENTERED_WRONG_FILE, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenTypeIncorrect_ShouldThrowCustomException() {
        try {
            File fileExtension = new File(INCORRECT_EXTENSION_CSV_STATE_CODE);
            stateCensusAnalyser.getFileExtension(fileExtension);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.Exceptiontype.ENTERED_WRONG_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenStateCode_WhenDelimiterIncorrect_ShouldThrowCustomException() throws IOException {
        try {
            stateCensusAnalyser.loadCsvData(INCORRECT_IN_DELIMITER_CSV_STATE_CODE_PATH,CSVState.class);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenHeaderIncorrect_ShouldThrowCustomException() throws IOException {
        try {
            stateCensusAnalyser.loadCsvData(INCORRECT_IN_HEADER_CSV_STATE_CODE_PATH,CSVState.class);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnState_ShouldReturnSortedList() throws IOException {
        try {
            stateCensusAnalyser.loadCsvData(SIMPLE_CSV_PATH,CSVStateCensus.class);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedCensusData();
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].getState());
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnState_ShouldReturnSortedList1() throws IOException {
        try {
            stateCensusAnalyser.loadCsvData(SIMPLE_CSV_PATH,CSVStateCensus.class);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedCensusData();
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Madhya Pradesh", censusCSV[13].getState());
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedImproperlyOnState_ShouldNotReturnSortedList() throws IOException {
        try {
            stateCensusAnalyser.loadCsvData(SIMPLE_CSV_PATH,CSVStateCensus.class);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedCensusData();
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertNotEquals("Maharashta", censusCSV[0].getState());
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenSortedOnStateCode_ShouldReturnSortedList() throws CSVBuilderException, IOException {
        stateCensusAnalyser.loadCsvData(CSV_STATE_CODE_PATH,CSVState.class);
        String sortedStateCodeData = stateCensusAnalyser.getStateWiseSortedCodeData();
        CSVState[] stateCodes = new Gson().fromJson(sortedStateCodeData, CSVState[].class);
        Assert.assertEquals("AD", stateCodes[0].getStateCode());
        Assert.assertEquals("WB", stateCodes[36].getStateCode());
    }
}