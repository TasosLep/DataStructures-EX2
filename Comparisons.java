import java.io.*;
import java.util.*;

public class Comparisons
{
	static int counter = 1, ranArrTime = 0,m = 1, temp = 60, stepForTarrival = 3;
	//counter: helping vairiable for giving names to the new files
	//ranArrTime: helping vairiable for giving random arrivalTimes to any new PrintJob Object(Whenever we close the file in order to create a new one,the variable ranArrTime returns to '0',beause it's a static variable)
	//temp: helping vairiable to separate Nnumber to small teams (60 files in every team)
	//stepForTarrival: helping vairiable that increases the bounds for the  random arrivalTimes of the files (Whenever we close the file in order to create a new one,the variable stepForTarrival returns to '3' ,beause it's a static variable)
	//m: helping vairiable to count the teams(of the files) that we already have created right now.(Whenever we close the file in order to create a new one,the variable m returns to '1' ,beause it's a static variable)
	static int[] array = new int [5];// an array that will kepp inside the 5 random number that we need
	static Random random = new Random();
	
	//Method that creates 5 random numbers between(100-50000) using the randombound method to create these numbers
	public static int[] rdm()
	{
		for (int i = 0; i < 5; i++)
		{
			array[i] = randombound(100,50000);//setting the bounds
		}
		for (int i = 0; i < 5; i++)
		{
			for (int j=0; j < 5; j++)
			{
				if(((Math.abs((array[i] - array[j])) < 5000) && i != j) || array[j] < 100) // checking if the random is elegible to our restriction
					rdm();
			}
		}
		return array;
	}
	
	//Methos that return a random number inside the bounds
	public static int randombound(int min, int max)
	{
		int numberrandom = random.nextInt(max);//creating a random number

		return numberrandom;
	}
	
	public static int randomArrivalTime(int T,int j)
	{
		
		int tempnum = randombound(T,stepForTarrival);//creating a temporary random number for arrivalTime of a file
		while(true)
		{
			if(ranArrTime > tempnum)//checking if the temporary arrivalTime is smaller than the arrivalTime of the previous file
				tempnum = randombound(T,stepForTarrival);//creating a new temporary random number for arrivalTime of a file
			else
			{
				ranArrTime = tempnum;
				break;
			}				
		}
		
		// /*Separeting N numbers to small teams in order to manage the arrivalTime of the files
			//the bound cannot be more than 900
			if( j>temp*m && stepForTarrival <= 900)
			{
				stepForTarrival +=1;//increasing the bounds
				m++;//increasing the counter of the teams
			}
			if( j>temp*m && stepForTarrival > 900)
			{
				m++;//increasing the counter of the teams
			}	
			if(stepForTarrival < 900 && j<temp*m)
				return ranArrTime;
		// *\
			
		return ranArrTime;
	}
	
	//Method that created 10 files with just taking the number of lines that must be int this 10 files
	public static void createrfiles(int N)
	{
		
		for (int i = 0; i < 10; i++)
		{
			File f = null;
			BufferedWriter writer = null;
			
			try {
				
				f = new File("subdirectory\\File" + counter + ".txt");//creating a file
			} catch (NullPointerException e) {
				System.err.println("File not found");
			}

			try {
				writer = new BufferedWriter(
					new OutputStreamWriter(
						new FileOutputStream(f)));
			} catch (FileNotFoundException e) {
				System.err.println("Error opening file for writing!");
			}
			
			try{
				for(int j = 0; j < N; j++)//N lines
				{
					int t = randombound(1,128);
					while (t == 0)//the size of the file must not be zero
						t = randombound(1,128);
					writer.write(randomArrivalTime(ranArrTime,j) + " ");//creating random arrivalTimes for the files
					writer.write(t + "");//creating random sizes for the files //convert to string -> "" <-
					if(j != N -1 )
						writer.write("\r\n");//changing line
						
				}
					
			}catch (IOException e) {
				System.err.println("Write error!");
			}catch(IllegalArgumentException e){
				System.out.println("Number of PrintJob objects read successfully = " + PrintJob.idCount);
			}
			
			try {
				writer.close();//closing the file
				ranArrTime = 0;
				counter ++;//updating counter in order to take the correct (file)name the next file 
				stepForTarrival = 3; m = 1;//updating the static variables
			} catch (IOException e) {
				System.err.println("Error closing file.");
			}
		}
	}
	
	//creating 50 output files for AlgorithmB and for AlgorithmC
	public static void outputfiles(String data,int i)
	{
		AlgorithmB.runB("subdirectory\\File" + i + ".txt", i);//creating an AlgorithmB file 
		AlgorithmC.runC("subdirectory\\File" + i + ".txt", i);//creating an AlgorithmC file 
	}
	
	public static void main(String[] args)
	{
		//AlgorithmB.runB("data.txt");//creating an AlgorithmB file 
		//AlgorithmC.runC("data.txt");//creating an AlgorithmC file 
		
		Comparisons.rdm();//calling the method rdm(to create 5 random numbers)
		System.out.println("The random numbers are : ");
		for(int i = 0; i < 5; i++)
			System.out.println(array[i]);
		
		try
		{
			System.out.println("Please wait for a little time to create the 50 files(about 1 - 3 minute for big numbers)");
			for (int i = 0; i < 5; i++)//creating from 5 numbers 50 files
			{
				createrfiles(array[i]);//creating 10 file with a specific number of lines
				System.out.println("We have already created " + (i+1)*10 + " files.");
			}
			
			System.out.println("Creating output files");
			for(int i = 1; i <= 50; i++)
			{
				outputfiles("subdirectory\\File" + i + ".txt",i);
				System.out.println(i +  "created");
			}
			
		}catch(Exception e){
        System.err.println("Error");
      }
	}
}