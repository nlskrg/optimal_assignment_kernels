package comparison.kernel.graph;

import java.util.Map.Entry;

import comparison.kernel.ExplicitMappingKernel;
import comparison.kernel.Kernel;
import comparison.kernel.basic.DiracKernel;
import datastructure.FeatureVector;
import datastructure.SparseFeatureVector;
import datastructure.Triple;
import graph.Graph;
import graph.Graph.Edge;
import graph.Graph.Vertex;
import graph.LGraph;
import graph.properties.EdgeArray;
import graph.properties.VertexArray;

/**
 * Compares the edges of two graphs taking the two vertex labels as
 * well as the edge label into account. Note: For two edges (u1, v1)
 * and (u2, v2) both mapping are considered, i.e. u1 to u2, v1 to v2
 * and u1 to v2, v1 to u2.
 * 
 * k(G1, G2) = \sum_(u1,v1) \sum_(u2,v2) kv(u1,u2)*kv(v1,v2)*ke((u1,v1),(u2,v2))
 * 
 * where ke(e1, e2) = 0 if e1 not in G1 or e2 not in G2.
 * 
 * Note: This implementation internally computes a random walk kernel 
 * with fixed length 1. This is equivalent to the EdgeKernel defined 
 * above and has the advantage that the product graph caches the results
 * of vertex and edge kernel evaluations.
 * 
 * In cases where vertex and edge kernels are Dirac kernels, feature
 * vector are constructed as follows:
 * Each component counts the occurrences of extended edge labels, i.e. 
 * a triple (l(u), l((u,v)), l(v)) for an edge (u, v), where l assigns 
 * a label to the two endpoints and the edge itself, respectively. Note 
 * that for each edge two such triples are generated where the position 
 * of u and v is swapped.
 * 
 * 
 * @author kriege
 *
 * @param <V> vertex label type
 * @param <E> edge label type
 */
public class EdgeKernel<V, E> implements ExplicitMappingKernel<LGraph<V, E>, Triple<?, ?, ?>>
{

	Kernel<? super E> edgeKernel;
	Kernel<? super V> vertexKernel;
	
	public EdgeKernel(Kernel<? super E> edgeKernel, Kernel<? super V> vertexKernel) {
		this.edgeKernel = edgeKernel;
		this.vertexKernel = vertexKernel;
	}
	
