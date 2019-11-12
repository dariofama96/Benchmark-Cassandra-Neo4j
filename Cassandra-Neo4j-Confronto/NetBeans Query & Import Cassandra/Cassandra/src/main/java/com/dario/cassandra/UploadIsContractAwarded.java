/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dario.cassandra;

import com.datastax.spark.connector.japi.CassandraJavaUtil;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

/**
 *
 * @author Dario
 */
public class UploadIsContractAwarded {
    private static final String CSV_FILE_PATH = "C://Users//Dario//Desktop//50000.csv";

    public static void main(String[] args) throws IOException {
        SparkConf conf = new SparkConf();
        conf.setAppName("Ciao");
        conf.set("spark.master", "local");
        conf.set("spark.cassandra.connection.host", "localhost");
        JavaSparkContext sc = new JavaSparkContext(conf);
        Map<String, IsContractAwarded> mappa = new HashMap<String, IsContractAwarded>();
        IsContractAwarded c_aw;
        Reader reader;
        CSVParser csvParser = null;
        try {
            reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH), StandardCharsets.UTF_8); 
            csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().withDelimiter(';').parse(reader);
            
            for (CSVRecord csvRecord : csvParser) {
                // Accessing values by the names assigned to each column
                String contract_number = csvRecord.get("CONTRACT_NUMBER");
                String id_award = csvRecord.get("ID_AWARD");
                if(!contract_number.equals("") && !id_award.equals("")){
                    c_aw = mappa.get(contract_number);
                    if (c_aw == null) {
                        c_aw = new IsContractAwarded();
                        c_aw.setContractNumber(contract_number);
                        c_aw.setTitle(csvRecord.get("TITLE"));
                        c_aw.setNumberOffers(csvRecord.get("NUMBER_OFFERS"));
                        c_aw.setIdAward(csvRecord.get("ID_AWARD"));
                        c_aw.setWinTown(csvRecord.get("WIN_TOWN"));
                        c_aw.setWinName(csvRecord.get("WIN_NAME"));
                        c_aw.setWinCountryCode(csvRecord.get("WIN_COUNTRY_CODE"));
                        if(csvRecord.get("AWARD_VALUE_EURO_FIN_1").equals("")){
                            c_aw.setContractPrice(0);
                        }else{
                        c_aw.setContractPrice(Float.parseFloat(
                                csvRecord.get("AWARD_VALUE_EURO_FIN_1")));
                        }
                        mappa.put(contract_number, c_aw);
                        List<IsContractAwarded> listainserimento = new ArrayList<IsContractAwarded>(mappa.values());
                        JavaRDD<IsContractAwarded> parallelize = sc.parallelize(listainserimento);
                        CassandraJavaUtil.javaFunctions(parallelize)
                        .writerBuilder("database2", "iscontractawarded", mapToRow(IsContractAwarded.class)).saveToCassandra();
                    }
                }
            }
        }catch (IOException ex) {
            Logger.getLogger(UploadContract.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
