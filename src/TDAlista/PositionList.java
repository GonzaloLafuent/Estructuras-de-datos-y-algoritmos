package TDAlista;

import java.util.Iterator;

import Exceptions.BoundaryViolationException;
import Exceptions.EmptyListException;
import Exceptions.InvalidPositionException;

public interface PositionList<E> extends java.lang.Iterable<E>{
	/**
	 *  consulta la cantidad de elementos de la lista
	 *  @return cantidad total de elementos de la lista
	 */
	public int size();
	
	/**
	 * Consulta si la lista esta vacia
	 * @return Retorna verdadero si la lista esta vacia o falso si no lo esta
	 */
	public boolean isEmpty();
	
	/**
	 * Devuelve la posisicion del primer elementod de la lista
	 * @return Posicion del primer elemento de la lista
	 * @throws EmptyListException si la lista esta vacia
	 */
	public Position<E> first() throws EmptyListException;
	
	/**
	 * Devuelve la ultima posicion de la lista
	 * @return Ultima posicion de la lista
	 * @throws EmptyListException si la lista esta vacia
	 */
	public Position<E> last() throws EmptyListException;
	
	/**
	 *  Devuelve la posicion del elemento previo al pasado por parametro
	 * 	@param p posicion a obtener su elemento previo
	 * 	@return Elemento anterior a la posicion pasada por parametro
	 * 	@throws InvalidPositionException si la lista esta vacia o la posicion es invalida 
	 * 	@throws BoundaryViolationException si la poscion pasada por parametro corresponde a la primer posicion
	 */
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException;
	
	/**
	 * devuelve la poscion del elemento siguiente al pasado por parametro
	 * @param p posicion a obtener su elemento siguiente
	 * @return Elemento siguiente a la poscion pasada por parametro
	 * @throws InvalidPositionException si la lista esta vacia o la poscion es invalida
	 * @throws BoundaryViolationException si la posicion pasada por parametro corresponde a la ultima posicion
	 */
	public Position<E> next(Position<E> p)throws InvalidPositionException, BoundaryViolationException;
	
	/**
	 *   Agrega elemento al principio de la lista
	 *   @param elemento a insertar 
	 */ 
	public void addFirst(E item);
	
	/**
	 *  Agrega elemento al final de la lista
	 *  @param Elemento a inserta
	 */
	public void addLast(E item);
	
	/**
	 * 	 Agrega un elemento despues de un elemento determinado
	 * 	 @param p posicion en cuya posicion siguiente se insertara el elemento
	 *   @param e elemento a insertar despues
	 */
	public void addAfter(Position<E> p,E e) throws InvalidPositionException;
	
	/**
	 *  Agrega un elemento antes de elemeneto determinados
	 *  @param p posicion en cuya posicion anterior se insertara un elemento
	 *  @param e elemento a insertar antes
	 */
	public void addBefore(Position<E> p,E e) throws InvalidPositionException;
	
	/**
	 * 	Remueve el elemento que se encuentra en la posicion pasada por parametro
	 *  @return retorna el elemento eliminado
	 *  @param p posicion del elemento a eliminar
	 *  @throws InvalidPositionException si la posicion no es valida
	 */
	public E remove(Position<E> p) throws InvalidPositionException; 
	
	/**
	 * 	@param p posicion a establecer elemento pasado por parametro
	 * 	@param e elemento a establecer en la posicion
	 * 	@return Elemento anterior
	 * 	@throws InvalidPositionException si la posicion es invalida o la lista esta vacia
	 */
	public E set(Position<E> p,E e)throws InvalidPositionException;
	
	/**
	 * Devuelve una coleccioniterable de posiciones.
	 * @return Una coleccion iterable de posiciones.
	 */
	public Iterable<Position<E>> positions();
	
	/**
	 * Devuelve un un iterador de todos los elementos de la lista.
	 * @return Un iterador de todos los elementos de la lista.
	 */
	public Iterator<E> iterator();
}
