package comparison.kernel.graph;

import java.util.ArrayList;
import java.util.List;

import algorithm.shortestpath.SPTools;
import comparison.kernel.ExplicitMappingKernel;
import comparison.kernel.Kernel;
import comparison.kernel.basic.DiracKernel;
import concepts.Transformation;
import concepts.TransformationTools;
import datastructure.FeatureVector;
import datastructure.Triple;
import graph.LGraph;

/**
 * Computes the shortest path kernel (Borgwardt, 2005). In addition this 
 * class supports fast computation for large datasets by explicit mapping
 * provided that vertex and edge kernels both are {@link DiracKernel}s.
 * 
 * Please note that each call of {@link #compute(LGraph, LGraph)}
 * computes the {@link FloydWarshallTransformation} for both graphs. 
 * A significant runtime improvement can be achieved using 
 * {@link #compute(List)}, which only computes the transformation once for
 * each graph.
 * 
 * @author kriege
 *
 * @param <V> vertex label type
 * @param <E> edge label type; note that edge labels are completely ignored by this kernel
 */
public class ShortestPathKernel<V, E> implements 
	ExplicitMappingKernel<LGraph<V, E>, Triple<?, ?, ?>> 
{
	
	EdgeKernel<V, Integer> ek;
	FloydWarshallTransformation<V> fwt;
	Kernel<? super Integer> edgeKernel;
	Kernel<? super V> vertexKernel;
	
	/**
	 * Instantiates a new shortest path kernel.
	 * 
	 * @param edgeKernel kernel used to compare shortest path distances
	 * @param vertexKernel kernel used to compare vertex labels
	 */
	public ShortestPathKernel(Kernel<? super Integer> edgeKernel, Kernel<? super V> vertexKernel) {
		this.edgeKernel = edgeKernel;
		this.vertexKernel = vertexKernel;
		fwt = new FloydWarshallTransformation<V>();
		ek = new EdgeKernel<V, Integer>(edgeKernel, vertexKernel);
	}

	@Override
	public double compute(LGraph<V, E> g1, LGraph<V, E> g2) {
		LGraph<V, Integer> fwtG1 = fwt.transform(g1);
		LGraph<V, Integer> fwtG2 = fwt.transform(g2);
		
		return ek.compute(fwtG1, fwtG2);
	}
	
	@Override
	public double[][] compute(List<? extends LGraph<V, E>> graphs) {
		ArrayList<LGraph<V, Integer>> transformedGraphs = 
				TransformationTools.transformAll(fwt, graphs);
		return ek.compute(transformedGraphs);
	}

	@Override
	public FeatureVector<Triple<?, ?, ?>> getFeatureVector(LGraph<V, E> lg) throws IllegalStateException {

		LGraph<V, Integer> fwtLG = fwt.transform(lg);
		
		return ek.getFeatureVector(fwtLG);
	}
	
	/**
	 * Computes the Floyd Warshall transformation of a graph, i.e. a
	 * complete graph with edge labels representing shortest path
	 * distances.
	 * 
	 * @author kriege
	 *
	 * @param <V> the preserved vertex label type
	 */
	public static class FloydWarshallTransformation<V> implements Transformation<LGraph<V,?>, LGraph<V, Integer>> {

		public LGraph<V, Integer> transform(LGraph<V, ?> in) {
			return SPTools.getAPSPTransformation(in);
		}
		
	}
	
	public String toString() {
		return "SPKernel+\n"+ek.toString();
	}

	public String getID() {
		if (vertexKernel instanceof DiracKernel && edgeKernel instanceof DiracKernel) {
			return "SP";
		} else {
			return "SP_"+vertexKernel.getID()+"_"+edgeKernel.getID();
		}
	}
}
