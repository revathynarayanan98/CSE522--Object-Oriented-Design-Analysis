// Assignment 1 Part 1: Starter Code

class Tree_Test {

	public static void main(String[] args) {
		//System.out.println("Starts at tree test");
		//System.out.println("Insert 100");
		AbsTree tr = new Tree(100);
		
		//System.out.println("Insert 50");
		
		tr.insert(50);		
		//System.out.println("Insert 125");
		tr.insert(125);	
		//System.out.println("Insert 150");
		tr.insert(150);
		//System.out.println("Insert 20");
		tr.insert(20);
		//System.out.println("Insert 75 ");
		tr.insert(75);
		//System.out.println("Insert 20");
		tr.insert(20);
		//System.out.println("Insert 90");
		tr.insert(90);
		//System.out.println("Insert 50 again");
		tr.insert(50);
		//System.out.println("Insert 125 again");
		tr.insert(125);
		//System.out.println("Insert 150 again");
		tr.insert(150);
		//System.out.println("Insert 75 again");
		tr.insert(75);
		//System.out.println("Insert 90 again");
		tr.insert(90);
		
		tr.delete(20);
		tr.delete(20);
		tr.delete(20);
 		tr.delete(150);
		tr.delete(100);
		tr.delete(150);
		tr.delete(125);
		tr.delete(125);
		tr.delete(50);
		tr.delete(50);
		tr.delete(50);
		tr.delete(75);
		tr.delete(90);
		tr.delete(75);
		tr.delete(90);
	}
}

class DupTree_Test {

	public static void main(String[] args) {
		AbsTree tr = new DupTree(100);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(20);
		tr.insert(75);
		tr.insert(20);
		tr.insert(90);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(75);
		tr.insert(90);
		
		tr.delete(20);
		tr.delete(20);
		tr.delete(20);
		tr.delete(150);
		tr.delete(100);
		tr.delete(150);
		tr.delete(125);
		tr.delete(125);
		tr.delete(50);
		tr.delete(50);
		tr.delete(50);
		tr.delete(75);
		tr.delete(90);
		tr.delete(75);
		tr.delete(90);
	}
}

abstract class AbsTree {
	public AbsTree(int n) {
		value = n;
		left = null;
		right = null;
		parent = null; //initially null when new node is created
	}

	public void insert(int n) {
		if (value == n) {
			count_duplicates();
			//System.out.println("after tree set count Value of c is---> "+this.get_count()); //to check if count is updated properly
		}
		else if (value < n)
			if (right == null) {
				right = add_node(n);
				right.parent = this; //sets the right child parent
			} else
				right.insert(n);
		else if (left == null) {
			left = add_node(n);
			left.parent = this; //sets the left child parent
		} else
			left.insert(n);
	}

	public void delete(int n) {  
		AbsTree t = find(n); //find location of the node in tree and returns node if present else returns null
		//System.out.println("Value of t is ---> "+t.value);
		if (t == null) { // n is not in the tree
			System.out.println("Unable to delete " + n + " -- not in the tree!");
			return;
		}

		int c = t.get_count();
		//System.out.println("Value of c is---> "+c);
		if (c > 1) {
			t.set_count(c-1);
			//System.out.println("after tree set count Value of c is---> "+c);
			return;
		}

		if (t.left == null && t.right == null) { // if n is a leaf value
			//System.out.println("Yes "+t.value+" is a leaf node ");
			if (t != this) {
				//System.out.println("Yes its not root "+this.value+" move ahead ");
				case1(t);
			}				
			else
				System.out.println("Unable to delete " + n + " -- tree will become empty!");
			return;
		}
		if (t.left == null || t.right == null) { // t has one subtree only
			//System.out.println("1 sub");
			if (t != this) { // check whether t is the root of the tree
				//System.out.println("1 sub Yes its not root "+this.value+" move ahead ");
				case2(t);
				return;
			} 
			else { // case when root is to be deleted having 1 sub tree only
				//System.out.println("Im the root "+ this.value);
				if (t.right == null)
					case3L(t); // when right sub tree is empty
				else
					case3R(t); // when left sub tree is empty
				return;
			}
		}
		// t has two subtrees; go with smallest in right subtree of t
		case3R(t);
	}
	
	protected void case1(AbsTree t) { // remove the leaf
		// to be filled by you
		//System.out.println("Inside case 1 ");
		if (t.parent == null) {   //if t turns out to be root node which is also leaf node if no other nodes are present
            System.out.println("Unable to delete " + t.value + " -- tree will become empty!");
            return;
        }
		else {
		    if (t.parent.right == t) {
				//System.out.println("To the right " + t.value);
	        	t.parent.right = null;
	        	t.parent = null;
	        	//System.out.println(t.value+" deleted");
	        }
	        else if(t.parent.left == t) {
		    	//System.out.println("To the left " + t.value);
	        	t.parent.left = null;
	        	t.parent = null;
	        }
		}
       
	}

