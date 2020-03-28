package com.bridgelabz.service.Test;

import com.bridgelabz.dao.IndianCensusDAO;
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
        int totalRecords = stateCensusAnalyser.loadStateCensusData(SIMPLE_CSV_PATH);
        Assert.assertEquals(29, totalRecords);
    }

    @Test
    public void givenStateCensusCsvFile_WhenFileNameIncorrect_ShouldThrowCustomException() throws IOException {
        try {
            stateCensusAnalyser.loadStateCensusData(INCORRECT_CSV_PATH);
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
            stateCensusAnalyser.loadStateCensusData(INCORRECT_DELIMITER_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenHeaderIncorrect_ShouldThrowCustomException() throws IOException {
        try {
            stateCensusAnalyser.loadStateCensusData(INCORRECT_HEADER_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException, IOException {
        int totalRecords = stateCensusAnalyser.loadStateCodeData(CSV_STATE_CODE_PATH);
        Assert.assertEquals(37, totalRecords);
    }

    @Test
    public void givenStateCodeCsvFile_WhenFileNameIncorrect_ShouldThrowCustomException() throws IOException {
        try {
            stateCensusAnalyser.loadStateCodeData(INCORRECT_CSV_STATE_CODE_PATH);
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
            stateCensusAnalyser.loadStateCodeData(INCORRECT_IN_DELIMITER_CSV_STATE_CODE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenHeaderIncorrect_ShouldThrowCustomException() throws IOException {
        try {
            stateCensusAnalyser.loadStateCodeData(INCORRECT_IN_HEADER_CSV_STATE_CODE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnState_ShouldReturnSortedList() throws IOException {
        try {
            stateCensusAnalyser.loadStateCensusData(SIMPLE_CSV_PATH);
            // censusAnalyser.loadStateCensusData(SIMPLE_CSV_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedCensusData();
            IndianCensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
            Assert.assertEquals("West Bengal", censusCSV[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnState_ShouldReturnSortedList1() throws IOException {
        try {
            stateCensusAnalyser.loadStateCensusData(SIMPLE_CSV_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedCensusData();
            IndianCensusDAO[] indianCensusDAOS = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
            Assert.assertEquals("Madhya Pradesh", indianCensusDAOS[13].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedImproperlyOnState_ShouldNotReturnSortedList() throws IOException {
        try {
            stateCensusAnalyser.loadStateCensusData(SIMPLE_CSV_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedCensusData();
            IndianCensusDAO[] indianCensusDAOS = new Gson().fromJson(sortedCensusData, IndianCensusDAO[].class);
            Assert.assertNotEquals("Maharashta", indianCensusDAOS[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenSortedOnStateCode_ShouldReturnSortedList() throws CSVBuilderException, IOException {
        stateCensusAnalyser.loadStateCodeData(CSV_STATE_CODE_PATH);
        String sortedStateCodeData = stateCensusAnalyser.getStateWiseSortedCodeData();
        IndianCensusDAO[] indianCensusDAOS = new Gson().fromJson(sortedStateCodeData, IndianCensusDAO[].class);
        Assert.assertEquals("AD", indianCensusDAOS[0].stateCode);
        Assert.assertEquals("WB", indianCensusDAOS[36].stateCode);
    }
}