package TDApila;

import Exceptions.EmptyStackException;

public interface Stack<E> {
	/**
	 *  Inserta Elemento en el tope de la fila
	 *  @param elemento elemento a insetar
	 */
	public void push(E elemento);
	
	/**
	 *  Retorna true si la pila esta vacia
	 *  @return retorna verdadero si la pila esta vacia
	 */
	public boolean isEmpty();
	
	/**
	 *   @return retorna el elemento eliminado
	 *   @throws emptyStackException si la pila esta vacia
	 */
	public E pop() throws EmptyStackException;
	
	/**
	 * 	@return retorna el elemento del tope de la pila
	 * 	@throws emptyStackException si la pila esta vacia
	 */
	public E top() throws EmptyStackException;
	
	/**
	 *   @return retorna la cantidad de elementos de la pila
	 */
	public int size();
}
