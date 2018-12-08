
public class PrintJob implements Comparable<PrintJob> {
	
	private long id,size,waitingTime,arrivalTime,priority;
	public static int idCount=0;
	
	//giving id to any new PrintJob object that is being insert
	private void id()
	{
		idCount++;
	}
	
	
	public PrintJob(int size,int arrivalTime)
	{
		this.size = size;
		this.arrivalTime = arrivalTime;
		priority = 127 - (size - 1);//initializing the priority of a new PrintJob object
		waitingTime = 0;
		id();
		id = idCount;
	}
	
	//-------GETTERS ---------
	public long getid()
	{
		return id;
	}
	
	public long getsize()
	{
		return size;
	}
	
	public long getwaitingTime()
	{
		return waitingTime;
	}
	
	public long getarrivalTime()
	{
		return arrivalTime;
	}
	
	public long getpriority()
	{
		return priority;
	}
	
	//-------SETTERS ---------
	public void setid(int id)
	{
		this.id = id;
	}
	
	public void setsize(long size)
	{
		this.size=size;
	}
	
	public void setwaitingTime(long waitingTime)
	{
		this.waitingTime=waitingTime;
	}
	
	public void setpriority(long priority)
	{
		this.priority=priority;
	}
	
	public int compareTo(PrintJob other )//comparing to PrintJobs objects
	{
		if(this.getpriority() > other.getpriority())
			return 1;
		else if(this.getpriority() == other.getpriority())
			return 0;
		else
			return -1;
	}
	
	public String toString()//to String()
	{
		return ("t = " + AlgorithmB.T + ", completed file " + getid());
	}
	
}
