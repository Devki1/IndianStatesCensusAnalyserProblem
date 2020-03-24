package com.bridgelabz.service;

import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.model.CSVState;
import com.bridgelabz.model.CSVStateCensus;
import com.bridgelabz.utility.CSVBuilderFactory;
import com.bridgelabz.utility.ICSVBuilder;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class StateCensusAnalyser {
    int countRecord = 0;

    //Reading and printing data from csv file
    public int loadCensusCsvData(String SIMPLE_CSV_PATH) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(SIMPLE_CSV_PATH));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCensus> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, CSVStateCensus.class);
            while (censusCSVIterator.hasNext()) {
                CSVStateCensus csvStateCensus = censusCSVIterator.next();
                countRecord++;
            }
            return countRecord;
        } catch (IOException e) {
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.ENTERED_WRONG_FILE_TYPE, e.getMessage());
        } catch (RuntimeException e) {
            throw new CSVBuilderException
                    (CSVBuilderException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER, e.getMessage());
        }
    }

    public int loadSateCodeCsvData(String CSV_PATH) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(CSV_PATH));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVState> csvStateIterator = csvBuilder.getCSVFileIterator(reader, CSVState.class);

            while (csvStateIterator.hasNext()) {
                countRecord++;
                CSVState csvState = csvStateIterator.next();
            }
            return countRecord;
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
}


