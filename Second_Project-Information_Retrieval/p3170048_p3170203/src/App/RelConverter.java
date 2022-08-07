package App;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
/**
 * 
 * @author Anastasios Zacharioudakis - Nikolaos Vattis
 */
public class RelConverter {
	public RelConverter() {
		try {
			File my_rel_file = new File("C:\\Users\\User\\Desktop\\new new\\trec_eval\\qrel.txt");
			FileWriter Writter = new FileWriter(my_rel_file,true);
			BufferedWriter bufferwriter = new BufferedWriter(Writter);
			
			File rel_file = new File("LISA/LISA.REL");
			BufferedReader bufferReader = new BufferedReader(new FileReader(rel_file));
			Scanner scan = new Scanner(bufferReader);
			String line = scan.nextLine();
			while(!line.isBlank()) {
				String id = line;
				String new_id = id.substring(6,id.length());
				line = scan.nextLine();
				String doc_id = "-1";
				do {
					if(!line.isBlank()) {
					doc_id = scan.next();
					}
					if (!doc_id.equals("-1")) {
					bufferwriter.write(new_id+" "+ "0"+" "+doc_id+" "+"1");
					bufferwriter.newLine();
					}
				}while(scan.hasNextInt());
				line = scan.nextLine();
				while(line.isBlank()) {
				if(new_id.equals("35") && doc_id.equals("-1")) {	
					break;
				}
				line = scan.nextLine();
				}
			}
			bufferReader.close();
			bufferwriter.close();
			scan.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}