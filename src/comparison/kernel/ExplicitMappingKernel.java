package comparison.kernel;

import java.util.ArrayList;
import java.util.List;

import comparison.kernel.basic.DotProductKernel;
import datastructure.FeatureVector;

/**
 * Interface for kernels allowing explicit mapping into feature space.
 *  
 * @author kriege
 *
 * @param <T> type of objects under comparison
 * @param <O> type of the feature representation
 */
public interface ExplicitMappingKernel<T,O> extends Kernel<T> {
	
	/**
	 * Computes the explicit mapping associated with a kernel for an object.
	 * @param t object
	 * @return a feature vector for the object
	 * @throws IllegalStateException if explicit mapping is not allowed with current state of the kernel 
	 */
	public FeatureVector<O> getFeatureVector(T t) throws IllegalStateException;
	
	/**
	 * Computes the explicit mapping associated with a kernel for a set of objects.
	 * @param list list of objects
	 * @return a feature vector for each object
	 * @throws IllegalStateException if explicit mapping is not allowed with current state of the kernel 
	 */
	default ArrayList<FeatureVector<O>> getFeatureVectors(List<? extends T> list) throws IllegalStateException {
		ArrayList<FeatureVector<O>> r = new ArrayList<FeatureVector<O>>(list.size());
		for (T t : list) {
			r.add(getFeatureVector(t));
		}
		return r;		
	}
	
	/**
	 * Computes kernel by explicit mapping, i.e., the feature vector of both
	 * is computed and then the dot product is returned.
	 * 
	 * Note: Do not call this method for all pairs of a set!
	 * 
	 * @see #computeExplicit(List)
	 * @param g1
	 * @param g2
	 * @return
	 */
	default double computeExplicit(T g1, T g2) {
		return getFeatureVector(g1).dotProduct(getFeatureVector(g2));
	}
	
	/**
	 * Computes kernel by explicit mapping, i.e., the feature vector for all
	 * objects in the set is computed once and then used to compute the value
	 * for all pairs by taking the the dot product.
	 * @param set the set of objects
	 * @return gram matrix
	 */
	default double[][] computeExplicit(List<? extends T> set) {
		DotProductKernel<O> dot = new DotProductKernel<O>();
		
		long startTime = System.nanoTime();
		ArrayList<FeatureVector<O>> fvs = getFeatureVectors(set); 
		long duration = System.nanoTime() - startTime;
		System.out.println("Computed vector space embedding "+duration/1000/1000+" [ms]");
		
		return dot.compute(fvs);
	}

}
