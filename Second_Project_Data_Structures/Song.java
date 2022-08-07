import java.lang.IllegalArgumentException; 
import java.lang.Comparable;
import java.io.*;
import java.util.*;

public class Song implements Comparable <Song>  {
public int id;
public String name="Default Song";
public int likes;

public Song(){
	id=0;
	name="Default Song";
	likes=0;
}
public Song(int id,String name, int likes)throws IllegalArgumentException{
	if(name.length()<=80)
		this.name=name;
	else 
		throw new IllegalArgumentException(name);
	if(id>=1&&id<=9999)
		this.id=id;
	else 
		throw new IllegalArgumentException(name);
	
	this.likes=likes;
}
	public void setId(int id) throws IllegalArgumentException{
		if(id>=1&&id<=9999) this.id=id;
		else throw new IllegalArgumentException(name);
	}
	public void setName(String name)throws IllegalArgumentException{
	if(name.length()<=80)
		this.name=name;
	else 
		throw new IllegalArgumentException(name);
	}
	public void setLikes(int likes){
		this.likes=likes;
	}
	public int getId(){
		return id;
	}
	public int getLikes(){
		return likes;
	}
	public String getName(){
		return name;
	}		
	public int compareTo(Song a) {			
		final int before=-1;
		final int equal=0;
		final int after=1;
		if(this.getLikes()==a.getLikes()) {
			if (this.getName().compareTo(a.getName())>0)
				return before;
			else 
				return after;
		}
		else if(this.getLikes()<a.getLikes()) 
			return before;
		
		else 
			return after;
		
	}
	public String  toString(){
		return (this.getId()+" "+this.getName()+" "+this.likes);
	
		}
	public static void exch(Song[] a, int i, int j) {
		Song t = a[i];
		a[i] = a[j];
		a[j] = t; 
		}
	//public void compExch(Song[] a, int i, int j) {
		//if (a[j].compareTo(a[i])==1) exch (a, i, j); 
		//} 
	public static void quickSort(Song[] a, int p, int r){
	if (r <= p) return;
		int i = partition(a, p, r);
									
		quickSort(a, p, i-1);
		quickSort(a, i+1, r);	
	} 
	public static int partition(Song a[], int p, int r){ 
		int i = p-1, j = r; Song v = a[r];
		for (;;){
			while (a[++i].compareTo(v)==-1) ;
			while (v.compareTo(a[--j])==-1)
				if (j == p) break;
			if (i >= j) break;
			exch(a, i, j);
		}
		exch(a, i, r);
		return i; 
	} 
	

}