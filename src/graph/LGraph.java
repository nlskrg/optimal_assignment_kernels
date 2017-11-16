package graph;

import graph.Graph.Edge;
import graph.Graph.Vertex;
import graph.properties.EdgeArray;
import graph.properties.VertexArray;

/**
 * Stores a graph together with its vertex and edge labels.
 * 
 * @author kriege
 *
 * @param <V> vertex label type
 * @param <E> edge label type
 */
public class LGraph<V,E> extends AbstractLGraph<Graph, V, E>{

	public LGraph(Graph graph, VertexArray<V> vertexLabel,EdgeArray<E> edgeLabel) {
		super(graph, vertexLabel, edgeLabel);
	}
	
	public static LGraph<Integer,Integer> indexLabeledGraph(Graph g) {
		VertexArray<Integer> va = new VertexArray<>(g);
		for (Vertex v : g.vertices()) {
			va.set(v, v.getIndex());
		}
		EdgeArray<Integer> ea = new EdgeArray<>(g);
		for (Edge e : g.edges()) {
			ea.set(e, e.getIndex());
		}
		return new LGraph<>(g, va, ea);
	}

}
