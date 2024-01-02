package TDAColaCP;

import Exceptions.EmptyPriorityQueueException;
import Exceptions.InvalidKeyException;

public class HeapSort {
	public static<V> void heapSort(V[] a) {
		PriorityQueue<V,V> col = new Heap<V,V>(new DefaultComparator<V>());
		int n = a.length;
		try {
			for(int i=0; i<n; i++) {
				col.insert(a[i], null);
				a[i] = null;
			}
			for(int i=0; i<n; i++) {
				a[i] = col.removeMin().getKey();
			}
		} catch(InvalidKeyException | EmptyPriorityQueueException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static <E> String toString (E [] A) { 
	    String msg = new String ();

	    for (E elem: A) {
	        msg += elem.toString() + " ";
	    }

	    return ("[ " + msg + "]");
	}

	public static void main (String [] args) {
	    Integer A [] = {9,8,7,6,5,4,3,2,1};
	    Character C [] = {'U','A','E','I','O'};
	    String S [] = {"Gonzalo P","Gonzalo L","Bruno","Albano","Nicolas"};

	    System.out.println (toString (A));
	    heapSort(A);
	    System.out.println (toString (A));
	    System.out.println();

	    System.out.println (toString (C));
	    heapSort(C);
	    System.out.println (toString (C));
	    System.out.println();

	    System.out.println (toString (S));
	    heapSort (S);
	    System.out.println (toString (S));
	    System.out.println();
	}
}
