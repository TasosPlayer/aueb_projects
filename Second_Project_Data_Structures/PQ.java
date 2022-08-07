
/*
 * 
PriorityQueue.java
 */
import java.util.Comparator;

public class PQ<T> {
    /**
     * Array based heap representation  T
     */
    private Song[] heap;
    /**
     * The number of objects in the heap
     */
    private int size;
    /**
     * Comparator.
     */
    protected Comparator<Song> cmp;
	double pososto=0.0;
    private int capacity;

    
    /**
     * Creates heap with a given capacity and comparator.
     * param capacity The capacity of the heap being created.
     * param cmp The comparator that will be used.
     */
    public PQ(int capacity, Comparator<Song> cmp) {
        if (capacity < 1) throw new IllegalArgumentException();
        
        this.heap = (Song []) new Song[capacity+1];
        this.size = 0;
        this.cmp = cmp;
		this.capacity=capacity;
    }
    /**
     * Inserts an object in this heap.
     * throws IllegalStateException if heap capacity is exceeded.
     * param object The object to insert.
     */
    public void insert(Song object) {
        // Ensure object is not null
		pososto=size/capacity*100;
		if( pososto>75.0)resize();
        if (object == null) throw new IllegalArgumentException();
        // Check available space
        if (size == heap.length - 1) throw new IllegalStateException();
        // Place object at the next available position
        heap[++size] = object;
        // Let the newly added object swim
        swim(size);
		
    }
    /**
     * Removes the object at the root of this heap.
     * throws IllegalStateException if heap is empty.
     * return The object removed.
     */
    public Song getMax() {
        // Ensure not empty
        if (size == 0) throw new IllegalStateException();
        // Keep a reference to the root object
        Song object = heap[1];			
        // Replace root object with the one at rightmost leaf
        if (size > 1) heap[1] = heap[size];
        // Dispose the rightmost leaf
        heap[size--] = null;
        // Sink the new root element
        sink(1);
        // Return the object removed
        return object;
    }
    /**
     * Shift up.
     */
    private void swim(int i) {
        while (i > 1) {  //if i root (i==1) return
            int p = i/2;  //find parent
            int result = cmp.compare(heap[i], heap[p]);  //compare parent with child
            if (result <= 0) return; 				//if child <= parent return
            swap(i, p);                 //else swap and i=p
            i = p;
        }
    }
    /**
     * Shift down.
     */
	 // returns the node at a specific location
	 public Song getHeap(int x){
		return heap[x];
	 }
    private void sink(int i){
        int left = 2*i, right = left+1, max = left;
        // If 2*i >= size, node i is a leaf
        while (left <= size) {
            // Determine the largest children of node i
            if (right <= size) {
                max = cmp.compare(heap[left], heap[right]) < 0 ? right : left;  //<
            }
            // If the heap condition holds, stop. Else swap and go on.
            if (cmp.compare(heap[i], heap[max]) >= 0) return;     //>=
            swap(i, max);
            i = max; left = 2*i; right = left+1; max = left;
        }
    }
	public Song Max(){
		return heap[1];
	}


	
	public boolean isEmpty(){
		return size==0;
	}
	public void resize(){
		this.capacity=capacity*2;
	}
    
    /**
     * Interchanges two array elements.
     */
    private void swap(int i, int j) {     
        Song tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }
    public void print() {
        for (int i=1; i<=size; i++){
            System.out.println(heap[i].getName());
        }
        System.out.println();
    }
	public int getSize(){
		return size;
	}
	public Song remove(int id){
		int parent;		
        Song x=new Song();
		for(int i=1;i<=size;i++){
			if(heap[i].getId()==id){
			
				x=heap[i];
				heap[i]=heap[size];
				size--;
				parent=i/2;
				if(i==1||heap[parent].getLikes()<heap[i].getLikes()){
					sink(i);
				}
				else{
					swim(i);
				}
				
			}		
		}
		return x;
	} 														
}
