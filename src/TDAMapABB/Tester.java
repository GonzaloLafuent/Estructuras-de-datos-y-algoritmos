package TDAMapABB;

public class Tester {
	public static void main(String args[]) {
		ABB<Integer> abb = new ABB<Integer>(new DefaultComparator<Integer>());
		abb.insert(2, abb.getRaiz());
		abb.insert(1, abb.getRaiz());
		abb.insert(5, abb.getRaiz());
		abb.insert(6, abb.getRaiz());
		abb.insert(10, abb.getRaiz());
		abb.insert(20, abb.getRaiz());
		abb.insert(7, abb.getRaiz());
		abb.insert(4, abb.getRaiz());
		
		ABB<Integer> abb2 = new ABB<Integer>(new DefaultComparator<Integer>());
		abb2.insert(5, abb2.getRaiz());
		abb2.insert(6, abb2.getRaiz());
		abb2.insert(10, abb2.getRaiz());
		abb2.insert(4, abb2.getRaiz());
		abb2.insert(3, abb2.getRaiz());
		
		System.out.println( abb.toString());
		System.out.println(abb.predecesor(1));
	}
	
	public static void met(int x) {
		x = 2;
	}
}
