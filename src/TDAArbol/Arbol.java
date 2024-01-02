package TDAArbol;

import java.util.Iterator;
import Exceptions.*;
import TDAArbol.Position;
import TDACola.ColaConArregloCircular;
import TDAMapeo.Map;
import TDAMapeo.MapeoOpenHash;
import TDAlista.*;
import TDApila.PilaConEnlaces;

public class Arbol <E> implements Tree <E> {

	protected TNodo<E> root;
	protected int size;

	public Arbol() {
		root = null;
		size = 0;
	}
	
	//O(1)
	public int size() {
		return size;
	}

	//O(1)
	public boolean isEmpty() {
		return root == null;
	}
	
	//O(2n) 
	public Iterator<E> iterator() {
		PositionList <E> positions = new ListaDoblementeEnlazada<E>();
		for (Position<E> pos : positions()) {
			positions.addLast (pos.element());
		}
		return positions.iterator();
	}

	//O(n)
	public Iterable <Position<E>> positions() {
		PositionList<Position<E>> p = new ListaDoblementeEnlazada<Position<E>>();
		if (!isEmpty()) preOrder (root, p);
			return p;
	}

	//O(n)
	protected void preOrder (TNodo<E> v, PositionList<Position<E>> l) {
		l.addLast (v);
		for (TNodo<E> c : v.getHijos()) {
			preOrder (c, l );
		}
	}
	
	//O(1)
	public E replace (Position<E> v, E element) throws InvalidPositionException {
		TNodo <E> n = checkPosition(v);
		E oldElement = n.element();
		n.setElemento (element);
		return oldElement;
	}

	//O(1)
	public Position<E> root() throws EmptyTreeException {
		if (isEmpty()) 
			throw new EmptyTreeException ("isEmpty: El �rbol se encuentra vac�o.");
		return root;
	}

