package TDAlista;

public class Nodo<E> implements Position<E>{
	private E elemento;
	private Nodo<E> siguiente;
	
	//Constructores
	public Nodo(E elem, Nodo<E> sig){
		this.elemento = elem;
		this.siguiente = sig;
	}
	
	//Setters
	public void setElemento(E elemento) {
		this.elemento = elemento;
	}
	
	public void setSiguiente(Nodo<E> siguiente) {
		this.siguiente = siguiente;
	} 
	
	//Getters
	public E element() {
		return elemento;
	}
	
	public Nodo<E> getSiguiente() {
		return siguiente;
	}
}
