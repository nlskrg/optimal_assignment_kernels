package util.kernel;

/**
 * Container for the data stored in a gram file. 
 * @author kriege
 */
public class GramMatrix {
	
	public double[][] gram;
	public String[] classes;
	public String name;
	
	public GramMatrix(double[][] gram, String[] classes) {
		this.gram = gram;
		this.classes = classes;
	}

	public GramMatrix(double[][] gram, String[] classes, String name) {
		this(gram, classes);
		this.name = name;
	}
	
	public int getDimension() {
		return gram.length;
	}
	
}
