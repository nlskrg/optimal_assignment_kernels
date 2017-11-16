package algorithm.graph.isomorphism.labelrefinement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import graph.Graph;
import graph.Graph.Edge;
import graph.Graph.Vertex;
import graph.properties.EdgeArray;
import graph.properties.VertexArray;

/**
 * Computes a Weisfeiler-Lehman refinement of a graph without label 
 * compression. 
 * 
 * Note: This class refines all types of vertex and edge labels using
 * their toString() function, i.e. vertex and edge labels are considered
 * equal iff their toString() representation is equal!
 * 
 * Note that this implementation by default does not use label 
 * compression, i.e. vertex labels grow with each iteration, which slows
 * down the refinement process. To avoid this you may interrupt the 
 * refinement steps by vertex label compressions steps:
 * 
 * 1. Compute single iteration Weisfeiler-Lehmann refinement for all graphs
 * 2. Compute label compression for all graphs
 * 3. Invoke {@link VertexLabelConverter#clearLabelMap()}
 * 4. go to Step 1.
 * 
 * Using this procedure keeps vertex labels at a reasonable size. Note that
 * using this procedure vertices may have the same vertex label in successive
 * iterations representing different refined vertex labels.
 * 
 * @see #refineAndCompressAll(Collection) 
 * @see VertexLabelConverter
 * 
 * @author kriege
 * 
 * @param <IE> edge label type of the input graph, which is preserved by 
 * the refinement step
 */
public class WeisfeilerLehmanRefiner<IE> extends IterativeVertexLabelRefiner<Object, IE, String> {
	
	public static final char DELIMITER = '|';
	public static final char PAIR_BEGIN = '[';
	public static final char PAIR_END = ']';
	
	@Override
	protected VertexArray<String> refinementStep(Graph g, VertexArray<? extends Object> va, EdgeArray<IE> ea) {
		
		VertexArray<String> vaRefined = new VertexArray<String>(g);
		for (Vertex v : g.vertices()) {
			
			// 1. multiset-label determination
			ArrayList<String> neighborLabels = new ArrayList<String>(v.getDegree());
			for (Edge e : v.edges()) {
				Vertex w = e.getOppositeVertex(v);
				neighborLabels.add(ea.get(e).toString()+DELIMITER+va.get(w).toString());
			}
			
			// 2. sorting 
			Collections.sort(neighborLabels); // TODO in theory radix sort should be better here
			// string concatenation
			StringBuilder sb = new StringBuilder();
			sb.append(va.get(v).toString());
			sb.append(DELIMITER);
			for (String s : neighborLabels) {
				sb.append(PAIR_BEGIN);
				sb.append(s);
				sb.append(PAIR_END);
			}
			String label = sb.toString();
			
			// 4. relabeling
			vaRefined.set(v, label);		
		}

		return vaRefined;
	}
	

}
