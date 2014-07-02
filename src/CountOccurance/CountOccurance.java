package CountOccurance;

import joiningEntriesFromTwoLists.InputList;

public class CountOccurance {
	
	public static int countIdenticalMatches( final String inputFile, final String separator, 
			final String stringOfInterest, final int fieldOfInterest ) {
		
		InputList list = new InputList(inputFile, separator, 0 );
		list.readList( false ); 
		
		int matchCount = 0; 
		for( String[] entry : list.getEntriesArrayList() ) {
			if( entry[ fieldOfInterest ].equals( stringOfInterest ) ) {
				matchCount ++; 
			}
		}
		
		return matchCount; 
	}
	
	public static double fractionOfIdenticalMatches( final String inputFile, final String separator, 
			final String stringOfInterest, final int fieldOfInterest ) {
		
		InputList list = new InputList(inputFile, separator, 0 );
		list.readList( false );
		final double entryCount = (double) list.getEntriesArrayList().size(); 
		final double matchCount = (double) countIdenticalMatches(inputFile, separator, stringOfInterest, fieldOfInterest);
		
		return (matchCount / entryCount ); 
	}
	
	public static int countContainingMatches( final String inputFile, final String separator, 
			final String stringOfInterest, final int fieldOfInterest ) {
		
		InputList list = new InputList(inputFile, separator, 0 );
		list.readList( false ); 
		
		int matchCount = 0; 
		for( String[] entry : list.getEntriesArrayList() ) {
			if( entry[ fieldOfInterest ].contains( stringOfInterest ) ) {
				matchCount ++; 
			}
		}
		
		return matchCount; 
	}
	
	public static double fractionOfContainingMatches( final String inputFile, final String separator, 
			final String stringOfInterest, final int fieldOfInterest ) {
		
		InputList list = new InputList(inputFile, separator, 0 );
		list.readList( false );
		final double entryCount = (double) list.getEntriesArrayList().size(); 
		final double matchCount = (double) countContainingMatches(inputFile, separator, stringOfInterest, fieldOfInterest);
		
		return (matchCount / entryCount ); 
	}
	
	public static int countMatchingEntries( final String inputFile, final String separator, 
			final String[] entry, final int[] fieldsOfInterest, final boolean[] identical ) {
		InputList list = new InputList(inputFile, separator, 0 ); 
		list.readList( false ); 
		
		int matchCount = 0; 
		for( String[] listEntry : list.getEntriesArrayList() ) {
			int entriesMatchCount = 0; 
			for( int fieldIndex = 0; fieldIndex < fieldsOfInterest.length; ++ fieldIndex ) {
				final String value = entry[ fieldIndex ]; 
				final String valueToMatch = listEntry[ fieldsOfInterest[ fieldIndex ] ]; 
				//System.out.println( value + " " + valueToMatch ); 
				if( identical[ fieldIndex ] && value.equals( valueToMatch ) )
					entriesMatchCount ++; 
				if( !identical[ fieldIndex ] && value.contains( valueToMatch ) )
					entriesMatchCount ++; 
			}
			
			//System.out.println( entriesMatchCount + " " + fieldsOfInterest.length ); 
			if( entriesMatchCount == ( fieldsOfInterest.length - 1 ) )
				matchCount++; 
		}
		
		return matchCount; 
	}
	
	public static double fractionOfMatchingEntries( final String inputFile, final String separator, 
			final String[] entry, final int[] fieldsOfInterest, final boolean[] identical ) {
		
		InputList list = new InputList(inputFile, separator, 0 );
		list.readList( false );
		final double entryCount = (double) list.getEntriesArrayList().size(); 
		final double matchCount = (double) countMatchingEntries(inputFile, separator, entry, fieldsOfInterest, identical); 
		
		return (matchCount / entryCount ); 
	}
	
	
	public static void main(String[] args) {
		final String inputFile = "data/output/spliceSequencesLow.txt";
		final String separator = "\t";
		
		/**
		final String[] entry = { "GTATGT", "AG", "UACUAAC" };
		final int[] fieldsOfInterest = { 1, 2, 3} ;
		final boolean[] identical = { false, false, false }; 
		
		System.out.println( CountOccurance.countMatchingEntries(inputFile, "\t", entry, fieldsOfInterest, identical) ); 
		System.out.println( CountOccurance.fractionOfMatchingEntries(inputFile, "\t", entry, fieldsOfInterest, identical) );
		*/
		
		final String stringOfInterest = "AG"; 
		final int fieldOfInterest = 2; 
		
		System.out.println( CountOccurance.fractionOfContainingMatches(inputFile, separator, stringOfInterest, fieldOfInterest) );
	}

}
