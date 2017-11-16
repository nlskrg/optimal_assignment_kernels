package algorithm.graph.isomorphism.labelrefinement;

import concepts.Transformation;
import graph.LGraph;

/**
 * Base class for algorithms computing a vertex/edge label refinement.
 * 
 * @author kriege
 *
 * @param <IG> labeled graph type which can be used for refinement
 * @param <OG> output type of the refined labeled graph
 */
public abstract class LabelRefiner<IG extends LGraph<?,?>, OG extends LGraph<?,?>> implements Transformation<IG,OG> {
	
	/**
	 * Returns a new LabeledGraph with refined vertex/edge labels.
	 * Note: Implementing classes should not copy the underlying graph,
	 * but create a shallow copy of the input LabeledGraph containing the 
	 * same objects with exception of the vertex/edge labels that have been 
	 * computed. 
	 *  
	 * @param lg input graph which will be refined
	 * @return the refined labeled graph
	 */
	public abstract OG refineGraph(IG lg);
	
	/**
	 * {@inheritDoc}
	 */
	public OG transform(IG in) {
		return refineGraph(in);
	}

}
