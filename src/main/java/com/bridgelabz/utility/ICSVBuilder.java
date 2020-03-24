package com.bridgelabz.utility;

import com.bridgelabz.exception.CSVBuilderException;
import com.bridgelabz.exception.StateCensusAnalyserException;
import com.bridgelabz.model.CSVStateCensus;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder {
    public <E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass) throws StateCensusAnalyserException;
}
