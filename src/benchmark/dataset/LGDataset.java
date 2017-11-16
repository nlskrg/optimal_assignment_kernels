package benchmark.dataset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import datastructure.NTuple;
import graph.LGraph;


/**
 * Base class for datasets of labeled graphs of the same type.
 * 
 * Note: A LabeledGraph g added to a dataset should be annotated by a class 
 * label returned by g.getProperty("class").
 * 
 * @author kriege
 *
 * @param <LG> labeled graph type
 */
// TODO move more methods to dataset
public class LGDataset<LG extends LGraph<?, ?>> extends Dataset<LG> {
	
	private static final long serialVersionUID = 1L;
	
	public LGDataset(String id) {
		super(id);
	}
	
	@Override
	public String getClassLabel(LG lg) {
		return getClassString(lg);
	}
	
	/**
	 * Creates a subset of this dataset by sampling random elements.
	 * 
	 * @param size size of the subset
	 * @param balanced tries to balance the subset in terms of class labels
	 * @return a subset of this dataset
	 */
	public LGDataset<LG> createRandomSubset(int size, boolean balanced) {
		return createRandomSubset(size, balanced, new LGDataset<LG>(id));
	}
	
	protected <T extends LGDataset<LG>> T createRandomSubset(int size, boolean balanced, T r) {
		if (this.size() < size) throw new IllegalArgumentException("Subset size exceeds dataset size.");
		Random rng = new Random(4242424242l);
		if (balanced) {
			HashMap<String, ArrayList<LG>> classPartition = getElementsByClass();
			while (r.size() < size) {
				for (String s : classPartition.keySet()) {
					ArrayList<LG> set = classPartition.get(s);
					if (set.isEmpty()) continue;
					r.add(set.remove(rng.nextInt(set.size())));
					if (r.size() == size) break;
				}
			}
		} else {
			r.addAll(this);
			while (r.size()>size) {
				r.remove(rng.nextInt(r.size()));			
			}
		}
		return r;
	}
	
	/**
	 * Creates a subset of this dataset containing only graphs with
	 * at most maxVertexCount vertices.
	 * 
	 * @param maxVertexCount threshold value.
	 * @return a subset of this dataset
	 */
	public LGDataset<LG> createSmallGraphSubset(int maxVertexCount) {
		return createSmallGraphSubset(maxVertexCount, new LGDataset<LG>(id));
	}
	
	protected <T extends LGDataset<LG>> T createSmallGraphSubset(int maxVertexCount, T r) {
		for (LG lg : this) {
			if (lg.getGraph().getVertexCount() <= maxVertexCount) {
				r.add(lg);
			}
		}
		return r;
	}
	
	/**
	 * Creates a subset of this dataset consisting of the first k elements only.
	 * 
	 * @param k
	 * @return a subset of this dataset
	 */
	public LGDataset<LG> createFirstKSubset(int k) {
		return createFirstKSubset(k, new LGDataset<LG>(id));
	}
	
	protected <T extends LGDataset<LG>> T createFirstKSubset(int k, T r) {
		for (int i=0; i<k; i++) {
			r.add(this.get(i));
		}
		return r;
	}
	
	public NTuple<LGDataset<LG>> getTrainingValidationTestSets() {
		LGDataset<LG> train = new LGDataset<LG>(id);
		LGDataset<LG> validation = new LGDataset<LG>(id);
		LGDataset<LG> test = new LGDataset<LG>(id);
		for (LG lg : this) {
			if (lg.getGraph().getProperty("set").equals("train")) {
				train.add(lg);
			} else if (lg.getGraph().getProperty("set").equals("valid")) {
				validation.add(lg);
			} else if (lg.getGraph().getProperty("set").equals("test")) {
				test.add(lg);
			}
		}
		return new NTuple<>(train, validation, test);
	}
	
	public LGDataset<LG> getTrainingSet() {
		return getTrainingValidationTestSets().get(0);
	}
	
	public LGDataset<LG> getValidationSet() {
		return getTrainingValidationTestSets().get(1);
	}
	
	public LGDataset<LG> getTestSet() {
		return getTrainingValidationTestSets().get(2);
	}
	
	public static String[] getClassesArray(Collection<? extends LGraph<?,?>> lgs) {
		String[] r = new String[lgs.size()];
		int i = 0;
		for (LGraph<?, ?> lg : lgs) {
			r[i++] = getClassString(lg);
		}
		return r;
	}
	
	public static String getClassString(LGraph<?, ?> lg) {
		Object set = lg.getGraph().getProperty("class");
//		if (set instanceof Number) {
//			Number s =  (Number)set;
//			return (s.intValue() == 1) ? "+1" : "-1";
//		}
//		String result = (set.equals("1") || set.equals("+1")) ? "+1" : "-1";
//		return result;
		return String.valueOf(set);
	}

	@Override
	public Dataset<LG> newEmptyInstance(String id) {
		return new LGDataset<>(id);
	}
	

}
