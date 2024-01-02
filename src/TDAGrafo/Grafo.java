package TDAGrafo;

import java.security.InvalidKeyException;

import Exceptions.EmptyListException;
import Exceptions.EmptyQueueException;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidPositionException;
import Exceptions.InvalidVertexException;
import TDACola.ColaConArregloCircular;
import TDACola.Queue;
import TDAMapeo.MapeoOpenHash;
import TDAlista.ListaDoblementeEnlazada;
import TDAlista.PositionList;

public class Grafo<V,E> implements Graph<V,E> {
	private class Vertice<V,E> extends MapeoOpenHash<Object, Object> implements Vertex<V>{
		private V rotulo;
		private PositionList<Arco<V, E>> adyacentes;
		private TDAlista.Position<Vertice<V, E>> posicionEnNodos;
		public Vertice(V rotulo) {
			this.rotulo = rotulo;
			adyacentes = new ListaDoblementeEnlazada<Arco<V, E>>();
		}
		@Override
		public V element() {
			return rotulo;
		}
		public void setRotulo(V nuevoRotulo) {
			rotulo = nuevoRotulo;
		}
		public TDAlista.Position<Vertice<V, E>> getPosicionEnNodos() {
			return posicionEnNodos;
		}
		public void setPosicionEnNodos(TDAlista.Position<Vertice<V, E>> posicionEnNodos) {
			this.posicionEnNodos = posicionEnNodos;
		}
		public PositionList<Arco<V, E>> getAdyacentes(){
			return adyacentes;
		}
		
		public String toString() {
			return "( "+rotulo+" )";
		}
	}
	
	public class Arco<V,E> extends MapeoOpenHash<Object,Object> implements Edge<E> {
		private E rotulo;
		private Vertice<V, E> v1, v2;
		private TDAlista.Position<Arco<V, E>> posicionEnArcos;
		private TDAlista.Position<Arco<V, E>> posicionEnIv1, posicionEnIv2;
		public Arco(E rotulo, Vertice<V, E> v1, Vertice<V, E> v2) {
			this.rotulo = rotulo;
			this.setV1(v1);
			this.setV2(v2);
		}
		
		@Override
		public E element() {
			return rotulo;
		}
		
		public TDAlista.Position<Arco<V, E>> getPosicionEnArcos() {
			return posicionEnArcos;
		}
		
		public void setPosicionEnArcos(TDAlista.Position<Arco<V, E>> posicionEnArcos) {
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
		
		public Vertice<V, E> getV1() {
			return v1;
		}
		
		public void setV1(Vertice<V, E> v1) {
			this.v1 = v1;
		}
		
		public Vertice<V, E> getV2() {
			return v2;
		}
		
		public void setV2(Vertice<V, E> v2) {
			this.v2 = v2;
		}
		
		public String toString() {
			return "("+rotulo+")";
		}
	}

	
	private PositionList<Vertice<V, E>> nodos;
	private PositionList<Arco<V, E>> arcos;
	private final Object visit = new Object();
	private final Object noVisit = new Object();
	private final Object state = new Object();
	
	//Constructor
		public Grafo() {
			nodos = new ListaDoblementeEnlazada<Vertice<V, E>>();
			arcos = new ListaDoblementeEnlazada<Arco<V, E>>();
		}
		@Override
		//O(n)
		public Iterable<Vertex<V>> vertices() {
			PositionList<Vertex<V>> lista = new ListaDoblementeEnlazada<Vertex<V>>();
			for (Vertex<V> v : nodos) {
				lista.addLast((Vertex<V>) v);
			}
			return lista;
		}

		@Override
		//O(m)
		public Iterable<Edge<E>> edges() {
			PositionList<Edge<E>> lista = new ListaDoblementeEnlazada<Edge<E>>();
			for (Edge<E> e : arcos) {
				lista.addLast(e);
			}
			return lista;
		}
		
		//O(1)
		private Vertice<V, E> checkVertex(Vertex<V> v) throws InvalidVertexException{
			if (v == null) throw new InvalidVertexException("El vertice es invalido");
			return (Vertice<V, E>) v;
		}
		
		//O(1)
		private Arco<V, E> checkEdge(Edge<E> e) throws InvalidEdgeException{
			if (e == null) throw new InvalidEdgeException("El arco es invalido");
			return (Arco<V, E>) e;
		}
		
		@Override
		//O(m)  m = cantiadad de arcos
		public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
			Vertice<V,E> vert = checkVertex(v);
			PositionList<Edge<E>> inc = new ListaDoblementeEnlazada<Edge<E>>();
			for(Edge<E> e: vert.getAdyacentes() )
				inc.addLast(e);
			return inc;
		} 

