import java.io.*;
import java.util.*;
import java.util.Comparator;

final class DynamicComparator implements Comparator<Song> {
// does the same with SongComparator , but returns diverced values
	@Override
	public int compare(Song o1, Song o2) {
		if(o1.getLikes() < o2.getLikes()){
			return -1;
		}
		else if(o1.getLikes() == o2.getLikes()){	
			if(o1.getName().compareTo(o2.getName())>=0){ return -1;}
			else{ return 1;}
		}
		else{
			return 1;
		}
	}
	
	
	
}