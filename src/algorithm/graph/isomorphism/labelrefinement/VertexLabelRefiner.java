package algorithm.graph.isomorphism.labelrefinement;

import graph.LGraph;
import graph.properties.VertexArray;

/**
 * Base class for algorithms computing a vertex label refinement.
 * 
 * @author kriege
 *
 * @param <IE> edge label type of the input graph, which is preserved by
 * the refinement step 
 * @param <IG> labeled graph type which can be used for refinement
 * @param <OV> output type of the refined vertex labels
 */
// TODO harmonize with VertexInvariant and VertexDescriptor
public abstract class VertexLabelRefiner<IE,IG extends LGraph<?, IE>,OV> extends LabelRefiner<IG,LGraph<OV, IE>> {

	/**
	 * Computes the refined vertex labeling.
	 * @param lg
	 * @return
	 */
	public abstract VertexArray<OV> vertexRefine(IG lg);
	
	@Override
	public LGraph<OV, IE> refineGraph(IG lg) {
		return new LGraph<OV, IE>(lg.getGraph(), vertexRefine(lg), lg.getEdgeLabel());
	}

}
