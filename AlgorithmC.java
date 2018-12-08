import java.io.*;
import java.util.*;

public class AlgorithmC
{	
	public static void runC(String data,int i)
	{
		//sumTime is the sum of time from one priority update to an other
		//T is the current time
		//T0 is the time before an event takes place
		//totalwaitingtime is the sum of all the waiting times
		//maxwatingtime is the maximum waiting time
		//fileIdmaxWatingtime is the id of the printJob with the hightest waiting time
		//pq is the priority queue
		//public static int sumTime = 0, T = 0, T0 = 0, totalwaitingtime=0, maxwaitingtime = 0, fileIdmaxWaitingtime = 0;
		//public static MaxPQ<PrintJob> pq;
		long sumTime = 0, T = 0, T0 = 0, totalwaitingtime=0, maxwaitingtime = 0, fileIdmaxWaitingtime = 0;
		MaxPQ<PrintJob> pq;
		File f = null;
		BufferedWriter writer = null;
		
		try {
			f = new File("outputAlgorithmC\\outputC" + i + ".txt");
		} catch (NullPointerException e) {
			System.err.println("File not found");
		}

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
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
				T += printJob.getarrivalTime() - T;//increment T
				printJob.setwaitingTime(T-printJob.getarrivalTime());//set the initial waitng time
				//insert to pq all the PrintJobs that came simultaneously
				while(!Read_Priority.list.isEmpty() && printJob.getarrivalTime() == Read_Priority.list.getHeadData().getarrivalTime())
				{
					printJob = Read_Priority.list.removeFromFront();
					printJob.setwaitingTime(T-printJob.getarrivalTime());//set the initial waitng time
					pq.insert(printJob);
					
				}
				
				while(!pq.isEmpty())
				{
					//take the PrintJobs with the highest priority
					printJob = pq.getMax();
					//check for maximum waiting time and id
					if(maxwaitingtime <= printJob.getwaitingTime())
					{
						maxwaitingtime = printJob.getwaitingTime();
						fileIdmaxWaitingtime = printJob.getid();
					}
					totalwaitingtime += printJob.getwaitingTime();
					
					T0 = T;//time before increment
					T+=printJob.getsize();//increment time
					sumTime+=T-T0;
					//now check if 15 or more secs have passed since the last update 
					if(sumTime >= 15)
					{
						//update both time and priority
						//and set sumTime to 0
						pq.updateWaitingTime(T-T0);
						pq.updatePriorities();
						sumTime = 0;
					}else
					{
						//if it is not time to update the priorities
						//just update the waiting time of every entry in the queue
						pq.updateWaitingTime(T-T0);
					}
					
					//write the correct output to the file
					writer.write("t = " + T + ", completed file " + printJob.getid() +"\r\n" );
					
					//update the pq with all the PrintJobs that came
					//during the printing of the last PrintJob
					while(!Read_Priority.list.isEmpty() && T >= Read_Priority.list.getHeadData().getarrivalTime())
					{
						printJob = Read_Priority.list.removeFromFront();
						printJob.setwaitingTime(T-printJob.getarrivalTime());//set the initial waitng time
						pq.insert(printJob);
						
					}
				}				
			}
			
			//write the total and average waiting time to the file
			writer.write("\r\nAverage waiting time = " + totalwaitingtime/PrintJob.idCount);
			writer.write("\r\nMaximum waiting time = " + maxwaitingtime + ", achieved by file " + fileIdmaxWaitingtime);
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
