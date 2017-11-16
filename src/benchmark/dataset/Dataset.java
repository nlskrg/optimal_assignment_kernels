package benchmark.dataset;


import java.util.ArrayList;
import java.util.HashMap;

import datastructure.FeatureVector;
import datastructure.SparseFeatureVector;

public abstract class Dataset<T> extends ArrayList<T> {
	
	private static final long serialVersionUID = 1L;
	String id;
	
	/**
	 * @param id a string to identify the dataset
	 */
	public Dataset(String id) {
		this.id = id;
	}

	public String getID() {
		return id;
	}
	
	public abstract String getClassLabel(T g);
	
	public abstract Dataset<T> newEmptyInstance(String id);
	
	/**
	 * Returns an array of class labels, where the i-th label corresponds to the i-th 
	 * graph in the dataset.
	 * 
	 * @return array of class labels
	 */
	public String[] getClassLabels() {
		String[] r = new String[this.size()];
		int i = 0;
		for (T t : this) {
			r[i++] = getClassLabel(t);
		}
		return r;
	}
	
	/**
	 * Returns the frequency distribution of class labels.
	 */
	public FeatureVector<String> getClassCounts() {
		SparseFeatureVector<String> r = new SparseFeatureVector<String>();
		for (String s : getClassLabels()) {
			r.increaseByOne(s);
		}
		return r;
	}
	
	/**
	 * Returns a HashMap containing an ArrayList of graphs for each
	 * class label.
	 */
	protected HashMap<String, ArrayList<T>> getElementsByClass() {
		HashMap<String, ArrayList<T>> byClass = new HashMap<String, ArrayList<T>>();
		for (T lg : this) {
			String s = getClassLabel(lg);
			ArrayList<T> lgs = byClass.get(s);
			if (lgs == null) {
				lgs = new ArrayList<T>();
				byClass.put(s, lgs);
			}
			lgs.add(lg);
		}
		return byClass;
	}
	



}
