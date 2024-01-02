package TDAMapABB;

public class NodoABB<E extends Comparable<E>> {
	private E rotulo;
	private NodoABB<E> padre,izq,der;
	
	public NodoABB(E rotulo,NodoABB<E> padre) {
		this.rotulo = rotulo;
		this.padre = padre;
		izq = der = null;
	}
	
	public E getRotulo() {
		return rotulo;
	}
	
	public NodoABB<E> getPadre(){
		return padre;
	}
	
	public NodoABB<E> getIzq(){
		return izq;
	}
	
	public NodoABB<E> getDer(){
		return der;
	}
	
	public void setRotulo(E rotulo) {
		this.rotulo = rotulo;
	}
	
	public void setIzq(NodoABB<E> izq) {
		this.izq = izq;
	}
	
	public void setDer(NodoABB<E> der) {
		this.der = der;
	}
	
	public void setPadre(NodoABB<E> padre) {
		this.padre = padre;
	}
}
