import java.io.*;
import java.util.*;

public class AlgorithmB
{
	//T is the current time
	//it must be static because we use it in
	//the toString implemetation of PrintJob
	public static int T = 0;
	
	public static void runB(String data,int i)
	{
		//totalwaitingTime is the sum of all the waiting times
		//maxwatingtime is the maximum waiting time
		//fileIdmaxWatingtime is the id of the printJob with the hightest waiting time
		//pq is the priority queue
		long totalwaitingTime = 0, maxwaitingTime = 0, fileIdmaxWaitingtime = 0;
		MaxPQ<PrintJob> pq;
		File f = null;
		BufferedWriter writer = null;

		try {
			f = new File("outputAlgorithmB\\outputB" + i + ".txt");
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
			
			Read_Priority.loadFile(data);

			pq = new MaxPQ<PrintJob>(3);
			
			
			while(!Read_Priority.list.isEmpty())
			{
				//printJob represents the current job of the printer
				PrintJob printJob = Read_Priority.list.removeFromFront();
				pq.insert(printJob);
				T += printJob.getarrivalTime() - T;
				//insert to pq all the PrintJobs that came simultaneously
				while(!Read_Priority.list.isEmpty() && printJob.getarrivalTime() == Read_Priority.list.getHeadData().getarrivalTime())
				{
					pq.insert(Read_Priority.list.removeFromFront());
					
				}
				
				
				while(!pq.isEmpty())
				{
					//take the PrintJobs with the highest priority
					printJob = pq.getMax();
					printJob.setwaitingTime(T - printJob.getarrivalTime());
					//check for maximum waiting time and id
					if(maxwaitingTime <= printJob.getwaitingTime())
					{
						maxwaitingTime = printJob.getwaitingTime();
						fileIdmaxWaitingtime = printJob.getid();
					}
					totalwaitingTime += printJob.getwaitingTime();
					
					T+=printJob.getsize();//increment time
					
					//write the correct output to the file
					//using toString...
					writer.write(printJob + "\r\n");
					
					//update the pq with all the PrintJobs that came
					//during the printing of the last PrintJob
					while(!Read_Priority.list.isEmpty() && T >= Read_Priority.list.getHeadData().getarrivalTime())
					{
						pq.insert(Read_Priority.list.removeFromFront());	
					}
					
					

				}				
			}
			//write the total and average waiting time to the file
			writer.write("\r\nAverage waiting time = " + totalwaitingTime/PrintJob.idCount);
			writer.write("\r\nMaximum waiting time = " + maxwaitingTime + ", achieved by file " + fileIdmaxWaitingtime);		
			//reset the idCount to 0 so that when we run
			//AlgorithmB or C again the id count will be correct	
			PrintJob.idCount = 0;
		}catch (IOException e) {
			System.err.println("Write error!");
		}catch(IllegalArgumentException e){
			System.err.println("Number of PrintJob objects read successfully = " + PrintJob.idCount);
		}
			
		
		try {
			writer.close();
		} catch (IOException e) {
			System.err.println("Error closing file.");
		}
	}
	
}
