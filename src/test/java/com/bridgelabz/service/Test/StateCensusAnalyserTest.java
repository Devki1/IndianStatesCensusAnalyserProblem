package com.bridgelabz.service.Test;

import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.service.StateCensus;
import com.bridgelabz.service.StateCensusAnalyser;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class StateCensusAnalyserTest {
    StateCensusAnalyser stateCensusAnalyser;
    String STATE_CSV_FILE_PATH;

    @Test
    public void givenStateCensusCSVFile_WhenTrue_NumberOfRecordShouldMatch() throws StateCensusAnalyserException, IOException {
        STATE_CSV_FILE_PATH = "./src/test/resources/StateCensusData.csv";
        stateCensusAnalyser = new StateCensusAnalyser();
        int countRecord = stateCensusAnalyser.loadCensusCSVData(STATE_CSV_FILE_PATH);
        Assert.assertEquals(countRecord, 29);
    }

    @Test
    public void givenStateCensusCsvFile_WhenIncorrect_ShouldReturnCustomException() throws IOException {
        STATE_CSV_FILE_PATH = "./src/tested/resources/StateCensusData.csv";
        stateCensusAnalyser = new StateCensusAnalyser();
        try {
            stateCensusAnalyser.loadCensusCSVData(STATE_CSV_FILE_PATH);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.Exceptiontype.ENTERED_WRONG_FILE, e.type);
        }
    }

    @Test
    public void givenStateCensusCSVFile_WhenTypeIncorrect_ShouldReturnCustomException() {
        STATE_CSV_FILE_PATH = "./src/test/resources/StateCensusData.pdf";
        stateCensusAnalyser = new StateCensusAnalyser();
        try {
            StateCensusAnalyser.getFileExtension(new File(STATE_CSV_FILE_PATH));
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.Exceptiontype.ENTERED_WRONG_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenStateCensusCSVFileCorrect_WhenDelimiterIncorrect_ShouldReturnCustomException() throws IOException {
        STATE_CSV_FILE_PATH = "./src/test/resources/StateCensusData (copy).csv";
        stateCensusAnalyser = new StateCensusAnalyser();
        try {
            stateCensusAnalyser.loadCensusCSVData(STATE_CSV_FILE_PATH);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.Exceptiontype.ENTERED_INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenStateCensusCSVFile_WhenCorrectButCsvHeaderIncorrect_ShouldReturnCustomException() throws IOException {
        STATE_CSV_FILE_PATH = "src/test/resources/StateCensusData2.csv";
        stateCensusAnalyser = new StateCensusAnalyser();
        try {
            stateCensusAnalyser.loadCensusCSVData(STATE_CSV_FILE_PATH);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.Exceptiontype.ENTERED_INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenStateCensusCode_WhenTrue_NumberOfRecordShouldBeMatch() throws IOException, StateCensusAnalyserException {
        STATE_CSV_FILE_PATH = "src/test/resources/StateCode.csv";
        stateCensusAnalyser = new StateCensusAnalyser();
        int countRecord = stateCensusAnalyser.loadCensusCodeCSVData(STATE_CSV_FILE_PATH);
        Assert.assertEquals(countRecord, 37);
    }

    @Test
    public void givenStateCodeCensusCsvFile_WhenIncorrect_ShouldReturnCustomException() throws IOException {
        STATE_CSV_FILE_PATH = "src/test/resources/StateCode.csv";
        try {
            stateCensusAnalyser.loadCensusCodeCSVData(STATE_CSV_FILE_PATH);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.Exceptiontype.ENTERED_WRONG_FILE, e.type);

        }
    }

    @Test
    public void givenStateStateCensusCSVFile_WhenTypeIncorrect_ShouldReturnCustomException() {
        STATE_CSV_FILE_PATH = "src/test/resources/StateCode1.csv";
        stateCensusAnalyser = new StateCensusAnalyser();
        try {
            StateCensus.getFileExtension(new File(STATE_CSV_FILE_PATH));
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.Exceptiontype.ENTERED_WRONG_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenStateCensusCodeCSVFileCorrect_WhenDelimiterIncorrect_ShouldReturnCustomException() throws IOException {
        STATE_CSV_FILE_PATH = "src/test/resources/StateCodeDataCopy1.csv";
        stateCensusAnalyser = new StateCensusAnalyser();
        try {
            int countResult = stateCensusAnalyser.loadCensusCodeCSVData(STATE_CSV_FILE_PATH);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCensusCodeCSVFile_WhenHeaderIncorrect_ShouldReturnCustomException() throws IOException {
        STATE_CSV_FILE_PATH = "src/test/resources/StateCodeDataCopy2.csv";
        ;
        stateCensusAnalyser = new StateCensusAnalyser();
        try {
            int countRecord = stateCensusAnalyser.loadCensusCodeCSVData(STATE_CSV_FILE_PATH);
        } catch (StateCensusAnalyserException e) {
            Assert.assertEquals(StateCensusAnalyserException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.type);
        }
    }
}

