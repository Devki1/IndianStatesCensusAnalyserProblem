package com.bridgelabz.service;

import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.model.CSVStateCensus;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class StateCensusAnalyser {
    //private static String getPaths;
    int countRecord = 0;
    ;

    //Reading and printing the data csv file
    public int loadCensusCSVData(String getPaths) throws StateCensusAnalyserException {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(getPaths));
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
                System.out.println(countRecord);
            }
        } catch (IOException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.Exceptiontype.ENTERED_WRONG_FILE, e.getMessage());
        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.Exceptiontype.ENTERED_INCORRECT_DELIMITER, e.getMessage());
        }
        return countRecord;
    }
    public static void getFileExtension(File getPaths) throws StateCensusAnalyserException {
        String fileName = getPaths.getName();
        String extension = null;
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        {
            extension = fileName.substring(fileName.lastIndexOf(".")+1);
        }
        if (!(extension.equals("csv")))
        {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.Exceptiontype.ENTERED_WRONG_FILE_TYPE,"FILE TYPE IS INCORRECT");
        }
    }
}

