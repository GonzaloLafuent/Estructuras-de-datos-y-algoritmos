package TDAArbolBusqBalan;

import java.util.Comparator;

public class Entrada <K,V> implements Entry <K, V>{

	private K key;
	private V value;
	
	public Entrada (K key, V value) {
	    this.key = key;
	    this.value = value;
	}
	
	public K getKey() {
	    return key;
	}
	
	public V getValue() {
	    return value;
	}
	
	public void setKey (K key) {
	    this.key = key; 
	}
	
	public void setValue (V value) {
	    this.value = value; 
	}
	
	public String toString () {
	    return "("+getKey() + "," + getValue()+")";
	}

	public int compareTo(Entry<K,V> e) {
		Comparator<K>comp = new KeyComparator<K>();
		return comp.compare(key,e.getKey());
	}

}

