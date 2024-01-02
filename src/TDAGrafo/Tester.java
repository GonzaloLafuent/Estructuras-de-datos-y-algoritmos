package TDAGrafo;

import Exceptions.InvalidVertexException;

public class Tester {
	public static void main(String args[]) {
		GraphD<Integer,Integer> a = new DirectedGraph<Integer,Integer>();
		DirectedGraph<Integer,Integer> b = (DirectedGraph<Integer,Integer> ) a;
		Vertex<Integer> aux1 = a.insertVertex(1);
		Vertex<Integer> aux2 = a.insertVertex(2);
		Vertex<Integer> aux3 = a.insertVertex(3);
		Vertex<Integer> aux4 = a.insertVertex(4);
		Vertex<Integer> aux5 = a.insertVertex(5);
		try {
			a.insertEdge(aux1, aux2, 4);
            a.insertEdge(aux2, aux3, 6);
            a.insertEdge(aux3, aux1, 10);
            a.insertEdge(aux3, aux4, 2);
            a.insertEdge(aux5, aux1, 1);
            a.insertEdge(aux5, aux3, 12);
			System.out.println( b.camino(aux1,aux4) );
		} catch(InvalidVertexException e) {
			System.out.println(e.toString());
		}
	}
}
