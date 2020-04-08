package com.bridgelabz.service.Test;

import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.service.StateCensusAnalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.bridgelabz.service.StateCensusAnalyser.COUNTRY.INDIA;
import static com.bridgelabz.service.StateCensusAnalyser.COUNTRY.US;

public class StateCensusAnalyserTest {
    StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
    //CSV Census file path
    private final String SIMPLE_CSV_PATH = "src/test/resources/StateCensusData.csv";
    private final String INCORRECT_CSV_PATH = "src/tested/resources/StateCensusData.csv";
    private final String INCORRECT_CSV_TYPE_PATH = "src/test/resources/StateCensusData (copy).pdf";
    private final String INCORRECT_DELIMITER_PATH = "src/test/resources/StateCensusData1.csv";
    private final String INCORRECT_HEADER_PATH = "src/test/resources/StateCensusData2.csv";
    //CSV State code file path
    private final String CSV_STATE_CODE_PATH = "src/test/resources/StateCode.csv";
    private final String INCORRECT_CSV_STATE_CODE_PATH = "src/tested/resources/StateCode1.csv";
    private final String INCORRECT_EXTENSION_CSV_STATE_CODE = "src/test/resources/StateCode.pdf";
    private final String INCORRECT_IN_DELIMITER_CSV_STATE_CODE_PATH = "src/test/resources/StateCodeDataCopy1.csv";
    private final String INCORRECT_IN_HEADER_CSV_STATE_CODE_PATH = "src/test/resources/StateCodeDataCopy2.csv";
    private final String CSV_US_CENSUS_PATH = "src/test/resources/USCensusData.csv";


    @Test
    public void givenStateCensusCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException, IOException {
        int totalRecords = stateCensusAnalyser.loadCensusData(INDIA, SIMPLE_CSV_PATH);
        Assert.assertEquals(29, totalRecords);
    }

    @Test
    public void givenStateCensusCsvFile_WhenFileNameIncorrect_ShouldThrowCustomException() throws IOException {
        try {
            stateCensusAnalyser.loadCensusData(INDIA, INCORRECT_CSV_PATH);
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
            stateCensusAnalyser.loadCensusData(INDIA, INCORRECT_DELIMITER_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenHeaderIncorrect_ShouldThrowCustomException() throws IOException {
        try {
            stateCensusAnalyser.loadCensusData(INDIA, INCORRECT_HEADER_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException, IOException {
        int totalRecords = stateCensusAnalyser.loadCensusData(INDIA, SIMPLE_CSV_PATH, CSV_STATE_CODE_PATH);
        Assert.assertEquals(37, totalRecords);
    }


    @Test
    public void givenStateCodeCsvFile_WhenFileNameIncorrect_ShouldThrowCustomException() throws IOException {
        try {
            stateCensusAnalyser.loadCensusData(INDIA, INCORRECT_CSV_STATE_CODE_PATH);
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
            stateCensusAnalyser.loadCensusData(INDIA, INCORRECT_IN_DELIMITER_CSV_STATE_CODE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenHeaderIncorrect_ShouldThrowCustomException() throws IOException {
        try {
            stateCensusAnalyser.loadCensusData(INDIA, INCORRECT_IN_HEADER_CSV_STATE_CODE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnState_ShouldReturnSortedList() throws IOException {
        try {
            stateCensusAnalyser.loadCensusData(INDIA, SIMPLE_CSV_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedCensusData();
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
            Assert.assertEquals("West Bengal", censusCSV[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnState_ShouldReturnSortedList1() throws IOException {
        try {
            stateCensusAnalyser.loadCensusData(INDIA, SIMPLE_CSV_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedCensusData();
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Madhya Pradesh", censusCSV[13].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedImproperlyOnState_ShouldNotReturnSortedList() throws IOException {
        try {
            stateCensusAnalyser.loadCensusData(INDIA, SIMPLE_CSV_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedCensusData();
            CensusDAO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDAO[].class);
            Assert.assertNotEquals("Maharashta", censusCSV[0].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeCsvFilee_WhenSortedOnStateCode_ShouldReturnSortedList() throws IOException {
        try {
            stateCensusAnalyser.loadCensusData(INDIA, SIMPLE_CSV_PATH, CSV_STATE_CODE_PATH);
            String sortedStateCodeData = stateCensusAnalyser.getStateCodeWiseSortedData();
            CensusDAO[] stateCodes = new Gson().fromJson(sortedStateCodeData, CensusDAO[].class);
            Assert.assertEquals("AD", stateCodes[0].stateCode);
            Assert.assertEquals("WB", stateCodes[36].stateCode);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCodeCsvFile_WhenSortedImproperOnStateCode_ShouldNotReturnSortedList() throws IOException {
        try {
            stateCensusAnalyser.loadCensusData(INDIA, CSV_STATE_CODE_PATH);
            String sortedStateCodeCensusData = stateCensusAnalyser.getStateCodeWiseSortedData();
            CensusDAO[] stateCensusesCSV = new Gson().fromJson(sortedStateCodeCensusData, CensusDAO[].class);
            Assert.assertNotEquals("Madhya Pradesh", stateCensusesCSV[0].stateCode);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnPopulation_ShouldReturnSortedList() throws IOException {
        try {
            stateCensusAnalyser.loadCensusData(INDIA, SIMPLE_CSV_PATH);
            String sortedStateCensusData = stateCensusAnalyser.getStateCensusPopulationWiseSortedData();
            CensusDAO[] stateCensusesCSV = new Gson().fromJson(sortedStateCensusData, CensusDAO[].class);
            Assert.assertEquals("Uttar Pradesh", stateCensusesCSV[0].state);
            Assert.assertEquals("Sikkim", stateCensusesCSV[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnPopulationDensity_ShouldReturnSortedList() throws IOException {
        try {
            stateCensusAnalyser.loadCensusData(INDIA, SIMPLE_CSV_PATH);
            String sortedStateCensusData = stateCensusAnalyser.getStateCensusPopulationDensityWiseSortedData();
            CensusDAO[] stateCensusesCSV = new Gson().fromJson(sortedStateCensusData, CensusDAO[].class);
            Assert.assertEquals("Bihar", stateCensusesCSV[0].state);
            Assert.assertEquals("Mizoram", stateCensusesCSV[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenSortedOnLargestArea_ShouldReturnSortedList() throws IOException {
        try {
            stateCensusAnalyser.loadCensusData(INDIA, SIMPLE_CSV_PATH);
            String sortedStateCensusData = stateCensusAnalyser.getStateCensusLargestAreaWiseSortedData();
            CensusDAO[] stateCensusesCSV = new Gson().fromJson(sortedStateCensusData, CensusDAO[].class);
            Assert.assertEquals("Rajasthan", stateCensusesCSV[0].state);
            Assert.assertEquals("Arunachal Pradesh", stateCensusesCSV[28].state);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCsvFile_WhenTrue_NumberOfRecordShouldMatch() throws CSVBuilderException, IOException {
        int totalRecords = stateCensusAnalyser.loadCensusData(US, CSV_US_CENSUS_PATH);
        Assert.assertEquals(51, totalRecords);
    }
}