package com.csvfile;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class CommonCSVBuilder<E> implements ICSVBuilder{

    @Override
    public Iterator getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withHeader("State", "Population", "AreaInSqKm", "DensityPerSqKm")
                .withIgnoreHeaderCase()
                .withTrim());
            return csvParser.iterator();
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                                          CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    @Override
    public List getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withHeader("State", "Population", "AreaInSqKm", "DensityPerSqKm")
                    .withIgnoreHeaderCase()
                    .withTrim());
            return csvParser.getRecords();
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
