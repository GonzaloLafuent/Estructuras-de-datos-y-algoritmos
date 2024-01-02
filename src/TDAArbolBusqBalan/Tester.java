package TDAArbolBusqBalan;

public class Tester {
	public static void main(String args[]){
		AVL <Float> rot2 = new AVL <Float> (new DefaultComparator <Float> ());
        rot2.insert(5f);
        rot2.insert(2f);
        rot2.insert(15f);
        rot2.insert(1f);
        rot2.insert(4f);
        rot2.insert(8f);
        rot2.insert(19f);
        rot2.insert(10f);
        rot2.insert(9f);
        rot2.insert(3f);
        System.out.println(rot2.toString());
	}	
}
