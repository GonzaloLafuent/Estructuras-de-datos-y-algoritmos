package TDApila;

import Exceptions.EmptyStackException;
import Exceptions.FullStackException;

public class PilaArreglo<E> implements Stack<E> {
	//atributos
	protected int tama�o;
	protected E [] datos;
	
	/**
	 *  Genero una pila de n elementos
	 */
	public PilaArreglo() {
		datos = (E[]) new Object[20];
		tama�o = 0;
	}
	
	public PilaArreglo(int max) {
		datos = (E[]) new Object[max];
		tama�o = 0;
	}
	
	//Comandos
	public void push(E elemento){
		if( tama�o == datos.length ) {
			E [] aux = (E[]) new Object[tama�o+10];
			for(int i=0; i<tama�o; i++) {
				aux[i] = (E) datos[i];
			}
			aux[tama�o] = elemento;
			tama�o++;
			datos = aux;
		}	 
		else{
			datos[tama�o] = elemento;
		    tama�o++;
		}
	}
	
	public void invertir() {
		int lector = tama�o-1;
		int medio = tama�o/2;
		for(int i=0; i<size() && lector>=medio && i!=medio; i++) {
			E aux;
			aux = datos[i];
			datos[i] = datos[lector];
			datos[lector] = aux;
			lector--;
		}
	}
	
	//Consulta
	public E pop() throws EmptyStackException{
		if( tama�o==0 )
			throw new EmptyStackException("No hay elemento en la pila");
		E aux = datos[tama�o-1];
		datos[tama�o-1] = null;
		tama�o--;
		return aux;
	}
	
	public E top() throws EmptyStackException{
		if( tama�o==0 )
			throw new EmptyStackException("No hay elementos en la pila");
		return datos[tama�o-1];
	}
	
	public boolean isEmpty() {
		return tama�o == 0;
	}
	
	public int size() {
		return tama�o;
	}
    
	public String toString() {
		String texto = "";
		for(int i=0; i<size(); i++)
			texto = texto + datos[i].toString() + " " ;
		return texto;
	}
}
