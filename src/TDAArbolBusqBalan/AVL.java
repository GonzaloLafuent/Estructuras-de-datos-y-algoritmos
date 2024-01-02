package TDAArbolBusqBalan;

import java.util.Comparator;

import Exceptions.EmptyQueueException;
import TDACola.ColaConArregloCircular;
import TDAMapABB.NodoABB;

public class AVL<E extends Comparable<E>> {
	private NodoAVL<E> raiz;
	private Comparator<E> comp;
	
	public AVL(Comparator<E> comp) {
		this.comp = comp;
		raiz = new NodoAVL<E>(null);
	}
	
	public void insert(E x) {
		insertAux(raiz,x);
	}
	
	private void insertAux(NodoAVL<E> p,E item) {
		if(p.getRotulo()==null) {
			p.setRotulo(item);
			p.setAltura(1);
			p.setIzq(new NodoAVL<E>(null));
			p.getIzq().setPadre(p);
			p.setDer(new NodoAVL<E>(null));
			p.getDer().setPadre(p);
		} else {
			int comparacion = comp.compare(item,p.getRotulo());
			if(comparacion==0) {
				p.setEliminado(false);
			} else if(comparacion<0) {
				insertAux(p.getIzq(),item);
				if(Math.abs(p.getIzq().getAltura()-p.getDer().getAltura())>1) {
					E y = p.getIzq().getRotulo();
					int comp_item_y = comp.compare(item,y);
					if( comp_item_y<0 ) rotacion1(p);
					else rotacion2(p);
				}
			} else if(comparacion>0) {
				insertAux(p.getDer(),item);
				if(Math.abs(p.getIzq().getAltura()-p.getDer().getAltura())>1) {
					E y = p.getDer().getRotulo();
					int comp_item_y = comp.compare(y,item);
					if(comp_item_y<0) rotacion3(p);
					else rotacion4(p);
				}
			}
			p.setAltura(max(p.getIzq().getAltura(),p.getDer().getAltura())+1);
		}
	}
	
	private void rotacion1(NodoAVL<E> p) {
		 NodoAVL<E> x = p;
	     NodoAVL<E> y = p.getIzq();
	     NodoAVL<E> z = p.getIzq().getIzq();
	     NodoAVL<E> xPadre = x.getPadre();
  	     if(x==raiz) {
  	    	 raiz=y;
  	    	 y.setPadre(null);
  	    	 y.getDer().setPadre(x);
  	    	 x.setIzq(y.getDer());
  	    	 y.setIzq(z);
  	    	 y.setDer(x);
  	    	 z.setPadre(y);
  	    	 x.setPadre(y);
  	     } else {
  	    	 if(xPadre.getIzq()==x) 
  	    		 xPadre.setIzq(y);
  	    	 else if(xPadre.getDer()==x)
  	    		 xPadre.setDer(y);
  	    	 y.setPadre(x.getPadre());
  	  	     y.getDer().setPadre(x);
  	  	     x.setIzq(y.getDer());
  	  	     y.setIzq(z);
  	  	     y.setDer(x);
  	  	     z.setPadre(y);
  	  	     x.setPadre(y);
  	     }	 
	}
	
	private void rotacion2(NodoAVL<E> p) {
		NodoAVL<E> x = p;
		NodoAVL<E> y = p.getIzq();
		NodoAVL<E> z = p.getIzq().getDer();
		NodoAVL<E> xPadre = x.getPadre();
		if(x==raiz) {
			raiz=z;
			z.setPadre(null);
			z.getIzq().setPadre(y);
			y.setDer(z.getIzq());
			z.getDer().setPadre(x);
			x.setIzq(z.getDer());
			y.setPadre(z);
			x.setPadre(z);
			z.setIzq(y);
			z.setDer(x);
		} else {
			if(xPadre.getIzq()==x)
				xPadre.setIzq(z);
			else if(xPadre.getDer()==x)
					xPadre.setDer(z);
			z.setPadre(x.getPadre());
			z.getIzq().setPadre(y);
			y.setDer(z.getIzq());
			z.getDer().setPadre(x);
			x.setIzq(z.getDer());
			y.setPadre(z);
			x.setPadre(z);
			z.setIzq(y);
			z.setDer(x);
		}
	}
	
