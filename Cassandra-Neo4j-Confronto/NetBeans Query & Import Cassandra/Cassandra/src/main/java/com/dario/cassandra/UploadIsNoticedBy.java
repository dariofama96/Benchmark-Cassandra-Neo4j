
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

public class UploadIsNoticedBy {
    private static final String CSV_FILE_PATH = "C://Users//Dario//Desktop//50000.csv";
    
    public static void main(String[] args) throws IOException {
        System.setProperty("hadoop.home.dir", "C://winutils");
        SparkConf conf = new SparkConf();
        conf.setAppName("Ciao");
        conf.set("spark.master", "local");
        conf.set("spark.cassandra.connection.host", "localhost");
        JavaSparkContext sc = new JavaSparkContext(conf);
        Map<String, IsNoticedBy> mappa = new HashMap<String, IsNoticedBy>();
        IsNoticedBy contractNotice;
        Reader reader;
        CSVParser csvParser = null;
        try {
            reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH), StandardCharsets.UTF_8); 
            csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().withDelimiter(';').parse(reader);
            
            for (CSVRecord csvRecord : csvParser) {
                // Accessing values by the names assigned to each column
                String id_notice_can = csvRecord.get(0);
                String contract_number = csvRecord.get("CONTRACT_NUMBER");

                if(!id_notice_can.equals("") && !contract_number.equals("")){
                    contractNotice = mappa.get(id_notice_can);
                    if (contractNotice == null) {
                        contractNotice = new IsNoticedBy();
                        contractNotice.setIdNoticeCan(csvRecord.get(0));
                        contractNotice.setTypeOfContract(csvRecord.get("TYPE_OF_CONTRACT"));
                        contractNotice.setContractNumber(csvRecord.get("CONTRACT_NUMBER"));
                        contractNotice.setTitle(csvRecord.get("TITLE"));
                        contractNotice.setNumberOffers(csvRecord.get("NUMBER_OFFERS"));
           
                        mappa.put(id_notice_can, contractNotice);
                        List<IsNoticedBy> listainserimento = new ArrayList<IsNoticedBy>(mappa.values());
                        JavaRDD<IsNoticedBy> parallelize = sc.parallelize(listainserimento);
                        CassandraJavaUtil.javaFunctions(parallelize)
                        .writerBuilder("database2", "contract_award_notice", mapToRow(IsNoticedBy.class)).saveToCassandra();
                    }
                    
                }
            }
        }catch (IOException ex) {
            Logger.getLogger(UploadAuthority.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
