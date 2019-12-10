package censusanalyser;

public class IndiaCensusDao {
    public  String state;
    public int population;
    public int areaInSqKm;
    public int densityPerSqKm;

    public IndiaCensusDao(IndiaCensusCSV indiaCensusCSV) {
        this.state = indiaCensusCSV.state;
        this.population = indiaCensusCSV.population;
        this.areaInSqKm = indiaCensusCSV.areaInSqKm;
        this.densityPerSqKm = indiaCensusCSV.densityPerSqKm;
    }
}
