package com.bridgelabz.service.Test;

import com.bridgelabz.service.StateCensusAnalyser;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class StateCensusAnalyserTest {
    @Test
    public void givenStateCensusCSVFile_WhenTrue_NumberOfRecordShouldMatch() throws IOException {
        StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
        int countRecord = stateCensusAnalyser.loadCensusCSVData();
        Assert.assertEquals(countRecord, 29);
    }
}