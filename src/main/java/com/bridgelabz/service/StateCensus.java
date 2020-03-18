package com.bridgelabz.service;

import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.model.CSVState;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class StateCensus {
    int countRecord = 0;

    public int loadCensusCodeCSVData(String getPaths) throws IOException, StateCensusAnalyserException {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(getPaths));
                CSVReader csvReader = new CSVReader(reader);
        ) {
            CsvToBean<CSVState> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(CSVState.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<CSVState> csvStateIterator = csvToBean.iterator();
            while (csvStateIterator.hasNext()) {
                CSVState csvState = csvStateIterator.next();
                System.out.println("SrNo : " + csvState.SrNo);
                System.out.println("StateName: " + csvState.StateName);
                System.out.println("TIN: " + csvState.TIN);
                System.out.println("StateCode" + csvState.StateCode);
                countRecord++;
                System.out.println(countRecord);
            }
        } catch (IOException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.Exceptiontype.ENTERED_WRONG_FILE, e.getMessage());
        }
        return countRecord;
    }

    public static void getFileExtension(File getPaths) throws StateCensusAnalyserException {
        String fileName = getPaths.getName();
        String extension = null;
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        if (!(extension.equals("csv"))) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.Exceptiontype.ENTERED_WRONG_FILE_TYPE,"File type is incorrect");
        }
    }
}

