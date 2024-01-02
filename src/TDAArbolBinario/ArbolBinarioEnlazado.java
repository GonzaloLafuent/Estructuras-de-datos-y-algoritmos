package TDAArbolBinario;

import java.util.Iterator;

import Exceptions.BoundaryViolationException;
import Exceptions.EmptyQueueException;
import Exceptions.EmptyTreeException;
import Exceptions.InvalidOperationException;
import Exceptions.InvalidPositionException;
import TDACola.ColaConArregloCircular;
import TDAlista.ListaDoblementeEnlazada;
import TDAlista.PositionList;

public class ArbolBinarioEnlazado<E> implements BinaryTree<E> {
	protected BTPosition<E> root;
	protected int size;
	
	public ArbolBinarioEnlazado() {
		root = null;
		size = 0;
	}

	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return root == null;
	}

	@Override
	public Iterator<E> iterator() {
		Iterable<Position<E>> positions = positions();
		PositionList<E> elements = new ListaDoblementeEnlazada<E>();
		for( Position<E> pos:positions)
			elements.addLast(pos.element());
		return elements.iterator();
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> positions = new ListaDoblementeEnlazada<Position<E>>();
		try {
			if( size!=0 )
				preorderPositions(root,positions);
		} catch(InvalidPositionException e) {
			System.out.println( e.getMessage() );
		}
		return positions;
	}

	@Override
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		E temp = v.element();
		n.setElement(e);
		return temp;
	}

	@Override
	public Position<E> root() throws EmptyTreeException {
		if(isEmpty() )
			throw new EmptyTreeException("El arbol esta vacio");
		return root;
	}

	@Override
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> n = checkPosition(v);
		Position<E> parentPos = n.getParent();
		if( parentPos ==null )
			throw new BoundaryViolationException("No posee padre");
		return parentPos;
	}

	@Override
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		checkPosition(v);
		PositionList<Position<E>> children = new ListaDoblementeEnlazada<Position<E>>();
		try {
			if( hasLeft(v) )
				children.addLast(left(v));
			if( hasRight(v) )
				children.addLast(right(v));
		} catch(InvalidPositionException | BoundaryViolationException e) {
			System.out.println( e.getMessage() );
		}
		return children;
	}

	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		checkPosition(v);
		return hasLeft(v) || hasRight(v);
	}

	@Override
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		checkPosition(v);
		return !hasLeft(v) && !hasRight(v);
	}

	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		checkPosition(v);
		return v == root;
	}

	@Override
	public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> n = checkPosition(v);
		Position<E> leftPos = n.getLeft();
		if( leftPos==null )
			throw new BoundaryViolationException("No posee hijo izquierdo");
		return leftPos;
	}

	@Override
	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> n = checkPosition(v);
		Position<E> rigthPos = n.getRigth();
		if( rigthPos==null )
			throw new BoundaryViolationException("No posee hijo derecho");
		return rigthPos;
	}

	@Override
	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		return( n.getLeft()!=null );
	}

	@Override
	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		return( n.getRigth()!=null );
	}

	@Override
	public Position<E> createRoot(E r) throws InvalidOperationException {
		if(!isEmpty() )
			throw new InvalidOperationException("El arbol ya posee una raiz");
		size++;
		root = new BTNode<E>(r,null,null,null);
		return root;
	}

	@Override
	public Position<E> addLeft(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		Position<E> leftPos = n.getLeft();
		if( leftPos!=null )
			throw new InvalidOperationException("La posicion ya tiene un hijo izqueirdo");
		BTPosition<E> newNode = new BTNode<E>(r,null,null,n);
		n.setLeft(newNode);
		size++;
		return newNode;
	}

	@Override
	public Position<E> addRight(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		Position<E> rigthPos = n.getRigth();
		if( rigthPos!=null )
			throw new InvalidOperationException("La posicion ya tiene un hijo derecho");
		BTPosition<E> newNode = new BTNode<E>(r,null,null,n);
		n.setRight(newNode);
		size++;
		return newNode;
	}

	@Override
	public E remove(Position<E> v) throws InvalidOperationException, InvalidPositionException {
		BTPosition<E> vv = checkPosition(v);
		if( v==root && size!=1 )
			throw new InvalidOperationException("No se puede eliminar la raiz");
		BTPosition<E> leftPos = vv.getLeft();
		BTPosition<E> rigthPos = vv.getRigth();
		if( leftPos!=null && rigthPos!=null )
			throw new InvalidPositionException("No se puede remover un nodo con dos hijos");
		BTPosition<E> ww;
		if( leftPos!=null )
			ww = leftPos;
		else if( rigthPos!=null)
			ww = rigthPos;
		else 
			ww = null;
		if( vv == root ) {
			if( ww!=null )
				ww.setParent(null);
			root = ww;
		}
		else {
			BTPosition<E> uu = vv.getParent();
			if(vv == uu.getLeft() )
				uu.setLeft(ww);
			else 
				uu.setRight(ww);
			if(ww!=null)
				ww.setParent(uu);
		}
		size--;
		return v.element();
	}

	@Override
	public void attach(Position<E> r, BinaryTree<E> T1, BinaryTree<E> T2) throws InvalidPositionException {
		BTPosition<E> n = checkPosition(r);
		if( isInternal(r) )
			throw new InvalidPositionException("No se puede adjuntar a un nodo interno");
		try {
			if( !T1.isEmpty() ) {
				BTPosition<E> r1 = checkPosition(T1.root());
				n.setLeft(r1);
				r1.setParent(n);
			}
			if( !T2.isEmpty() ) {
				BTPosition<E> r2 = checkPosition(T2.root());
				n.setRight(r2);
				r2.setParent(n);
			}
		} catch(EmptyTreeException e) {
			System.out.println( e.getMessage() );
		}	
	}
	
	private BTNode<E> checkPosition(Position<E> v) throws InvalidPositionException {
		if( v==null || !(v instanceof BTPosition) )
			throw new InvalidPositionException("La posicion es invalida");
		return (BTNode<E>) v;
	}
	
	//ORDENES
	private void preorderPositions(Position<E> v,PositionList<Position<E>> pos) throws InvalidPositionException{
		pos.addLast(v);
		try {
			if( hasLeft(v) )
				preorderPositions(left(v),pos);
			if( hasRight(v) )
				preorderPositions(right(v),pos);
		} catch (InvalidPositionException | BoundaryViolationException e) {
			System.out.println( e.getMessage() );
		}	
	}
	
	public PositionList<Position<E>> preOrden(){
		PositionList<Position<E>> l = new ListaDoblementeEnlazada<Position<E>>();
		preOrdenPriv(root,l);
		return l;
	}
	
	private void preOrdenPriv(BTPosition<E> v,PositionList<Position<E>> l) {
		try {
			if( !isEmpty() ) {
				l.addLast(v);
				if( hasLeft(v) ) {
					preOrdenPriv(v.getLeft(),l);
				} 
				if( hasRight(v) )
					preOrdenPriv(v.getRigth(),l);
			}
		} catch(InvalidPositionException e) {
			System.out.println( e.getMessage() );
		}	
	}
	
	public PositionList<Position<E>> postOrden(){
		PositionList<Position<E>> l = new ListaDoblementeEnlazada<Position<E>>();
		postOrdenPriv(root,l);
		return l;
	}
	
	private void postOrdenPriv(BTPosition<E> v,PositionList<Position<E>> l) {
		try {
			if( !isEmpty()) {
				if( hasLeft(v) )
					postOrdenPriv(v.getLeft(),l);
				if( hasRight(v) )
					postOrdenPriv(v.getRigth(),l);
				l.addLast(v);
			}	
		} catch(InvalidPositionException e) {
			
		}
	}
	
	public PositionList<Position<E>> inOrden(){
		PositionList<Position<E>> l = new ListaDoblementeEnlazada<Position<E>>();
		inOrdenPriv(root,l);
		return l;
 	}
	
	private void inOrdenPriv(BTPosition<E> v,PositionList<Position<E>> l){
		try {
			if( !isEmpty() ) {
				if( hasLeft(v) )
					inOrdenPriv(v.getLeft(),l);
				l.addLast(v);
				if( hasRight(v) )
					inOrdenPriv(v.getRigth(),l);
			}
		} catch(InvalidPositionException e) {
			System.out.println( e.getMessage() );
		}
	}
	
	public String porNiveles(){
		ColaConArregloCircular<Position<E>> c = new ColaConArregloCircular<Position<E>>();
		c.enqueue(root);
		c.enqueue(null);
		int i = 0;
		String texto =  "Linea " + i +": ";
		try {
			while( !c.isEmpty() ) {
				Position<E> v;
				v = c.dequeue();
				if( v!=null ) {
					texto = texto + v.toString();
					Iterator<Position<E>> it1 = children(v).iterator();
					while( it1.hasNext() ) {
						c.enqueue(it1.next());
					}
				}
				else {
					i++;
					if( !c.isEmpty() ) {
						texto = texto + "\n" + "Linea " + i + ": ";
						c.enqueue(null);
					}
				}
			} 
		} catch(InvalidPositionException | EmptyQueueException e) {
			System.out.println( e.getMessage() );
		}
		return texto;
	}
	
	//CLONE
	public BinaryTree<E> clone(){
		BinaryTree<E> t = new ArbolBinarioEnlazado<E>();
		try {
			BTPosition<E> r = (BTPosition<E>) t.createRoot(root.element());
			if( !isEmpty() )
				cloneRec(root,t,r);
		} catch(InvalidOperationException e) {
			System.out.println( e.getMessage() );
		}	
		return t;
	}
	
	private void cloneRec(BTPosition<E> v,BinaryTree<E> t,BTPosition<E> r) {
		try {
			if( hasLeft(v) ) {
				t.addLeft(r,v.getLeft().element());
				cloneRec(v.getLeft(),t,r.getLeft());
			}	
			if( hasRight(v) ) {
				t.addRight(r,v.getRigth().element());
				cloneRec(v.getRigth(),t,r.getRigth());
			}
		} catch(InvalidPositionException | InvalidOperationException e) {
			System.out.println( e.getMessage() );
		}
	}
	
	//SUBARBOL
	public static<E> boolean subArbol(BinaryTree<E> A,BinaryTree<E> A1) {
		Position<E> aux = null;
		boolean toReturn = false;
		try {
			for(Position<E> n:A.positions() ) {
				if(n.element()==A1.root().element() ) {
					aux = n;
					break;
				}
			}
			if(aux!=null) toReturn = subArbolRec(A,A1,aux,A1.root());
		} catch(EmptyTreeException e) {
			System.out.println(e.getMessage());
		}	
		return toReturn;
	}
	
	private static<E> boolean subArbolRec(BinaryTree<E> A,BinaryTree<E> A1,Position<E> Ra,Position<E> R1) {
		boolean toReturn = true;
		try {
			if(A.hasLeft(Ra) && A1.hasLeft(R1) )
				toReturn = (A.left(Ra) == A1.left(R1)) && subArbolRec(A,A1,A.left(Ra),A1.left(R1));
			else toReturn = false;
			if(A.hasRight(Ra) && A1.hasLeft(R1) )
				toReturn = (A.right(Ra) == A1.left(R1)) && subArbolRec(A,A1,A.right(Ra),A1.right(R1));
			else toReturn = false;
		} catch(InvalidPositionException | BoundaryViolationException e) {
			System.out.println(e.getMessage());
		}
		return toReturn;
	}
	
	public static<E> void preOrdV(BTPosition<E> v,ArbolBinarioEnlazado<E> A,PositionList<Position<E>> l) {
		try {
			if(!A.isEmpty()) {
				l.addLast(v);
				if(A.hasLeft(v))
					preOrdV(v.getLeft(),A,l);
				if(A.hasRight(v))
					preOrdV(v.getRigth(),A,l);
			}
		} catch(InvalidPositionException e) {
			System.out.println( e.getMessage() );
		}
	}
}
