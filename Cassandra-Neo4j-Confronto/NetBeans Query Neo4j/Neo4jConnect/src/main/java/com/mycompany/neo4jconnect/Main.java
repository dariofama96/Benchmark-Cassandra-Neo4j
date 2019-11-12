package com.mycompany.neo4jconnect;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public class Main {
    private final Driver driver;
    public Main(String uri, String user, String password){
       driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
     
    }
    
    public void close() throws Exception{
        driver.close();
    }
    
    public static void main(String[] args) throws Exception {
            double millisecondi = Math.pow(10,6);
            FileWriter w;
            String separator = System.getProperty("line.separator");
            Main db = new Main("bolt://localhost:7687", "neo4j", "milan22");
            System.out.println("Seleziona la query da eseguire.");
            Scanner tastiera = new Scanner(System.in);
            int input = tastiera.nextInt();
            switch(input){
                case 1: 
                    String percorso = System.getProperty("user.dir") + File.separator + "Query1_" + "Neo4J.txt";
                    w = new FileWriter(percorso);
                    for(int i=0; i<31; i++){
                        long start = System.nanoTime();
                        db.query1();
                        long end = System.nanoTime();
                        String total = String.valueOf((end - start) / millisecondi);
                        w.write(total + " ");
                    }
                    w.flush();
                    w.close();
                    db.close();
                break;
                case 2: 
                    String percorso2 = System.getProperty("user.dir") + File.separator + "Query2_" + "Neo4J.txt";
                    w = new FileWriter(percorso2);
                    for(int i=0; i<31; i++){
                        long start = System.nanoTime();
                        db.query2();
                        long end = System.nanoTime();
                        String total = String.valueOf((end - start) / millisecondi);
                        w.write(total + " ");
                    }
                    w.flush();
                    w.close();
                    db.close();
                break;
                case 3: 
                    String percorso3 = System.getProperty("user.dir") + File.separator + "Query3_" + "Neo4J.txt";
                    w = new FileWriter(percorso3);
                    for(int i=0; i<31; i++){
                        long start = System.nanoTime();
                        db.query3();
                        long end = System.nanoTime();
                        String total = String.valueOf((end - start) / millisecondi);
                        w.write(total + " ");
                    }
                    w.flush();
                    w.close();
                    db.close();
                break;
                case 4: 
                    String percorso4 = System.getProperty("user.dir") + File.separator + "Query4_" + "Neo4J.txt";
                    w = new FileWriter(percorso4);
                    for(int i=0; i<31; i++){
                        long start = System.nanoTime();
                        db.query4();
                        long end = System.nanoTime();
                        String total = String.valueOf((end - start) / millisecondi);
                        w.write(total + " ");

                    }
                    w.flush();
                    w.close();
                    db.close();
                break;
                case 5: 
                    String percorso5 = System.getProperty("user.dir") + File.separator + "Query5_" + "Neo4J.txt";
                    w = new FileWriter(percorso5);
                    for(int i=0; i<31; i++){
                        long start = System.nanoTime();
                        db.query5();
                        long end = System.nanoTime();
                        String total = String.valueOf((end - start) / millisecondi);
                        w.write(total + " ");

                    }
                    w.flush();
                    w.close();
                    db.close();
                break;
                 default:
                    System.out.println("Errore nella scelta");
            
            }

    }
    public void query1(){
        try(Session session = driver.session()){
            StatementResult result = session.run("MATCH (a:AUTHORITY {authority_town: 'Beaune', main_activity: 'Health'}) RETURN a");
        }catch(Exception ex){
        }

    }
    public void query2(){
        try(Session session = driver.session()){
            StatementResult result = session.run("MATCH (c:CONTRACT {number_offers:'1'}) RETURN c");
        }catch(Exception ex){
        }

    }
    public void query3(){
        try(Session session = driver.session()){
            StatementResult result = session.run("MATCH (c:CONTRACT)<-[r:IS_CONTRACT_AWARDED]-(ca:CONTRACT_AWARD) WHERE ca.win_town ='Bucuresti' RETURN c,ca");
        }catch(Exception ex){
        }
    }
    public void query4(){
        try(Session session = driver.session()){
            StatementResult result = session.run("MATCH(c:CONTRACT)-[r:IS_NOTICED_BY]->(can:CONTRACT_AWARD_NOTICE)-[re:IS_AWARDED_BY]->(ca:CONTRACT_AWARD) WHERE can.type_of_contract='S' AND ca.win_country_code= 'RO' RETURN c,ca,can");
        }catch(Exception ex){
        }
    }
    public void query5(){
        try(Session session = driver.session()){
            StatementResult result = session.run("MATCH(c:CONTRACT)-[r:IS_NOTICED_BY]->(can:CONTRACT_AWARD_NOTICE)-[re:IS_AWARDED_BY]->(ca:CONTRACT_AWARD) WHERE ca.contract_price > 1000000 AND can.type_of_contract='W' RETURN c,can,ca");
        }catch(Exception ex){
        }
    }
}
