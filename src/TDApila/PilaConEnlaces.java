package TDApila;

import Exceptions.EmptyStackException;

public class PilaConEnlaces<E> implements Stack<E> {
	//atributos 
	protected Nodo<E> head;
	protected int tamaño;
	
	//constructor
	public PilaConEnlaces() {
		head = null;
		tamaño = 0;
	}
	
	//Setters
	public void push(E elemento) {
		Nodo<E> aux = new Nodo<E>(elemento,head);
		head = aux;
		tamaño++;
	}
	
	public E pop() throws EmptyStackException{
		if(head == null)
			throw new EmptyStackException("La pila esta vacia");
		E aux = head.getElemento();
		head = head.getSiguiente();
		tamaño--;
		return aux;
	}
	
	//getters
	public int size() {
		return tamaño;
	}
	
	public E top() throws EmptyStackException{
		if( tamaño==0 )
			throw new EmptyStackException("Pila Vacia");
		return head.getElemento();
	}
	
	public boolean isEmpty() {
		return tamaño == 0;
	}
}
