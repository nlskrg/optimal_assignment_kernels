package comparison.kernel.graph;

import java.util.Arrays;
import java.util.Map.Entry;

import comparison.kernel.ExplicitMappingKernel;
import comparison.kernel.basic.DotProductKernel;
import datastructure.SparseFeatureVector;
import graph.Graph;
import graph.Graph.Edge;
import graph.Graph.Vertex;
import graph.LGraph;
import graph.properties.EdgeArray;
import graph.properties.VertexArray;

/**
 * A graphlet kernel taking connected induced subgraphs with three 
 * vertices and discrete vertex and edge labels into account, similar 
 * to the extension of 
 * 
 *  N. Shervashidze, S. Vishwanathan, T. Petri, K. Mehlhorn, and K. Borgwardt
 *  "Efficient graphlet kernels for large graph comparison,"
 *  Journal of Machine Learning Research - Proceedings, vol. 5, pp. 488–495, 2009.
 *  
 *  used in:
 *  
 *  N. Shervashidze, P. Schweitzer, E. van Leeuwen, K. Mehlhorn, and K. Borgwardt,
 *  "Weisfeiler-lehman graph kernels,"
 *  JMLR, vol. 12, pp. 2539–2561, 2011.
 *   
 * @author kriege
 *
 * @param <V>
 * @param <E>
 */
public class GraphletKernel<V,E> implements ExplicitMappingKernel<LGraph<V, E>, String> {
	private DotProductKernel<String> dotProd;
	
	public GraphletKernel() {
		dotProd = new DotProductKernel<String>();
	}

	public SparseFeatureVector<String> getFeatureVector(LGraph<V, E> lg) {
		SparseFeatureVector<String> r = new SparseFeatureVector<String>();
		Graph g = lg.getGraph();
		// TODO use index to avoid duplicates, i.e., i(u) < i(v) < i(w)
		for (Vertex u : g.vertices()) {
			for (Edge e : u.edges()) {
				Vertex v = e.getOppositeVertex(u);
				for (Edge f : v.edges()) {
					if (e == f) continue; // all three vertices/edges must be different!
					Vertex w = f.getOppositeVertex(v);
					Edge d = g.getEdge(u, w);
					if (d != null) {
						// triangle
						String[] canon = {
								triangleString(lg, u, v, w, e, f, d),
								triangleString(lg, u, w, v, d, f, e),
								triangleString(lg, v, u, w, e, d, f),
								triangleString(lg, v, w, u, f, d, e),
								triangleString(lg, w, v, u, f, e, d),
								triangleString(lg, w, u, v, d, e, f)
						};
						Arrays.sort(canon);
						r.increase(canon[0],1); // each is found 6 times 
					} else {
						// path
						String[] canon = {
								pathString(lg, u, v, w, e, f),
								pathString(lg, w, v, u, f, e),
						};
						Arrays.sort(canon);
						r.increase(canon[0],3); // each is found 2 times 

					}
				}
			}
		}
		for (Entry<String, Double> e : r.nonZeroEntries()) {
			e.setValue(e.getValue()/6);
		}
		return r;
	}
	
	public static char DELIMITER = '|';
	private String triangleString(LGraph<V, E> lg, Vertex u, Vertex v, Vertex w, Edge e, Edge f, Edge d) {
		VertexArray<V> va = lg.getVertexLabel();
		EdgeArray<E> ea = lg.getEdgeLabel();
		StringBuffer sb = new StringBuffer();
		sb.append(va.get(u));
		sb.append(DELIMITER);
		sb.append(ea.get(e));
		sb.append(DELIMITER);
		sb.append(va.get(v));
		sb.append(DELIMITER);
		sb.append(ea.get(f));
		sb.append(DELIMITER);
		sb.append(va.get(w));
		sb.append(DELIMITER);
		sb.append(ea.get(d));
		return sb.toString();
	}
	private String pathString(LGraph<V, E> lg, Vertex u, Vertex v, Vertex w, Edge e, Edge f) {
		VertexArray<V> va = lg.getVertexLabel();
		EdgeArray<E> ea = lg.getEdgeLabel();
		StringBuffer sb = new StringBuffer();
		sb.append(va.get(u));
		sb.append(DELIMITER);
		sb.append(ea.get(e));
		sb.append(DELIMITER);
		sb.append(va.get(v));
		sb.append(DELIMITER);
		sb.append(ea.get(f));
		sb.append(DELIMITER);
		sb.append(va.get(w));
		return sb.toString();
	}
	
	@Override
	public double compute(LGraph<V, E> g1, LGraph<V, E> g2) {
		return dotProd.compute(getFeatureVector(g1), getFeatureVector(g2));
	}

	@Override
	public String getID() {
		return "GL3";
	}
	
	
}