import java.util.NoSuchElementException;

public class LinkedList<T> 
{
	//reference to the first element of the list.
	private Node head,tail;
	
	
	LinkedList()
	{
		this.head = new Node(null,null);
		this.tail = new Node(null,null);
	}
	
	class Node
	{
		T data;
		Node next;
		Node()
		{
			this(null,null);
		}
		
		Node(T data,Node next)
		{
			this.data = data;
			this.next = next;
		}
		
		Node getNext(){return next;}
		T getData(){return data;}
	}
	
	public void addToFront(T data)
	{
		if(isEmpty())
			tail.next = head.next = new Node(data,null);
		else 
			head.next = new Node(data,head.getNext());
		
	}
	
	public T getTailData(){return tail.getNext().data;}
	public T getHeadData(){return head.getNext().data;}
	
	public void addToEnd(T data)
	{
		if(isEmpty()) 
			addToFront(data);
		else
		{
			tail.next.next = new Node(data,null);
			tail.next = tail.getNext().getNext();
		}
	}
	
	public T removeFromFront() throws NoSuchElementException
	{
		if(isEmpty()) 
			throw new NoSuchElementException();
		
		T output = head.getNext().getData();
		//if there is only one element in the list
		if(head.getNext().getNext() == null)
		{
			head.next = tail.next = null;
			return output;
		}
		head.next = head.getNext().getNext();//First getNext to go to the first Element
											//second getNext To go to the second Element
		return output;
	}
	
	public T removeFromEnd() throws NoSuchElementException
	{
		if(isEmpty())
			throw new NoSuchElementException();
		
		//Important to check if the list contains only 1 element.
		if(head.getNext().getNext() == null) return removeFromFront();
		Node temp = head.getNext();
		//find the second from the end Node
		while(temp.getNext() != tail.getNext())
			temp = temp.getNext();
		T output = tail.getData();
		temp.next = null;//garbage collector
		tail.next = temp;
		return output;
	}
	
	public Node getHead()
	{
		return head;
	}
	public boolean isEmpty()
	{
		return head.getNext()==null;
	}
	
	//Override
	public String toString()
	{
		String out = "";
		Node temp = head.getNext();
		while(temp!=null)
		{
			out+=temp.getData()+"\n";
			temp = temp.getNext();
		}
		return out;
	}
	
}
