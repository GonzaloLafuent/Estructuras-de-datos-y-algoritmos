package TDADiccionario;

import Exceptions.InvalidEntryException;
import Exceptions.InvalidKeyException;
import TDAMapeo.Entrada;
import TDAMapeo.Entry;
import TDAMapeo.MapeoConLista;
import TDAlista.ListaDoblementeEnlazada;
import TDAlista.PositionList;

public class DiccionarioDispAb<K,V> implements Dictionary<K,V>{
	private DiccionarioConLista<K,V> A[];
	private int n;
	private static int N = 13; 

	public DiccionarioDispAb() {
		n = 0;
		A = new DiccionarioConLista[N];
		for(int i=0; i<A.length; i++) {
			A[i] = new DiccionarioConLista<K,V>();
		}
	} 
	
	public int size() {
		return n;
	}
	
	public boolean isEmpty() {
		return n==0;
	}

	public Entry<K, V> find(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("La claves es nula");
		return A[h(key)].find(key);
	}
	
	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("La clave es nula");
		return A[h(key)].findAll(key);
	}
	
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if(n/N > 0.9)
			rehash();
		if(key==null)
			throw new InvalidKeyException("La clave es nula");
		Entry<K,V> retorno = A[h(key)].insert(key,value);
		n++;
		return retorno;
	}

	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		if(e==null)
			throw new InvalidEntryException("la entrada es nula");
		Entry<K,V> retorno = A[h(e.getKey())].remove(e);
	    n--;
		return retorno;
	}
	
	public Iterable<Entry<K, V>> entries() {
		PositionList <Entry <K,V>> entries = new ListaDoblementeEnlazada <Entry <K,V>>();
		
		for (int i = 0; i < A.length; i ++) {
			for (Entry <K,V> e: A [i].entries()) {
				entries.addLast (e);
			}
		}
		
		return entries;
	}
	
	private int h(K key) {
		return Math.abs(key.hashCode()) % N;
	}
	
	private void rehash() {
		N = N*2;
		while(!esPrimo(N))
			N++;
		DiccionarioConLista<K,V> newTable[] = new DiccionarioConLista[N];
		for(int i=0; i<newTable.length; i++)
			newTable[i] = new DiccionarioConLista<K,V>();
		try {
			for(Entry<K,V> e: entries() )
				newTable[ h(e.getKey())].insert(e.getKey(),e.getValue());
		} catch(InvalidKeyException e) {
			System.out.println(e.getMessage());
		}
		A = newTable;
	}
	
	private boolean esPrimo(int n) {
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
