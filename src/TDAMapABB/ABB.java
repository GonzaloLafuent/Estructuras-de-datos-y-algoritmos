package TDAMapABB;

import java.util.Comparator;

import Exceptions.EmptyListException;
import TDAlista.ListaDoblementeEnlazada;
import TDAlista.PositionList;

public class ABB<E extends Comparable<E>>{
	protected NodoABB<E> raiz;
	protected Comparator<E> comp;
	
	public ABB(Comparator<E> comp) {
		raiz = new NodoABB<E>(null,null);
		this.comp = comp;
	}
	
	public void insert(E rotulo,NodoABB<E> nodo) {
		if(nodo.getRotulo()==null) {
			expandir(nodo);
			nodo.setRotulo(rotulo);
		} else {
			if(comp.compare(rotulo,nodo.getRotulo())<0) {
				insert(rotulo,nodo.getIzq());
			} else if(comp.compare(rotulo,nodo.getRotulo())>0) {
				insert(rotulo,nodo.getDer());
			} else {
				expandir(nodo);
				nodo.setRotulo(rotulo);
			}
		}
	}
	
	public NodoABB<E>buscar(E x) {
		return buscarAux(x,raiz);
	}
	
	private NodoABB<E> buscarAux(E x,NodoABB<E> p){
		if(p.getRotulo()==null)
			return p;
		else {
			int c = comp.compare(x,p.getRotulo());
			if(c==0)
				return p;
			else if(c<0)
				return buscarAux(x,p.getIzq());
			else
				return buscarAux(x,p.getDer());
		}
	}
	
	public void expandir(NodoABB<E> p) {
		p.setIzq(new NodoABB<E>(null,p));
		p.setDer(new NodoABB<E>(null,p));
	}
	
	public void eliminar(NodoABB<E> n) {
		//Caso 1
		if( isExternal(n) ) {
			n.setRotulo(null);
			n.setDer(null);
			n.setIzq(null);
		} else {
			//Caso raiz
			if(n==raiz) {
				//Caso 2
				if(soloTieneHijoIzquierdo(n)) {
					raiz = n.getIzq();
					n.setIzq(null);
					n.setRotulo(null);
					raiz.setPadre(null);
				} else if(soloTieneHijoDerecho(n)) {
					//Caso 3
					raiz = n.getDer();
					n.setDer(null);
					n.setRotulo(null);
					raiz.setPadre(null);
				} else {
					//Caso 4
					n.setRotulo(eliminarMinimo(n.getDer()));
				}	
			} else if(soloTieneHijoIzquierdo(n) ) {
				//Caso 2
				if(n.getPadre().getIzq()==n)
					n.getPadre().setIzq(n.getIzq());
				else
					n.getPadre().setDer(n.getIzq());
				n.getIzq().setPadre(n.getPadre());
			} else if(soloTieneHijoDerecho(n)) { 		
				//Caso 3
				if(n.getPadre().getIzq()==n)
					n.getPadre().setIzq(n.getDer());
				else
					n.getPadre().setDer(n.getDer());
				n.getDer().setPadre(n.getPadre());
			} else {	
				//Caso 4
				n.setRotulo(eliminarMinimo(n.getDer()));
			}	
		}	
	}
	
	private E eliminarMinimo(NodoABB<E> n) {
		if(n.getIzq().getRotulo()==null) {
			E toReturn = n.getRotulo();
			if(n.getDer().getRotulo()==null) {
				n.setRotulo(null);
				n.setIzq(null);
				n.setDer(null);
			} else {
				n.getPadre().setDer(n.getDer());
				n.getDer().setPadre(n.getPadre());
			}
			return toReturn;
		} else {
			return eliminarMinimo(n.getIzq());
		}
	}
	
	public NodoABB<E> getRaiz(){
		return raiz;
	}
	
	public void creatRoot(E x) {
		if(raiz==null)
			raiz.setRotulo(x);
	}
	
	private boolean isExternal(NodoABB<E> p) {
		return p.getIzq().getRotulo()==null && p.getDer().getRotulo()==null;
	}
	
	private boolean soloTieneHijoDerecho(NodoABB<E> p) {
		return p.getIzq().getRotulo()==null && p.getDer().getRotulo()!=null;	
	}
	
	private boolean soloTieneHijoIzquierdo(NodoABB<E> p) {
		return p.getIzq().getRotulo()!=null && p.getDer().getRotulo()==null;	
	}
	
	public PositionList<E> inOrder() {
		PositionList<E> l = new ListaDoblementeEnlazada<E>();
		if(raiz.getRotulo()!=null)
			inOrderRec(l,raiz);
		return l;	
	}
	
	private void inOrderRec(PositionList<E> l,NodoABB<E> r) {
		if(r.getRotulo()!=null) {
			if(r.getIzq().getRotulo()!=null) {
				inOrderRec(l,r.getIzq());
			}
			l.addLast(r.getRotulo());
			if(r.getDer().getRotulo()!=null) {
				inOrderRec(l,r.getDer());
			}
		}
	}
	
	public String toString() {
		return inOrder().toString();
	}
}
