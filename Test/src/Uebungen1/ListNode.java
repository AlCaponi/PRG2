package Uebungen1;



public class ListNode<T> {

	private T data;
	private ListNode<T> next;
	
	public ListNode(ListNode<T> next, T data){
		setData(data);
		setNext(next);
	}

	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}

	public ListNode<T> getNext() {
		return next;
	}

	public void setNext(ListNode<T> next) {
		this.next = next;
	}
}
