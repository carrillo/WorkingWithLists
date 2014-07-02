package joiningEntriesFromTwoLists;

import inputOutput.TextFileAccess;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class OutputList extends List {
	
	public OutputList( final String fileName, final String separator ) {
		super( fileName, separator ); 
	}
	
	public void generateOutputListFromTwoInputLists( final InputList list1, final InputList list2, 
			final int[] list1FieldsOfInterest, final int[] list2FieldsOfInterest, final boolean verbose ) {
		
		setHeader( generateHeader(list1, list2, list1FieldsOfInterest, list2FieldsOfInterest, verbose ) ); 
		setEntries( generateEntries(list1, list2, list1FieldsOfInterest, list2FieldsOfInterest) );
	}
	
	private HashMap<String, String[]> generateEntries( final InputList list1, final InputList list2, 
			final int[] list1FieldsOfInterest, final int[] list2FieldsOfInterest ) {
		HashMap<String, String[]> entries = new HashMap<String, String[]>(); 
		
		//Generate set of all ids
		HashSet<String> ids = new HashSet<String>();
		ids.addAll( list1.getIds() ); 
		ids.addAll( list2.getIds() ); 
		
		//loop through id set
		for( String id : ids )
		{
			//Get subarrays
			final String[] list1SubArray = list1.getFieldsOfInterestForID( id, list1FieldsOfInterest ); 
			final String[] list2SubArray = list2.getFieldsOfInterestForID( id, list2FieldsOfInterest );
			
			//Check if there are entries in both lists
			if( list1SubArray != null && list2SubArray != null ) 
			{
				final String[] joinedEntry = joinSubEntries(id, list1SubArray, list2SubArray); 
				entries.put( id, joinedEntry ); 
			}
		}
		
		return entries; 
	}
	
	/**
	 * This method returns the header fields for list1 and list2 for the fields of interests only.
	 * The id field is put to the first position 
	 * @param list1 The first input list
	 * @param list2 The second input list
	 * @param list1FieldsOfInterest The fields to be kept from the first list 
	 * @param list2FieldsOfInterest The fields to be kept from the second list 
	 * @return a String[] representing the header 
	 */
	private String[] generateHeader( final InputList list1, final InputList list2, 
			final int[] list1FieldsOfInterest, final int[] list2FieldsOfInterest, final boolean verbose ) {
		
		final String id = list1.getHeaderField( getIdField() ); 
		final String[] list1SubHeader = list1.getHeaderEntriesForFieldsOfInterest( list1FieldsOfInterest ); 
		final String[] list2SubHeader = list2.getHeaderEntriesForFieldsOfInterest( list2FieldsOfInterest ); 
		
		final String[] header =  joinSubEntries(id, list1SubHeader, list2SubHeader );
		
		if( verbose )
		{
			System.out.println("The output list will have the values for: ");
			for( String s : header ) {
				System.out.print( s + "\t" ); 
			}
			System.out.print("\n"); 			
		}
		
		return header; 
	}
	
	public String[] joinSubEntries( final String id, final String[] subStringArray1, final String[] subStringArray2 ) {
		//System.out.println( id ); 
		//System.out.println( subStringArray1.length );
		//System.out.println( subStringArray2.length );
		final String[] joinedEntries = new String[ (1 + subStringArray1.length + subStringArray2.length ) ];
		
		joinedEntries[ 0 ] = id; 
		
		int joinedEntriesIndex = 1; 
		
		for( String s : subStringArray1 ) {
			joinedEntries[ joinedEntriesIndex ] = s;
			joinedEntriesIndex++; 
		}
		for( String s : subStringArray2 ) {
			joinedEntries[ joinedEntriesIndex ] = s;
			joinedEntriesIndex++; 
		}
		
		return joinedEntries; 
	}
	
	
	/**
	 * This method writes the output list to the fileName
	 */
	public void writeList() 
	{
		if( getFileName().equals( "" ) )
		{
			System.out.println( entryToString( getHeader() ) ); 
			for( String[] entry : getSortedEntriesArrayList() ) 
			{
				System.out.println( entryToString( entry ) ); 
			}
		}
		else
		{
			System.out.println("Writing output list to " + getFileName() ); 
			final PrintWriter out = TextFileAccess.openFileWrite( getFileName() ); 
			
			out.println( entryToString( getHeader() ) ); 
			
			for( String[] entry : getSortedEntriesArrayList() ) 
			{
				out.println( entryToString( entry ) ); 
			}
			
			out.close(); 
			
		}
	}
	
	public String toString() {
		String s = "Output List\n";
		s += super.toString(); 
		return s; 
	}
	
	public static void main(String[] args) {
		final String fileName1 = "data/input/elongationRatesIntronContainingWildType.txt";
		final String fileName2 = "data/input/fivePrimeIntensitiesICGs.txt";
		final String separator = "\t"; 
		final int idField = 0 ; 
		InputList list1 = new InputList( fileName1, separator, idField ); 
		InputList list2 = new InputList( fileName2, separator, idField ); 
		
		list1.readList( true ); 
		list2.readList( true ); 
		
		//list1.printList( list1.getSortedEntriesArrayList() );
		//list2.printList( list2.getSortedEntriesArrayList() );

		final int[] list1FieldsOfInterest = { 1 ,3 }; 
		final int[] list2FieldsOfInterest = { 1 };
		
		final String outPutFileName = "data/output/joinedList.txt"; 
		OutputList outputList = new OutputList( outPutFileName, separator );

		//outputList.generateOutputListFromTwoInputLists(list1, list2, list1FieldsOfInterest, list2FieldsOfInterest); 
		outputList.writeList(); 
 	}

}