	public double compute(LGraph<V, E> lg1, LGraph<V, E> lg2) {
		Graph g1 = lg1.getGraph();
		Graph g2 = lg2.getGraph();
		VertexArray<V> va1 = lg1.getVertexLabel();
		VertexArray<V> va2 = lg2.getVertexLabel();
		EdgeArray<E> ea1 = lg1.getEdgeLabel();
		EdgeArray<E> ea2 = lg2.getEdgeLabel();
		int n1 = g1.getVertexCount();
		int n2 = g2.getVertexCount();
		
		// compute compatible vertices
		double[][] m = new double[n1][n2];
		for (Vertex v1 : g1.vertices()) {
			V l1 = va1.get(v1);
			for (Vertex v2 : g2.vertices()) {
				V l2 = va2.get(v2);
				double value = vertexKernel.compute(l1, l2);
				m[v1.getIndex()][v2.getIndex()] = value;
			}
		}

		double totalValue = 0;
		for (Edge e1 : g1.edges()) {
			for (Edge e2 : g2.edges()) {
				Vertex u1 = e1.getFirstVertex();
				Vertex v1 = e1.getSecondVertex();
				Vertex u2 = e2.getFirstVertex();
				Vertex v2 = e2.getSecondVertex();
				double val1 = m[v1.getIndex()][v2.getIndex()]*m[u1.getIndex()][u2.getIndex()];
				double val2 = m[v1.getIndex()][u2.getIndex()]*m[u1.getIndex()][v2.getIndex()];
				if (val1 != 0 || val2 != 0) {
					double edgeValue = edgeKernel.compute(ea1.get(e1), ea2.get(e2));
					totalValue += val1*edgeValue;
					totalValue += val2*edgeValue;
				}
			}
		}
		
		return 2*totalValue;
	}

// Note: This was found to be slower in general than the method using the
// a product graph in combination with a fixed length random walk.
// However, for sparse unlabeled graphs and dense vertex kernels that
// can be computed efficiently the other implementation might be 
// preferable.
//
//	public double evaluate(LabeledGraph<V, E> lg1, LabeledGraph<V, E> lg2) {
//		computations++;
//		
//		double totalValue = 0;
//		
//		long startTime = System.nanoTime();		
//		Graph g1 = lg1.getGraph();
//		Graph g2 = lg2.getGraph();
//		VertexArray<V> va1 = lg1.getVertexLabel();
//		VertexArray<V> va2 = lg2.getVertexLabel();
//		EdgeArray<E> ea1 = lg1.getEdgeLabel();
//		EdgeArray<E> ea2 = lg2.getEdgeLabel();
//		
//		for (Edge e1 : g1.edges()) {
//			for (Edge e2 : g2.edges()) {
//				Vertex u1 = e1.getFirstVertex();
//				Vertex v1 = e1.getSecondVertex();
//				Vertex u2 = e2.getFirstVertex();
//				Vertex v2 = e2.getSecondVertex();
//				double edgeValue = edgeKernel.evaluate(ea1.get(e1), ea2.get(e2));
//				// first mapping
//				double val = edgeValue;
//				val *= vertexKernel.evaluate(va1.get(u1), va2.get(u2));
//				val *= vertexKernel.evaluate(va1.get(v1), va2.get(v2));
//				totalValue += val;
//				// second mapping
//				val = edgeValue;
//				val *= vertexKernel.evaluate(va1.get(u1), va2.get(v2));
//				val *= vertexKernel.evaluate(va1.get(v1), va2.get(u2));
//				totalValue += val;
//			}
//		}
//		kernelComputationTime += System.nanoTime() - startTime;
//		
//		return totalValue;
//	}

	public String getID() {
		if (vertexKernel instanceof DiracKernel && edgeKernel instanceof DiracKernel) {
			return "EL";
		} else {
			return "EL_"+vertexKernel.getID()+"_"+edgeKernel.getID();
		}
	}

	@Override
	public FeatureVector<Triple<?, ?, ?>> getFeatureVector(LGraph<V, E> in)	throws IllegalStateException {
		if (!(vertexKernel instanceof ExplicitMappingKernel) || !(edgeKernel instanceof ExplicitMappingKernel)) {
			throw new IllegalStateException("Explicit mapping requires both, vertex and edge kernel, to support explicit mapping!");
		}
		
		Graph g = in.getGraph();
		VertexArray<V> va = in.getVertexLabel();
		EdgeArray<E> ea = in.getEdgeLabel();
		
		SparseFeatureVector<Triple<?, ?, ?>> r = new SparseFeatureVector<Triple<?, ?, ?>>();
		for (Edge e : g.edges()) {
			V lu = va.get(e.getFirstVertex());
			V lv = va.get(e.getSecondVertex());
			E le = ea.get(e);
			r.add(getKroneckerProduct(lu, le, lv));
			r.add(getKroneckerProduct(lv, le, lu));
		}
		
		return r;
	}
	
	@SuppressWarnings("unchecked")
	private FeatureVector<Triple<?, ?, ?>> getKroneckerProduct(V lu, E le, V lv) {
		FeatureVector<Triple<?, ?, ?>> r = new SparseFeatureVector<Triple<?,?,?>>();
		ExplicitMappingKernel<V,?> vk = (ExplicitMappingKernel<V,?>) vertexKernel;
		ExplicitMappingKernel<E,?> ek = (ExplicitMappingKernel<E,?>) edgeKernel;
		for (Entry<?, Double> fu : vk.getFeatureVector(lu).nonZeroEntries()) {
			for (Entry<?, Double> fe : ek.getFeatureVector(le).nonZeroEntries()) {
				for (Entry<?, Double> fv : vk.getFeatureVector(lv).nonZeroEntries()) {
					r.increase(new Triple<>(fu.getKey(), fe.getKey(), fv.getKey()), 
							fu.getValue()*fe.getValue()*fv.getValue());
				}
			}
		}
		return r;
	}
	
}
