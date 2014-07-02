package joiningEntriesFromTwoLists;

import java.util.Comparator;

public class SortByID implements Comparator<String[]> {
	
	protected int idField; 
	
	public SortByID( final int idField ) {
		this.idField = idField; 
	}
	
	public int compare( final String[] entry1, final String[] entry2 ) {
		final String id1 = entry1[ this.idField ]; 
		final String id2 = entry2[ this.idField ]; 
		
		final int compareValue = id1.compareTo( id2 ); 
		if( compareValue > 0 )
			return 1;
		else if( compareValue < 0 )
			return -1; 
		else 
			return 0; 
		
	}

}