	private void rotacion3(NodoAVL<E> p) {
		NodoAVL<E> x = p;
		NodoAVL<E> y = p.getDer();
		NodoAVL<E> z = p.getDer().getDer();
		NodoAVL<E> xPadre = x.getPadre();
		if(x==raiz) {
			raiz = y;
			y.setPadre(null);
			y.getIzq().setPadre(x);
			x.setDer(y.getIzq());
			y.setIzq(x);
			x.setPadre(y);
		} else {
			if(xPadre.getIzq()==x)
				xPadre.setIzq(y);
			else if(xPadre.getDer()==x)
				xPadre.setDer(y);
			y.setPadre(x.getPadre());
			y.getIzq().setPadre(x);
			x.setDer(y.getIzq());
			y.setIzq(x);
			y.setDer(z);
			x.setPadre(y);
			z.setPadre(y);
		}
	}
	
	private void rotacion4(NodoAVL<E> p) {
		NodoAVL<E> x = p;
		NodoAVL<E> y = p.getDer();
		NodoAVL<E> z = p.getDer().getIzq();
		NodoAVL<E> xPadre = p.getPadre();
		if(x==raiz) {
			raiz = z;
			z.setPadre(null);
			z.getIzq().setPadre(x);
			x.setDer(z.getIzq());
			z.getDer().setPadre(y);
			y.setIzq(z.getDer());
			x.setPadre(z);
			y.setPadre(z);
			z.setIzq(x);
			z.setDer(y);
		} else {
			if(xPadre.getIzq()==x)
				xPadre.setIzq(z);
			else if(xPadre.getDer()==x)
				xPadre.setDer(z);
			z.setPadre(xPadre);
			z.getIzq().setPadre(x);
			x.setDer(z.getIzq());
			z.getDer().setPadre(y);
			y.setIzq(z.getDer());
			x.setPadre(z);
			y.setPadre(z);
			z.setIzq(x);
			z.setDer(y);
		}
	}
	
	private int max(int i,int j) {
		return i>j?i:j;
	}
	
	public String toString() {
		ColaConArregloCircular<NodoAVL<E>> col = new ColaConArregloCircular<NodoAVL<E>>();
        col.enqueue(raiz);
        col.enqueue(null);
        int i = 0;
        String texto = "Linea " + i +": ";
        try {
            while( !col.isEmpty() ) {
                NodoAVL<E> v;
                v = col.dequeue();
                if( v!=null ) {
                    texto = texto +" "+v.getRotulo().toString();
                    if(v.getIzq().getRotulo()!=null)
                    	col.enqueue(v.getIzq());
                    if(v.getDer().getRotulo()!=null)
                    	col.enqueue(v.getDer());
                }
                else {
                    i++;
                    if( !col.isEmpty() ) {
                        texto = texto + "\n" + "Linea " + i +": ";
                        col.enqueue(null);
                    }
                }
            }
        } catch (EmptyQueueException e) {
            e.printStackTrace();
        }
        return texto;
	}
	
	public NodoAVL<E> buscar(E x){
		return buscarAux(x,raiz);
	}
	
	private NodoAVL<E> buscarAux(E x,NodoAVL<E> r){
		if(r.getRotulo()==null)
			return r;
		else {
			int c = comp.compare(x,r.getRotulo());
			if(c==0)
				return r;
			else if(c<0)
				return buscarAux(x,r.getIzq());
			else 
				return buscarAux(x,r.getDer());
		}
	}
	
	public NodoAVL<E> getRoot(){
		return raiz;
	}
}
