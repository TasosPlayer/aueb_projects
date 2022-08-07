import java.io.PrintStream;
import java.util.NoSuchElementException; 

public class IntQueueImpl <T> implements IntQueue <T> { 

	private ListNode<T> firstNode;
	private ListNode<T> lastNode;
	private String name;
	
	public IntQueueImpl() {
		this("queue");
	}
	public IntQueueImpl(String queueName) {
		name = queueName;
		firstNode = lastNode = null;
	}
	public boolean isEmpty() {
		return firstNode == null;
	}
	public void put(T insertItem) {
		ListNode<T> node = new ListNode<T>(insertItem);
		
		if (isEmpty()) // firstNode and lastNode refer to same Object
			firstNode = lastNode = node;
		else { // lastNode's nextNode refers to new node
			lastNode.nextNode = node;
			lastNode = node;
			// you can replace the two previous lines with this line: lastNode =
			// lastNode.nextNode = new ListNode( insertItem );
		}
	} // end method insertAtBack
	public T get() throws NoSuchElementException {
		if (isEmpty()) // throw exception if List is empty
			throw new NoSuchElementException(name);

		T removedItem = firstNode.data; // retrieve data being removed

		// update references firstNode and lastNode
		if (firstNode == lastNode)
			firstNode = lastNode = null;
		else
			firstNode = firstNode.nextNode;

		return removedItem; // return removed node data
	} // end method removeFromFront
	
	public T peek() throws NoSuchElementException {
		if (isEmpty()) // throw exception if Stack is empty
			throw new NoSuchElementException(name);
		return firstNode.data;
	}
	
	
	//methodos gia print 
	public void printQueue(PrintStream stream) {
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
		public void setfirstNode( T input) {
			firstNode.data = input;
		}
	}