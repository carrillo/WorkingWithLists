package joiningEntriesFromTwoLists;


public class JoinEntriesForShell {

	protected String inputFile1, inputFile2, outputFile; 
	protected int[] inputFile1FieldsOfInterest, inputFile2FieldsOfInterest;
	
	protected InputList inputList1, inputList2; 
	protected OutputList outputList; 
	
	public JoinEntriesForShell( final String inputFile1, final String inputFile2, final String outputFile,
			final int[] inputFile1FieldsOfInterest, final int[] inputFile2FieldsOfInterest ) 
	{
		this.inputFile1 = inputFile1; 
		this.inputFile2 = inputFile2; 
		this.outputFile = outputFile; 
		
		this.inputFile1FieldsOfInterest = inputFile1FieldsOfInterest; 
		this.inputFile2FieldsOfInterest = inputFile2FieldsOfInterest; 
		
		readInputFiles(); 
		generateOutputFile(); 
		
		//System.out.println( "Done" ); 
	}
	
	/**
	 * This method reads the entries and header sectin of the inputLists 
	 */
	public void readInputFiles() {
		final String separator = "\t"; 
		final int idField = 0; 
		this.inputList1 = new InputList( getInputFile1(), separator, idField ); 
		this.inputList2 = new InputList( getInputFile2(), separator, idField ); 
		
		getInputList1().readList( false ); 
		getInputList2().readList( false ); 
		
		if( getInputFile1FieldsOfInterest() == null )
		{
			this.inputFile1FieldsOfInterest = getAllFieldsWithoutId( getInputList1() ); 
		}
		if( getInputFile2FieldsOfInterest() == null )
		{
			this.inputFile2FieldsOfInterest = getAllFieldsWithoutId( getInputList2() ); 
		}
		
		//System.out.println( getInputList1() ); 
		//System.out.println( getInputList2() ); 
	}
	
	private int[] getAllFieldsWithoutId( final List list )
	{
		final String[] entry = list.getEntriesArrayList().get( 0 ); 
		final int[] fieldIndices = new int[ entry.length - 1 ]; 
		
		int j = 0; 
		for( int i = 0; i < entry.length; i++ )
		{
			if( i != list.getIdField() )
			{
				fieldIndices[ j ] = i; 
				j++; 
			}
		}
		
		return fieldIndices; 
	}
	
	public void generateOutputFile() {
		final String separator = "\t"; 
		this.outputList = new OutputList( getOutputFile(), separator ); 
		
		getOutputList().generateOutputListFromTwoInputLists( getInputList1(), getInputList2(), getInputFile1FieldsOfInterest(), getInputFile2FieldsOfInterest(), false ); 
		getOutputList().writeList();
	}
	
	
	//Getter 
	public String getInputFile1() { return inputFile1; }
	public String getInputFile2() { return inputFile2; }
	public String getOutputFile() { return outputFile; }
	public int[] getInputFile1FieldsOfInterest() { return inputFile1FieldsOfInterest; }
	public int[] getInputFile2FieldsOfInterest() { return inputFile2FieldsOfInterest; }
	public InputList getInputList1() { return inputList1; }
	public InputList getInputList2() { return inputList2; }
	public OutputList getOutputList() { return outputList; } 
	
	public static void main(String[] args) 
	{
		if( args.length != 4 && args.length != 2 )
		{
			final String info = "\n#######################\n" + 
					"java -jar joinListEntries inputList1 -fieldsToKeepinList1=[int,int,int...] inputList2 -fieldsToKeepinList2=[int,int,int...] \n" +
					"#######################\n" +
					"alternatively specify only the lists and all entries will be kept:\n" + 
					"java -jar joinListEntries inputList1 inputList2\n" +
					"#######################\n" +
					"Joins two list if rows share a common id in column 1.\n" +
					"Specify the tab separated list1 (inputList1) and the columns to keep by providing an comma-separated array of integer values -fieldsToKeepinList1=[int,int,int...].\n" +
					"Specify the tab separated list2 (inputList2) and the columns to keep by providing an comma-separated array of integer values -fieldsToKeepinList2=[int,int,int...].\n" +
					"\n"; 
			System.err.println( info );  
		}
		else if( args.length == 4 )
		{
 
			final String inputFile1 = args[ 0 ];
			final String[] if1 = args[ 1 ].substring( args[ 1 ].indexOf("=") + 1).split(",");
			final String inputfile2 = args[ 2 ];
			final String[] if2 = args[ 3 ].substring( args[ 3 ].indexOf("=") + 1).split(",");
			int[] inputFile1FieldsOfInterest = null;
			int[] inputFile2FieldsOfInterest = null;
			try
			{
				inputFile1FieldsOfInterest = new int[ if1.length ];
				for( int i = 0; i < if1.length; i++ )
					inputFile1FieldsOfInterest[ i ] = ( Integer.parseInt( if1[ i ] ) - 1 ); 
					
				
				inputFile2FieldsOfInterest = new int[ if2.length ]; 
				for( int i = 0; i < if2.length; i++ )
					inputFile2FieldsOfInterest[ i ] = Integer.parseInt( if2[ i ] ) - 1 ;  
			}
			catch (Exception e) 
			{
				System.err.println( "Cannot read the field of interest parameters: " + e ); 
				System.exit( 1 );
			}
				
			final String outputFile = "";			
			new JoinEntriesForShell( inputFile1, inputfile2, outputFile, inputFile1FieldsOfInterest, inputFile2FieldsOfInterest );  
		}
		else 
		{
			final String inputFile1 = args[ 0 ];
			final String inputFile2 = args[ 1 ];
			int[] inputFile1FieldsOfInterest = null;
			int[] inputFile2FieldsOfInterest = null;
			final String outputFile = "";			
			new JoinEntriesForShell( inputFile1, inputFile2, outputFile, inputFile1FieldsOfInterest, inputFile2FieldsOfInterest );
		}
	}

}
