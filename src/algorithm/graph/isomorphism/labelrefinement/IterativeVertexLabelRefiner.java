package algorithm.graph.isomorphism.labelrefinement;

import graph.Graph;
import graph.LGraph;
import graph.properties.EdgeArray;
import graph.properties.VertexArray;


/**
 * Abstract class for algorithms providing iterative vertex label refinement, 
 * i.e. the refinement step can be repeated multiple times. Such algorithms 
 * must generate labeled graphs of the same type as the graphs they can be 
 * applied to.
 * 
 * @author kriege
 *
 * @param <IV> vertex label type of the input graph
 * @param <IE> edge label type of the input graph, which is preserved by
 * the refinement step 
 * @param <OV> output vertex label type, must extend IV
 */
public abstract class IterativeVertexLabelRefiner<IV, IE, OV extends IV> extends VertexLabelRefiner<IE, LGraph<? extends IV,IE>, OV> {

	private int iterations;
	
	/**
	 * Creates a new refiner which uses the specified number of
	 * iterations.
	 * @param iterations the number of iterations performed when 
	 * calling {@link #vertexRefine(graph.LGraph)}
	 */
	public IterativeVertexLabelRefiner(int iterations) {
		this.iterations = iterations;
	}

	/**
	 * Default constructor for single iteration refinement.
	 */
	public IterativeVertexLabelRefiner() {
		this(1);
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public int getIterations() {
		return iterations;
	}
	
	/**
	 * Computes the vertex refinement for the given number of iterations.
	 */
	@Override
	public VertexArray<OV> vertexRefine(LGraph<? extends IV, IE> lg) {
		
		Graph g = lg.getGraph();
		VertexArray<? extends IV> va = lg.getVertexLabel();
		EdgeArray<IE> ea = lg.getEdgeLabel();
		
		VertexArray<OV> result = refinementStep(g, va, ea);
		
		for (int i=1; i<iterations; i++) {
			result = refinementStep(g, result, ea);
		}
		
		return result;
	}
	
	protected abstract VertexArray<OV> refinementStep(Graph g, VertexArray<? extends IV> va, EdgeArray<IE> ea);

}
