package benchmark.dataset;

import comparison.kernel.attributes.AttributedGraph;
import comparison.kernel.attributes.Attributes;
import graph.Graph;
import graph.Graph.Edge;
import graph.Graph.Vertex;
import graph.LGraph;
import graph.properties.EdgeArray;
import graph.properties.VertexArray;

public class AttrDataset extends LGDataset<AttributedGraph> {

	private static final long serialVersionUID = 1L;



	public AttrDataset(String id) {
		super(id);
	}
	
	/**
	 * Normalizes all real valued attributes to [0..1].
	 */
	public void normalize() {
		int vAttrCount = get(0).getVertexLabel().get(get(0).getGraph().getVertex(0)).getRealValuedAttributeCount();
		int eAttrCount = get(0).getEdgeLabel().get(get(0).getGraph().getEdge(0)).getRealValuedAttributeCount();
		double[] vMin = new double[vAttrCount];
		double[] vMax = new double[vAttrCount];
		double[] eMin = new double[eAttrCount];
		double[] eMax = new double[eAttrCount];
		
		// scan to determine min and max
		for (AttributedGraph ag : this) {
			Graph g = ag.getGraph();
			VertexArray<Attributes> va = ag.getVertexLabel();
			for (Vertex v : g.vertices()) {
				double[] attr = va.get(v).getRealValuedAttributes();
				for (int i=0; i<vAttrCount; i++) {
					vMin[i] = Math.min(vMin[i], attr[i]);
					vMax[i] = Math.max(vMax[i], attr[i]);
				}
			}
			EdgeArray<Attributes> ea = ag.getEdgeLabel();
			for (Edge e : g.edges()) {
				double[] attr = ea.get(e).getRealValuedAttributes();
				for (int i=0; i<eAttrCount; i++) {
					eMin[i] = Math.min(eMin[i], attr[i]);
					eMax[i] = Math.max(eMax[i], attr[i]);
				}
			}
		}
		
		// scale attributes
		for (AttributedGraph ag : this) {
			Graph g = ag.getGraph();
			VertexArray<Attributes> va = ag.getVertexLabel();
			for (Vertex v : g.vertices()) {
				double[] attr = va.get(v).getRealValuedAttributes();
				for (int i=0; i<vAttrCount; i++) {
					attr[i] = (attr[i] - vMin[i])/(vMax[i] - vMin[i]);
				}
				va.get(v).setRealValuedAttributes(attr);
			}
			EdgeArray<Attributes> ea = ag.getEdgeLabel();
			for (Edge e : g.edges()) {
				double[] attr = ea.get(e).getRealValuedAttributes();
				for (int i=0; i<eAttrCount; i++) {
					attr[i] = (attr[i] - eMin[i])/(eMax[i] - eMin[i]);
				}
				ea.get(e).setRealValuedAttributes(attr);
			}
		}
	}
	
	
	public AttrDataset createFirstKSubset(int k) {
		return createFirstKSubset(k, new AttrDataset(id));
	}
	
	/**
	 * Creates a dataset with simple labels by ;-separated concatenation of all nominal attributes.
	 * Real-valued attributes are ignored. 
	 * @return the SDataset
	 */
	public SDataset getSDataset() {
		SDataset copy = new SDataset("S"+getID());
		for (AttributedGraph ag : this) {
			VertexArray<String> va = new VertexArray<String>(ag.getGraph());
			for (Vertex v : ag.getGraph().vertices()) {
				StringBuffer sb = new StringBuffer();
				Attributes attr = ag.getVertexLabel().get(v);
				for (int i=0; i<attr.getNominalAttributeCount();i++) {
					sb.append(attr.getNominalAttribute(i));
					sb.append(';');
				}
				if (attr.getNominalAttributeCount() > 0)  {
					sb.deleteCharAt(sb.length()-1); // delete last ';'
				}
				va.set(v, sb.toString());
			}
			EdgeArray<String> ea = new EdgeArray<String>(ag.getGraph());
			for (Edge e : ag.getGraph().edges()) {
				StringBuffer sb = new StringBuffer();
				Attributes attr = ag.getEdgeLabel().get(e);
				for (int i=0; i<attr.getNominalAttributeCount();i++) {
					sb.append(attr.getNominalAttribute(i));
					sb.append(';');
				}
				if (attr.getNominalAttributeCount() > 0)  {
					sb.deleteCharAt(sb.length()-1); // delete last ';'
				}
				ea.set(e, sb.toString());
			}

			LGraph<String,String> lg = new LGraph<String, String>(ag.getGraph(), va, ea);
			copy.add(lg);
		}
		return copy;
	}
	
	public LGDataset<LGraph<double[],double[]>> getRealValuedDataset() {
		LGDataset<LGraph<double[],double[]>> copy = new LGDataset<LGraph<double[],double[]>>("RV"+getID());
		for (AttributedGraph ag : this) {
			VertexArray<double[]> va = new VertexArray<double[]>(ag.getGraph());
			for (Vertex v : ag.getGraph().vertices()) {
				va.set(v, ag.getVertexLabel().get(v).getRealValuedAttributes());
			}
			EdgeArray<double[]> ea = new EdgeArray<double[]>(ag.getGraph());
			for (Edge e : ag.getGraph().edges()) {
				ea.set(e, ag.getEdgeLabel().get(e).getRealValuedAttributes());
			}

			LGraph<double[],double[]> lg = new LGraph<double[],double[]>(ag.getGraph(), va, ea);
			copy.add(lg);
		}
		return copy;
	}

}
