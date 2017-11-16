package graph;

import java.util.Iterator;

import graph.Digraph.DiEdge;
import graph.Digraph.DiVertex;
import graph.Graph.Edge;
import graph.Graph.Vertex;


public class ImplementationHelper {

	public static <V extends Vertex> Iterable<V> createNeighborIterator(final V v) {
		return new Iterable<V>() {
			public Iterator<V> iterator() {
				return new Iterator<V>() {
					Iterator<? extends Edge> it = v.edges().iterator();
					public boolean hasNext() {
						return it.hasNext();
					}
					@SuppressWarnings("unchecked")
					public V next() {
						Edge e = it.next();
						// casting is safe here since a graph features
						// only vertices of the same type
						// TODO enhance generic usages!?
						return (V)e.getOppositeVertex(v);
					}
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};		
	}
	
	public static <V extends DiVertex> Iterable<V> createInNeighborIterator(final V v) {
		return new Iterable<V>() {
			public Iterator<V> iterator() {
				return new Iterator<V>() {
					Iterator<? extends DiEdge> it = v.inEdges().iterator();
					public boolean hasNext() {
						return it.hasNext();
					}
					@SuppressWarnings("unchecked")
					public V next() {
						DiEdge e = it.next();
						return (V)e.getSourceVertex();
					}
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};		
	}
	
	public static <V extends DiVertex> Iterable<V> createOutNeighborIterator(final V v) {
		return new Iterable<V>() {
			public Iterator<V> iterator() {
				return new Iterator<V>() {
					Iterator<? extends DiEdge> it = v.outEdges().iterator();
					public boolean hasNext() {
						return it.hasNext();
					}
					@SuppressWarnings("unchecked")
					public V next() {
						DiEdge e = it.next();
						return (V)e.getTargetVertex();
					}
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};		
	}

	
	public static Edge getEdge(Vertex u, Vertex v) {
		Vertex w = (u.getDegree() < v.getDegree()) ? u : v;
		v = (w == v) ? u : v;
		for (Edge e : w.edges()) 
			if (e.getOppositeVertex(w) == v)
				return e;
		return null;		
	}
	
	public static String toString(Graph g) {
		StringBuilder sb = new StringBuilder();
		sb.append("V={");
		for (Vertex v : g.vertices()) {
			sb.append(v.getIndex()+",");
		}
		if (g.getVertexCount() != 0) {
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("}\n");
		sb.append("E={");
		for (Edge e : g.edges()) {
			sb.append("(");
			sb.append(e.getFirstVertex().getIndex());
			sb.append(",");
			sb.append(e.getSecondVertex().getIndex());
			sb.append(") ");
		}
		if (g.getEdgeCount() != 0) {
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("}");
		
		return sb.toString();
	}
}
