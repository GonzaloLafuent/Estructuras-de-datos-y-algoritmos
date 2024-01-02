package TDAMapABB;

import java.util.Comparator;

import Exceptions.InvalidKeyException;
import TDAlista.ListaDoblementeEnlazada;
import TDAlista.PositionList;

public class MapABB<K,V> implements Map<K,V>{
	protected ABB<Entry<K,V>> arbol;
	protected int size;
	protected Comparator<Entry<K,V>> comp;
	
	public MapABB() {
		comp = new DefaultComparator<Entry<K,V>>();
		arbol = new ABB<Entry<K,V>>(comp);
		size = 0;
	}
	
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public V get(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("la clave es nula");
		Entry<K,V> aux = new Entrada<K,V>(key,null);
		NodoABB<Entry<K,V>> busq = arbol.buscar(aux);
		if(busq.getRotulo()==null) {
			return null;
		} else return busq.getRotulo().getValue();
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("La clave es nula");
		Entry<K,V> aux = new Entrada<K,V>(key,value);
		NodoABB<Entry<K,V>> buscar = arbol.buscar(aux);
		Entrada<K,V> entBusq = (Entrada<K, V>) buscar.getRotulo();
		V toReturn = null;
		if(entBusq!=null) {
			toReturn = entBusq.getValue();
			entBusq.setValue(value);
		} else {
			arbol.expandir(buscar);
			buscar.setRotulo(aux);
		}
		size++;
		return toReturn;
	}
	
	@Override
	public V remove(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("la clave es nula");
		Entry<K,V> aux = new Entrada<K,V>(key,null);
		NodoABB<Entry<K,V>> remover = arbol.buscar(aux);
		V toReturn = null;
		if(remover.getRotulo()!=null) {
			toReturn = remover.getRotulo().getValue();
			arbol.eliminar(remover);
			size--;
		}
		return toReturn;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> keys = new ListaDoblementeEnlazada<K>();
		if(!isEmpty() ) {
			for(Entry<K,V> e:arbol.inOrder()) {
				keys.addLast(e.getKey());
			}
		}
		return keys;	
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> values = new ListaDoblementeEnlazada<V>();
		if(!isEmpty() ) {
			for(Entry<K,V> e:arbol.inOrder()) {
				values.addLast(e.getValue());
			}
		}
		return values;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> keys = new ListaDoblementeEnlazada<Entry<K,V>>();
		if(!isEmpty() ) {
			for(Entry<K,V> e:arbol.inOrder()) {
				keys.addLast(e);
			}
		}
		return keys;
	}
	
}
