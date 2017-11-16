package comparison.kernel.graph;

import comparison.kernel.ExplicitMappingKernel;
import comparison.kernel.Kernel;
import comparison.kernel.basic.DiracKernel;
import datastructure.FeatureVector;
import datastructure.SparseFeatureVector;
import graph.Graph;
import graph.Graph.Vertex;
import graph.LGraph;
import graph.properties.VertexArray;

/**
 * Compares two graphs by comparing their vertex labels.
 * 
 * @author kriege
 *
 * @param <V> vertex label type
 * @param <E> edge label type; note that edge labels are completely ignored by this kernel
 */
public class VertexKernel<V, E> implements ExplicitMappingKernel<LGraph<V, E>, V> {
	
	Kernel<? super V> vertexKernel;
	
	public VertexKernel(Kernel<? super V> vertexKernel) {
		this.vertexKernel = vertexKernel;
	}
	
	public double compute(LGraph<V, E> lg1, LGraph<V, E> lg2) {
		Graph g1 = lg1.getGraph();
		VertexArray<V> va1 = lg1.getVertexLabel();
		Graph g2 = lg2.getGraph();
		VertexArray<V> va2 = lg2.getVertexLabel(); 

		double k = 0;
		
		for (Vertex v1 : g1.vertices()) {
			V l1 = va1.get(v1);
			for (Vertex v2 : g2.vertices()) {
				V l2 = va2.get(v2);
				k += vertexKernel.compute(l1, l2);
			}
		}

		return k;
	}

	@Override
	public FeatureVector<V> getFeatureVector(LGraph<V, E> lg) {
		if (!(vertexKernel instanceof DiracKernel)) {
			throw new IllegalStateException("Explicit mapping requires the vertex kernel to be a dirac kernel!");
		}
		
		Graph g = lg.getGraph();
		VertexArray<V> va = lg.getVertexLabel();
		
		SparseFeatureVector<V> r = new SparseFeatureVector<V>();
		
		for (Vertex v : g.vertices()) {
			r.increaseByOne(va.get(v));
		}
		
		return r;
	}
	
	public String getID() {
		if (vertexKernel instanceof DiracKernel) {
			return "VL";
		} else {
			return "VL_"+vertexKernel.getID();
		}
	}

}
