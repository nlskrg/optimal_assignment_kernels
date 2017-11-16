package comparison.kernel.basic;

import comparison.kernel.ExplicitMappingKernel;
import datastructure.FeatureVector;
import datastructure.SparseFeatureVector;

public class DiracKernel implements ExplicitMappingKernel<Object, String> {

	/**
	 * Returns 1 iff g1.equals(g2) returns true, 0 otherwise.
	 */
	public double compute(Object g1, Object g2) {
		return (g1.equals(g2)) ? 1d : 0d;
	}

	public String getID() {
		return "D";
	}

	/**
	 * Note: Only works for Objects uniquely identified by their to String method. 
	 */
	@Override
	public FeatureVector<String> getFeatureVector(Object t) throws IllegalStateException {
		SparseFeatureVector<String> fv = new SparseFeatureVector<String>();
		fv.increaseByOne(t.toString());
		return fv;
	}

}
