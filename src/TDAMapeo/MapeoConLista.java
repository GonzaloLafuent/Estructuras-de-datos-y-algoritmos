package TDAMapeo;

import Exceptions.BoundaryViolationException;
import Exceptions.EmptyListException;
import Exceptions.InvalidKeyException;
import Exceptions.InvalidPositionException;
import TDAlista.ListaDoblementeEnlazada;
import TDAlista.ListaSimplementeEnlazada;
import TDAlista.Position;
import TDAlista.PositionList;

public class MapeoConLista <K,V> implements Map <K,V> {
	private PositionList <Entrada <K,V>> S;

	public MapeoConLista () {
	    S = new ListaDoblementeEnlazada <Entrada <K,V>> ();
	}

	public int size() {
	    return S.size();
	}

	public boolean isEmpty() {
	    return S.isEmpty();
	}

	public V get(K key) throws InvalidKeyException {
	    if (key == null) 
	    	throw new InvalidKeyException ("La clave ingresada no es válida.");
	   Iterable <Position<Entrada<K,V>>> it = S.positions() ;
	    for( Position<Entrada<K,V>> p : it)
	        if( p.element().getKey().equals( key ) )
	            return p.element().getValue();

	    return null;

	}

	public V put(K key, V value) throws InvalidKeyException {
	    if (key == null) 
	    	throw new InvalidKeyException ("La clave ingresada no es válida.");
	    Iterable <Position<Entrada<K,V>>> it = S.positions();
	    for( Position<Entrada<K,V>> p : it) {
	        if( p.element().getKey().equals (key)) {
	            V aux = p.element().getValue();
	            p.element().setValue( value );
	            return aux;
	        }
	    }
	    S.addLast(new Entrada<K,V>(key, value) );
	    return null;
	}

	public V remove(K key) throws InvalidKeyException {
	    if (key == null) 
	    	throw new InvalidKeyException ("La clave ingresada no es válida.");
	    Iterable <Position<Entrada<K,V>>> it = S.positions();
	    for( Position<Entrada<K,V>> p:it)
	        if( p.element().getKey().equals( key ) ) {
	        	V value = p.element().getValue();
	        	try {
	        		S.remove( p );
	        	} catch (InvalidPositionException e) {
	        		System.out.println(e.getMessage());
	        	}
	        return value;
	        }
	    return null;
	}

	public Iterable<K> keys() {
	    PositionList <K> aux = new ListaDoblementeEnlazada <K>();
	    Iterable <Position<Entrada<K,V>>> it = S.positions();
	    for (Position <Entrada <K,V>> k:it) {
	        aux.addLast (k.element().getKey());
	    }
	    return aux;
	}

	public Iterable<V> values() {
	    PositionList <V> aux = new ListaDoblementeEnlazada <V>();
	    Iterable <Position<Entrada<K,V>>> it = S.positions();
	    for (Position <Entrada <K,V>> v: it) {
	        aux.addLast (v.element().getValue());
	    }
	    return aux;
	}

	public Iterable <Entry<K, V>> entries() {
	    PositionList <Entry <K,V>> aux = new ListaDoblementeEnlazada <Entry<K,V>> ();
	    Iterable <Position<Entrada<K,V>>> it = S.positions();
	    for (Position <Entrada <K,V>> p: it) {
	        aux.addLast (p.element());
	    }
	    return aux;
	}
	
	public String toString() {
		return S.toString();
	}
}
