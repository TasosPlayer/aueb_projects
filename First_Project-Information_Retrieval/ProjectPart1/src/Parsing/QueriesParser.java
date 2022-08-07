package Parsing;

/**
 * 
 * @author Anastasios Zacharioudakis - Nikolaos Vattis
 */
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;

import utils.IO;

public class QueriesParser {
	public static List<QueryDoc> parse(String file) throws Exception {
        try{
            //Parse text file
        	String txt_file = IO.ReadEntireFileIntoAString(file);
        	String[] query_docs = txt_file.split("\\#");
        	 
            System.out.println("Read: "+query_docs.length + " docs");
            //Parse each document from the text file
            List<QueryDoc> parsed_queries= new ArrayList<QueryDoc>();
            for (String query:query_docs){
            	BufferedReader	reader = new BufferedReader(new StringReader(query));
            	String line =reader.readLine();
            	while(line.isBlank()) {
            		line =reader.readLine();
            	}
            	boolean flag = true; //// check if the text is more than 1 line in the txt
            	String[] qdoc = new String[2];	
            	
            	qdoc[0] = line;
            	line=reader.readLine();
            	 
            	 while(line != null) {
            		   if(flag) {
            			   qdoc[1] = line;
                           flag=false;
                       }
                       else {
                    	   qdoc[1] += " ";
                    	   qdoc[1] += line;
                       }
                       line=reader.readLine();
                   }
            	 System.out.println(qdoc[0]  + "   " + qdoc[1]);
            	 QueryDoc querydoc = new QueryDoc(qdoc[0]  ,qdoc[1]);
            	 parsed_queries.add(querydoc);
            }
       
            return parsed_queries;
        } catch (Throwable err) {
            err.printStackTrace();
            return null;
        } 
    }
}