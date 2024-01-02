package TDAMapeo;

import Exceptions.InvalidKeyException;
import TDAlista.ListaDoblementeEnlazada;
import TDAlista.PositionList;

public class MapeoOpenHash<K,V> implements Map<K,V> {
	private Map<K,V> A[];
	private int n;
	private static int N = 13;
	
	public MapeoOpenHash() {
		n = 0;
		A = (Map<K,V>[]) new MapeoConLista[N];
		for(int i=0; i<A.length; i++) {
			A[i] = new MapeoConLista<K,V>();
		}
	}
	
	public int size() {
		return n;
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public V get(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("La clave es nula");
		return A[h(key)].get(key);
	}

	public V put(K key, V value) throws InvalidKeyException {
		if( n/N > 0.9)
			rehash();
		if(key==null)
			throw new InvalidKeyException("La clave es nula");
		V aux = A[h(key)].put(key,value);
		if( aux==null )
			n++;
		return aux;
	}

	public V remove(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("La clave es nula");
		V aux = A[h(key)].remove(key);
		if( aux!=null )
			n--;
		return aux;
	}

	public Iterable<K> keys() {
		PositionList<K> aux = new ListaDoblementeEnlazada<K>();
		for(int i=0; i<A.length; i++) {
			for(K key: A[i].keys() )
				aux.addLast(key);
		}
 		return aux;
	}

	public Iterable<V> values() {
		PositionList<V> aux = new ListaDoblementeEnlazada<V>();
		for(int i=0; i<A.length; i++) {
			for(V value: A[i].values() )
				aux.addLast(value);;
		}
 		return aux;
	}
	
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> aux = new ListaDoblementeEnlazada<Entry<K,V>>();
		for(int i=0; i<A.length; i++) {
			for(Entry<K,V> ent: A[i].entries() )
				aux.addLast(ent);;
		}
 		return aux;
	}
	
	private int h(K key) {
		return key.hashCode() % N;
	}
	
	public String toString() {
		String texto = "";
		for(int i=0; i<A.length; i++) {
			texto = texto + "P (" + (i) + "): " + A [i].toString() + "\n";
		}
		return texto;
	}
	
	public void rehash() {
		N = N*2;
		while(!esPrimo(N))
			N++;
		 MapeoConLista<K,V> [] newTable = new MapeoConLista[N];
		 for(int i=0; i<newTable.length; i++)
			 newTable[i] = new MapeoConLista<K,V>();
		 try {
			 for(Entry<K,V> e: entries())
				 newTable[h(e.getKey())].put(e.getKey(),e.getValue());
		 } catch(InvalidKeyException e) {
			 System.out.println(e.getMessage());
		 }
		 A = newTable;
 	}
	
	public boolean esPrimo(int n) {
		boolean aux = false;
		for(int i=2; i<=n/2; i++) {
			if(n % i == 0) {
				aux = true;
				break;
			}
		}
		return !aux;
	}
}
