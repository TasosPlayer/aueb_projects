import java.io.*;
import java.util.*;
import java.lang.NullPointerException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Character;

public class NetBenefit{
	public static void main(String[] args) throws Exception {
		IntQueueImpl <Integer> ListBuy = new IntQueueImpl <Integer> ();
		IntQueueImpl <Integer> ListPrice=new IntQueueImpl <Integer> ();
		File f=null;
		BufferedReader reader=null;
		String line;
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
			String text="";
			int buy;         // pairnei timh to synolo ton metoxon poy agorasthkan 
			int price;		// pairnei timh to poso poy agorasthkan 
			int sell;		//pairnei timh to synolo ton metoxon poy poylithikan 
			int thesiOfBuy;
			int thesiOfSell;
			int thesiOfPrice;
			String Strbuy="";
			String Strprice="";
			String Strsell="";
			int sum ;
			int i;			
			while(line!=null){
				text=line; // anti gia to line xrhsimopoioume to string text
				thesiOfBuy=text.indexOf("buy"); // h thesi prwtou stoixeiou tou buy sto line
				thesiOfPrice=text.indexOf("price"); // h thesi prwtou stoixeiou tou price sto line
				thesiOfSell=text.indexOf("sell");   // h thesi prwtou stoixeiou tou sell sto line			
				if(thesiOfBuy>=0){
					i =0;
					thesiOfBuy+=4;  //prosthetoume ton analogo arithmo oste na mhn lifthoyn oi theseis apo ta grammata tou buy ypospin 
					Strbuy=""; // o arithmos twn metoxwn pou agoprazei, se string
					while(text.substring(thesiOfBuy).charAt(i)!=' '){ 
						Strbuy+=text.charAt(thesiOfBuy);
						thesiOfBuy+=1;
					}
					
					buy=Integer.parseInt(Strbuy); // metatrepetai apo string se int
					Strbuy=""; // h timh twn metoxwn p agorazei, se string ksana arxikopoieitai gia melontikh xrhsh 
					ListBuy.put(buy);
					thesiOfPrice+=6;
					i=0;
					while(!text.substring(thesiOfPrice).equals("")) {
						Strprice+=text.charAt(thesiOfPrice);
						thesiOfPrice+=1;
					}
					
					price=Integer.parseInt(Strprice.trim());     //(to ".trim()" yparxei oste an yprxei ena space meta to price na mhn yparksei kapoio problima ), h metabliti price pairnei timh , thn timh ton metoxon 
					Strprice="";
					ListPrice.put(price);
				}
				if(thesiOfSell>=0){
					sell=0;
					Strsell=""; // o arithmos twn metoxwn pou poulaei, se string, arxikopoieitai oste na ksanaxrisimopoihtei an xreiastei 
					thesiOfSell+=5;
					i=0;
					while(text.substring(thesiOfSell).charAt(i)!=' '){
						Strsell+=text.charAt(thesiOfSell);
						thesiOfSell+=1;
					}
					sell=Integer.parseInt(Strsell);
					thesiOfPrice+=6;
					i=0;
					while(!text.substring(thesiOfPrice).equals("")){ 
						Strprice+=text.charAt(thesiOfPrice);
						thesiOfPrice+=1;
					}
				
					price=Integer.parseInt(Strprice.trim()); // h timh twn metoxwn p poulaei 
					Strprice=""; 
					sum = 0; //krateitai kerdos h zhmia 
					int sub;
					System.out.println("---Queue of stocks that were bought : ");
					ListBuy.printQueue(System.out); //typwnei ta stoixeia ths ouras twn buy
					System.out.println("---Queue with the prices of the stocks that were bought : ");
					ListPrice.printQueue(System.out); // antistoixa ths price
					while (sell>0){
						if (ListBuy.peek()<=sell){
							sum+= (ListBuy.peek())*(price - ListPrice.peek()); 
							sell -= ListBuy.peek(); //afairei ths metoxes pou poulhthikan
							//System.out.println( "  sell if: " + sell );
							ListBuy.get();	// afaireitai apo thn oura
							ListPrice.get(); // ki edw
							//System.out.println(" sum if: " + sum );
						}
						else {
							//System.out.println(" sell else: "+ sell );
							sub=ListBuy.peek()-sell; //kratame th diafora
							//System.out.println(" substract: " + sub );
							sum+= sell*(price - ListPrice.peek()); 
							ListBuy.setfirstNode(sub);// thetei th timh twn metoxwn pou perisseuoun, exoume ylopoihsei th setfirstNode sthn IntQueueImpl
							//System.out.println(" sum else: " + sum );
							sell=0;
						}
					}
					if(sum>0) System.out.println("there was a : " + sum + " profit\n");
					else if (sum==0) System.out.println("There was no profit neither damage.Sum=0\n");
					else System.out.println("there was a damage = " + sum + "\n");
					
				}	
				sum=0; //mhdenizoume to sum sto telos tou line
				
				line=reader.readLine();
			}
					System.out.println("---Queue of stocks that were bought : ");
					ListBuy.printQueue(System.out); //typwnei ta stoixeia ths ouras twn buy
					System.out.println("---Queue with the prices of the stocks that were bought : ");
					ListPrice.printQueue(System.out);//typwnei ta stoixeia ths ouras twn price 
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