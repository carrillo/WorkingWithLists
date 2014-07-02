package joiningEntriesFromTwoLists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class List {
	
	protected String fileName; 
	protected String separator; 

	protected String[] header; 
	protected HashMap<String, String[]> entries; 
	
	protected int idField;
	
	public List( final String fileName, final String separator ) {
		setFileName( fileName ); 
		setSeparator( separator );
	}
	
	public void printList() {
		printList( getEntriesArrayList() ); 
	}
	
	public void printList( final ArrayList<String[]> entriesList ) {
		System.out.println( entryToString( getHeader() ) ); 
		for( String[] entry : entriesList )
			System.out.println( entryToString( entry ) ); 
	}
	
	public String entryToString( final String[] entry ) {
		String s = "";
		
		int valueCount = 0; 
		for( String value : entry ) {
			valueCount++; 
			if( valueCount == entry.length ) 
				s += value; 
			else 
				s += value + getSeparator(); 
		}
		return s; 
	}
	
	/**
	 * Returns all ids as ArrayList
	 * @return
	 */
	public ArrayList<String> getIds()
	{
		ArrayList<String> out = new ArrayList<String>(); 
		for( String[] entry : getEntriesArrayList() )
		{
			out.add( entry[ getIdField() ] ); 
		}
		return out; 
	}
	
	public String toString() {
		String s = "File: " + getFileName() + "\n";
		s += "Nr of columns: " + getHeader().length + "\n";
		s += "Nr of rows: " + getEntriesArrayList().size(); 
		return s; 
	}
	
	//Setter and Getter
	public void setFileName( final String fileName ) { this.fileName = fileName; }
	public String getFileName() { return fileName; }
	
	public void setSeparator( final String separator ) { this.separator = separator; }
	public String getSeparator() { return separator; }
	
	public void setIdField( final int idField ) { this.idField = idField; }
	public int getIdField() { return idField; }
	
	public void setHeader( final String[] header ) { this.header = header; }
	public String[] getHeader() { return header; }
	public String getHeaderField( final int fieldPosition ) { return header[ fieldPosition ]; }
	
	public void setEntries( final HashMap<String, String[]> entries ) { this.entries = entries; }
	public HashMap<String, String[]> getEntries() { return entries; }
	public ArrayList<String[]> getEntriesArrayList() {
		ArrayList<String[]> entries = new ArrayList<String[]>(); 
		
		Collection<String[]> entryCollection = getEntries().values(); 
		for( String[] entry : entryCollection )
			entries.add( entry ); 
		
		return entries;  
	}
	public ArrayList<String[]> getSortedEntriesArrayList() {
		ArrayList<String[]> sortedEntries = getEntriesArrayList();
		Collections.sort( sortedEntries, new SortByID( getIdField() ) ); 
		return sortedEntries; 
	}

}
