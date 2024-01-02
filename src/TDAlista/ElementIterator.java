package TDAlista;

import java.util.NoSuchElementException;

import Exceptions.BoundaryViolationException;
import Exceptions.EmptyListException;
import Exceptions.InvalidPositionException;


public class ElementIterator<E> implements java.util.Iterator<E>{
	//Atributos de instancia
	protected PositionList<E> list;
	protected Position<E> cursor;
	
	//Constructor
	public ElementIterator(PositionList<E> l){
		try {
			list = l;
			if( list.isEmpty() )
				cursor = null;
				else cursor = l.first();
		}
			catch(EmptyListException e) {
				System.out.println( e.getMessage() );
			}
	}
	
	public boolean hasNext(){
		return cursor != null;
	}
	
	public E next() throws NoSuchElementException{
		E retorno = null;
		try {
			if( cursor==null )
				throw new NoSuchElementException("No hay siguiente");
			retorno = cursor.element();
			cursor = (cursor == list.last())? null: list.next(cursor);
			return retorno;
		}
			catch(EmptyListException | InvalidPositionException | BoundaryViolationException  e) {
				System.out.println( e.getMessage() );
			}
		return retorno;
	}
}
