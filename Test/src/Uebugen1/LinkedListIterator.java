

import java.util.Iterator;

public class LinkedListIterator<T> implements Iterator<T> {

	private final LinkedList<T> linkedList;
	private ListNode<T> current=null;
	private boolean isFirst=true;
	
	public LinkedListIterator(LinkedList<T> linkedList1){
		this.linkedList=linkedList1;
		current=this.linkedList.getHead();
		
	}
	
	public LinkedList<T> getLinkedList() {
		return linkedList;
	}

	
	@Override
	public boolean hasNext() {
		if(current!=null){
			return current.getNext()!=null;
		}else{
			return false;
		}
	}

	@Override
	public T next() {    
		if(!hasNext()){
			return null;
		}else{
			if(!isFirst){
				current =current.getNext();
			}else{
				current=this.linkedList.getHead();
				isFirst=false;
			}
			return current.getData();
		}
	}
	
	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
