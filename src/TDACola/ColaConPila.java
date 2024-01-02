package TDACola;

import Exceptions.EmptyQueueException;
import Exceptions.EmptyStackException;
import TDApila.PilaArreglo;
import TDApila.PilaConEnlaces;

public class ColaConPila<E> implements Queue<E> {
	//Atributos
	private PilaArreglo<E> cola;
	
	//constructor
	public ColaConPila() {
		cola = new PilaArreglo<E>();
	}
	public void enqueue(E elemento) {
		cola.push(elemento);
	}
	
	public E dequeue() throws EmptyQueueException{
		E aux = null;
		try {
			if( cola.isEmpty() )
				throw new EmptyQueueException("la cola esta vaica");
			invertir(cola);
			aux = cola.pop();
			invertir(cola);
		}
		catch(EmptyStackException e) {
			System.out.println( e.getMessage() );
		}
		return aux;
	}
	
	public E front() throws EmptyQueueException{
		E aux = null;
		try {
			if( cola.isEmpty() )
				throw new EmptyQueueException("La cola esta vacia");
			invertir(cola);
			aux = cola.top();
			invertir(cola);
		}
			catch(EmptyStackException e) {
				System.out.println( e.getMessage() );
			}
		return aux;
	}
	
	private void invertir(PilaArreglo<E> a) {
			try {
				int cantElems = a.size();
				PilaArreglo<E> aux = new PilaArreglo<E>();
				PilaArreglo<E> aux2 = new PilaArreglo<E>();
				for(int i=0; i<cantElems; i++) {
					aux.push(a.pop());
				}
				for(int t=0; t<cantElems; t++) {
					aux2.push(aux.pop());
				}		
				for(int j=0; j<cantElems; j++) {
					a.push(aux2.pop());
				}
			}
			catch(EmptyStackException e) {
				System.out.println(e.getMessage());
			}
     }
	
	
	public boolean isEmpty() {
		return cola.isEmpty();
	}
	
	public int size() {
		return cola.size();
	}
}
