package reformatDanisFiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ReformatDanisFiles {

	public ReformatDanisFiles( final String fileName, final int fieldOfInterest )
	{
		ArrayList<ArrayList<String>> list = getList( fileName );
		for( int i = 1; i <= 10; i++ )
		{
			//String name = "column_" + i + ".csv"; 
			writeOutput( list, i, fileName );	
		}
		  
	}
	
	public void writeOutput( final ArrayList<ArrayList<String>> list, final int fieldOfInterest, final String inputFile )
	{
		double[][] outputArray = getEntryArray(list, fieldOfInterest); 
		
		
		PrintWriter out = openFileWrite( inputFile.substring( 0, inputFile.lastIndexOf(".") ) + "column_" + fieldOfInterest + ".csv" ); 
		
		for( int y = 0; y < outputArray[ 0 ].length; y++ )
		{
			for( int x = 0; x < outputArray.length; x++ )
			{
				//if( x == 0 )
					//out.print( outputArray[ x ][ y ] ); 
				//else
					out.print( outputArray[ x ][ y ] + "," ); 
			}
			out.print("\n"); 
		} 
		
		out.close(); 
	}
	
	public double[][] getEntryArray( final ArrayList<ArrayList<String>> list, final int fieldOfInterest )
	{
		final int sizeX = list.size(); 
		int sizeY = -1; 
		for( ArrayList<String> subList : list )
		{
			if( subList.size() > sizeY )
			{
				sizeY = subList.size(); 
			}
		} 
		
		double[][] entryArray = new double[ sizeX ][ sizeY ];  
		
		for( int x = 0; x < sizeX; x++ )
		{
			ArrayList<String> currentList = list.get( x ); 
			for( int y = 0; y < currentList.size(); y++ )
			{
				String[] currentEntries = currentList.get( y ).split(",");
				entryArray[ x ][ y ] = Double.parseDouble( currentEntries[ fieldOfInterest - 1 ] );  

			}
		}
		
		return entryArray; 
	}
	
	public ArrayList<ArrayList<String>> getList( final String fileName )
	{
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		
		BufferedReader reader = openFileRead( fileName );  
		
		try 
		{
			String currentId = "";
			ArrayList<String> currentList = new ArrayList<String>(); 
			while( reader.ready() )
			{
				String line = reader.readLine(); 
				String[] entries = line.split(","); 
				//System.out.println( entries[ 0 ] );
				if( currentId.equals( entries[ 1 ] ) )
				{
					//System.out.println( "CurrentId matches" ); 
					currentList.add( line ); 
				}
				else
				{
					//System.out.println( "CurrentId does not match: " + currentId + ":" + entries[ 1 ]); 
					if( currentList.size() != 0 )
					{
						ArrayList<String> listClone = (ArrayList<String>) currentList.clone(); 
						list.add( listClone ); 
					}
					currentList = new ArrayList<String>(); 
					currentList.add( line ); 
					
					currentId = entries[ 1 ]; 
					
				}
			}
		} catch (Exception e) 
		{
			// TODO: handle exception
		}
		
		//remove header
		list.remove( 0 ); 
		
		return list; 
	}
	
	public static BufferedReader openFileRead(String fileName)
	{
	  BufferedReader inputFile;
	  try
	  {
		inputFile = new BufferedReader(new FileReader(fileName));
	  }
	  catch (IOException e)
	  {
		System.err.println("mpi.fruitfly.general.openFileRead(): " + e);
		inputFile = null;
	  }
	  return(inputFile);
	}

	public static PrintWriter openFileWrite(String fileName)
	{
	  PrintWriter outputFile;
	  try
	  {
		outputFile = new PrintWriter(new FileWriter(fileName));
	  }
	  catch (IOException e)
	  {
		System.err.println("mpi.fruitfly.general.openFileWrite(): " + e);
		outputFile = null;
	  }
	  return(outputFile);
	}

	public static PrintWriter openFileWriteEx( final String fileName ) throws IOException { return new PrintWriter( new FileWriter( fileName ) ); }
	public static BufferedReader openFileReadEx( final String fileName ) throws IOException{ return new BufferedReader( new FileReader( fileName ) ); }
	
	public static void main(String[] args) 
	{
		String fileName = ""; 
		int fieldOfInterest; 
		if( args.length == 2 )
		{
			System.out.println( "Input File: " + args[ 0 ] );
			System.out.println( "Column of Interest: " + args[ 1 ] );
			fileName = args[ 0 ]; 
			fieldOfInterest = Integer.parseInt( args[ 1 ] );
			new ReformatDanisFiles( fileName, fieldOfInterest ); 
		} 
		else 
		{
			System.out.println( "Please specify inputFile and fieldOfInterest" ); 
			//fileName = "danisFile.csv";
			//fieldOfInterest = 4; 			
		}
	}

}
