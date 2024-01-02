package TDACola;

import Exceptions.EmptyQueueException;
import Exceptions.FullQueueException;

public class ColaConArregloCircular<E> implements Queue<E>{
	//Atributos
	protected int front,tail;
	protected E[] datos;
	
	//Constructor
	public ColaConArregloCircular() {
		datos = (E[]) new Object[10]; 
		front = 0;
		tail = 0;
	}
	
	//Setters
	public void enqueue(E element) {
		if(size() == datos.length-1) {
			E[] aux = copiar(front);
			tail = size();
			front = 0;
			datos = aux;
		}
			datos[tail] = element;
			tail = (tail + 1) % datos.length;
	}
	
	
	public E dequeue() throws EmptyQueueException {
		if( isEmpty()) {
			throw new EmptyQueueException("Cola vacía.");
		}
		else {
			E temp = datos[front];
			datos[front] = null;
			front = (front + 1) % (datos.length);
			return temp;
		}
	}
	
	private E[] copiar(int start) {
		int j = 0;
		E[] aux = (E[]) new Object[datos.length*2];
		for(int i = front;!(start == tail);i++) {
			start = i % datos.length;
			aux[j++] = datos[start];
		}
		return aux;
	} 
	
	//Getters
	public boolean isEmpty() {
		return front == tail;
	}
	
	public E front() throws EmptyQueueException {
		if(isEmpty()) {
			throw new EmptyQueueException("Cola vacía.");
		}
		else {
			return datos[front];
		}
	}
	
	public int size() {
		return((datos.length - front + tail) % datos.length) ;
	}
	
	public String toString () {
        String s = "";
        for (int i = 0; i < size(); i++) {
            s += datos[i];
            if (i + 1 != size()) 
                s += ", ";
        }
        return s;
    }
}
