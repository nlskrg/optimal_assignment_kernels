package comparison.kernel.graph;

import comparison.kernel.basic.DiracKernel;

/**
 * Weisfeiler Lehman Subtree Kernel (Shervashidze, 2011) supporting 1:1 kernel 
 * evaluation as well as explicit mapping into feature space for fast n:n 
 * computation.
 * 
 * @author kriege
 */
public class WeisfeilerLehmanSubtreeKernel<V, E> extends WeisfeilerLehmanKernel<V, E, Integer> {

	/**
	 * Creates a new instance of the Weisfeiler-Lehmann subtree kernel.
	 * 
	 * @param height the number of WL refinement steps, in total the number of graphs 
	 * in the computed WL sequence is height+1 
	 */
	public WeisfeilerLehmanSubtreeKernel(int height) {
		super(height, new VertexKernel<Integer, E>(new DiracKernel()));
	}
	
	@Override
	public String toString() {
		return "Weisfeiler-Lehman subtree kernel:\n"+super.toString();
	}
	
	public String getID() {
		return "WLS_"+height;
	}
}
