package TDADiccionario;

import Exceptions.EmptyListException;
import Exceptions.InvalidEntryException;
import Exceptions.InvalidKeyException;
import Exceptions.InvalidPositionException;
import TDAMapeo.Entrada;
import TDAMapeo.Entry;
import TDAMapeo.MapeoOpenHash;
import TDAlista.ListaDoblementeEnlazada;
import TDAlista.Position;
import TDAlista.PositionList;

public class DiccionarioConLista<K,V> implements Dictionary<K,V> {
	private ListaDoblementeEnlazada<Entry<K,V>> D;
	
	public DiccionarioConLista() {
		D = new ListaDoblementeEnlazada<Entry<K,V>>();
	}
	
	public int size() {
		return D.size();
	}

	public boolean isEmpty() {
		return D.isEmpty();
	}

	public Entry<K, V> find(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("La clave es nula");
		for(Entry<K,V>p: D) {
			if(key.equals(p.getKey()))
				return p;
		}
		return null;
	}

	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("la clave es nula");
		PositionList<Entry<K,V>> l = new ListaDoblementeEnlazada<Entry<K,V>>();
		for(Position<Entry<K,V>> p: D.positions()) {
			if(p.element().getKey().equals(key))
				l.addLast(p.element());
		}
		return l;
	}

	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("La clave es nula");
		Entry<K,V> e = new Entrada<K,V>(key,value);
		D.addLast(e);
		return e;
	}

	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		if(e==null)
			throw new InvalidEntryException("la clave es nula");
		try {
			for(Position<Entry<K, V>> p: D.positions()) {
					if( p.element() == e) {
						D.remove(p);
						return e;
					}
			}
			throw new InvalidEntryException("Diccionario::remove:..");
		}
			catch(InvalidPositionException e1) {
				System.out.println( e1.getMessage() );
			}
		return null;
	}

	public Iterable<Entry<K, V>> entries() {
		return D;
	}	
	
	public String toString () {
	    return (D.toString());
	}
}
