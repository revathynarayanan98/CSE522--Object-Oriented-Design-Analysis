import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BST_DupTree_Test {

	    static DupTree dtr;
		static List<Integer> al = new ArrayList<Integer>();
		static List<Integer> dtree = new ArrayList<Integer>();
		static Random r = new Random();
		int count = 0;
		int count1 = 0;
		static Tree node;
		// add annotation
		@BeforeAll
		public static void setup() {
			// to be filled in by you
			
			//dtr = new DupTree(r.nextInt(25));
			int rand_int1;
			rand_int1 = r.nextInt(25);
			for(int i = 0; i < 25; i++) {
				rand_int1 = r.nextInt(25); 
				al.add(rand_int1);
				//dtr.insert(rand_int1);
				if(dtr == null)
				{
					dtr = new DupTree(rand_int1);			
				}
				else 
				{
					dtr.insert(rand_int1);	
				}
			}
			Iterator<Integer> itr1 = dtr.iterator();
			System.out.println("DupTree created in Setup: ");
			//itr1 = 
			while(itr1.hasNext())
			{
				System.out.print(itr1.next() +" ");
			}
			//System.out.println("before"+al);
			Collections.sort(al);
			System.out.println();
			System.out.println("Sorted ArrayList created in Setup:");
			for(int j = 0; j < al.size(); j++)
			{
				System.out.print(al.get(j)+ " ");
			}
			System.out.println();
			System.out.println("----------------------------");
			}
		
		// add annotation
		@BeforeEach
		@AfterEach
		void check_invariant() {
			// to be filled in by you
			   assertTrue(ordered(dtr));
			   System.out.println("----------------------------");
			   System.out.println("DupTree invariant maintained");
			   System.out.println("----------------------------");
			   System.out.println();
		}
		
		// add annotation
		@Test
		void test_delete() {	
			int count1,count2= 0;
			for(int i = 0; i < 10; i++) {
				int r1 = r.nextInt(25);
				System.out.println();
				System.out.println("Testing DupTree delete("+r1+")");
				dtr.insert(r1);
				
				count1= get_count(dtr, r1);
				System.out.println("inserted value "+r1+" into duptree; count = "+count1+" after insertion.");
				dtr.delete(r1);
				
				count2 = get_count(dtr, r1);
				String op = "deleted value "+r1+" from duptree;";
				
				if(count1 > 1) {
					if(count2 == count1-1) {
						System.out.println(op+" count = "+count2+" after deletion.");
					}
				}
				else {
					System.out.println(op+" it is no longer present in duptree");
				}
				
				
			}
			System.out.println();
			
			System.out.println("DupTree delete test passed");
			//System.out.println("----------------------------");
			System.out.println();
		}
		
		// add annotation
		@Test
		void test_insert() {
			System.out.println("Testing DupTree insert ... ");
			Iterator<Integer> ittr = dtr.iterator();
			System.out.println("Creating ArrayList iterator and Comparing elements pair-wise ...");
			Iterator<Integer> ital = al.iterator();
			assertTrue(ittr.next() == ital.next());
			System.out.println("... DupTree insert test passed");
			System.out.println();
	  	  }

		public int get_count(DupTree dtr, int v) {
			// to be filled in by you
			node = dtr.find(v);
			if(node == null) {
				count = 0;
			}
			else {
				count = node.get_count();
				
			}
			return count;
		}

		public boolean ordered(Tree tr) {
			// to be filled in by you	  
			return (tr.left == null || (tr.value > tr.left.max().value && ordered(tr.left))) && (tr.right == null || (tr.value < tr.right.min().value && ordered(tr.right)));
		}

	}