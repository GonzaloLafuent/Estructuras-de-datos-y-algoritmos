package TDAColaCP;

import java.util.Comparator;

import Exceptions.EmptyPriorityQueueException;
import Exceptions.InvalidKeyException;

public class Heap<K,V> implements PriorityQueue<K,V>{
	protected Entrada<K,V> [] elems;
	protected Comparator<K> comp;
	protected int size;
	
	
	public Heap(Comparator<K> comp) {
		this(20,comp);
	}
	
	/**
	 * 
	 * @param maxElems
	 * @param comp
	 */
	public Heap(int maxElems,Comparator<K> comp) {
		elems = (Entrada<K,V>[]) new Entrada[maxElems];
		this.comp = comp;
		size = 0;
	}
	
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Entry<K, V> min() throws EmptyPriorityQueueException {
		if( isEmpty() )
			throw new EmptyPriorityQueueException("La cola esta vacia");
		return elems[1];
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if( size == elems.length-1 ) {
			Entrada<K,V> [] elemsAux = (Entrada<K,V>[]) new Entrada[elems.length*2];
			for(int i=0; i<elems.length; i++) {
				elemsAux[i] = elems[i];
			}
			elems = elemsAux;
		}
		if( key==null )
			throw new InvalidKeyException("La clave es nula");
		Entrada<K,V> entrada = new Entrada<K,V>(key,value);
		elems[++size] = entrada;
		int i = size;
		boolean seguir = true;
		while( i>1 && seguir ) {
			Entrada<K,V> elemActual = elems[i];
			Entrada<K,V> elemPadre = elems[i/2];
			if( comp.compare(elemActual.getKey(),elemPadre.getKey()) < 0 ) {
				Entrada<K,V> aux = elems[i];
				elems[i] = elems[i/2];
				elems[i/2] = aux;
				i/=2;
			} else seguir = false;
		}
		return entrada;
	}

	@Override
	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		if( isEmpty() )
			throw new EmptyPriorityQueueException("La cola vacia");
		Entry<K,V> entrada = min();
		Integer m = null;
		if( size==1 ) {
			elems[1] = null;
			size = 0;
			return entrada;
		} else {
			elems[1] = elems[size];
			elems[size] = null;
			size--;
			int i = 1;
			boolean seguir = true;
			while(seguir) {
				int hi = i*2;
				int hd = (i*2)+1;
				boolean tieneHijoIzquierdo = hi<=size;
				boolean tieneHijoDerecho = hd<=size;
				if(!tieneHijoIzquierdo)
					seguir = false;
				else {	
					if(tieneHijoDerecho) {
						if(comp.compare(elems[hi].getKey(),elems[hd].getKey()) < 0 )
							m = hi;
						else m = hd;
					} else m = hi;
				}
				if( m!=null && comp.compare(elems[i].getKey(),elems[m].getKey()) > 0 ) {
					Entrada<K,V> aux = elems[i];
					elems[i] = elems[m];
					elems[m] = aux;
					i = m;
				} else seguir = false;
			}
		}
		return entrada;
	}
}