		// O(1) 
		public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
			checkVertex(v);
			Arco<V,E> arc = checkEdge(e);
			if( arc.getV1()== v)
				return arc.getV2();
			else if(arc.getV2()==v)
				return arc.getV1();
			else throw new InvalidEdgeException("EL arco es invalido");
		}

		// O(1)
		public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
			Arco<V,E> arc = checkEdge(e);
			Vertex<V> [] toReturn = (Vertex<V>[]) new Vertice[2];
			toReturn[0] =  arc.getV1();
			toReturn[1] = arc.getV2();
			return toReturn;
		}

		// O(m) m = cantidad de arcos  
		public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
			Vertice<V,E> vv = checkVertex(v);
			Vertice<V,E> ww = checkVertex(w);
			for(Arco<V, E> e: vv.getAdyacentes() ) {
				if(e.getV1()==ww || e.getV2()==ww)
					return true;
			}	
			return false;
		}

		// O(1)
		public V replace(Vertex<V> v, V x) throws InvalidVertexException {
			Vertice<V,E> vv = checkVertex(v);
			V toReturn = vv.element();
			vv.setRotulo(x);
			return toReturn;
		}

		// O(1)
		public Vertex<V> insertVertex(V x) {
			Vertice<V,E> v = new Vertice<V,E>(x);
			nodos.addLast(v);
			try {
				v.setPosicionEnNodos(nodos.last());
			} catch (EmptyListException e) {
				System.out.println(e.getMessage());
			}
			return v;
		} 

		// O(1)
		public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
			Vertice<V,E> vv = checkVertex(v);
			Vertice<V,E> ww = checkVertex(w);
			Arco<V,E> arco = new Arco<V,E>( e, vv, ww); 
			vv.getAdyacentes().addLast( arco );
			ww.getAdyacentes().addLast( arco );
			try {
				arco.setPosicionEnIv1( vv.getAdyacentes().last() );
				arco.setPosicionEnIv2( ww.getAdyacentes().last() );
				arcos.addLast(arco);
				arco.setPosicionEnArcos( arcos.last() );
			} catch (EmptyListException e1) {
				e1.printStackTrace();
			}
			return arco; // Retorno el arco creado
		}

		// O(m) m = cantiadad de arcos
		public V removeVertex(Vertex<V> v) throws InvalidVertexException {
			try {
				Vertice<V,E> aux = checkVertex(v);
				TDAlista.Position<Vertice<V,E>> pos = aux.getPosicionEnNodos();
				for (Arco<V, E> arco : aux.getAdyacentes()) {
					arcos.remove(arco.getPosicionEnArcos());
				}
				return nodos.remove(pos).element();
			} catch(InvalidPositionException e) {
				e.printStackTrace();
				return null;
			}
		}

		// O(1)
		public E removeEdge(Edge<E> e) throws InvalidEdgeException {
			Arco<V,E> ee= checkEdge(e);
			Vertice<V,E> v1 = ee.getV1();
			Vertice<V,E> v2 = ee.getV2();
			try {
				v1.getAdyacentes().remove(ee.getPosicionEnIv1());
				v2.getAdyacentes().remove(ee.getPosicionEnIv2());
				TDAlista.Position<Arco<V,E>> pee = ee.getPosicionEnArcos();
				return arcos.remove(pee).element();
			} catch(InvalidPositionException e1) {
				System.out.println(e1.getMessage());
			}
			return null;
		}	
		
		//DFS search
		// La diferencia al hacerlo dentro de la estrctura es que
		// no tendremos que usar el metodo incident, de esta forma nos ahorramos
		// una operaciond de orden n, siedno n la cantidad de arcos adyacentes
		public PositionList<V> dfsShell() {
			PositionList<V> list = new ListaDoblementeEnlazada<V>();
			try {
				for(Vertex<V> vv: nodos)
					vv.put(state,noVisit);
				for(Vertex<V> vv:nodos)
					if(vv.get(state)==noVisit)
						dfs(list,vv);
			} catch(Exceptions.InvalidKeyException e) {
				System.out.println(e.getMessage());
			}
			return list;
		}
		
		private void dfs(PositionList<V> l,Vertex<V> v) {
			l.addLast(v.element());
			try {
				v.put(state, visit);
				Vertice<V,E> vv = (Vertice<V,E>)v;
				Iterable<Arco<V,E>> adyacentes = vv.getAdyacentes(); 
				for(Edge<E> e:adyacentes) {
					Vertex<V> w = opposite(v,e);
					if(w.get(state)==noVisit)
						dfs(l,w);
				}
			}catch(Exceptions.InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
				System.out.println(e.getMessage());
			}
		}
		
		//BFS
		public PositionList<V> bfsShell(){
			PositionList<V> list = new ListaDoblementeEnlazada<V>();
			try {
				for(Vertex<V> v:nodos)
					v.put(state, noVisit);
				for(Vertex<V> v:nodos)
					if(v.get(state)==noVisit)
						bfs(list,v);
			} catch(Exceptions.InvalidKeyException e) {
				System.out.println(e);
			}
			return list;
		}
		
		private void bfs(PositionList<V> l,Vertex<V> v) {
			Queue<Vertex<V>> col = new ColaConArregloCircular<Vertex<V>>();
			col.enqueue(v);
			Vertice<V,E> vv = (Vertice<V,E>)v;
			try {
				vv.put(state, visit);
				while(!col.isEmpty()) {
					Vertice<V,E> aux = (Vertice<V, E>) col.dequeue();
					l.addLast(aux.element());
					for(Arco<V,E> w: aux.getAdyacentes()) {
						Vertex<V> temp = opposite(aux,w);
						if(temp.get(state)==noVisit) {
							temp.put(state,visit);
							col.enqueue(temp);
						}
 					}
				}
			} catch(Exceptions.InvalidKeyException | EmptyQueueException | InvalidVertexException | InvalidEdgeException e) {
				System.out.println(e.getMessage());
			}
		}
		
}
