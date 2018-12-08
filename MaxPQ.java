import java.util.*;

public class MaxPQ<T extends Comparable<T>>
{
	private  T []heap;
	private  int size;//the actual size of the queue
	
	//constructor
	public MaxPQ(int capacity) throws IllegalArgumentException
	{
		//This is an unsafe method to create Generic arrays 
		//check if capacity is valid
		if(capacity<=0) throw new IllegalArgumentException("The capacity must be a positive number!");
		heap = (T[])new Comparable[capacity+1];//root at index 1
		size = 0;
	}
	
	//returns the size
	public int size()
	{
		return size;
	}
	
	//returns the item with the highest priority
	public T getMax() throws NoSuchElementException
	{
		if(isEmpty()) throw new NoSuchElementException("Empty Queue!");
		//should I check when the queue has 1 element?
		T temp = heap[1];
		swap(1,size);
		heap[size--] = null;
		sink(1);
		
		return temp;
	}
	
	//Note that this method works only for PrintJob instances
	//because we need to use setpriority and getpriority.
	//This method was created for the third part of the assignment.
	public void updatePriorities()
	{
		for(int i = 1; i <= size ; ++i)
		{
			//downcast check
			if(heap[i] instanceof PrintJob)
			{
				((PrintJob)heap[i]).setpriority(127 <= ((PrintJob)heap[i]).getpriority() + ((PrintJob)heap[i]).getwaitingTime() ? 127 : ((PrintJob)heap[i]).getwaitingTime() + ((PrintJob)heap[i]).getpriority() );
				//we need to swim to guarantee the heap condition
				//since the priorities only increase
				swim(i);
			}
		}
	}
	
	//Note that this method works only for PrintJob instances
	//because we need to use setwaitingTime, getwaitingTime
	//This method was created for the third part of the assignment.
	public void updateWaitingTime(long time)
	{
		for(int i = 1; i <= size ; ++i)
		{
			//downcast check
			if(heap[i] instanceof PrintJob)
			{
				((PrintJob)heap[i]).setwaitingTime(time + ((PrintJob)heap[i]).getwaitingTime());
			}
		}
	}
	
	//returns the element with the highest priority
	//without removing it
	public T peek() throws NoSuchElementException
	{
		if(isEmpty()) throw new NoSuchElementException("Empty Queue!");
		return heap[1];
	}
	
	//check if the queue is empty
	public boolean isEmpty(){return size == 0;}
	
	//insert an element to the queue
	public void insert(T item)
	{
		//check for resize
		if(size+1 > 75*heap.length/100)resize();
		heap[++size] = item;
		swim(size);
	}
	
	//resize the array
	private void resize()
	{
		heap = Arrays.copyOf(heap,heap.length+10);
	}
	
	private void sink(int index)
	{
		if(index <= 0 && index <= heap.length) return;
		
		while(2*index<=size)//while not leaf
		{
			int left = index*2,right = index*2 + 1;
			int max = left;
			
			if(2*index +1 <= size) max = heap[right].compareTo(heap[left]) < 0 ? left : right;//find max

			if(((T)heap[index]).compareTo(heap[max]) < 0)swap(index,max);
			
			index = max;
		}
	}
	
	private void swim(int index)
	{
		if(index <= 0 && index <= heap.length) return;
		
		while(index > 1 && ((T)heap[index]).compareTo(heap[index/2]) >= 0)//while not root and grater than it's parent
		{
			swap(index,index/2);
			index = index/2;
		}
	}
	
	private void swap(int i,int j)
	{
		T temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}
	
	//override
	public String toString()
	{
		String output = "";
		for(int i = 1;i <=size;++i)output+=heap[i]+"\n";
		
		return output;
	}
}
