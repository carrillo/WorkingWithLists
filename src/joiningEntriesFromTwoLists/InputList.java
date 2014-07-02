package joiningEntriesFromTwoLists;

import inputOutput.TextFileAccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import sun.tools.tree.SuperExpression;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

public class InputList extends List {
	
	public InputList( final String fileName, final String separator, final int idField ) 
	{
		super( fileName, separator ); 
		setIdField( idField ); 
	}

	/**
	 * This method reads the associated file and returns a Hashmap 
	 * containing the idField as key and the whole entry as Value
	 */
	public void readList( final boolean verbose ) {
		if ( verbose ) 
			System.out.println("Reading input list from " + getFileName() ); 
		setEntries( new HashMap<String, String[]>() ); 
		try {
			final BufferedReader reader = TextFileAccess.openFileRead( getFileName() );
			
			int lineCount = 0;
			while ( reader.ready() ) {
				String[] entry = reader.readLine().trim().split( getSeparator() ); 
				if( lineCount == 0 ) { setHeader( entry ); }
				else { getEntries().put( entry[ getIdField() ], entry ); }
				lineCount++; 
			}
		}
		catch( IOException e ) { System.err.println("Can't read List: " + getFileName() + " " + e ); } 
	}
	
	/**
	 * This method returns selected fields of the list if an entry of the list of the given id is existing
	 */
	public String[] getFieldsOfInterestForID( final String idOfInterest, final int[] fieldsOfInterest ) {
		
		final String[] entry = new String[ fieldsOfInterest.length ]; 
		final String[] emptyEntry = getMissingEntryValues( fieldsOfInterest, "NA" );   
		
		if( getEntries().containsKey( idOfInterest ) ) 
		{
			final String[] originalEntry = getEntries().get( idOfInterest ); 
			
			for( int i = 0; i < fieldsOfInterest.length; ++i ) 
			{
				entry[ i ] = originalEntry[ fieldsOfInterest[ i ] ]; 
			}
			return entry; 
		}
		else 
		{
			return emptyEntry; 
		}
	}
	
	/**
	 * Generate a String representing missing values. 
	 * @param filedsOfInterest
	 * @return
	 */
	private String[] getMissingEntryValues( final int[] filedsOfInterest, final String missingValueString )
	{
		final String[] out = new String[ filedsOfInterest.length ]; 
		for( int i = 0; i < out.length; i++ )
		{
			out[ i ] = missingValueString; 
		}
		return out; 
	}
	
	public String[] getHeaderEntriesForFieldsOfInterest( final int[] fieldsOfInterest ) {
		final String[] headerEntries = new String[ fieldsOfInterest.length ];
		
		for( int i = 0; i < fieldsOfInterest.length; ++i ) {
			headerEntries[ i ] = getHeader()[ fieldsOfInterest[ i ] ]; 
		}
		
		return headerEntries; 
	}
	
	public String toString() {
		String s = "Input List\n";
		s += super.toString(); 
		return s; 
	}
	
	public static void main(String[] args) {
		final String fileName = "data/input/elongationRatesIntronContainingWildType.txt";
		final String separator = "\t"; 
		final int idField = 0 ; 
		InputList list = new InputList( fileName, separator, idField );  
		
		list.readList( true );
		
		list.printList(); 
		
		list.printList( list.getSortedEntriesArrayList() ); 
		
		/**
		final String idOfInterest = "YGL030W"; 
		final int[] fieldsOfInterest = { 1, 3 }; 
		
		final String[] trimmedEntry = list.getSelectedFieldsForID(idOfInterest, fieldsOfInterest );
		
		if( trimmedEntry == null  ) {
			System.out.println("no entry found"); 
		}
		else {
			for( String s : trimmedEntry ) {
				System.out.println( s ); 
			}
			
		}
		*/
	}
	
}
