package TDAGrafo;

import java.security.InvalidKeyException;

import Exceptions.EmptyListException;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidPositionException;
import Exceptions.InvalidVertexException;
import TDACola.ColaConArregloCircular;
import TDACola.Queue;
import TDAMapeo.MapeoOpenHash;
import TDAlista.ListaDoblementeEnlazada;
import TDAlista.PositionList;
import TDAMapeo.Entrada;
import TDAMapeo.Entry;
import TDAMapeo.Map;

public class DirectedGraph<V,E> implements GraphD<V,E> {
	private static final Object state = new Object();
	private static final Object visit = new Object();
	private static final Object noVisit = new Object();
	
	private class Vertice<V> extends MapeoOpenHash<Object,Object> implements Vertex<V>{
		private V rotulo;
		private int indice;
		private TDAlista.Position<Vertex<V>> posicionEnVertices;
		
		public Vertice(V rotulo,int indice) {
			this.rotulo = rotulo;
			this.indice = indice;
		}
		
		public V getRotulo() {
			return rotulo;
		}
		
		public void setRotulo(V rot) {
			rotulo = rot;
		}
		
		public int getIndice() {
			return indice;
		}
		
		public void setIndice(int indice) {
			this.indice = indice;
		}
		
		public V element() {
			return rotulo;
		}
		
		public TDAlista.Position<Vertex<V>> getPosicionEnVertices(){
			return posicionEnVertices;
		}
		
		public void setPosicionEnVertices(TDAlista.Position<Vertex<V>> pos) {
			posicionEnVertices = pos;
		}
		
		public String toString() {
			return "("+rotulo +")";
		}
	}
	private class Arco<V,E> extends MapeoOpenHash<Object,Object> implements Edge<E>{
		private E rotulo;
		private Vertice<V> vertOri;
		private Vertice<V> vertFin;
	    private TDAlista.Position<Edge<E>> posicionEnArcos;
	    
	    public Arco(E rotulo,Vertice<V> ori,Vertice<V> fin) {
	    	this.rotulo = rotulo;
	    	vertOri = ori;
	    	vertFin = fin;
	    }
	    
		public E getRotulo() {
			return rotulo;
		}

		public void setRotulo(E rotulo) {
			this.rotulo = rotulo;
		}

		public Vertice<V> getVertOri() {
			return vertOri;
		}

		public void setVertOri(Vertice<V> vertOri) {
			this.vertOri = vertOri;
		}

		public Vertice<V> getVertFin() {
			return vertFin;
		}

		public void setVertFin(Vertice<V> vertFin) {
			this.vertFin = vertFin;
		}

		public TDAlista.Position<Edge<E>> getPosicionEnArcos() {
			return posicionEnArcos;
		}

		public void setPosicionEnArcos(TDAlista.Position<Edge<E>> posicionEnArcos) {
			this.posicionEnArcos = posicionEnArcos;
		}

		public E element() {
			return rotulo;
		}
	}
	private PositionList<Vertex<V>> vertices;
	private PositionList<Edge<E>> arcos;
	private Edge<E> [][] matriz;
	private int cantidadVertices;
	
	public DirectedGraph() {
		this(5);
	}
	
