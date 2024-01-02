package TDAGrafo;

import Exceptions.EmptyListException;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidPositionException;
import Exceptions.InvalidVertexException;
import TDAMapeo.MapeoOpenHash;
import TDAlista.ListaDoblementeEnlazada;
import TDAlista.Position;
import TDAlista.PositionList;

public class GrafoMatriz<V,E> implements Graph<V,E> {
	private class Vertice<V> extends MapeoOpenHash<Object,Object> implements Vertex<V>{
		private V rotulo;
		private int indice;
		private Position<Vertex<V>> posicionEnVertices;
		
		public Vertice(V rotulo,int indice) {
			this.rotulo = rotulo;
			this.indice = indice; 
			posicionEnVertices = null;
		}
		
		public V element() {
			return rotulo;
		}

		public V getRotulo() {
			return rotulo;
		}

		public void setRotulo(V rotulo) {
			this.rotulo = rotulo;
		}

		public int getIndice() {
			return indice;
		}

		public void setIndice(int indice) {
			this.indice = indice;
		}

		public TDAlista.Position<Vertex<V>> getPosicionEnVertice() {
			return posicionEnVertices;
		}

		public void setPosicionEnVertice(Position<Vertex<V>> position) {
			this.posicionEnVertices = position;
		}
		
		public String toString() {
			return "("+rotulo+")";
		}
	}
	
	private class Arco<V,E> extends MapeoOpenHash<Object,Object> implements Edge<E> {
		private E rotulo;
		private Vertice<V> v1, v2;
		private TDAlista.Position<Edge<E>> posicionEnArcos;
		private TDAlista.Position<Arco<V, E>> posicionEnIv1, posicionEnIv2;
		public Arco(E rotulo, Vertice<V> v1, Vertice<V> v2) {
			this.rotulo = rotulo;
			this.setV1(v1);
			this.setV2(v2);
		}
		
		@Override
		public E element() {
			return rotulo;
		}
		
		public TDAlista.Position<Edge<E>> getPosicionEnArcos() {
			return posicionEnArcos;
		}
		
		public void setPosicionEnArcos(TDAlista.Position<Edge<E>> posicionEnArcos) {
			this.posicionEnArcos = posicionEnArcos;
		}
		
		public TDAlista.Position<Arco<V, E>> getPosicionEnIv1() {
			return posicionEnIv1;
		}
		
		public void setPosicionEnIv1(TDAlista.Position<Arco<V, E>> posicionEnIv1) {
			this.posicionEnIv1 = posicionEnIv1;
		}
		
		public TDAlista.Position<Arco<V, E>> getPosicionEnIv2() {
			return posicionEnIv2;
		}
		
		public void setPosicionEnIv2(TDAlista.Position<Arco<V, E>> posicionEnIv2) {
			this.posicionEnIv2 = posicionEnIv2;
		}
		
		public Vertice<V> getV1() {
			return v1;
		}
		
		public void setV1(Vertice<V> v1) {
			this.v1 = v1;
		}
		
		public Vertice<V> getV2() {
			return v2;
		}
		
		public void setV2(Vertice<V> v2) {
			this.v2 = v2;
		}
		
		public String toString() {
			return "("+rotulo+")";
		}
	}
	private PositionList <Vertex <V>> vertices;
	private PositionList <Edge <E>> arcos;
	private Edge <E> [][] matriz;
	private int cantidadVertices;
	
	public GrafoMatriz (int n) {
		vertices = new ListaDoblementeEnlazada <Vertex <V>>();
		arcos = new ListaDoblementeEnlazada <Edge <E>>();
		matriz = (Edge <E> [][]) new Arco [n][n];
		cantidadVertices = 0;
	}
	
	public GrafoMatriz () {
		this (20);
	}
	
	//O(n) n = cantidad de vertices
	public Iterable <Vertex <V>> vertices() {
		PositionList<Vertex<V>> vert = new ListaDoblementeEnlazada<Vertex<V>>();
		for(Vertex<V> e:vertices)
			vert.addLast(e);
		return vert;
	}

	// O(m) m = cantidad de arcos
	public Iterable <Edge<E>> edges() {
		PositionList<Edge<E>> arc = new ListaDoblementeEnlazada<Edge<E>>();
		for(Edge<E> e:arcos)
			arc.addLast(e);
		return arc;
	}

	// O(n + deg(v)) n = cantidad de vertices,  deg(v) = el grado de los arcos de v
	public Iterable <Edge<E>> incidentEdges (Vertex<V> v) throws InvalidVertexException {
		if(v==null)
			throw new InvalidVertexException("El vertice es invalido");
		Vertice<V> vv = (Vertice<V>) v;
		int i = vv.getIndice();
		PositionList<Edge<E>> lista = new ListaDoblementeEnlazada<Edge<E>>();
		for(int j=0; j<cantidadVertices; j++)
			if(matriz[i][j]!=null)
				lista.addLast(matriz[i][j]);
		return lista;
	}

