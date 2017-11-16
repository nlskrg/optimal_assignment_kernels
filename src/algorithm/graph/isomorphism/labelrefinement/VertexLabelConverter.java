package algorithm.graph.isomorphism.labelrefinement;

import java.util.HashMap;

import graph.Graph;
import graph.Graph.Vertex;
import graph.LGraph;
import graph.properties.VertexArray;


/**
 * Converts the vertex labels of a graph to integers in {0, ..., n}.
 * An object of this class can be used to subsequently process multiple
 * graphs, internally building a 1:1 mapping of vertex labels occurring 
 * in the set of graphs and new integer labels. This map can be cleared
 * manually if required.
 * 
 * @see #clearLabelMap()
 * 
 * @author kriege
 * 
 * @param <IE> edge label type of the input graph, which is preserved by 
 * the refinement step
 */
public class VertexLabelConverter<IE> extends VertexLabelRefiner<IE, LGraph<?,IE>, Integer> {

	private int offset;
	private HashMap<Object, Integer> labelMap;
	
	public VertexLabelConverter() {
		this(0);
	}
	
	/**
	 * Creates a new vertex label converter with an initial empty mapping.
	 * @param offset the vertex label assign to the first unseen vertex.
	 */
	public VertexLabelConverter(int offset) {
		labelMap = new HashMap<Object, Integer>();
		this.offset = offset;
	}
	
	@Override
	public VertexArray<Integer> vertexRefine(LGraph<?, IE> lg) {
		Graph g = lg.getGraph();
		VertexArray<?> va = lg.getVertexLabel();
		
		VertexArray<Integer> vaRefined = new VertexArray<Integer>(g);
		for (Vertex v : g.vertices()) {
			Object label = va.get(v);
			Integer newLabel = labelMap.get(label);
			if (newLabel == null) {
				newLabel = getNextLabel();
				labelMap.put(label, newLabel);
			}
			vaRefined.set(v, newLabel);		
		}
		
		return vaRefined;
	}
	
	/**
	 * Clears the current vertex label mapping.
	 */
	public void clearLabelMap() {
		offset = getNextLabel();
		labelMap.clear();
	}
	
	/**
	 * Returns the integer label that will be assigned to the next unseen 
	 * vertex label.
	 */
	public int getNextLabel() {
		return labelMap.size()+offset;
	}

}
