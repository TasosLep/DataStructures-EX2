import java.io.*;
import java.util.*; 

public class Read_Priority {
	
	private static final int MAXSIZE = 128;
	static LinkedList<PrintJob> list = new LinkedList<PrintJob>(); //a list that has inside PrintJob Objects after reading the file
	public static void loadFile(String data) throws IllegalArgumentException
	{
		
		int counter = 0;//helping variable for exceptions
		//also helps to count the number of lines of the file
		
		File f = null;
		BufferedReader reader = null;
		String line;
	
	
		try {
			f = new File(data);//creating the file
		} catch (NullPointerException e) {
			System.err.println("File not found");
		}
		
		try {
			reader = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(f)));
		} catch (Exception e) {
			System.err.println("Error opening file!");
			e.printStackTrace();
		}
		
		try{
			int tempTime=0;//helping variable for checking the arrivalTime of every in order to have them in ascending order
			line=reader.readLine();////reading the 1st line
			while(line!=null){
				
				counter ++;
				String[] temp = line.split(" ");
				if(temp.length != 2)
				{
					reader.close();
					throw new IllegalArgumentException("The line must have two integers");
				}
				if(isInteger(temp[0]) && isInteger(temp[1]))//checkinh if the values are integers
				{
					int time = Integer.parseInt(temp[0]);
					int size = Integer.parseInt(temp[1]);
					if(size > 0 && size <= MAXSIZE)//<=maxsize
					{
							if(time >= tempTime)//checking if the arrivalTime of the new file continues the ascending order 
							{
								PrintJob a = new PrintJob(size,time);//creating a new PrintJob Object
								list.addToEnd(a);//adding a new PrintJob Object in the list
								tempTime = time;//update the current temptTime
							}
							else
							{
								reader.close();//closing the file
								throw new IllegalArgumentException("The  Arrival time must like this 0 <= t1 <= t2 <= ...");
							}	
							
					}						
					else
					{
						reader.close();
						throw new IllegalArgumentException("The size of the files must be between 0-128");
					}
				}else
				{
					reader.close();
					throw new IllegalArgumentException("The inputs must be integers");
				}
				line=reader.readLine();//changing line
			}	
		
		}
		catch(IOException e){
			System.err.println("Error reading line" + counter + ".");
		}
		
		try{
			reader.close();
		}
		catch(IOException e){
			System.out.println("Error closing file.");
		}
	}
	
	//###### METHODS ######
	public static boolean isInteger( String input )  //Checking if the input is Integer
	{  
	   try{  
	      Integer.parseInt( input );  
	      return true;  
	   }catch( Exception e ) {  
	      return false;  
	   }  
	}

}
