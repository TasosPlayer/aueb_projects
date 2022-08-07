import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.IllegalArgumentException; 
import java.util.Comparator;


// TO ANOIGMA KAI TO DIABASMA TOU .TXT ARXEIOU GINETE ME TON IDIO TROPO OPOS STO EROTIMA <A> 


public class Dynamic_Median{
	public static void main(String[] args) throws Exception {	
	File f=null;
	//Scanner in=new Scanner(System.in);
	BufferedReader reader=null;
	String line;
	Comparator<Song> cmp;
	cmp=new SongComparator();
	int counterMedian=0; 
	Song	median=new Song();
	
	
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
		
			PQ <Song> minsongs = new PQ<Song>(10, new DynamicComparator());
			PQ <Song> maxsongs  = new PQ<Song>(10, new SongComparator());
			line=reader.readLine();
			String text="";
			text=text.trim();
			String name;
			int id;
			int likes;
			int i;
			String stringid;
			String stringlikes;
			while(line!=null){
				text=line;
				 i=0;
				stringid="";
				
				
				while(text.charAt(i)!=' '){															
			
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
			
				i=0;				
				Song a=new Song(id,name,likes);
				//songs.print();		
				counterMedian++;
				
				// insert songs in the trees
				if (minsongs.isEmpty()&& maxsongs.isEmpty()){
					minsongs.insert(a);
				}
				else{
					if (cmp.compare(a,median)>0)minsongs.insert(a);
					else maxsongs.insert(a);
				}
				// make the trees balanced
				if (Math.abs(minsongs.getSize() - maxsongs.getSize())>1){
					if (maxsongs.getSize()>minsongs.getSize()){
						minsongs.insert(maxsongs.getMax());
					}
					else if (maxsongs.getSize()<minsongs.getSize()) maxsongs.insert(minsongs.getMax());
				}
				// calculate the median 
				if(!minsongs.isEmpty()&& !maxsongs.isEmpty()) {
					if( maxsongs.getSize() == minsongs.getSize()) {
						median = maxsongs.Max();
					}
					else if ( minsongs.getSize()>maxsongs.getSize())median=minsongs.Max();
					else median=maxsongs.Max();
				}
				//print the median 
				if(counterMedian%5==0){ 
					System.out.println("The median is : "+ median.getLikes() + " likes , achieved by Song " + median.getName() );
				 }
				 // initialize the variables so they can be re-used
				id=0;
				name="";
				likes=0;
				i=0;
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
    }
}