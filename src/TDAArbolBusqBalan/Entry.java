package TDAArbolBusqBalan;

public interface Entry<K,V> extends Comparable<Entry<K,V> >{
	
	/**
	 * Devuelve la clave almacenada en la entrada
	 * @return clave almacena en la entrada
	 */
	public K getKey();
	
	/**
	 * Devuleve el valor almacenado en la entrada
	 * @return valor almacenada en la entrada
	 */
	public V getValue();
}
