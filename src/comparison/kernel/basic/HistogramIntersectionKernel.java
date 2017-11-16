package comparison.kernel.basic;

import java.util.Map;

import comparison.kernel.Kernel;
import datastructure.FeatureVector;

public class HistogramIntersectionKernel<T> implements Kernel<FeatureVector<T>> {

	public double compute(FeatureVector<T> u, FeatureVector<T> v) {
		if (v.size()<u.size()) {
			//swap
			FeatureVector<T> tmp = u;
			u = v;
			v = tmp;
		}
		
		double min = 0;
		
		for (Map.Entry<T, Double> e : u.nonZeroEntries()) {
			min += Math.min(e.getValue(), v.getValue(e.getKey()));
		}
		
		return min;
	}

	public String getID() {
		return "HI";
	}
	
}
