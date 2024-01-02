package TDAlista;

import java.util.Iterator;

import Exceptions.BoundaryViolationException;
import Exceptions.EmptyListException;
import Exceptions.InvalidPositionException;

import java.util.Iterator;

import Exceptions.BoundaryViolationException;
import Exceptions.EmptyListException;
import Exceptions.InvalidPositionException;

public class ListaDoblementeEnlazada<E> implements PositionList<E> {
	//Atributos de instancia
			private DNode<E> head;
			private int size;
			private DNode<E> tail;
			//Constructor
			public ListaDoblementeEnlazada() {
				head = new DNode<E>(null,null,null);
				tail = new DNode<E>(null,null,null);
				tail.setPrev( head );
				head.setNext( tail );
				size = 0;
			}
			
			//Comandos y consultas
			public int size() {
				return size;
			}
			
			public boolean isEmpty() {
				return size == 0;
			}
			
			public Position<E> first() throws EmptyListException{
				if( isEmpty() )
					throw new EmptyListException("La lista esta vacia");
				return head.getNext();
			}
			
			public Position<E> last() throws EmptyListException{
				if( isEmpty() )
					throw new EmptyListException("La lista esta vacia");
				return tail.getPrev();
			}
			
			public Position<E> prev (Position<E> p) throws InvalidPositionException, BoundaryViolationException{
				DNode<E> aux = checkPosition(p);
				if( p == head.getNext() )
					throw new BoundaryViolationException("El primero no tiene prev");
		        return aux.getPrev();
		    }
			
			public Position<E> next(Position<E> p) throws InvalidPositionException,BoundaryViolationException{
				DNode<E> n = checkPosition(p);
				if( p == tail.getPrev() )
					throw new BoundaryViolationException("El ultimo no tiene next");
				return n.getNext();
			}
			
			public void addFirst(E item) {
				DNode<E> newNode = new DNode<E>( item, head , head.getNext() );
				head.getNext().setPrev(newNode);			
				head.setNext(newNode);
				size++;
			}
			
			public void addLast(E item) {
				DNode<E> newNode= new DNode<E>( item , tail.getPrev() , tail );
				tail.getPrev().setNext(newNode);
				tail.setPrev(newNode);
				size++;
			}
			
			public void addAfter(Position<E> p, E element)
					throws InvalidPositionException {
					DNode<E> pos = checkPosition(p);
					DNode<E> nuevo = new DNode<E>(element,pos,pos.getNext());
					pos.getNext().setPrev(nuevo);
					pos.setNext(nuevo);
					size++;
			}
			
			public void addBefore(Position<E> p,E e) throws InvalidPositionException{
				DNode<E> v = checkPosition(p);
				DNode<E> newNode = new DNode<E> ( e , v.getPrev() , v );
				v.getPrev().setNext(newNode);
				v.setPrev(newNode);
				size++;
			}
			
			public E remove(Position<E> p) throws InvalidPositionException{
		        DNode<E> pos = checkPosition(p);
		        DNode<E> pPrev = pos.getPrev();
		        DNode<E> pNext = pos.getNext();
		        E retorno = pos.element();
		        pPrev.setNext(pNext);
		        pNext.setPrev(pPrev);
		        pos.setNext(null);
		        pos.setPrev(null);
		        size--;
		        return retorno;
		    }
			
			public E set(Position<E> p,E e)throws InvalidPositionException{
				DNode<E> n = checkPosition(p);
				E aux = n.element();
				n.setElement(e);
				return aux;
			}
			
			private DNode<E> checkPosition(Position<E> p) throws InvalidPositionException{
				if ( p == null ) 
					throw new InvalidPositionException("Posicion invalida");
				if( p == head )
					throw new InvalidPositionException("La cabeza no es una posicion valida");
				if( p == tail )
					throw new InvalidPositionException("La cola no es una posicion valida");
				DNode<E> aux;
				try {
					aux = (DNode<E>) p;
					if( aux.getNext() == null || aux.getPrev() == null )
						throw new InvalidPositionException(" La posicion no es valida en esta lista");
				}
				catch (ClassCastException e){
					throw new InvalidPositionException(" No se puede castear ");
				}
				return aux;
			}
			/**
			public void eliminar(PositionList<E> l) {
				try {
					Iterator<E> it = l.iterator();
					Position<E> f1 = null;
					Position<E> l1 = null;
					Position<E> aux = null;
					Position<E> f2 = l.first();
					Position<E> l2 = l.last();
					E elem = null;
					while( it.hasNext() ) {
						elem = it.next();
						f1 = first();
						l1 = last();
						while( f1!=l1 ) {
							if( elem.equals(f1.element()) ) {
								aux = next(f1);
								remove(f1);
								f1 = aux;
							}
							else f1 = next(f1);
						}
						if( elem.equals(l1.element()) )
							remove(l1);
					}
					while( l2!=f2 ) {
						addLast(l2.element());
						aux = l.prev(l2);
						l2 = aux;
					}
					addLast(f2.element());
				}
				catch(EmptyListException | InvalidPositionException | BoundaryViolationException e) {
					System.out.println(e.getMessage());
				}
			}
			*/
			
			public Iterable <Position<E>> positions() {
				PositionList <Position <E>> p = new ListaDoblementeEnlazada <Position <E>> ();
				try {
					if (size > 0) {
						Position <E> pos = first();
						Position <E> tope = last();
						
						while (pos != tope) {
							p.addLast(pos);
							pos = next (pos);
						}
						
						p.addLast(pos);
					}
					
				} catch (InvalidPositionException | BoundaryViolationException | EmptyListException e) {
					System.out.println (e.getMessage());
					e.printStackTrace();
				}
				
				return p;
			}
			
			public String toString() {
				String msg = null;
				if( size!=0 ) {
					DNode <E> aux = head.getNext();
					msg = new String ("[ ");
					for (int i = 0; i < size(); i ++) {
						msg += aux.element() + " ";
						aux = aux.getNext();
					}
					msg += "]";
				} else msg = "la lista esta vacia";	
			    return msg;
			}
			
			public Iterator<E> iterator() {
				return new ElementIterator<E>(this);
			} 
			
			public void concatenar(PositionList<E> l) {
				for(Position<E> p: l.positions()) {
					addLast(p.element());
				}
			}
}