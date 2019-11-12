
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


public class UploadIsNoticedBytoIsContractAwarded {
    private static final String CSV_FILE_PATH = "C://Users//Dario//Desktop//50000.csv";

    public static void main(String[] args) throws IOException {
        SparkConf conf = new SparkConf();
        conf.setAppName("Ciao");
        conf.set("spark.master", "local");
        conf.set("spark.cassandra.connection.host", "localhost");
        JavaSparkContext sc = new JavaSparkContext(conf);
        Map<String, IsNoticedBytoIsContractAwarded> mappa = new HashMap<String, IsNoticedBytoIsContractAwarded>();
        IsNoticedBytoIsContractAwarded contr;
        Reader reader;
        CSVParser csvParser = null;
        try {
            reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH), StandardCharsets.UTF_8); 
            csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().withDelimiter(';').parse(reader);
            
            for (CSVRecord csvRecord : csvParser) {
                // Accessing values by the names assigned to each column
                String id_award = csvRecord.get("ID_AWARD");
                String contract_number = csvRecord.get("CONTRACT_NUMBER");
                String id_notice_can = csvRecord.get(0);
                if(!id_award.equals("") && !contract_number.equals("") && !id_notice_can.equals("")){
                    contr = mappa.get(id_award);
                    if (contr == null) {
                        contr = new IsNoticedBytoIsContractAwarded();
                        contr.setIdNoticeCan(csvRecord.get(0));
                        contr.setTypeOfContract(csvRecord.get("TYPE_OF_CONTRACT"));
                        contr.setIdAward(csvRecord.get("ID_AWARD"));
                        contr.setWinTown(csvRecord.get("WIN_TOWN"));
                        contr.setWinName(csvRecord.get("WIN_NAME"));
                        contr.setWinCountryCode(csvRecord.get("WIN_COUNTRY_CODE"));
                        if(csvRecord.get("AWARD_VALUE_EURO_FIN_1").equals("")){
                            contr.setContractPrice(0);
                        }else{
                        contr.setContractPrice(Float.parseFloat(
                                csvRecord.get("AWARD_VALUE_EURO_FIN_1")));
                        }
                        contr.setContractNumber(csvRecord.get("CONTRACT_NUMBER"));
                        contr.setTitle(csvRecord.get("TITLE"));
                        contr.setNumberOffers(csvRecord.get("NUMBER_OFFERS"));
                        mappa.put(id_award, contr);
                        List<IsNoticedBytoIsContractAwarded> listainserimento = new ArrayList<IsNoticedBytoIsContractAwarded>(mappa.values());
                        JavaRDD<IsNoticedBytoIsContractAwarded> parallelize = sc.parallelize(listainserimento);
                        CassandraJavaUtil.javaFunctions(parallelize)
                        .writerBuilder("database2", "isnoticedbytoiscontractawarded", mapToRow(IsNoticedBytoIsContractAwarded.class)).saveToCassandra();
                    }
                    
                }
            }
        }catch (IOException ex) {
            Logger.getLogger(UploadAuthority.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
