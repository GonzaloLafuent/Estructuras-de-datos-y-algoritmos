package TDAlista;

import Exceptions.InvalidPositionException;

public class DNode<E> implements Position<E> {
	private DNode<E> prev,next;
	private E element;
	
	public DNode() {
		prev = null;
		next = null;
		element = null;
	}
	
	public DNode( E e , DNode<E> newPrev, DNode<E> newNext) {
		prev = newPrev;
		next = newNext;
		element = e;
	}
	
	public E element() {
		return element;
	}
	
	public DNode<E> getNext(){
		return next;
	}
	
	public DNode<E> getPrev(){
		return prev;
	}
	
	public void setNext(DNode<E> n) {
		next = n;
	}
	
	public void setPrev(DNode<E> p) {
		prev = p;
	}
	
	public void setElement(E elem) {
		element = elem;
	}

}
