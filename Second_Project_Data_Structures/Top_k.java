import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.IllegalArgumentException; 


public class Top_k{
	public static void main(String[] args) throws Exception {
	File f=null;
	Scanner in=new Scanner(System.in);
	BufferedReader reader=null;
	 String line;
	File f2=null;
	int counter=0;
	int k=0 ;
	int bale=0; // h metabliti auth xrisimopoieitai san deikths gia tis theseis tou array pinaka(songs)
	try{
		k=Integer.parseInt(args[1]);
		}
	catch(NumberFormatException e){
		System.err.println("Argument" + args[1] + "must be an integer . ");
	}

	// anoigoyme to arxeio th proth fora oste na diabasoyme tis grammes toy kai na kratisoyme ayton ton arithmo se mia metabliti (counter), oste na kseroyme to megethos toy array 
	try {
           f = new File(args[0]);
		   
     }
	catch (NullPointerException e) {
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
		while(line !=null){
			counter++;
			line=reader.readLine();
		}
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
		// ksana anoigoyme to arxeio gia na ylopoihthei to programma 
   try {
          f = new File(args[0]);
     }
	catch (NullPointerException e) {
           System.err.println("File not found.");
     }
    try {
		if (null != args[0])
		reader = new BufferedReader(new FileReader(f));
     } 
	catch (FileNotFoundException e) {
        System.err.println("Error opening file!");
     }
	try{
		Song[] songs=new Song[counter];
		line=reader.readLine();
		String text="";
		text=text.trim();
		String name;
		int id;
		int likes;
		int i;
		String c;
		String stringid;
		String stringlikes;
		while(line!=null){
			text=line;
			 i=0;
			stringid="";
			//WHILE GIA EURESH ID 
			while(text.charAt(i)!=' '){																
				stringid+=text.charAt(i);
				i++;					
			}				
			id=Integer.parseInt(stringid);
			i+=1;				
			name="";
			//while gia euresh onomatos 
			while (text.substring(i).charAt(0)!='1'&&text.substring(i).charAt(0)!='2'&&text.substring(i).charAt(0)!='3'&&text.substring(i).charAt(0)!='4'&&text.substring(i).charAt(0)!='5'&&text.substring(i).charAt(0)!='6'&&
			text.substring(i).charAt(0)!='7'&&text.substring(i).charAt(0)!='8'&&text.substring(i).charAt(0)!='9'){
				name+=text.charAt(i);
				i++;
			}
			name=name.trim();
			stringlikes="";
			//while gia euresh ton likes
			while(!text.substring(i).equals("")&&!text.substring(i).equals(" ")){ 
				stringlikes+=text.charAt(i);
				i++;
			}
			stringlikes=stringlikes.trim();
			likes=Integer.parseInt(stringlikes);
			i=0;	
			
			Song a=new Song(id,name,likes);
			songs[bale]=a;								//bazo to antikeimeno A mesa ston pinaka songs 
			bale++;				
			//arxikopoioume pali tis metablites oste na ksana xrisimopoihthoun 
			id=0;
			name="";
			likes=0;
			i=0;
			line=reader.readLine();
		}
		// taksinomisi toy pinaka meso ths quicksort kai ektiposi ton K kalyteron songs 
		Song.quickSort(songs,0,counter-1);	
		System.out.println("The Top K Songs Are : " );
		for (i=counter-1;i>=counter-k;i--){
			System.out.println(songs[i].getName());
		}		
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