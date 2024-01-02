package TDApila;

import Exceptions.EmptyStackException;

public class PilaConEnlaces<E> implements Stack<E> {
	//atributos 
	protected Nodo<E> head;
	protected int tama�o;
	
	//constructor
	public PilaConEnlaces() {
		head = null;
		tama�o = 0;
	}
	
	//Setters
	public void push(E elemento) {
		Nodo<E> aux = new Nodo<E>(elemento,head);
		head = aux;
		tama�o++;
	}
	
	public E pop() throws EmptyStackException{
		if(head == null)
			throw new EmptyStackException("La pila esta vacia");
		E aux = head.getElemento();
		head = head.getSiguiente();
		tama�o--;
		return aux;
	}
	
	//getters
	public int size() {
		return tama�o;
	}
	
	public E top() throws EmptyStackException{
		if( tama�o==0 )
			throw new EmptyStackException("Pila Vacia");
		return head.getElemento();
	}
	
	public boolean isEmpty() {
		return tama�o == 0;
	}
}
