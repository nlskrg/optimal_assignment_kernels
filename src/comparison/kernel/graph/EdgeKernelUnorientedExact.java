package comparison.kernel.graph;

import comparison.kernel.ExplicitMappingKernel;
import datastructure.FeatureVector;
import datastructure.SparseFeatureVector;
import datastructure.Triple;
import graph.Graph;
import graph.Graph.Edge;
import graph.LGraph;
import graph.properties.EdgeArray;
import graph.properties.VertexArray;

/**
 * Compares the edges of two graphs taking the two vertex labels as
 * well as the edge label into account. Two edges are considered to 
 * match if at least one mapping of their endpoints exactly 
 * matches vertex and edge labels.
 * 
 * @author kriege
 *
 * @param <V> vertex label type
 * @param <E> edge label type
 */

public class EdgeKernelUnorientedExact<V extends Comparable<V>, E> implements ExplicitMappingKernel<LGraph<V, E>, Triple<V, E, V>>
{

	@Override
	public double compute(LGraph<V, E> g1, LGraph<V, E> g2) {
		return getFeatureVector(g1).dotProduct(getFeatureVector(g2));
	}	
	
	@Override
	public FeatureVector<Triple<V, E, V>> getFeatureVector(LGraph<V, E> in)	throws IllegalStateException {
		SparseFeatureVector<Triple<V, E, V>> r = new SparseFeatureVector<Triple<V,E,V>>();
		
		Graph g = in.getGraph();
		VertexArray<V> va = in.getVertexLabel();
		EdgeArray<E> ea = in.getEdgeLabel();

		for (Edge e : g.edges()) {
			V labelV1 = va.get(e.getFirstVertex());
			V labelV2 = va.get(e.getSecondVertex());
			E labelE = ea.get(e);
			
			if (labelV1.compareTo(labelV2) < 0) {
				r.increaseByOne(new Triple<V, E, V>(labelV1, labelE, labelV2));
			} else {
				r.increaseByOne(new Triple<V, E, V>(labelV2, labelE, labelV1));
			}
		}
		
		return r;
	}
	
	public String getID() {
		return "ELU";
	}

}
