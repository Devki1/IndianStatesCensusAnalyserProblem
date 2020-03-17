package com.bridgelabz.service;

import com.bridgelabz.model.CSVStateCensus;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class StateCensusAnalyser {
    //Take the path of STATE_CSV_FILE_PATH
    public static final String STATE_CSV_FILE_PATH = "/home/user/IdeaProjects/IndianStatesCensusAnalyzerProblem/src/test/resources/StateCensusData.csv";
    int countRecord = 0;

    //Reading and printing the data csv file
    public int loadCensusCSVData() throws IOException {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(STATE_CSV_FILE_PATH));
                CSVReader csvReader = new CSVReader(reader);
        ) {
            //POJO file iterate and printing the data of csv file.
            CsvToBean<CSVStateCensus> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(CSVStateCensus.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<CSVStateCensus> csvStateCensusIterator = csvToBean.iterator();

            while (csvStateCensusIterator.hasNext()) {
                CSVStateCensus csvStateCensus = csvStateCensusIterator.next();
                System.out.println("State : " + csvStateCensus.State);
                System.out.println("Area: " + csvStateCensus.Population);
                System.out.println("Population : " + csvStateCensus.AreaInSqKm);
                System.out.println("DensityPerSqKm: " + csvStateCensus.DensityPerSqKm);
                countRecord++;
               // System.out.println(countRecord);
            }
        }
        return countRecord;
    }
}