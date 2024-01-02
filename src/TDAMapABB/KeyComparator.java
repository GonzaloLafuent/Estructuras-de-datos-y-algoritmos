package TDAMapABB;

import java.util.Comparator;

public class KeyComparator<K> implements Comparator<K> {
	@Override
	public int compare(K k1, K k2) throws ClassCastException{
		return ((Comparable<K>)k1).compareTo(k2);
	}
}
