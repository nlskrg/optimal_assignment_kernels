package benchmark.dataset;

import graph.Graph.Edge;
import graph.Graph.Vertex;
import graph.LGraph;
import graph.properties.EdgeArray;
import graph.properties.VertexArray;

/**
 * A dataset for graphs with simple labels, i.e., of type LGraph<String, String>.
 * 
 * @author kriege
 *
 */
public class SDataset extends LGDataset<LGraph<String, String>> {
	
	private static final long serialVersionUID = 1L;

	public SDataset(String id) {
		super(id);
	}
	
	public SDataset createRandomDSubset(int size, boolean balanced) {
		return createRandomSubset(size, balanced, new SDataset(id));
	}
	
	public SDataset createSmallGraphSubset(int maxVertexCount) {
		return createSmallGraphSubset(maxVertexCount, new SDataset(id));
	}
	
	public SDataset createFirstKSubset(int k) {
		return createFirstKSubset(k, new SDataset(id));
	}
	
	public void removeEdgeLabels(String uniforLabel) {
		for (LGraph<String, String> lg : this) {
			EdgeArray<String> ea = lg.getEdgeLabel();
			for (Edge e : lg.getGraph().edges()) {
				ea.set(e, uniforLabel);
			}
		}
	}
	
	public void removeVertexLabels() {
		for (LGraph<String, String> lg : this) {
			VertexArray<String> va = lg.getVertexLabel();
			for (Vertex v : lg.getGraph().vertices()) {
				va.set(v, "X");
			}
		}
	}
	
	public void labelVerticesByDegree() {
		for (LGraph<String, String> lg : this) {
			VertexArray<String> va = lg.getVertexLabel();
			for (Vertex v : lg.getGraph().vertices()) {
				va.set(v, String.valueOf(v.getDegree()));
			}
		}
	}

}
