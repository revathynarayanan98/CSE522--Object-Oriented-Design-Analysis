// Assignment 3 Part 1 
//
// Fill in code in class SafeTree.
// 
// In ThreadSafeTree.main(), change the line
//
// Tree tr = new Tree(5000);
//
// to
//
// Tree tr = new SafeTree(5000);
//
// and test your solution.

import java.util.List;
import java.util.ArrayList;
import java.util.Random;


public class ThreadSafeTree { 
	
public static void main(String[] args) {
	
	Tree tr = new SafeTree(5000);
	
	List<InsertNums> threads = new ArrayList<InsertNums>();
	
	threads.add(new InsertNums(tr));
	threads.add(new InsertNums(tr));
	threads.add(new InsertNums(tr));
	threads.add(new InsertNums(tr));
	threads.add(new InsertNums(tr));
	threads.add(new InsertNums(tr));
	threads.add(new InsertNums(tr));
	threads.add(new InsertNums(tr));
	threads.add(new InsertNums(tr));
	threads.add(new InsertNums(tr));
	 
    int N = threads.size();
    
	try {
		System.out.println("Start Parallel Insertions ...");
		for (int i = 0; i < N; i++) {
			threads.get(i).start();
		}
		for (int i = 0; i < N; i++) {
			threads.get(i).join();
		}
		System.out.println("Done Parallel Insertions ...\nResults:");
		tr.print();
	} catch (Exception e) {
	}
}
}

class InsertNums extends Thread {
	Tree tr;

	public InsertNums(Tree tr) {
		this.tr = tr;
	}

	public void run() {
		Random r = new Random();
		for (int i = 0; i < 3; i++)
			tr.insert(r.nextInt(10000));
		tr = null;
	}
}

class Tree {

	public Tree(int n) {
		value = n;
		left = null;
		right = null;
	}

	public void insert(int n) {
		if (value == n) {
			return;
		}
		if (value < n)
			if (right == null)
				right = new Tree(n);
			else
				right.insert(n);
		else if (left == null)
			left = new Tree(n);
		else
			left.insert(n);
	}

	void print() {
		if (left != null)
			left.print();
		System.out.print(value + " ");
		if (right != null)
			right.print();
	}

	protected int value;
	protected Tree left, right;

}

class SafeTree extends Tree {

	public SafeTree(int n) {
		// fill in code here
		super(n);
		locked = true;
	}

	public void insert(int n) {	
		lock();
	  	if (value == n) {
	  	    unlock();
			return;
	  	}
	  	if (value < n) {			
			if (right == null) {					
				right = new SafeTree(n);
				unlock(); 
			}
		    else {
		    	unlock();
				right.insert(n);
			}				  
		}
		else if (left == null) {
				left = new SafeTree(n);
				unlock();
		}	
		else {
				unlock();
				left.insert(n);
		}	
	}

	synchronized void lock() {
		// fill in code here
		while(locked != true) {
				try {
					wait();
				} 
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		locked = false;
	
	}

	synchronized void unlock() {
		// fill in code here
		try {
			locked = true;
			notify();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	boolean locked;
	
}
