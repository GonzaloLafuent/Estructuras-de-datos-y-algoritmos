package TDAArbolBinario;

public class Comparator<E extends Comparable<E>> implements java.util.Comparator<E>{
	public int compare(E o1, E o2) {
		return o1.compareTo(o2);
	}
	
}
