
package com.dario.cassandra;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.spark.connector.cql.CassandraConnector;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class Query {
     
     public static void query1(JavaSparkContext sc) {
        
        CassandraConnector connector = CassandraConnector.apply(sc.getConf());

        // Prepare the schema
        try (Session session = connector.openSession()) {
                ResultSet results = session.execute("SELECT * FROM database2.authority " +
                        "WHERE CAE_TOWN = 'Beaune' AND MAIN_ACTIVITY = 'Health' ALLOW FILTERING;");
            /*for (Row row : results) {
                System.out.print("Nome dell'autorità: " + row.getString("CAE_NAME"));
                System.out.print(" ");
               // System.out.print("Attività: " + row.getString("MAIN_ACTIVITY"));
                System.out.println(); 
            }*/
            }

     }
     
    public static void query2(JavaSparkContext sc) {

        CassandraConnector connector = CassandraConnector.apply(sc.getConf());

        // Prepare the schema
        try (Session session = connector.openSession()) {

                ResultSet results = session.execute("SELECT * FROM database2.contract " +
                        "WHERE NUMBER_OFFERS = '1' ALLOW FILTERING;");

            /*for (Row row : results) {
                System.out.print("Numero contratto " + row.getString("CONTRACT_NUMBER"));
                System.out.print(" ");
               // System.out.print("Attività: " + row.getString("MAIN_ACTIVITY"));
                System.out.println(); 
            }*/

            }

    }
    
    public static void query3(JavaSparkContext sc) {

        CassandraConnector connector = CassandraConnector.apply(sc.getConf());

        // Prepare the schema
        try (Session session = connector.openSession()) {
                ResultSet results = session.execute("SELECT CONTRACT_NUMBER, TITLE, NUMBER_OFFERS, WIN_TOWN, WIN_NAME FROM database2.iscontractawarded " +
                        "WHERE WIN_TOWN = 'Bucuresti' ALLOW FILTERING;");

            /*for (Row row : results) {
                System.out.print("Numero del contratto: " + row.getString("CONTRACT_NUMBER"));
                System.out.println(); 
            }*/
            }

    }
    public static void query4(JavaSparkContext sc) {

        CassandraConnector connector = CassandraConnector.apply(sc.getConf());

        // Prepare the schema
        try (Session session = connector.openSession()) {

                ResultSet results = session.execute("SELECT * FROM database2.isnoticedbytoiscontractawarded " +
                        "WHERE TYPE_OF_CONTRACT = 'S' AND WIN_COUNTRY_CODE = 'RO' ALLOW FILTERING;");

            /*for (Row row : results) {
                System.out.print("Numero del contratto: " + row.getString("CONTRACT_NUMBER"));
                System.out.println(); 
            }*/
            }

    }
    
    public static void query5(JavaSparkContext sc) {
        CassandraConnector connector = CassandraConnector.apply(sc.getConf());
        // Prepare the schema
        try (Session session = connector.openSession()) {
                ResultSet results = session.execute("SELECT * FROM database2.isnoticedbytoiscontractawarded " +
                        "WHERE CONTRACT_PRICE > 1000000 AND TYPE_OF_CONTRACT = 'W' ALLOW FILTERING;");
                
           /* for (Row row : results) {
                System.out.print("Numero del contratto: " + row.getString("CONTRACT_NUMBER"));
                System.out.println(); 
            }*/
                
            }

    }
    
    

    public static void main(String[] args) throws IOException {
        double millisecondi = Math.pow(10, 6);
        FileWriter w;
        String separator = System.getProperty("line.separator");
        SparkConf conf = new SparkConf();
        conf.setAppName("Ciao");
        conf.set("spark.master", "local");
        conf.set("spark.cassandra.connection.host", "localhost");
        JavaSparkContext sc = new JavaSparkContext(conf);
        System.out.println("1.Esegui la query 1.\n2.Esegui la query 2.\n3.Esegui la query 3.\n4.Esegui la query 4.\n5.Esegui la query 5.");
        System.out.println("Effettua la tua scelta: ");
        Scanner input = new Scanner(System.in);
        int x = input.nextInt();
        switch (x) {
          case 1:
            String percorso = System.getProperty("user.dir") + File.separator + "Query1_" + "Cassandra.txt";
            w = new FileWriter(percorso);
            System.out.println("Esecuzione query 1");
            for(int i=0; i<31; i++){
            long start = System.nanoTime();
            query1(sc);
            long end = System.nanoTime();
            String total = String.valueOf((end - start) / millisecondi);
            w.write(total + " ");
            }
            w.flush();
            w.close();
          break;
          case 2:
            System.out.println("Esecuzione query 2");
            String percorso2 = System.getProperty("user.dir") + File.separator + "Query2_" + "Cassandra.txt";
            w = new FileWriter(percorso2);
            for(int i=0; i<31; i++){
            long start = System.nanoTime();
            query2(sc);
            long end = System.nanoTime();
            String total = String.valueOf((end - start) / millisecondi);
            w.write(total + " ");
            }
            w.flush();
            w.close();
          break;
          case 3:
            System.out.println("Esecuzione query 3");
            String percorso3 = System.getProperty("user.dir") + File.separator + "Query3_" + "Cassandra.txt";
            w = new FileWriter(percorso3);
            for(int i=0; i<31; i++){
            long start = System.nanoTime();
            query3(sc);
            long end = System.nanoTime();
            String total = String.valueOf((end - start) / millisecondi);
            w.write(total + " ");
            }
            w.flush();
            w.close();
          break;
          case 4:
            System.out.println("Esecuzione query 4");
            String percorso4 = System.getProperty("user.dir") + File.separator + "Query4_" + "Cassandra.txt";
            w = new FileWriter(percorso4);
            for(int i=0; i<31; i++){
            long start = System.nanoTime();
            query4(sc);
            long end = System.nanoTime();
            String total = String.valueOf((end - start) / millisecondi);
            w.write(total + " ");
            }
            w.flush();
            w.close();
          break;
          case 5:
            System.out.println("Esecuzione query 5");
            String percorso5 = System.getProperty("user.dir") + File.separator + "Query5_" + "Cassandra.txt";
            w = new FileWriter(percorso5);
            for(int i=0; i<31; i++){
            long start = System.nanoTime();
            query5(sc);
            long end = System.nanoTime();
            String total = String.valueOf((end - start) / millisecondi);
            w.write(total + " ");
            }
            w.flush();
            w.close();
          break;
          default:
            System.out.println("Errore nella scelta");
        }    
    }
    
}
