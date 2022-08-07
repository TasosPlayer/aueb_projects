import java.io.*;
import java.util.*;
import java.lang.NullPointerException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class TagMatching {
	public static void main(String[] args) throws Exception {
		StringStackImpl <String> list = new StringStackImpl <String> ();
		File f=null;
		BufferedReader reader=null;
		String line;
		try {
            f = new File(args[0]);
        } catch (NullPointerException e) {
            System.err.println("File not found.");
        }
        try {
			if (null != args[0])
				reader = new BufferedReader(new FileReader(f));
        } 
		catch (FileNotFoundException e) {
            System.err.println("Error opening file!");
        }
		
		 try {			
			line=reader.readLine();					
			String text="";						// to text pairnei os timh tis grammes toy keimenou mia mia 
			boolean tf=true;					// an ginei false , stamataei to programma dhladh einai lathos ta tags
			String tagname;						// tha parei timh to string poy yparxei anamesa sta "<" & ">" ean yparxei, se kathe grammh ,  ean se mia grammh yparxoyn pano apo ena tags, tote se aythn thn grammh tha allaksei pano apo mia fores timh  
			int thesi;							// pairnei os timh thn thesi poy briskei to proto "<" sthn grammh 
			String temp;					
			int size;							// pairnei os timh to length tou tagname, xrisimeuei gia ta substring 		
			outerloop:							//tag gia na kanei to break ean tf=false , apo to foliasmeno while line=42
			while (line!=null){
				text=line;
				thesi=0;
				thesi=text.indexOf('<');
				tagname="";				
				while(thesi>=0){				//ean yparxei sth grammh "<" 
					thesi+=1;					//pare os timh th thesi poy briskete to proto gramma toy tag
						tagname="";				//arxikopoihse thn tagname		
						while (text.charAt(thesi)!='>' ){		//mexri na breis ">" pare ton xarakthra sthn thesi poy diabazeis kai balton sthn tag
							tagname+=text.charAt(thesi);
							thesi+=1;
						}					
					size=tagname.length();
					if(list.isEmpty()&&tagname.substring(0,1).equals("/")){		//ean h stoiba einai adeia kai to proto tag einai kleisimatos (ksekinaei me "</", tote brake dioti einai lathos ta tags
						tf=false;
						break outerloop;
					} 					
					if(tagname.substring(0,1).equals("/") && !list.peek().equals(tagname.substring(1,size))){	//ean to tagname einai kleisimatos kai to teleutaio tag sth stoiba 
																												//den einai idio me ayto poy prepei na kleisei , tote brake dioti einai lathos ta tags
						tf=false;
						break outerloop;
					} 										
									
					if(!tagname.substring(0,1).equals("/")){			//ean to tagname einai tag anoigmatos "<" kai oxi "</" , balto sth stoiba
						list.push(tagname);
						
					}
					if (tagname.substring(1,size).equals(list.peek())){		//ean to tag kleisimatos tautizetai me to teleutaio tag poy mphke sth stoiba, kane pop to teleytaio tag ths stoibas(peek())
						temp=list.pop();
						//System.out.println(temp);
					}
					text=text.substring(thesi);			//to neo text epeksergasias gia euresh tag, ginetai apo to tag poy brethike os to telos, wste na elegxei mhpos yparxei deytero tag sthn idia grammh 
					thesi=text.indexOf('<');			//allazei timh kai h "thesi" gia na elegxei auto 
				}
				line=reader.readLine();		
			}						
			list.printStack(System.out);					
			if(tf==true && list.isEmpty())      //ean h stoiba einai adeia kai den exei prohghthei kapoio break, tote to file exei matched tags
				System.out.print(" The File has matched tags" );
			else 
				System.out.print("The file has not matched tags");				//diaforetika, den exei 
		}
			catch (FileNotFoundException e) {
            System.err.println("Error opening file!");
			}
			try {
				reader.close();
			} 
			catch (IOException e) {
				System.err.println("Error closing file.");
			}
	}
}		

	


						

						
						
						
