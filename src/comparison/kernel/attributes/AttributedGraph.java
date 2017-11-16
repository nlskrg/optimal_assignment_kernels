package comparison.kernel.attributes;

import java.util.ArrayList;
import java.util.List;

import concepts.Transformation;
import concepts.TransformationTools;
import graph.Graph;
import graph.Graph.Edge;
import graph.Graph.Vertex;
import graph.LGraph;
import graph.properties.EdgeArray;
import graph.properties.VertexArray;

public class AttributedGraph extends LGraph<Attributes, Attributes> {
	
	public static Converter CONVERTER = new Converter();

	public AttributedGraph(Graph graph,	VertexArray<Attributes> vertexLabel, EdgeArray<Attributes> edgeLabel) {
		super(graph, vertexLabel, edgeLabel);
	}
	
	public static AttributedGraph convert(LGraph<String, String> lg) {
		return CONVERTER.transform(lg);
	}
	
	public static ArrayList<AttributedGraph> convert(List<LGraph<String, String>> lgs) {
		return TransformationTools.transformAll(CONVERTER, lgs);
	}
	
	/**
	 * @return true iff the graph has at least one vertex and the first vertex
	 * has at least one nominal attribute. 
	 */
	public boolean hasNominalNodeLabel() {
		if (graph.getVertexCount() == 0) return false;
		Vertex v = graph.vertices().iterator().next();
		return (getVertexLabel().get(v).getNominalAttributeCount() != 0);
	}
	
	/**
	 * @return true iff the graph has at least one edge and the first edge
	 * has at least one nominal attribute. 
	 */
	public boolean hasNominalEdgeLabel() {
		if (graph.getEdgeCount() == 0) return false;
		Edge e = graph.edges().iterator().next();
		return (getEdgeLabel().get(e).getNominalAttributeCount() != 0);
	}
	
	/**
	 * @return true iff the graph has at least one vertex and the first vertex
	 * has at least one real valued attribute. 
	 */
	public boolean hasRealValuedNodeLabel() {
		if (graph.getVertexCount() == 0) return false;
		Vertex v = graph.vertices().iterator().next();
		return (getVertexLabel().get(v).getRealValuedAttributeCount() != 0);
	}
	
	/**
	 * @return true iff the graph has at least one edge and the first edge
	 * has at least one real valued attribute. 
	 */
	public boolean hasRealValuedEdgeLabel() {
		if (graph.getEdgeCount() == 0) return false;
		Edge e = graph.edges().iterator().next();
		return (getEdgeLabel().get(e).getRealValuedAttributeCount() != 0);
	}

	/**
	 * Converts a string labeled graph to an attributed graph by parsing the vertex 
	 * and edge labels.
	 * 
	 * @author kriege
	 * @see Attributes#setAttributes(String)
	 */
	public static class Converter implements Transformation<LGraph<String, String>, AttributedGraph> {

		public AttributedGraph transform(LGraph<String, String> lg) {
			VertexArray<String> va = lg.getVertexLabel();
			EdgeArray<String> ea = lg.getEdgeLabel();
			Graph g = lg.getGraph();
			
			VertexArray<Attributes> va2 = new VertexArray<Attributes>(g);
			EdgeArray<Attributes> ea2 = new EdgeArray<Attributes>(g);
			
			for (Vertex v : g.vertices()) {
				va2.set(v, new Attributes(va.get(v)));
			}
			
			for (Edge e : g.edges()) {
				ea2.set(e, new Attributes(ea.get(e)));
			}
			
			return new AttributedGraph(g, va2, ea2);
		}
		
	}

}
