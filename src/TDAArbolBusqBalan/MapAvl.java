package TDAArbolBusqBalan;

import java.util.Comparator;

import Exceptions.InvalidKeyException;
import TDAlista.ListaDoblementeEnlazada;
import TDAlista.PositionList;

public class MapAvl<K,V> implements Map<K,V> {
    
protected AVL <Entry <K, V>> data;
protected Comparator <Entry<K, V>> comparator;
protected int size;

public MapAvl () {
    comparator = new KeyComparator <Entry <K,V>>();
    data = new AVL <Entry <K,V>>(comparator);
    size = 0;
}

public int size() {
    return size;
}

public boolean isEmpty() {
    return (size == 0);
}

public V get(K key) throws InvalidKeyException {
    checkKey(key);
    NodoAVL <Entry <K,V>> node = data.buscar(new Entrada <K,V> (key, null));
    V toReturn;
    
    if (node.getRotulo() == null) {
        toReturn = null;
    } else toReturn = node.getRotulo().getValue();
    
    return toReturn;
}

public V put(K key, V value) throws InvalidKeyException {
    checkKey(key);
    NodoAVL <Entry <K, V>> searchedNode = data.buscar(new Entrada <K,V> (key, null));
    Entrada <K,V> searched = (Entrada <K, V>) searchedNode.getRotulo();
    V toReturn = null;
    
    if (searched != null) {
        toReturn = searched.getValue();
        searched.setValue (value);
    } else {
        data.expand (searchedNode);
        searchedNode.setRotulo (new Entrada <K, V> (key, value));
        searchedNode.getRotulo().getValue();
    }
    
    size ++;
    
    return toReturn;
}

public V remove (K key) throws InvalidKeyException {
    checkKey(key);
    NodoAVL <Entry <K,V>> toRemove = data.buscar (new Entrada <K,V> (key, null));
    V toReturn = null;
    
    if (toRemove != null && toRemove.getRotulo() != null && !toRemove.getEliminado()) {
        toReturn = toRemove.getRotulo().getValue();
        toRemove.setEliminado(true);;
        size --;
    }
    
    return toReturn;
}

public Iterable<K> keys() {
    PositionList <K> keys = new ListaDoblementeEnlazada <K>();
    if (!isEmpty()) keysInOrder (data.getRoot(), keys);
    
    return keys;
}

private void keysInOrder (NodoAVL <Entry<K, V>> node, PositionList <K> l) {
    if (node.getRotulo() != null) {
        keysInOrder (node.getIzq(), l);
        l.addLast (node.getRotulo().getKey());
        keysInOrder (node.getDer(), l);
    }
}

public Iterable <V> values() {
    PositionList <V> values = new ListaDoblementeEnlazada <V>();
    if (!isEmpty()) valuesInOrder (data.getRoot(), values);
    
    return values;
}

private void valuesInOrder (NodoAVL <Entry<K, V>> node, PositionList<V> l) {
    if (node.getRotulo() != null) {
        valuesInOrder (node.getIzq(), l);
        l.addLast (node.getRotulo().getValue());
        valuesInOrder (node.getDer(), l);
    }
}

public Iterable <Entry <K,V>> entries () {
    PositionList <Entry <K,V>> itEntries = new ListaDoblementeEnlazada <Entry <K,V>>();
    if (!isEmpty()) entriesInOrder (data.getRoot(), itEntries);
    
    return itEntries;
}

private void entriesInOrder (NodoAVL <Entry<K, V>> node, PositionList <Entry <K,V>> l) {
    if (node.getRotulo() != null) {
        entriesInOrder (node.getIzq(), l);
        l.addLast (node.getRotulo());
        entriesInOrder (node.getDer(), l);
    }
}

private void checkKey (K key) throws InvalidKeyException {
    if (key == null) throw new InvalidKeyException ("checkKey: La clave no es válida.");
}

public String toString () {
    return data.toString();
}

}
