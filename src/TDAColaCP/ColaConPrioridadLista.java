package TDAColaCP;

import TDAlista.*;
import java.util.Comparator;

import Exceptions.BoundaryViolationException;
import Exceptions.EmptyListException;
import Exceptions.EmptyPriorityQueueException;
import Exceptions.InvalidKeyException;
import Exceptions.InvalidPositionException;

public class ColaConPrioridadLista <K,V> implements PriorityQueue <K, V> {

	protected PositionList <Entry <K,V>> entries;
	protected Comparator <K> c;
	
	public ColaConPrioridadLista () {
		entries = new ListaDoblementeEnlazada <Entry <K,V>>();
		c = new DefaultComparator<K>();
	}
	
	public ColaConPrioridadLista (Comparator <K> comp) {
		entries = new ListaDoblementeEnlazada <Entry <K,V>>();
		c = comp;
	}
	
	public int size() {
		return (entries.size());
	}
	
	public boolean isEmpty() {
		return (entries.isEmpty());
	}
	
	public Entry<K, V> min() throws EmptyPriorityQueueException {
		if (isEmpty()) throw new EmptyPriorityQueueException ("min: La CCP se encuentra vacía.");
		
		Entry<K, V> toReturn = null;
		
		try {
			toReturn = entries.first().element();
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
		
		return toReturn;
	}
	
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if (key == null) throw new InvalidKeyException ("insert: La clave no es válida.");
		Entry <K,V> toInsert = new Entrada <K,V> (key, value);
		insertEntry (toInsert);
		return toInsert;
	}
	
	protected void insertEntry(Entry<K, V> entry) {
		try {
		
			if (isEmpty()) {
				entries.addFirst(entry);
				// Position <Entry<K, V>> actionPos = entries.last();
			} else if (c.compare(entry.getKey(), entries.last().element().getKey()) > 0) {
				entries.addLast(entry);
				// Position <Entry<K, V>> actionPos = entries.last();
			} else {
				Position <Entry<K, V>> cursor = entries.first();
				
				while (c.compare(entry.getKey(), cursor.element().getKey()) > 0) {
					cursor = entries.next(cursor);
				}
				
				entries.addBefore(cursor, entry);
				// Position <Entry<K, V>> actionPos = entries.prev(cursor);
			}
		
		} catch (EmptyListException | BoundaryViolationException | InvalidPositionException e) {
			e.printStackTrace();
		}
	}
	
	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		if (isEmpty()) throw new EmptyPriorityQueueException ("removeMin: La CCP se encuentra vacía.");
		
		Entry <K,V> removed = null;
		
		try {
			removed = entries.remove(entries.first());
		} catch (InvalidPositionException | EmptyListException e) {
			e.printStackTrace();
		}
		
		return removed;
	}
	
}
