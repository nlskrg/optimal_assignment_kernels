package graph.properties;

import graph.Graph;

// TODO revise all graph properties
public interface GraphProperty<K extends Graph.Element,V> {

	public void set(K k, V v);
	public V get(K e);

}
