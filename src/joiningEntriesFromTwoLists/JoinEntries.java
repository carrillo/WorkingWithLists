package joiningEntriesFromTwoLists;

import sun.security.action.GetLongAction;

public class JoinEntries {

	protected String inputFile1, inputFile2, outputFile; 
	protected int[] inputFile1FieldsOfInterest, inputFile2FieldsOfInterest;
	
	protected InputList inputList1, inputList2; 
	protected OutputList outputList; 
	
	public JoinEntries( final String inputFile1, final String inputFile2, final String outputFile,
			final int[] inputFile1FieldsOfInterest, final int[] inputFile2FieldsOfInterest ) {
		this.inputFile1 = inputFile1; 
		this.inputFile2 = inputFile2; 
		this.outputFile = outputFile; 
		
		this.inputFile1FieldsOfInterest = inputFile1FieldsOfInterest; 
		this.inputFile2FieldsOfInterest = inputFile2FieldsOfInterest; 
		
		readInputFiles(); 
		generateOutputFile(); 
		
		System.out.println( "Done" ); 
	}
	
	/**
	 * This method reads the entries and header sectin of the inputLists 
	 */
	public void readInputFiles() {
		final String separator = "\t"; 
		final int idField = 0; 
		this.inputList1 = new InputList( getInputFile1(), separator, idField ); 
		this.inputList2 = new InputList( getInputFile2(), separator, idField ); 
		
		getInputList1().readList( true ); 
		getInputList2().readList( true ); 
		
		System.out.println( getInputList1() ); 
		System.out.println( getInputList2() ); 
	}
	
	public void generateOutputFile() {
		final String separator = "\t"; 
		this.outputList = new OutputList( getOutputFile(), separator ); 
		
		getOutputList().generateOutputListFromTwoInputLists( getInputList1(), getInputList2(), getInputFile1FieldsOfInterest(), getInputFile2FieldsOfInterest(), true );
		System.out.println( getOutputList() ); 
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
	
	public static void main(String[] args) {
		final String inputFile1 = "/Users/carrillo/Documents/workspace/WorkingWithLists/data/input/fiveHighNew.txt";
		final String inputfile2 = "/Users/carrillo/Documents/workspace/WorkingWithLists/data/input/orf_transcriptome.txt";
		final String outputFile = "/Users/carrillo/Documents/workspace/WorkingWithLists/data/output/fiveHighTranscriptionDataNew.txt";
		
		final int[] inputFile1FieldsOfInterest = { 1 }; 
		final int[] inputFile2FieldsOfInterest = { 3 };
		
		new JoinEntries( inputFile1, inputfile2, outputFile, inputFile1FieldsOfInterest, inputFile2FieldsOfInterest );  
	}

}
