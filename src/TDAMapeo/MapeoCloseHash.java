package TDAMapeo;

import Exceptions.InvalidKeyException;
import TDAlista.ListaDoblementeEnlazada;
import TDAlista.PositionList;

public class MapeoCloseHash<K,V> implements Map<K,V> {
	private Entry<K,V> Available = new Entrada<K,V>(null,null); 
	private Entry<K,V> A[];
	private int n;
	private int N;
	private int scale,shift;
	
	public MapeoCloseHash() {
		this(7);
	}
	
	public MapeoCloseHash(int c) {
		n = 0;
		N = c;
		A = (Entry<K,V>[]) new Entrada[N];
		java.util.Random ramd = new java.util.Random();
		scale = ramd.nextInt(N-1)+1;
		shift = ramd.nextInt(N);
	}
	
	public int size() {
		return n;
	}

	public boolean isEmpty() {
		return n==0;
	}

	public V get(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("La clave es nula");
		int i = findEntry(key);
		if(i<0)
			return null;
		return A[i].getValue();
	}

	public V put(K key, V value) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("La clave es nula");
		int i = findEntry(key);
		if(i>=0) {
			V aux = A[i].getValue();
			((Entrada <K,V>) A[i]).setValue(value);
			return aux;
		}
		if( n>=N/2) {
			rehash();
			i = findEntry(key);
		}
		A[-i-1] = new Entrada<K,V>(key,value);
		n++;
		return null;
	}

	public V remove(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("La clave es nula");
		int i = findEntry(key);
		if(i<0)
			return null;
		V retorno = A[i].getValue();
		A[i] = Available;
		n--;
		return retorno;
	}

	public Iterable<K> keys() {
		PositionList<K> keys = new ListaDoblementeEnlazada<K>();
		for(int i=0; i<N; i++) {
			if( (A[i]!=null)&&  (A[i]!=Available) )
				keys.addLast(A[i].getKey());
		}
		return keys;
	}

	public Iterable<V> values() {
		PositionList<V> values = new ListaDoblementeEnlazada<V>();
		for(int i=0; i<N; i++) {
			if( A[i]!=null && A[i]!=Available)
				values.addLast(A[i].getValue());
		}
		return values;
	}

	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> entrys = new ListaDoblementeEnlazada<Entry<K,V>>();
		for(int i=0; i<A.length; i++) {
			if( A[i]!=null && A[i]!=Available )
				entrys.addLast(A[i]);
		}
		return entrys;
	}
	
	private void rehash() {
		N = 2*N;
		Entry<K,V>[] old = A;
		A = (Entry<K,V>[]) new Entrada[N];
		java.util.Random ramd = new java.util.Random();
		scale = ramd.nextInt(N-1)+1;
		shift = ramd.nextInt(N);
		for(int i=0; i<old.length; i++) {
			Entry<K,V> e = old[i];
			if( (e!=null) && (e!=Available)) {
				try {
					int j = -1-findEntry(e.getKey());
					A[j] = e;
				} catch (InvalidKeyException e1) {
					e1.getMessage();
				}
			}
		}
	}
	
	private int findEntry(K key) throws InvalidKeyException{
		if(key==null)
			throw new InvalidKeyException("La clave es nula");
		int i = h(key), j = i,avail = -1;
		do {
			Entry<K,V> e = A[i];
			if(e==null) {
				if(avail<0)
					avail = i;
				break;	
			}	
			if(key.equals(e.getKey()))
				return i;
			if(e==Available) {
				if(avail<0)
					avail = i;
			}
			i = (i+1) % N;
		}while(i!=j);
		return -(avail+1);	
	}
		
	private int h(K key) throws InvalidKeyException{
		if(key==null)
			throw new InvalidKeyException("La clave es nula");
		return Math.abs((key.hashCode()*scale+shift)) % N;
	}
	
	public String toString () {
	    String msg = new String ();
	    for (int i = 0; i < A.length; i ++) {
	        if (A[i] != null && A[i] != Available) {
	            msg += "P (" + (i) + "): " + A[i].toString() + "\n";
	        } else msg += "P (" + (i) + "): NULL" + "\n";
	    }

	    return msg;
	}

}
