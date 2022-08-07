import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.IllegalArgumentException; 
import java.util.Comparator;


public class Top_k_withPQ{
	public static void main(String[] args) throws Exception {
	//ArrayList<Song> songs=new ArrayList<Song>();
	File f=null;
	Scanner in=new Scanner(System.in);
	BufferedReader reader=null;
	String line;
	Comparator<Song> cmp;
	cmp=new SongComparator();
	File f2=null;
	int counter=0;
	int k=0 ;
	try{
		k=Integer.parseInt(args[1]);
		}
	catch(NumberFormatException e){
		System.err.println("Argument" + args[1] + "must be an integer . ");
	}
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
		//int x=5;
		
		line=reader.readLine();
		while(line !=null){
			counter++;
			line=reader.readLine();
		}
		//System.out.println(counter);
	}
	catch (FileNotFoundException e) {
        System.err.println("Error opening file!");
	}
	//System.out.println(counter);
	try {
		//System.out.println("edo");
		reader.close();
		} 
	catch (IOException e) {
		System.err.println("Error closing file.");
		}
	// System.out.println(counter);
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
		
		//System.out.println(x);
		  PQ <Song> songs= new PQ<Song>(k, new SongComparator());
			//System.out.println(counter);
			line=reader.readLine();
			String text="";
			text=text.trim();
			String name;
			int id;
			int likes;
			int i;
			String c;
		//	int k;
		
			
			String stringid;
			String stringlikes;
			while(line!=null){
				text=line;
				 i=0;
				stringid="";
				
				//System.out.println("edo");
				while(text.charAt(i)!=' '){																//WHILE GIA EURESH ID 
					//System.out.println("edo  2");
					//System.out.println(text.charAt(i));
					stringid+=text.charAt(i);
					i++;
					
				}
				
				id=Integer.parseInt(stringid);

				i+=1;
				
				name="";

				while (text.substring(i).charAt(0)!='1'&&text.substring(i).charAt(0)!='2'&&text.substring(i).charAt(0)!='3'&&text.substring(i).charAt(0)!='4'&&text.substring(i).charAt(0)!='5'&&text.substring(i).charAt(0)!='6'&&
				text.substring(i).charAt(0)!='7'&&text.substring(i).charAt(0)!='8'&&text.substring(i).charAt(0)!='9'){
					
					name+=text.charAt(i);
					i++;
				}
				name=name.trim();
				
				stringlikes="";
				while(!text.substring(i).equals("")&&!text.substring(i).equals(" ")){ 
					stringlikes+=text.charAt(i);
					i++;
				}
				stringlikes=stringlikes.trim();
				likes=Integer.parseInt(stringlikes);
				//System.out.println(likes);
				i=0;				
				Song a=new Song(id,name,likes);
				songs.print();
				//System.out.println(a.getLikes());
				if(songs.getSize()<k){
					songs.insert(a);					//bazo to antikeimeno A mesa ston pinaka songs 
				}
				else  {
					
					int minId=songs.getHeap(1).getId();
					int that=1;
					
					for(int l=2;l<=songs.getSize();l++){
						if(cmp.compare(songs.getHeap(l),songs.getHeap(that))==-1){

							minId= songs.getHeap(l).getId();
							that=l;
						}
					}
					if(cmp.compare(songs.getHeap(that),a)==-1){
						songs.remove(minId);
						songs.insert(a);
					}
				}
							
			
		
								
				
				id=0;
				name="";
				likes=0;
				i=0;
				line=reader.readLine();
			}
			System.out.println("The Top "+k+" Songs Are : " );
				songs.print();

		



			
		
			
			
			
			//for (i=counter-1;i>=counter-k;i--){
				//System.out.println(songs[i].getName());
		//	}
			
				
		
			
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