	protected void case2(AbsTree t) { // remove internal node
		// to be filled by you
		//System.out.println("Inside case 2 ");
		if (t.left != null) {  
			//System.out.println("Left not null " + t.value);
			t.left.parent = t.parent;
            if (t.parent.left == t){
                t.parent.left = t.left;
            }
            else
            {
                t.parent.right = t.left; 
            }
            t.left = t.parent = null;
        	
        }
        else if (t.right != null)
        {
        	//System.out.println("Right not null " + t.value);
        	t.right.parent = t.parent;       	
            if (t.parent.right == t) 
            {
            	 t.parent.right = t.right;            
            }
            else
            {
            	 t.parent.left = t.right;
            }
            t.right = t.parent = null;

        }
	}

	protected void case3L(AbsTree t) { // replace t.value and t.count
		// to be filled by you
		//System.out.println("Inside case 3L ");
		int count = 0;
    	count = t.left.max().get_count();
    	t.set_count(count);
    	t.left.max().set_count(1);
    	int temp = 0;
    	temp = t.value;
    	t.value = t.left.max().value;
    	t.left.max().value = temp;
    	t = t.left.max();
    	
    	if(t.parent.left == t)
    	{
    		if(t.left == null)
    			t.parent.left = t.parent = null;
    		else
    			case2(t);
    	}
    	t.parent.right = t.parent = null;
	}

	protected void case3R(AbsTree t) { // replace t.value
		// to be filled by you
		//System.out.println("Inside case 3R ");
		t.set_count(t.right.min().get_count());
    	t.right.min().set_count(1);
    	int temp = 0;  // temp variable to store value to be deleted
    	temp = t.value;
    	t.value = t.right.min().value;
    	t.right.min().value = temp;
    	
    	t = t.right.min(); //replacing deleted value with min value from right sub tree
    	
    	if(t.parent.right == t)
    	{
    		if(t.right == null) {
    			t.parent.right = t.parent = null;
    		}   			
    		else 
    		{
    			case2(t);
    		}
    			
    	}
    	else {
    		t.parent.left = t.parent = null;
    	}
    		
	}

  	private AbsTree find(int n) {
		// to be filled by you
        if(n > value) {
            if(this.right == null) {
            	//System.out.println("1not in the tree!");
                return null;
            	
            } 
            else {
            	//System.out.println("Going back right");
                return this.right.find(n);
            }
        }
        else if(value == n) {
  			//System.out.println("Found   !"+this.value);
            return this;
        }
        else {
            if(this.left == null) {
            	//System.out.println("2not in the tree!");
                return null;
            	
            }
            else {
            	//System.out.println("Going back left");
                return this.left.find(n);
            }
        }	
	}

  	 public AbsTree min() {
		// to be filled by you
  		//System.out.println("Inside min fn. Value of this is "+ this.value);
  		if (this.left == null) {
			//System.out.println("this is end of left sub tree");
			//return;
		}
  		if(this.left != null) {
            return this.left.min();
        }
  		
        return this;
	}

	public AbsTree max() {
		// to be filled by you
		//System.out.println("Inside max fn. Value of this is "+ this.value);
		if (this.right == null) {
			//System.out.println("this is end of right sub tree");
			//return;
		}
		else if (this.right != null) 
		{
            return this.right.max();
        }
		
		//System.out.println("Final max fn. Value of this is "+ this.value);
        return this;
	}

	protected int value;
	protected AbsTree left;
	protected AbsTree right;
	protected AbsTree parent;

	protected abstract AbsTree add_node(int n);
	protected abstract void count_duplicates();
	protected abstract int get_count();
	protected abstract void set_count(int v);
}

class Tree extends AbsTree {
	public Tree(int n) {
		super(n);
	}

	protected AbsTree add_node(int n) {
		//super(n);
		return new Tree(n);
	}

	protected void count_duplicates() {
		;
	}

	protected int get_count() {
		return 1;
		// to be filled by you
	}

	protected void set_count(int v) {
		// to be filled by you
		;
	}
}

class DupTree extends AbsTree {
	public DupTree(int n) {
		super(n);
		count = 1;
	};

	protected AbsTree add_node(int n) {
		return new DupTree(n);
	}

	protected void count_duplicates() {
		count++; 
	}

	protected int get_count() {
		return count;   //returns count of times every node was inserted/deleted and duplicated in dup tree
		// to be filled by you
	}

	protected void set_count(int v) {
		// to be filled by you
		count = v; // the decreased value of count-1 is v which is set in count variable in duptree for every node
	}

	protected int count;
}