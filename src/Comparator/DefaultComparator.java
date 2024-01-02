package Comparator;

public class DefaultComparator <E> implements java.util.Comparator <E> {
	
	public int compare (E A, E B) throws ClassCastException {
		return ((Comparable <E>) A).compareTo (B);
	}

}
