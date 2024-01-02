package TDAArbolBusqBalan;

public class NodoAVL<E extends Comparable<E>> {
	private NodoAVL<E> padre;
	private E rotulo;
	private int altura;
	private NodoAVL<E> izq,der;
	private boolean eliminado;
	
	public NodoAVL(E rotulo) {
		this.rotulo = rotulo;
		this.altura = 0;
		padre = null;
		izq = null;
		der = null;
		eliminado = false;
	}

	public NodoAVL<E> getPadre() {
		return padre;
	}

	public void setPadre(NodoAVL<E> padre) {
		this.padre = padre;
	}

	public E getRotulo() {
		return rotulo;
	}

	public void setRotulo(E rotulo) {
		this.rotulo = rotulo;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public NodoAVL<E> getIzq() {
		return izq;
	}

	public void setIzq(NodoAVL<E> izq) {
		this.izq = izq;
	}

	public NodoAVL<E> getDer() {
		return der;
	}

	public void setDer(NodoAVL<E> der) {
		this.der = der;
	}

	public boolean getEliminado() {
		return eliminado;
	}

	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}
	
	
}
