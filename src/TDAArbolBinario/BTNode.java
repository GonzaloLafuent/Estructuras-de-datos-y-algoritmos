package TDAArbolBinario;

public class BTNode<E> implements BTPosition<E> {
	private E element;
	private BTPosition<E> left,rigth,parent;
	
	public BTNode(E element,BTPosition<E> left,BTPosition<E> right,BTPosition<E> parent) {
		this.element = element;
		this.left = left;
		this.rigth = right;
		this.parent = parent;
	}
	
	public void setParent(BTPosition<E> parent) {
		this.parent = parent;
	}
	
	public void setRight(BTPosition<E> right) {
		this.rigth = right;
	}
	
	public void setLeft(BTPosition<E> left) {
		this.left = left;
	}
	
	public void setElement(E element) {
		this.element = element;
	}
	
	public BTPosition<E> getParent(){
		return parent;
	}
	
	public BTPosition<E> getRigth(){
		return rigth;
	}
	
	public BTPosition<E> getLeft(){
		return left;
	}
	
	public E element() {
		return element;
	}
	
	public String toString() {
		return "("+ element + ")";
	}
}
