package Uebungen1;



import java.util.Iterator;

public class LinkedList<T> implements Iterable<T> {

	private static int llLenght=0;
	private ListNode<T> head;
	

	// constructors
	public LinkedList(){
		setHead(null);
	}


	// properties
	public ListNode<T> getHead() {
		return head;
	}


	public void setHead(ListNode<T> head) {
		this.head = head;
	}


	public static int getLlLenght() {
		return llLenght;
	}


	public static void setLlLenght(int llLenght) {
		LinkedList.llLenght = llLenght;
	}


	// methods

	/** * Pr�ft, ob Liste leer ist. */ 
	public boolean isEmpty() { return (head == null); }

	public boolean isFound(T searchData){
		ListNode<T> actualNode = head;
		while ((actualNode != null) && !searchData.equals(actualNode.getData())) { 
			actualNode = actualNode.getNext(); 
		} 
		if (actualNode == null) { 
			return false; 
		} else { 
			return true; 
		}
	}

	public T findAccount(String accountNumber){
		return null;
	}

	public void insert(T newData){
		setLlLenght(getLlLenght()+1);

		head = new ListNode<T>(head, newData);

	}

	public void remove(T item){		
		ListNode<T> currentNode = head;
		ListNode<T> previousNode = null;

		while((currentNode!=null) && !item.equals(currentNode.getData())){
			previousNode=currentNode;
			currentNode=previousNode.getNext();
		}
		if(currentNode!=null){
			setLlLenght(getLlLenght()-1);
			if(currentNode==head){
				head=currentNode.getNext();
			}else{
				previousNode.setNext(currentNode.getNext());
			}
		}		
	}

	public void print(){
		ListNode<T> currentNode=head;
		while(currentNode!=null){
			System.out.println(currentNode.getData());
			currentNode=currentNode.getNext();
		}
	}

	public int length(){
		return getLlLenght();
	}


	@Override
	public Iterator<T> iterator() {
		return new LinkedListIterator<T>(this);
	}
	


}