	//O(1)
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		TNodo <E> n = checkPosition(v);
		if (isRoot(v)) 
			throw new BoundaryViolationException ("parent: No es posible obtener el nodo padre de la ra�z.");
		return n.getPadre();
	}

	//O(m) m = hijos de v
	public Iterable <Position<E>> children(Position<E> v) throws InvalidPositionException {
		TNodo<E> padre = checkPosition(v);
		PositionList <Position<E>> children = new ListaDoblementeEnlazada <Position<E>>();
		for (TNodo<E> hijo : padre.getHijos()) {
			children.addLast(hijo);
		}
		return children;
	}

	//O(1)
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		TNodo <E> n = checkPosition(v);
		return (!n.getHijos().isEmpty());
	}

	//O(1)
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		TNodo <E> n = checkPosition(v);
		return n.getHijos().isEmpty();
	}
	
	//O(1)
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		TNodo <E> n = checkPosition(v);
		return n==root;
	}

	//O(1)
	public void createRoot(E element) throws InvalidOperationException {
		if (!isEmpty() ) 
			throw new InvalidOperationException ("createRoot: Ya existe una ra�z en el �rbol.");
		root = new TNodo <E> (element, null);
		size ++;
	}

	//O(1)
	public Position<E> addFirstChild(Position<E> p, E element) throws InvalidPositionException {
		if( isEmpty() )
			throw new InvalidPositionException("El arbol esta vacio");
		TNodo <E> n = checkPosition(p);
		TNodo <E> newChild = new TNodo<E> (element, n);
		n.getHijos().addFirst(newChild);
		size ++;
		return newChild;
	}

	//O(1)
	public Position<E> addLastChild(Position<E> p, E element) throws InvalidPositionException {
		if( isEmpty() )
			throw new InvalidPositionException("El arbol esta vacio");
		TNodo <E> n = checkPosition(p);
		TNodo <E> newChild = new TNodo<E> (element,n);
		n.getHijos().addLast (newChild);
		size ++;
		return newChild;
	}

	//O(m) m = siendo la cantidad de hijos de p
	public Position<E> addBefore(Position<E> p, Position<E> rb, E element) throws InvalidPositionException {
		if (isEmpty()) throw new InvalidPositionException ("addBefore: El �rbol se encuentra vac�o.");
		TNodo<E> padre = checkPosition (p);
		TNodo<E> hermanoDer = checkPosition (rb);
		if (!(hermanoDer.getPadre() == padre)) 
			throw new InvalidPositionException ("El primer Nodo no es el padre del segundo.");
		TNodo<E> newChild = new TNodo<E>(element,padre);
		PositionList<TNodo<E>> hijos = padre.getHijos();	
		boolean encontre = false;
		try {
			TDAlista.Position<TNodo<E>> pos = hijos.first();
			while (pos != null && !encontre) {
				if (pos.element() == hermanoDer) encontre = true;
				else pos = (pos != hijos.last() ? hijos.next(pos) : null);
			}
			if (!encontre) 
				throw new InvalidPositionException ("addBefore: p no es padre del Nodo rb.");
			hijos.addBefore (pos,newChild);
			size++;
		} catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {
			e.printStackTrace();
		}
		return newChild;
	}
	
	//O(m) m = siendo m la cantidad de hijos de p
	public Position<E> addAfter(Position<E> p, Position<E> lb, E element) throws InvalidPositionException {
		if ( isEmpty() ) 
			throw new InvalidPositionException ("addBefore: El �rbol se encuentra vac�o.");
		TNodo<E> padre = checkPosition (p);
		TNodo<E> hermanoIzq = checkPosition (lb);
		if (!(hermanoIzq.getPadre()== padre)) throw new InvalidPositionException ("El primer Nodo no es el padre del segundo.");
		TNodo<E> newNode = new TNodo<E>(element,padre);
		PositionList<TNodo<E>> hijos = padre.getHijos();	
		boolean encontre = false;
		try {
			TDAlista.Position<TNodo<E>> pos = hijos.first();
			while (pos != null && !encontre) {
				if (pos.element() == hermanoIzq) encontre = true;
				else pos = (pos != hijos.last() ? hijos.next(pos) : null);
			}
			if (!encontre) throw new InvalidPositionException ("addAfter: p no es padre del Nodo rb.");
			hijos.addAfter (pos, newNode);
			size++;
		} catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {
			e.printStackTrace();
		}
		return newNode;
	}

	//O(m) m = la cantiada de hijos del padre de p
	public void removeExternalNode (Position<E> p) throws InvalidPositionException {
		 if (isEmpty()) 
			 throw new InvalidPositionException("removeExternalNode: El �rbol se encuentra vac�o.");
		    TNodo<E> n = checkPosition (p);
		    if (!isExternal(n)) throw new InvalidPositionException("removeExternalNode: p no es un nodo externo del �rbol.");
		    if (isRoot(p)) {
		        removeRoot (p);
		    } else {
		        TNodo<E> padre = n.getPadre();
		        PositionList<TNodo<E>> hijos = padre.getHijos();
		        boolean encontre = false;
		        TDAlista.Position<TNodo<E>> pos = null;
		        Iterable<TDAlista.Position<TNodo<E>>> posiciones = hijos.positions();
		        Iterator <TDAlista.Position<TNodo<E>>> it = posiciones.iterator();
		        while (it.hasNext() && !encontre) {
		            pos = it.next();
		            if (pos.element() == n) encontre = true;
		        }
		        if (!encontre) throw new InvalidPositionException("removeExternalNode: No es posible eliminar, ya que p no es un hijo de su padre.");
		        hijos.remove (pos);
		        n.setElemento(null);
		        size--;
		    }

	}

	//O(m + n) m = hijos del padre de p , n = cantidad de hijos p
	public void removeInternalNode(Position<E> p) throws InvalidPositionException {
		if( isEmpty() )
			throw new InvalidPositionException("El arbol est vacio");
		if( isExternal(p) )
			throw new InvalidPositionException("El nodo no es interno");
		TNodo<E> n = checkPosition(p);
		TNodo<E> padre= n.getPadre();
		if( isRoot(p) )
			removeRoot(p);
		else {
			PositionList<TNodo<E>> hPadre = padre.getHijos(); 
			PositionList<TNodo<E>> hN=n.getHijos();
			try {
				TDAlista.Position<TNodo<E>>posDeN;
				TDAlista.Position<TNodo<E>> cursor= hPadre.first();
				while(cursor.element()!=n && cursor!=null){
					if (cursor==hPadre.last())
						cursor=null;
					else
						cursor= hPadre.next(cursor);
				}
				if(cursor!=null)	
					posDeN= cursor;
				else throw new InvalidPositionException("La estructura no corresponde a un arbol valido");		
				while(!hN.isEmpty()) {
					TDAlista.Position<TNodo<E>> hijoN = hN.first();
					hPadre.addBefore(posDeN,hijoN.element());
					hijoN.element().setPadre(padre);
					hN.remove(hijoN);
				}
				hPadre.remove(posDeN);
			} catch(EmptyListException | BoundaryViolationException e) {
				System.out.println( e.getMessage() );
			}
			size--;
		}	
	}

	// O(n,m) = O(O(m+n) + O(m)) = O(max(m+n,m))
	public void removeNode(Position<E> p) throws InvalidPositionException {
		if (isRoot(p))
			removeRoot (p);
		else if (isInternal(p))
			removeInternalNode(p);
		else 
			removeExternalNode(p);
	}
	
	// O(1) 
	protected void removeRoot (Position <E> p) throws InvalidPositionException {
	    if(size == 0) 
	    	throw new InvalidPositionException("No se puede eliminar de un arbol vacio");
	    try {
	        if(root.getHijos().size() == 1) {
	            TDAlista.Position<TNodo<E>> rN = root.getHijos().first();
	            rN.element().setPadre(null);
	            TNodo<E> aux = rN.element();
	            root.getHijos().remove(rN);
	            root = aux;
	            size--;
	        } else if (size == 1) {
	            root = null;
	            size--;
	        } else throw new InvalidPositionException("Solo se puede eliminar la raiz si es el unico elemento o si tiene un solo hijo");
	    } catch(EmptyListException e) {
	        throw new InvalidPositionException(e.getMessage());
	    }
	}
	
	//O(1)
	protected TNodo <E> checkPosition (Position <E> p) throws InvalidPositionException {	
		TNodo <E> toReturn = null;
		if (p == null || !(p instanceof TNodo)) throw new InvalidPositionException ("La posici�n es nula.");
		if (p.element() == null) throw new InvalidPositionException ("Posici�n eliminada previamente.");
		try {
			toReturn = (TNodo <E>) p;
		} catch (ClassCastException e) {
			throw new InvalidPositionException ("P no es es un Nodo de la lista.");
		}
			return toReturn;
	}
	
	//PREORDEN
	public PositionList<Position<E>> preOrden() throws InvalidPositionException{
		if( isEmpty() )
			throw new InvalidPositionException("El arbol esta vacio");
		PositionList<Position<E>> l = new ListaDoblementeEnlazada<Position<E>>();
		try {
			preOrdenPriv(root(),l);
		} catch (EmptyTreeException e) {
			System.out.println( e.getMessage() );
		}
		return l;
	}
	
	private void preOrdenPriv(Position<E> r,PositionList<Position<E>> l){
		l.addLast(r);
		try {
			for(Position<E> w: children(r)) {
				preOrdenPriv(w,l);
			}
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
	}
	
	//POSTORDEN
	public PositionList<Position<E>> postOrden() throws InvalidPositionException{
		PositionList<Position<E>> l = new ListaDoblementeEnlazada<Position<E>>();
		if( isEmpty() )
			throw new InvalidPositionException("El arbol esta vacio");
		try {
			postOrdenPriv(root(),l);
		} catch (EmptyTreeException e) {
			System.out.println( e.getMessage() );
		}
		return l;
	}
	
	private void postOrdenPriv(Position<E> r,PositionList<Position<E>> l) {
		try {
			for(Position<E> w: children(r)) {
				postOrdenPriv(w,l);
			}
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
		l.addLast(r);
	}
	
	//ITPREORDEN
	public Iterator<Position<E>> itPreOrden() throws InvalidPositionException{
		if( isEmpty() )
			throw new InvalidPositionException("El arbol esta vacio");
		PositionList<Position<E>> l = new ListaDoblementeEnlazada<Position<E>>();
		try {
			preOrdenPriv(root(),l);
		} catch (EmptyTreeException e) {
			System.out.println( e.getMessage() );
		}
		return l.iterator();
	}
	
	//INORDEN
	public PositionList<Position<E>> inOrden() throws InvalidPositionException{
		PositionList<Position<E>> l = new ListaDoblementeEnlazada<Position<E>>();
		if( isEmpty() )
			throw new InvalidPositionException("El arbol esta vacio");
		try {
			inOrdenPriv(root(),l);
		} catch (EmptyTreeException e) {
			System.out.println( e.getMessage() );
		}
		return l;
	} 
	
	private void inOrdenPriv(Position<E> r,PositionList<Position<E>> l) {
		try {
			if( isExternal(r) )
				l.addLast(r);
			else {
				Iterator<Position<E>> i = children(r).iterator();
				Position<E> w = i.next();
				inOrdenPriv(w,l);
				l.addLast(r);
				while( i.hasNext() ) {
					w = i.next();
					inOrdenPriv(w,l);
				}
			}
		} catch(InvalidPositionException e) {
			System.out.println( e.getMessage() );
		}
	}
	
	//CLONE
	public Tree<E> clone() {
		Tree<E> arb = new Arbol<E>();
		try {
			arb.createRoot(root().element());
			cloneRec(root(),(TNodo<E>)arb.root(),arb);
		} catch (InvalidOperationException | EmptyTreeException e) {
			System.out.println( e.getMessage() );
		}
		return arb;
	}
	
	private void cloneRec(Position<E> r,TNodo<E> rArb,Tree<E> arb) {
		try {
			for(Position<E> n: children(r)){
				arb.addLastChild(rArb,n.element());
				cloneRec(n, rArb.getHijos().last().element(),arb);
			}
		} catch (InvalidPositionException | EmptyListException e) {
			e.printStackTrace();
		}
	}
	
	//PRINT POR NIVELES
	public String printPorNiveles() {
		ColaConArregloCircular<Position<E>> col = new ColaConArregloCircular<Position<E>>();
		col.enqueue(root);
		col.enqueue(null);
		int i = 0;
		String texto = "Linea " + i +": ";
		try {
			while( !col.isEmpty() ) {
				Position<E> v;
				v = col.dequeue();
				if( v!=null ) {
					texto = texto + v.toString();
					Iterator<Position<E>> it1 = children(v).iterator();
					while( it1.hasNext() ) {
						col.enqueue(it1.next());
					}
				}
				else {
					i++;
					if( !col.isEmpty() ) {
						texto = texto + "\n" + "Linea " + i +": ";
						col.enqueue(null);
					}	
				}
			}
		} catch (EmptyQueueException | InvalidPositionException e) {
			e.printStackTrace();
		}
		return texto;
	}
	
	//PRINT POR NIVELES AL REVES
	public String printPorNivelesAlR() {
        ColaConArregloCircular<Position<E>> col = new ColaConArregloCircular<Position<E>>();
        PilaConEnlaces<Position<E>> pil = new PilaConEnlaces<Position<E>>();
        col.enqueue(root);
        col.enqueue(null);
        String texto = null;
        int i = 0;
        try {
            while( !col.isEmpty() ) {
                Position<E> v;
                v = col.dequeue();
                if( v!=null ) {
                    pil.push(v);
                    Iterator<Position<E>> it1 = children(v).iterator();
                    while( it1.hasNext() ) {
                        col.enqueue(it1.next());
                    }
                }
                else {
                    i++;
                    if( !col.isEmpty() ) {
                        pil.push(v);
                        col.enqueue(null);
                    }
                }
            }
            i--;
            texto = "Linea " + i +": ";
            PilaConEnlaces<Position <E>> printer = new PilaConEnlaces<Position <E>> ();
            while (!pil.isEmpty()) {
                while (!pil.isEmpty() && pil.top() != null) {
                    printer.push(pil.pop());
                }

                while (!printer.isEmpty()) {
                    texto += printer.pop().toString() + " ";
                }

                if (!pil.isEmpty()) {
                    pil.pop();
                    i--;
                    texto +="\n"+ "Linea " + i +": ";
                }
            }
        } catch (EmptyQueueException | InvalidPositionException | EmptyStackException e) {
            e.printStackTrace();
        }
        return texto;
    }
	
	//EXTREMOS IZQUIERDO
	public PositionList<Position<E>> extremIzq() throws InvalidPositionException{
		PositionList<Position<E>> l = new ListaDoblementeEnlazada<Position<E>>();
		if( isEmpty() )
			throw new InvalidPositionException("la lista esta vacia");
		if( size!=1 )
			extremIzqRecurc(root,l);
		return l;
	}
	
	private void extremIzqRecurc(TNodo<E> r,PositionList<Position<E>> l) {
		try {
			for(TNodo<E> n: r.getHijos()) { 
				if( n==r.getHijos().first().element() && !n.getHijos().isEmpty() ) {
					l.addLast(n);
					extremIzqRecurc(n,l);
				}
				extremIzqRecurc((TNodo<E>) n,l);
			}
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
	}
	
	//ELIMINAR EXTREMO IZQUIERDO
	public void elimExtremIzq() throws InvalidPositionException{
		PositionList<TNodo<E>> l = new ListaDoblementeEnlazada<TNodo<E>>();
		if( isEmpty() )
			throw new InvalidPositionException("la lista esta vacia");
		if( size!=1 )
			elimExtremIzqRecurc(root,l);
		for(TNodo<E> n:l) {
			removeNode(n);
		}
	}
	
	private void elimExtremIzqRecurc(TNodo<E> r,PositionList<TNodo<E>> l) {
		try {
			for(TNodo<E> n:r.getHijos()) { 
				if( n==r.getHijos().first().element() && !n.getHijos().isEmpty() ) {
					l.addLast(n);
					elimExtremIzqRecurc(n,l);
				}
				elimExtremIzqRecurc(n,l);
			}
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
	}
	
	//ELIMINAR HOJAS DEL ARBOL
	public void eliminarHojas() {
		PositionList<TNodo<E>> l = new ListaDoblementeEnlazada<TNodo<E>>();
		try {
			if (size == 1)
				removeRoot(root);
			else eliminarRecurc(root,l);
			for(TNodo<E> n:l)
				removeNode(n);
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
	}
	
	private void eliminarRecurc(TNodo<E> r,PositionList<TNodo<E>> l) {
		try {
			for( TNodo<E> p: r.getHijos() ) {
				if( isExternal(p) )
					l.addLast(p);
				else {
					eliminarRecurc(p,l);
				}
			}	
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
	}
	
	//ELIMINAR NODO CON ROTULO R
	public void elimR(E r) throws EmptyTreeException{
		PositionList<TNodo<E>> l = new ListaDoblementeEnlazada<TNodo<E>>();
	    if (isEmpty()) 
	    	throw new EmptyTreeException ("El �rbol esta vacio");
		elimRRec(root,r,l);
		try {
			for(TNodo<E> n:l)
				removeNode(n);
		} catch(InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void elimRRec(TNodo<E> n,E r,PositionList<TNodo<E>> l) {
		for( TNodo<E> p: n.getHijos() ) {
			if(n.element()==r)
				l.addLast(n);
			elimRRec(p,r,l);
		}
	}
	
	// CAMINO ENTRE NODOS
	public PositionList<Position<E>> camino(Position<E> n1,Position<E> n2) {
		PositionList<Position<E>> l = new ListaDoblementeEnlazada<Position<E>>();
		caminoRec(root,(TNodo<E>) n1,(TNodo<E>) n2,l,null,null);
		return l;
	}
	
	private void caminoRec(TNodo<E> r,TNodo<E> n1,TNodo<E> n2,PositionList<Position<E>> l,TNodo<E> aux1,TNodo<E> aux2) {
			for( TNodo<E> p: r.getHijos() ) {
				if( aux1==n1 && aux2==null ) {
					l.addLast(root);
					aux1 = new TNodo<E>(null);
				}
				if( p==n1 || p==n2 ) {
					if( aux1==null) {
						aux1 = p;
						l.addLast(p);
						caminoRec(p,n1,n2,l,aux1,aux2);
					} else if( aux2==null ){
						aux2 = p;
						l.addLast(p);
					}
				}
				else {
					caminoRec(p,n1,n2,l,aux1,aux2);
					if( aux2!=null )
						l.addLast(p);
				}
			}
	}
	
	//PROFUNDIDAD DEL ARBOL
	public String profundidad(Position<E> v) {
		PositionList<Position<E>> l = new ListaDoblementeEnlazada<Position<E>>();
		profundidadRec(l,v);
		return l.toString() + " Profundidad: " + (l.size()-1);
	}
	
	private void profundidadRec(PositionList<Position<E>> l,Position<E> v) {
		try {
			if( isRoot(v) ) {
				l.addLast(v);
			} else {
				l.addLast(v);
				profundidadRec(l,parent(v));
			}
		} catch(InvalidPositionException | BoundaryViolationException e) {
			System.out.println( e.getMessage() );
		}
	}
	
	//ALTURA DEL ARBOL
	public int altura(Position<E> v) {
		TNodo<E> aux = (TNodo<E>)v;
		if( aux.getHijos().isEmpty() ) {
			return 0;
		} else {
			int h = 0;
			for(Position<E> w: aux.getHijos() ) {
				h = Math.max(h,altura(w));
			}
			return h+1;
		}
	}
	
	//CAMINO DE LA ALTURA
	public String caminoAltura(E r){
		ListaDoblementeEnlazada<Position<E>> l = new ListaDoblementeEnlazada<Position<E>>();
		ListaDoblementeEnlazada<Position<E>> max = new ListaDoblementeEnlazada<Position<E>>();
		try {
			for(Position<E> p: positions() ) {
				if( isExternal(p) ) {
					caminoAlturaRec(r,l,p);
				}
				if( l.size() > max.size() ) {
					max = l;
				}	
				l = new ListaDoblementeEnlazada<Position<E>>();
			}
		} catch (InvalidPositionException e) {
				e.printStackTrace();
		}
		return max.toString() + " Altura: " + (max.size()-1);
	}
	
	public boolean caminoAlturaRec(E r,PositionList<Position<E>> l,Position<E> p) {
		try {
			if( p.element()==r ) {
				l.addFirst(p);
				return true;
			}	
				else {
					if( p!=root	) {
						if(caminoAlturaRec(r,l,parent(p)) ) {
								l.addFirst(p);
								return true;
						} else return false;
 					}	
				}
		} catch(InvalidPositionException | BoundaryViolationException e) {
			System.out.println(e.getMessage());
		}	
		return false;
	}
	
	//ANCESTRO EN COMUN
	public Position<E> ancestroComun(Position<E> p1,Position<E> p2){
		Position<E> aux = null;
		aux = ancestroComunRec(p1,p2);
		if(aux==root) aux = ancestroComunRec(p2,p1);
		return aux;
	}
	
	private Position<E> ancestroComunRec(Position<E> p1,Position<E> p2){
        Position<E> aux = null;
        try {
        	if(p1==p2) return p1;
        	else if(p1==root | p2==root) return root;
        	else if(parent(p1)==p2) return p2;
        	else if(parent(p2)==p1) return p1;
        	aux = ancestroComunRec(parent(p1),parent(p2));
        	if(aux==root) aux = ancestroComunRec(parent(p1),p2);
        } catch(InvalidPositionException | BoundaryViolationException e) {
        	System.out.println(e.getMessage());
        }
        return aux;
    }
	
	//CAMINO DE UN NODO N1 A N2
	public PositionList<Position<E>>caminoN1N2(Position<E> n1, Position<E>n2){
        PositionList<Position<E>> caminoN1=new ListaDoblementeEnlazada<Position<E>>();
        Position<E> nuevaRaiz = ancestroComun(n1,n2);
        creadorDeCaminos1(caminoN1,n1,nuevaRaiz);
        caminoN1.addLast(nuevaRaiz);
        creadorDeCaminos2(caminoN1,n2,nuevaRaiz);
        return caminoN1;
    }

    private void creadorDeCaminos1(PositionList<Position<E>> l,Position<E>t,Position<E>p) {
        try {
            if(t!=p) {
                l.addLast(t);
                creadorDeCaminos1(l,parent(t),p);
            }
        } catch (InvalidPositionException | BoundaryViolationException   e) {
            e.printStackTrace();
        }
    }

    private void creadorDeCaminos2(PositionList<Position<E>> l,Position<E>t,Position<E>p) {
        try {
            if(t!=p) {
                creadorDeCaminos2(l,parent(t),p);
                l.addLast(t);
            }
        } catch (InvalidPositionException | BoundaryViolationException   e) {
            e.printStackTrace();
        }
    }
}
