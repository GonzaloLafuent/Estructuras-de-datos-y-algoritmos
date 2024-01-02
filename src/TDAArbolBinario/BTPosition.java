package TDAArbolBinario;

public interface BTPosition<E> extends Position<E>  {
	public E element(); 
	
	public void setParent(BTPosition<E> parent);

	public void setRight(BTPosition<E> right); 
	
	public void setLeft(BTPosition<E> left); 
		
	public void setElement(E element); 

	public BTPosition<E> getParent();
	
	public BTPosition<E> getRigth();

	public BTPosition<E> getLeft();
	
	public String toString();
}
