
/*
 * Author: Shashank Anand
 * Teacher: Mr. Radulovic
 * Date: 17 June 2019
 * This is an ADT that I made. I use it to store the tanks.
 * The reason I used this class over a normal array is because, like with a few other things in my code, I have future improvements in mind. 
 * I will continue to work on this project over the summer and make it look and play a lot better. I plan to make the game support 3-player, or maybe even 4-player game modes
 * The reason I used this class instead of an ArrayList is because it's more space-efficient. 
 * The fact that ArrayLists are more time efficient doesn't matter as much as the list is small (only 2 elements long). Therefore the time difference is negligible.
 */

public class LinkedList{

	private class Node {
		Node next;
		Tank value;
		boolean hasNext;
		boolean hasValue;

		public Node(Node n, Tank v){
			next = n;
			value = v;
			hasNext = true;
			hasValue = true;
		}

		public Node(Tank v){
			next = null;
			value = v;
			hasNext = false;
			hasValue = true;
		}

		public Node(Node n){
			next = n;
			hasNext = true;
			hasValue = false;
		}

		public Node(){
			next = null;
			hasNext = false;
			hasValue = false;
		}
	}

	private Node head;
	private int size;

	public LinkedList(Tank initial){
		head = new Node(initial);
		size = 1;
	}

	public LinkedList(){
		size = 0;
	}


	public Tank get(int i){
		Node n = head;
		for (int j=0; j<i; j++){
			n = n.next;
		}

		return n.value;
	}
	
	//Inserts an element at the specified index i
	public void insert(int i, Tank j){

		Node n = head;
		for (int k = 1; k<i; k++){
			n = n.next;
		}

		if(size==0){
			head.value = j;
		}
		else{

			if (n.hasNext){
				Node m = n.next;
				n.next = new Node(m, j);
			}
			else{
				n.next = new Node(j);
				n.hasNext = true;
			}	
		}

		size++;
	}
	
	
	public void add(Tank v) {
		if(head != null){
			insert(size, v);
		}
		else{
			head = new Node(v);
			size++;
		}
	}


	public void remove(Tank i){
		Node n = head;
		for (int j = 0; j<size-2; j++){
			if (n.next.value == i){
				if(n.next.hasNext)
					n.next = n.next.next;
				else{
					n.next = null;
				}

				size--;
				break;
			}

			n = n.next;
		}
	}

	public void removeAt(int i){
		Node n = head;

		for (int j = 1; j<i; j++)
			n = n.next;	
		
		if (n.next.hasNext){
			n.next = n.next.next;
		}
		else{
			n.next=null;
		}
		size--;
	}

	public void replace(int i, Tank j){
		Node n = head;
		for (int k = 0; k<i; k++){
			n = n.next;
		}
		n.value = j;

		if (!n.hasValue)
			n.hasValue = true;
	}
	
	public void clear() {
		size = 0;
		head = null;
	}

	public int size(){
		return size;
	}

	public boolean isEmpty(){
		Node n = head;
		for(int i = 0; i<size; i++){
			if (!n.hasValue)  
				return true;
			n=n.next;
		}

		return false;
	}

	public boolean isFull(){
		Node n = head;
		for(int i = 0; i<size; i++){
			if (!n.hasValue)  
				return false;
			n=n.next;
		}

		return true;
	}

}
