import java.io.PrintStream;
import java.util.NoSuchElementException;

public class StringStackImpl <T> implements StringStack <T> {
	
	private ListNode<T> firstNode;
	private ListNode<T> lastNode;
	private String name;
	
	public StringStackImpl() {
		this("stack");
	}
	public StringStackImpl(String stackName) {
		name = stackName;
		firstNode = lastNode = null;
	}
	
	public boolean isEmpty() {
		return firstNode == null;
	}

	public void push(T item) {
		ListNode<T> node = new ListNode<T>(item);
		
		if (isEmpty()) // firstNode and lastNode refer to same object
			firstNode = lastNode = node;
		else { // firstNode refers to new node
			node.nextNode = firstNode;
			firstNode = node;
		}
	}

	public T pop() throws NoSuchElementException {
		if (isEmpty()) // throw exception if Stack is empty
			throw new NoSuchElementException(name);

		T removedItem = firstNode.data; // retrieve data being removed

		// update references firstNode and lastNode
		if (firstNode == lastNode)
			firstNode = lastNode = null;
		else
			firstNode = firstNode.nextNode;

		return removedItem; // return removed node data
	}

	public T peek() throws NoSuchElementException {
		if (isEmpty()) // throw exception if Stack is empty
			throw new NoSuchElementException(name);
		return firstNode.data;
	}
	
	public void printStack(PrintStream stream) {
		if (isEmpty()) {
			System.out.printf("Empty %s\n", name);
			return;
		} // end if

		System.out.printf("The %s is: ", name);
		ListNode<T> current = firstNode;

		// while not at end of list, output current node's data
		while(current!=null){
			System.out.printf("%s ", current.data);
			current = current.nextNode;
			
			}
		// end while

		System.out.println("\n");
	} 

	public int size() {
		if (isEmpty()) 
			return 0;
		ListNode<T> current = firstNode;
		int k=0;
		// while not at end of list, output current node's data
		while (current != null) {
			k++;
			current = current.nextNode;
		} // end while

		return k;
	}
	
	
	
	
	
	
	

}