	public DirectedGraph(int n) {
		vertices = new ListaDoblementeEnlazada<Vertex<V>>();
		arcos = new ListaDoblementeEnlazada<Edge<E>>();
		matriz = (Edge<E> [][]) new Arco[n][n];
		cantidadVertices = 0;
	}

	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> vert = new ListaDoblementeEnlazada<Vertex<V>>();
		for(Vertex<V> pos: vertices)
			vert.addLast(pos);
		return vert;
	}

	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> edg = new ListaDoblementeEnlazada<Edge<E>>();
		for(Edge<E> pos: arcos)
			edg.addLast(pos);
		return edg;
	}

	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		Vertice<V> vert = checkVertex(v);
		PositionList<Edge<E>> inc = new ListaDoblementeEnlazada<Edge<E>>();
		for(int i=0; i<matriz.length; i++) {
			if(matriz[i][vert.getIndice()]!=null )
				inc.addLast(matriz[i][vert.getIndice()]);
		}
		return inc;
	}

	@Override
	public Iterable<Edge<E>> succesorEdges(Vertex<V> v) throws InvalidVertexException {
		Vertice<V> vert = checkVertex(v);
		PositionList<Edge<E>> emrg = new ListaDoblementeEnlazada<Edge<E>>();
		for(int i=0; i<matriz.length; i++) {
			if(matriz[vert.getIndice()][i]!=null)
				emrg.addLast(matriz[vert.getIndice()][i]);
		}
		return emrg;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		Vertice<V> vert = checkVertex(v);
		Arco<V,E> arc = checkEdge(e);
		if(arc.getVertFin()==v)return arc.getVertOri();
		if(arc.getVertOri()==v)return arc.getVertFin();
		throw new InvalidEdgeException("No hay relacion entre en el arco y el vertice");
	}

	@Override
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		Arco<V,E> arc = checkEdge(e);
		Vertex<V> [] a = (Vertex<V>[]) new Vertice[2];
		a[0] = arc.getVertOri();
		a[1] = arc.getVertFin();
		return a;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		Vertice<V> vert = checkVertex(v);
		Vertice<V> vert2 = checkVertex(w);
		int i = vert.getIndice();
		int j = vert2.getIndice();
		return matriz[i][j]!=null;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		Vertice<V> vert = checkVertex(v);
		V temp = vert.getRotulo();
		vert.setRotulo(x);
		return temp;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		if(cantidadVertices==matriz.length)
			expandMatriz();
		Vertice<V> vert = new Vertice<V>(x,cantidadVertices++);
		vertices.addLast(vert);
		try {
			vert.setPosicionEnVertices(vertices.last());
		} catch(EmptyListException e) {
			System.out.println(e.getMessage());
		}
		return vert;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		Vertice<V> vert = checkVertex(v);
		Vertice<V> vert2 = checkVertex(w);
		int fila = vert.getIndice();
		int col = vert2.getIndice();
		Arco<V,E> arc = new Arco(e,vert,vert2);
		matriz[fila][col] = arc;
		arcos.addLast(arc);
		try {
			arc.setPosicionEnArcos(arcos.last());
		} catch(EmptyListException e1) {
			System.out.println(e1.getMessage());
		}
		return arc;
	}

	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		Vertice<V> vert = checkVertex(v);
		TDAlista.Position<Vertex<V>> pos = vert.getPosicionEnVertices();
		int i = vert.getIndice();
		for(int j=0; j<cantidadVertices; j++)
			matriz[i][j] = null;
		try {
			for(TDAlista.Position<Edge<E>> ed: arcos.positions() ) {
				Arco<V,E> aux = (Arco<V,E>) ed.element();
				if(aux.getVertOri()==vert | aux.getVertFin()==vert)
					arcos.remove(ed);
			}
			return vertices.remove(pos).element();
		} catch(InvalidPositionException e) {
			System.out.println(e.getMessage());
			return null;
		}	
	}

	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		Arco<V,E> arc = checkEdge(e);
		E temp = e.element();
		try {
			int fila = arc.getVertOri().getIndice();
			int col = arc.getVertFin().getIndice();
			matriz[fila][col] = null;
			arcos.remove(arc.getPosicionEnArcos());
			return temp;
		} catch(InvalidPositionException e1) {
			System.out.println(e1.getMessage());
		}
		return null;
	}
	
	private Vertice<V> checkVertex(Vertex<V> v) throws InvalidVertexException{
		if(v==null)throw new InvalidVertexException("El vertice es invalido");
		return (Vertice<V>)v;
	}
	
	private Arco<V,E> checkEdge(Edge<E> e) throws InvalidEdgeException {
		if(e==null)throw new InvalidEdgeException("El arco es invalido");
		return (Arco<V,E>)e;
	}
	
	private void expandMatriz() {
		Edge<E> [][] newMatriz = (Edge<E> [][]) new Arco[matriz.length*2][matriz.length*2];
		for(int i=0; i<matriz.length; i++) {
			for(int j=0; j<matriz.length; j++) {
				newMatriz[i][j] = matriz[i][j];
			}
		}
		matriz = newMatriz;
	}
	
	public String dfsShell() {
		PositionList<Vertex<V>> list = new ListaDoblementeEnlazada<Vertex<V>>();
		try {
			for(Vertex<V> v: vertices)
				v.put(state,noVisit);
			for(Vertex<V> v: vertices)
				if(v.get(state)!=visit)
					dfs(v,list);
		} catch(Exceptions.InvalidKeyException e) {
			System.out.println(e.getMessage());
		}
		return list.toString();
	}
	
	private void dfs(Vertex<V> v,PositionList<Vertex<V>> l) {
		l.addLast(v);
		try {
			v.put(state,visit);
			for(Edge<E> ed:succesorEdges(v)) {
				Vertex<V> ad = opposite(v,ed);
				if(ad.get(state)!=visit)
					dfs(ad,l);
			}	
		} catch(Exceptions.InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//WARSHALL
	public String warshall(){
		Integer[][] w = matrizBooleana();
		Edge<E>[][] m = (Edge<E> [][]) new Arco[matriz.length][matriz.length];
		copy(m);
		for(int k=0; k<w.length; k++) {
			for(int i=0; i<w.length; i++) {
				for(int j=0; j<w.length; j++) {
					w[i][j] = Math.max(w[i][j],Math.min(w[i][k],w[k][j]));
				}
			}
		}
		System.out.println(toStringM(w));
		matrizAdyacencia(m,w);
		return relacion(m);
	}
	
	private void matrizAdyacencia(Edge<E>[][] w,Integer[][] m) {
		Vertice<V> v1 = null;
		Vertice<V> v2 = null;
		Vertice<V> aux = null;
		for(int i=0; i<m.length; i++) {
			for(int j=0; j<m.length; j++) {
				if((m[i][j]==1 && w[i][j]==null) || (i==j)) {
					for(Vertex<V> v: vertices() ) {
						aux = (Vertice<V>) v;
						if(aux.getIndice()==i)
							v1 = aux;
						if(aux.getIndice()==j)
							v2 = aux;
					}
					w[i][j] = new Arco<V,E>(null,v1,v2);
				}
			}
		}
	}
	
	public String relacion(Edge<E>[][] m) {
		PositionList<String> list =new ListaDoblementeEnlazada<String>();
		String aux = "";
		for(int i=0; i<m.length; i++) {
			for(int j=0; j<m.length; j++) {
				if(m[i][j]!=null) {
					aux = "("+ ((Arco<V,E>) m[i][j]).getVertOri() + "," + ((Arco<V,E>) m[i][j]).getVertFin() + ")";
					list.addLast(aux);
				}	
			}
		}
		return list.toString();
	}
	
	public Integer[][] matrizBooleana(){
		Integer[][] mBool = new Integer[matriz.length][matriz.length];
		for(int i=0; i<matriz.length; i++) {
			for(int j=0; j<matriz.length; j++) {
				if(matriz[i][j]!=null)
					mBool[i][j] = 1;
				else mBool[i][j] = 0;
			}
		}
		return mBool;
	}
	
	public void copy(Edge<E>[][] m) {
		for(int i=0; i<m.length; i++) {
			for(int j=0; j<m.length; j++) {
				m[i][j] = matriz[i][j];
			}
		}
	}
	
	//FLOYD
	public Integer[][] matrizPesada(){
		Integer[][] mPesada = new Integer[matriz.length][matriz.length];
		for(int i=0; i<matriz.length; i++) {
			for(int j=0; j<matriz.length; j++) {
				if(matriz[i][j]!=null)
					mPesada[i][j] = (Integer) matriz[i][j].element();
				else mPesada[i][j] = 0;
			}
		}
		return mPesada;
	}
	
	public String toStringM(Integer[][] w) {
		String texto ="";
		for(int i=0; i<w.length; i++) {
			for(int j=0; j<w.length; j++) {
				texto = texto +" "+ " (" + w[i][j] + ")";
			}
			texto = texto + "\n";
		}
		return texto;
	}
	
	public Integer[][] floyd(){
		Integer[][] pes = matrizPesada();
		for(int i=0; i<matriz.length; i++) {
			for(int j=0; j<matriz.length; j++) {
				if(pes[i][j]==0)
					pes[i][j] = null;
			}
		}
		for(int i=0; i<pes.length; i++)
			pes[i][i] = 0;
		for(int k=0; k<pes.length; k++) {
			for(int i=0; i<pes.length; i++) {
				for(int j=0; j<pes.length; j++) {
					if(pes[i][k]!=null && pes[k][j]!=null )
						if(pes[i][j]==null)
							pes[i][j] = pes[i][k] + pes[k][j];
						else if( (pes[i][k] + pes[k][j]) < pes[i][j] )
								pes[i][j] = pes[i][k] + pes[k][j];
				}
			}
		}
		return pes;
	}
	
	//DIJKSTRA
	private Entry<Map<Vertex<V>,Double>,Map<Vertex<V>,Vertex<V>>> dijkstra(GraphD<V,E> g,Vertex<V> v) {
		Map<Vertex<V>,Double> fv = new MapeoOpenHash<Vertex<V>,Double>();
		Map<Vertex<V>,Vertex<V>> vv = new MapeoOpenHash<Vertex<V>,Vertex<V>>();
		Entry<Map<Vertex<V>,Double>,Map<Vertex<V>,Vertex<V>>> ent = new Entrada<Map<Vertex<V>,Double>,Map<Vertex<V>,Vertex<V>>>(fv,vv);
		Float i = 0F;
		try {
			for(Vertex<V> ver: vertices()) {
				fv.put(ver,Double.POSITIVE_INFINITY);
				vv.put(ver,null);
			}
			fv.put(v,0D);
			Vertex<V> u = null;
			PositionList<Vertex<V>> s = new ListaDoblementeEnlazada<Vertex<V>>();
			for(Vertex<V> vet: vertices()) {
				s.addLast(u);
				for(Edge<E> ed: succesorEdges(u) ) {
					
					Vertex<V> ad = opposite(u,ed);
					if( (fv.get(u) + ((Double)ed.element())) < fv.get(ad) ) {
						fv.put(ad,fv.get(u)+ ((Double)ed.element()));
						vv.put(v,u);
					}
				}
			}
		} catch(Exceptions.InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			System.out.println(e.getMessage());
		}
		return ent;
	}	
	
	public String camino(Vertex<V> ori,Vertex<V> dest) {
		Entry<Map<Vertex<V>,Double>,Map<Vertex<V>,Vertex<V>>> ent = dijkstra(this,ori);
		PositionList<Vertex<V>> list = new ListaDoblementeEnlazada<Vertex<V>>();
		list.addLast(ori);
		recuperarAux(ent.getValue(),dest,list);
		return list.toString();
	}
	
	private void recuperarAux(Map<Vertex<V>,Vertex<V>> m,Vertex<V> dest,PositionList<Vertex<V>> list) {
		try {
			Vertex<V> ant = m.get(dest);
			if(ant!=null)
				recuperarAux(m,ant,list);
			list.addLast(ant);
		} catch(Exceptions.InvalidKeyException e) {
			System.out.println(e.getMessage());
		}
	}
}
