package Parsing;

import utils.IO;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;

/**
 * 
 * @author Anastasios Zacharioudakis - Nikolaos Vattis
 */
public class Fileparser {

    public static List<MyDoc> parse(String file) throws Exception {
        try{
            //Parse text file
        	String txt_file = IO.ReadEntireFileIntoAString(file);
        	System.out.println(txt_file);
        	String[] docs = txt_file.split("\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*");
        	 
            System.out.println("Read: "+docs.length + " docs");
            //Parse each document from the text file
            List<MyDoc> parsed_docs= new ArrayList<MyDoc>();
            for (String doc:docs){
            	BufferedReader	reader = new BufferedReader(new StringReader(doc));
            	String line =reader.readLine();
            	
            	while(line.isBlank()) {
            		line =reader.readLine();
            	}
            	boolean flag = true; // check if the title is more than 1 line in the txt
            	boolean flag2= true; // check if the text is more than 1 line in the txt
            	String[] adoc = new String[3];	
            	
            	adoc[0]=line.substring(9, line.length());
            	line=reader.readLine();
            		
            	 while(!line.isBlank()) {
            		   if(flag) {
                           adoc[1] = line;
                           flag=false;
                       }
                       else {
                    	   adoc[1] += " ";
                           adoc[1] += line;
                       }
                       line=reader.readLine();
                   }
            	 line=reader.readLine();

            	 while(line != null) {
          		   if(flag2) {
                         adoc[2] = line;
                         flag2=false;
                     }
                     else {
                    	 adoc[2] += " ";
                         adoc[2] += line;
                     }
                     line=reader.readLine();
                 }
                MyDoc mydoc = new MyDoc(adoc[0]  ,adoc[1],adoc[2]); 
                parsed_docs.add(mydoc);
            }
            return parsed_docs;
        } catch (Throwable err) {
            err.printStackTrace();
            return null;
        }  
    }
}