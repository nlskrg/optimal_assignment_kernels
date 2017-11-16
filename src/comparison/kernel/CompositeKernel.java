package comparison.kernel;

import java.util.List;

/**
 * Returns the result of several kernel functions, computed by a single 
 * call of the method {@link #computeComposition(Object, Object)}. Each 
 * of the returned values, i.e. the elements at the same index, must 
 * yield a positive semidefinite kernel matrix.
 * 
 * @author kriege
 *
 * @param <T> type of the objects that can be compared
 * by this implementation
 */
public interface CompositeKernel<T> extends Kernel<T> {
	
	/**
	 * {@inheritDoc}
	 * Computes a single kernel value by adding up all kernel
	 * function values.
	 */
	default double compute(T g1, T g2) {
		double r = 0;
		double[] comp = computeComposition(g1, g2);
		for (double c : comp) {
			r += c;
		}
		
		return r;
	}

	/**
	 * Computes the kernel values for the given objects.
	 * @param g1 first object under comparison
	 * @param g2 second object under comparison
	 * @return the result of the kernel functions computed
	 * by this composite kernel
	 */
	public double[] computeComposition(T g1, T g2);
	
	/**
	 * Uses composite kernels to compute kernel values; each value is a vector and
	 * each element yields a valid kernel. These values can be combined to a single
	 * kernel value.
	 * @see CompositeMerger
	 * @param set the objects under comparison
	 * @return a gram matrix, where each element is a vector of kernel values
	 */
	default double[][][] computeComposition(List<? extends T> set) {
		int n = set.size();
		double[][][] gram = new double[n][n][getComponentCount()];
		
		for (int i=0; i<n; i++) {
			System.out.print('.');
			T e1 = set.get(i);
			for (int j=i; j<n; j++) {
				T e2 = set.get(j);
				gram[i][j] = gram[j][i] = computeComposition(e1, e2);
			}
		}
		
		System.out.println();
		return gram;
	}
	
	/**
	 * @return the number of kernel values computed
	 */
	public int getComponentCount();
	
}
