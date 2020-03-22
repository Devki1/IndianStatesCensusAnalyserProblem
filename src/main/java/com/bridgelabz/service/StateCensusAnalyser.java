package com.bridgelabz.service;

import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.exception.StateCensusAnalyserException.Exceptiontype;
import com.bridgelabz.model.CSVState;
import com.bridgelabz.model.CSVStateCensus;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;

import static com.bridgelabz.exception.StateCensusAnalyserException.Exceptiontype.*;

public class StateCensusAnalyser {
    //private static String getPaths;
    //int countRecord = 0;

    //Reading and printing the data csv file
    public int loadCensusCSVData(String filePath) throws StateCensusAnalyserException, IOException {
        int countRecord = 0;
        try (
                Reader reader = Files.newBufferedReader(Paths.get(filePath));
        ) {
            Iterator<CSVStateCensus> csvStateCensusIterator = getCSVFileIterator(reader, CSVStateCensus.class);
            while (csvStateCensusIterator.hasNext()) {
                countRecord++;
                csvStateCensusIterator.next();
                //System.out.println(countRecord);
            }
            return countRecord;
        } catch (NoSuchFileException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.Exceptiontype.ENTERED_WRONG_FILE, e.getMessage());
        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.Exceptiontype.ENTERED_INCORRECT_DELIMITER, e.getMessage());
        }
    }

    //Reading and printing data from state code csv file
    public static int loadCensusCodeCSVData(String filePath) throws StateCensusAnalyserException, IOException {
        int countRecord = 0;
        try (
                Reader reader = Files.newBufferedReader(Paths.get(filePath))
        ) {
            Iterator<CSVState> csvStateIterator = getCSVFileIterator(reader, CSVState.class);
            while (csvStateIterator.hasNext()) {
                countRecord++;
                csvStateIterator.next();
            }
            return countRecord;

        } catch (NoSuchFileException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.Exceptiontype.ENTERED_WRONG_FILE, e.getMessage());
        } catch (RuntimeException e) {
            throw new StateCensusAnalyserException(StateCensusAnalyserException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.getMessage());
        }
    }

    //Method to check file extension
    public static void getFileExtension(File getPaths) throws StateCensusAnalyserException {
        String fileName = getPaths.getName();
        String extension = null;
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        if (!(extension.equals("csv"))) {
            throw new StateCensusAnalyserException(ENTERED_WRONG_FILE_TYPE, "FILE TYPE IS INCORRECT");
        }
    }

    //Generic method to iterate through csv file
    private static <E> Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws StateCensusAnalyserException {
        try {
            CSVReader csvReader = new CSVReader(reader);
            CsvToBean<E> csvToBean = new CsvToBeanBuilder<E>(csvReader).withType(csvClass).build();
            return csvToBean.iterator();
        } catch (IllegalStateException e) {
            throw new StateCensusAnalyserException(UNABLE_TO_PARSE, e.getMessage());
        }
    }
}



