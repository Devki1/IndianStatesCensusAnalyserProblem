package com.bridgelabz.adaptor;

import com.bridgelabz.dao.CensusDAO;
import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.model.IndianStateCensusCSV;
import com.bridgelabz.model.IndianStateCodeCSV;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter {
    HashMap<String, CensusDAO> censusDAOHashMap = null;

    @Override
    public HashMap<String, CensusDAO> loadCensusData(String... csvFilePath) throws CSVBuilderException {
        censusDAOHashMap = super.loadCensusData(IndianStateCensusCSV.class, csvFilePath[0]);
        if (csvFilePath.length == 1)
            return censusDAOHashMap;
        return this.loadStateCodeData(censusDAOHashMap, csvFilePath[1]);
    }

    //Reading data from StateCode csv file
    public HashMap<String, CensusDAO> loadStateCodeData(HashMap<String, CensusDAO> censusDAOMap, String getPath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(getPath))
        ) {
            Iterator<IndianStateCodeCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndianStateCodeCSV.class);
            Iterable<IndianStateCodeCSV> csvIterable = () -> csvFileIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .map(IndianStateCodeCSV.class::cast)
                    .forEach(csvStateCode -> censusDAOMap.put(csvStateCode.stateName, new CensusDAO(csvStateCode)));
            return censusDAOMap;
        } catch (IOException e) {
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.ENTERED_WRONG_FILE,
                    e.getMessage());
        } catch (RuntimeException e) {
            throw new CSVBuilderException(CSVBuilderException.Exceptiontype.ENTERED_INCORRECT_DELIMITER_OR_HEADER,
                    "Number of data fields does not match number of delimitr or headers.");
        }
    }
}
