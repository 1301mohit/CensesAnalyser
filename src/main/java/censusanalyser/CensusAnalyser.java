package censusanalyser;

import com.csvfile.CSVBuilderException;
import com.csvfile.CSVBuilderFactory;
import com.csvfile.ICSVBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaCensusDao> censusList = null;

    public CensusAnalyser() {
        this.censusList = new ArrayList<IndiaCensusDao>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV>  censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while(censusCSVIterator.hasNext()) {
                this.censusList.add(new IndiaCensusDao(censusCSVIterator.next()));
            }
            return this.censusList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaStateCode> stateCodeList = csvBuilder.getCSVFileList(reader, IndiaStateCode.class);
            return stateCodeList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numOfEnteries = (int)StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEnteries;
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if(censusList == null || censusList.size() == 0)
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        Comparator<IndiaCensusDao> csvComparator = Comparator.comparing(census -> census.state);
        this.sort(csvComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }


    private void sort(Comparator<IndiaCensusDao> censusComparator) {
        for(int i=0; i<censusList.size()-1; i++) {
            for(int j=0; j<censusList.size() - i -1; j++) {
                IndiaCensusDao indiaCensusCSV1 = censusList.get(j);
                IndiaCensusDao indiaCensusCSV2 = censusList.get(j+1);
                if (censusComparator.compare(indiaCensusCSV1, indiaCensusCSV2) > 0) {
                    censusList.set(j, indiaCensusCSV2);
                    censusList.set(j+1, indiaCensusCSV1);
                }
            }
        }
    }
}
