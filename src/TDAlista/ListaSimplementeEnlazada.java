package TDAlista;

import java.util.Iterator;

import Exceptions.BoundaryViolationException;
import Exceptions.EmptyListException;
import Exceptions.InvalidPositionException;

public class ListaSimplementeEnlazada<E> implements PositionList<E> {
	//Atributos de instancia
	protected Nodo<E> head;
	protected int tamaño;
	
	//Constrcutor
	public ListaSimplementeEnlazada() {
		head = null;
		tamaño = 0;
	}
	
	//Comandos y consultas
	public int size() {
		return tamaño;
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public Position<E> first() throws EmptyListException{
		if( head == null )
			throw new EmptyListException("La lista esta vacia");
		return head;
	}
	
	public Position<E> last() throws EmptyListException{
		if( head==null )
			throw new EmptyListException("La lista esta vacia");
		Nodo<E> last = head;
		while( last.getSiguiente()!=null  ) 
			last = last.getSiguiente();
		return last;
	}
	
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
		Nodo<E> aux = head;
		checkPosition(p);
		if( p == head )
			throw new BoundaryViolationException("Posicion Primera");
		while( aux.getSiguiente() != p && aux.getSiguiente() != null)
			aux=aux.getSiguiente();
		if(aux.getSiguiente() == null)
			throw new InvalidPositionException("Posicion no pertenece a la lista");
		return aux;
	}
	
	public Position<E> next(Position<E> p) throws InvalidPositionException,BoundaryViolationException{
		Nodo<E> n = checkPosition(p);
		if( n.getSiguiente()==null )
			throw new BoundaryViolationException("p es el ultimo elemento");
		return n.getSiguiente();
	}
	
	public void addFirst(E item) {
		head = new Nodo<E>(item, head);
		tamaño++;
	}
	
	public void addLast(E item) {
		if( isEmpty() )
			addFirst(item);
			else {
				Nodo<E> p = head;
				while(p.getSiguiente()!=null)
					p = p.getSiguiente();
				p.setSiguiente(new Nodo<E>(item,null));
				tamaño++;
			}
	}
	
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		Nodo<E> n = checkPosition(p);
		Nodo<E> aux = new Nodo<E>(element,n.getSiguiente());
		n.setSiguiente(aux);
		tamaño++;
	}
	
	public void addBefore(Position<E> p,E e) throws InvalidPositionException{
		if( isEmpty() )
			throw new InvalidPositionException("La lista esta vacia");
		checkPosition(p);
		try {
			if( p==first() )
				addFirst(e);
				else addAfter( prev(p),e); 
		}
			catch(EmptyListException | BoundaryViolationException e1) {
				System.out.println( e1.getMessage() );
			}
	}
	
	public E remove(Position<E> p) throws InvalidPositionException{
		if (isEmpty()) 
			throw new InvalidPositionException("La lista está vacía");
		Nodo<E> n = checkPosition(p);
		try {
			if (p == first()) 
					head = n.getSiguiente();
				else 
					checkPosition(prev(p)).setSiguiente(n.getSiguiente());	
			}
			catch (EmptyListException | BoundaryViolationException e) {
				System.out.println( e.getMessage() );
			}
			E aux = p.element();
			n.setElemento(null);
			n.setSiguiente(null);
			tamaño--;
			return aux;
	}
	
	public E set(Position<E> p,E e)throws InvalidPositionException{
		if( isEmpty() )
			throw new InvalidPositionException("La lista esta vacia"); 
		Nodo<E> n = checkPosition(p);
		E aux = n.element();
		n.setElemento(e);
		return aux;
	}
	
	public String toString() {
		String texto = "";
		Nodo<E> aux = head;
		texto = "";
		if( tamaño == 0)
			return texto = "La lista esta vacia";
		else{	
				texto = aux.element().toString() + " ";
				while( aux.getSiguiente() != null ) {
					aux = aux.getSiguiente();
					texto = texto + aux.element().toString() + " ";
				}
		}
		return texto;
	}
	
	private Nodo<E> checkPosition(Position<E> p) throws InvalidPositionException{
		if (p == null) 
			throw new InvalidPositionException("Posicion invalida");
		Nodo<E> aux;
		try {
			aux = (Nodo<E>) p;
		}
		catch (ClassCastException e){
			throw new InvalidPositionException("Posicion invalida");
		}
		return aux;
	} 
	
	public Iterable <Position<E>> positions() {
		PositionList <Position <E>> p = new ListaSimplementeEnlazada<Position <E>> ();
		try {
			if (tamaño > 0) {
				Position <E> pos = first();
				Position <E> tope = last();
				
				while (pos != tope) {
					p.addLast(pos);
					pos = next (pos);
				}
				
				p.addLast(pos);
			}
			
		} catch (InvalidPositionException | BoundaryViolationException | EmptyListException e) {
			System.out.println (e.getMessage());
			e.printStackTrace();
		}
		
		return p;
	}
	
	public Iterator<E> iterator(){
		return new ElementIterator<E>(this);
	}
	
	public void invertir() {
		Position<E> aux = null;
		Position<E> last = null;
		try {
			Position<E> h1 = first();
			aux = last();
			addFirst(aux.element());
			remove(aux);
			aux = first();
			last = last();
			while( h1!=last ) {
				addAfter(aux, last.element());
				remove(last);
				aux = next(aux);
				last = last();
			}
		}
		catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {
			e.getMessage();
		}	
	}
	
	public ListaSimplementeEnlazada<E> clone(){
		ListaSimplementeEnlazada<E> clone = new ListaSimplementeEnlazada();
		Iterator<E> it = iterator();
		while( it.hasNext() ) 
			clone.addLast(it.next());
		return clone;	
	}
}
