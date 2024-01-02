package TDACola;

import Exceptions.EmptyQueueException;
import Exceptions.FullQueueException;

public interface Queue<E> {
	/**
	 *   inserta un elemento a lo ultimo de la cola
	 *   @param elemento a insertar
	 *   @throws FullQueueException
	 */
	public void enqueue(E elemento);
	
	/**
	 *   Elimina el elemento del frente de la cola y lo retorna
	 *   @throws EmptyQueueException
	 *   @return elemento eliminado 
	 */
	public E dequeue() throws EmptyQueueException;
	
	/**
	 *    Retorna el elemento del frente de la cola
	 *    @throws EmptyQueueException
	 */
	public E front() throws EmptyQueueException;
	
	/**
	 *   retorna verdadero si la cola no tiene elementos
	 */
	public boolean isEmpty();
	
	/**
	 *  retorna la cantidad de elementos de la cola
	 */
	public int size();
}
