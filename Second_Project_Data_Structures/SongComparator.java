import java.io.*;
import java.util.*;
import java.util.Comparator;

final class SongComparator implements Comparator<Song> {
	// compares 2 Songs based on their likes , or if they have the same number of likes, by name
	@Override
	public int compare(Song o1, Song o2) {
		if(o1.getLikes() < o2.getLikes()){
			return 1;
		}
		else if(o1.getLikes() == o2.getLikes()){	
			if(o1.getName().compareTo(o2.getName())>=0){ return 1;}
			else{ return -1;}
		}
		else{
			return -1;
		}
	}
	
	
	
}
