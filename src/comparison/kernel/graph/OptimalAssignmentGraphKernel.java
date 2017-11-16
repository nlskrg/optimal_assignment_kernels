package comparison.kernel.graph;

import comparison.kernel.ExplicitMappingKernel;
import comparison.kernel.basic.DiracKernel;
import comparison.kernel.basic.HistogramIntersectionKernel;
import datastructure.Triple;
import graph.LGraph;

/**
 * Computes optimal assignment kernels by histogram intersection.
 * 
 * @author kriege
 *
 * @param <V>
 * @param <E>
 * @param <F>
 */
public class OptimalAssignmentGraphKernel<V, E, F> extends FeatureVectorKernel<V, E, F> {
	
	
	public OptimalAssignmentGraphKernel(ExplicitMappingKernel<LGraph<V, E>, F> explicitKernel) {
		super(new HistogramIntersectionKernel<>(), explicitKernel);
	}
	
	@Override
	public String getID() {
		return "OA-"+explicitKernel.getID();
	}

	public static class WeisfeilerLehmanSubtree<V,E> extends OptimalAssignmentGraphKernel<V, E, Integer> {
		public WeisfeilerLehmanSubtree(int height) {
			super(new WeisfeilerLehmanSubtreeKernel<>(height));
		}

		@SuppressWarnings("rawtypes")
		@Override
		public String getID() {
			return "WLOA_"+((WeisfeilerLehmanSubtreeKernel)explicitKernel).getHeight();
		}
	}

	public static class Vertex<V,E> extends OptimalAssignmentGraphKernel<V, E, V> {
		public Vertex(boolean jaccardNormalization) {
			super(new VertexKernel<V,E>(new DiracKernel()));
		}
		
		@Override
		public String getID() {
			return "VLOA";
		}
	}

	public static class EdgeUnoriented<V extends Comparable<V>, E> extends OptimalAssignmentGraphKernel<V, E, Triple<V, E, V>> {
		public EdgeUnoriented() {
			super(new EdgeKernelUnorientedExact<V, E>());
		}
		
		@Override
		public String getID() {
			return "ELUOA";
		}
	}
}
