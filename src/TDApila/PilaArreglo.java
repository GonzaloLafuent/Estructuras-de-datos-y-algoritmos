package TDApila;

import Exceptions.EmptyStackException;
import Exceptions.FullStackException;

public class PilaArreglo<E> implements Stack<E> {
	//atributos
	protected int tamaño;
	protected E [] datos;
	
	/**
	 *  Genero una pila de n elementos
	 */
	public PilaArreglo() {
		datos = (E[]) new Object[20];
		tamaño = 0;
	}
	
	public PilaArreglo(int max) {
		datos = (E[]) new Object[max];
		tamaño = 0;
	}
	
	//Comandos
	public void push(E elemento){
		if( tamaño == datos.length ) {
			E [] aux = (E[]) new Object[tamaño+10];
			for(int i=0; i<tamaño; i++) {
				aux[i] = (E) datos[i];
			}
			aux[tamaño] = elemento;
			tamaño++;
			datos = aux;
		}	 
		else{
			datos[tamaño] = elemento;
		    tamaño++;
		}
	}
	
	public void invertir() {
		int lector = tamaño-1;
		int medio = tamaño/2;
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
		if( tamaño==0 )
			throw new EmptyStackException("No hay elemento en la pila");
		E aux = datos[tamaño-1];
		datos[tamaño-1] = null;
		tamaño--;
		return aux;
	}
	
	public E top() throws EmptyStackException{
		if( tamaño==0 )
			throw new EmptyStackException("No hay elementos en la pila");
		return datos[tamaño-1];
	}
	
	public boolean isEmpty() {
		return tamaño == 0;
	}
	
	public int size() {
		return tamaño;
	}
    
	public String toString() {
		String texto = "";
		for(int i=0; i<size(); i++)
			texto = texto + datos[i].toString() + " " ;
		return texto;
	}
}
