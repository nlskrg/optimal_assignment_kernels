package comparison.kernel;

import comparison.DisSimilarity;

/**
 * Note: Classes should only implement the interface if
 * the function computed is positive semidefinite!
 * 
 * @author kriege
 */
public interface Kernel<T> extends DisSimilarity<T> {

	/**
	 * An easily interpretable short identifier describing
	 * the kernel and its settings.
	 */
	public String getID();
	
}
