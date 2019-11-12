
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


public class UploadContractAward {
    private static final String CSV_FILE_PATH = "C://Users//Dario//Desktop//50000.csv";

    public static void main(String[] args) throws IOException {
        SparkConf conf = new SparkConf();
        conf.setAppName("Ciao");
        conf.set("spark.master", "local");
        conf.set("spark.cassandra.connection.host", "localhost");
        JavaSparkContext sc = new JavaSparkContext(conf);
        Map<String, ContractAward> mappa = new HashMap<String, ContractAward>();
        ContractAward contr;
        Reader reader;
        CSVParser csvParser = null;
        try {
            reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH), StandardCharsets.UTF_8); 
            csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().withDelimiter(';').parse(reader);
            
            for (CSVRecord csvRecord : csvParser) {
                // Accessing values by the names assigned to each column
                String id_award = csvRecord.get("ID_AWARD");
                if(!id_award.equals("")){
                    contr = mappa.get(id_award);
                    if (contr == null) {
                        contr = new ContractAward();
                        contr.setIdAward(csvRecord.get("ID_AWARD"));
                        contr.setWinTown(csvRecord.get("WIN_TOWN"));
                        contr.setWinName(csvRecord.get("WIN_NAME"));
                        contr.setWinCountryCode(csvRecord.get("WIN_COUNTRY_CODE"));
                        if(!csvRecord.get("AWARD_VALUE_EURO_FIN_1").equals("")){
                        contr.setContractPrice(Float.parseFloat(
                                csvRecord.get("AWARD_VALUE_EURO_FIN_1")));
                        }
                        mappa.put(id_award, contr);
                        List<ContractAward> listainserimento = new ArrayList<ContractAward>(mappa.values());
                        JavaRDD<ContractAward> parallelize = sc.parallelize(listainserimento);
                        CassandraJavaUtil.javaFunctions(parallelize)
                        .writerBuilder("database2", "contract_award", mapToRow(ContractAward.class)).saveToCassandra();
                    }
                    /*int id_notice_can = Integer.parseInt(csvRecord.get("ID"));
                    String cae_name = csvRecord.get("CAE_NAME");
                    String cae_town = csvRecord.get("CAE_TOWN");
                    String cae_type = csvRecord.get("CAE_TYPE");
                    String main_activity = csvRecord.get("MAIN_ACTIVITY");
                    String type_of_contract = csvRecord.get("TYPE_OF_CONTRACT");
                    String id_award = csvRecord.get("ID_AWARD");
                    String id_lot_awarded = csvRecord.get("ID_LOT_AWARDED");
                    String win_name = csvRecord.get("WIN_NAME");
                    String win_town = csvRecord.get("WIN_TOWN");
                    String win_country_code = csvRecord.get("WIN_COUNTRY_CODE");
                    String title = csvRecord.get("TITLE");
                    String number_offers = csvRecord.get("NUMBER_OFFERS");
                    String award_value_euro = csvRecord.get("AWARD_VALUE_EURO_FIN_1");
                    String dt_award = csvRecord.get("DT_AWARD");
                    /*System.out.println("Record No - " + csvRecord.getRecordNumber());
                    System.out.println("---------------");
                    //System.out.println(id_notice_can);
                    System.out.println(contract_number);
                    System.out.println(title);
                    System.out.println(number_offers);
                    System.out.println("---------------\n\n");*/
                }
            }
        }catch (IOException ex) {
            Logger.getLogger(UploadAuthority.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