	// O(1)
	public Vertex <V> opposite (Vertex <V> v, Edge <E> e) throws InvalidVertexException, InvalidEdgeException {
		if(v==null)
			throw new InvalidVertexException("El vertice es invalido");
		if(e==null)
			throw new InvalidEdgeException("El arco es invalido");
		Arco<V,E> ee = (Arco<V,E>)e;
		if(ee.getV1()==v) return ee.getV2();
		else if(ee.getV2()==v) return ee.getV1();
		else throw new InvalidEdgeException("El arco y el vertice no estan relacionados");
	}

	// O(1)
	public Vertex<V> [] endvertices (Edge <E> e) throws InvalidEdgeException {
		if(e==null)
			throw new InvalidEdgeException("El arco es invalido");
		Vertex<V> [] a =(Vertex<V>[]) new Vertice[2];
		Arco<V,E> ee = (Arco<V,E>) e;
		a[0] = ee.getV1();
		a[1] = ee.getV2();
		return a;
	}

	// O(1)
	public boolean areAdjacent (Vertex <V> v, Vertex <V> w) throws InvalidVertexException {
		if(v==null || w==null)
			throw new InvalidVertexException("El vertice es invalido");
		Vertice<V> vv =(Vertice<V>) v;
		Vertice<V> ww = (Vertice<V>) w;
		int i = vv.getIndice();
		int j = ww.getIndice();
		return matriz[i][j]!=null;
	}

	// O(1)
	public V replace (Vertex <V> v, V x) throws InvalidVertexException {
		if(v==null)
			throw new InvalidVertexException("El vertice es invalido");
		V toReturn = v.element();
		Vertice<V> aux = (Vertice<V>) v;
		aux.setRotulo(x);
		return toReturn;
	}

	// O(n^2) n = cantiada de vertices
	public Vertex<V> insertVertex(V x) {
		if(cantidadVertices==matriz.length)
			expandMatriz();
		Vertice<V> vv = new Vertice<V>(x,cantidadVertices++);
		vertices.addLast(vv);
		try {
			vv.setPosicionEnVertice(vertices.last());
		} catch (EmptyListException e) {
			System.out.println(e.getMessage());
		}
		return vv;
	}

	// O(1)
	public Edge<E> insertEdge (Vertex <V> v, Vertex <V> w, E e) throws InvalidVertexException {
		if(v==null || w==null )
			throw new InvalidVertexException("El vertice no es valido");
		Vertice<V> vv = (Vertice<V>) v;
		Vertice<V> ww = (Vertice<V>) w;
		int fila = vv.getIndice();
		int col = ww.getIndice();
		Arco<V,E> arco = new Arco<V,E>(e,vv,ww);
		matriz[fila][col] = matriz[col][fila] = arco;
		arcos.addLast(arco);
		try {
			arco.setPosicionEnArcos(arcos.last());
		} catch (EmptyListException e1) {
			System.out.println(e1.getMessage());
		}
		return arco;
	}

	@SuppressWarnings("unchecked")
	// O(n^2) n = cantidad vertices
	public V removeVertex (Vertex <V> v) throws InvalidVertexException {
		if(v==null) throw new InvalidVertexException("El vertice es invalido");
		try {
			Vertice<V> vv = (Vertice<V>)v;
			TDAlista.Position<Vertex<V>> pos = vv.getPosicionEnVertice();
			int i = vv.getIndice();
			for(int j=0; j< cantidadVertices; j++)
				matriz[i][j] = null;
			for(TDAlista.Position<Edge<E>> ed: arcos.positions() ) {
				Arco<V,E> aux = (Arco<V,E>) ed.element();
				if(aux.getV1()==vv || aux.getV2()==vv)
					arcos.remove(ed);
			}
			return vertices.remove(pos).element();
		} catch(InvalidPositionException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	// O(1)
	public E removeEdge(Edge <E> e) throws InvalidEdgeException {
		if(e==null)
			throw new InvalidEdgeException("El arco es invalido");
		try {
			Arco<V,E> ee = (Arco<V,E>)e;
			int fila = ee.getV1().getIndice();
			int col = ee.getV2().getIndice();
			matriz[fila][col] = matriz[col][fila] = null;
			arcos.remove(ee.getPosicionEnArcos());
			return e.element();
		} catch(InvalidPositionException e1) {
			System.out.println(e1.getMessage());
		}
		return null;
	}
	
	private void expandMatriz () {
		Edge <E> [][] nuevaMatriz = (Edge <E> [][]) new Arco [matriz [0].length * 2][matriz [0].length * 2];
		for (int i = 0; i < matriz [0].length; i ++) {
			for (int j = 0; j < matriz.length; j ++) {
				nuevaMatriz [i][j] = matriz [i][j];
			}
		}
		matriz = nuevaMatriz;
	}
}
