package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCode {
    @CsvBindByName(column = "State Name", required = true)
    private String State_Name;

    @CsvBindByName(column = "StateCode", required = true)
    private String StateCode;

    @Override
    public String toString() {
        return "IndiaStateCode{" +
                "State_Name='" + State_Name + '\'' +
                ", StateCode='" + StateCode + '\'' +
                '}';
    }
}
