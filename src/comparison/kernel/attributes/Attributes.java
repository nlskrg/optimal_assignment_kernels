package comparison.kernel.attributes;

/**
 * This class stores nominal and real valued attributes. Of each type
 * an arbitrary number of attributes may exist.
 * 
 * @author kriege
 *
 */
public class Attributes {
	
	private final static String TYPE_DELIMITER = ";";
	private final static String VALUE_DELIMITER = ",";
	
	private Object[] nominalAttributes;
	private double[] realValuedAttributes;
	
	/**
	 * Creates a new set of attributes.
	 * 
	 * @param n number of nominal attributes
	 * @param m number of real valued attributes
	 */
	public Attributes(int n, int m) {
		nominalAttributes = new Object[n];
		realValuedAttributes = new double[m];
	}
	
	/**
	 * Creates a new set of attributes.
	 * 
	 * @param values a string containing the values
	 * @see #setAttributes(String)
	 */
	public Attributes(String values) {
		setAttributes(values);
	}
	
	/**
	 * Creates a new set of attributes.
	 * 
	 * @param nominalValues string with nominal values
	 * @param realValues string with real values
	 */
	public Attributes(String nominalValues, String realValues) {
		setNominalAttributes(nominalValues);
		setRealValuedAttributes(realValues);
	}
	
	/**
	 * @return true iff nominal attributes are present
	 */
	public boolean hasNominalAttributes() {
		return nominalAttributes.length != 0;
	}
	
	/**
	 * @return true iff real valued attributes are present
	 */
	public boolean hasRealValuedAttributes() {
		return realValuedAttributes.length != 0;
	}
	
	/**
	 * Parses a string containing nominal and reals values,
	 * separated by a semicolon.
	 * @param values
	 */
	public void setAttributes(String values) {
		String[] tokens = values.split(TYPE_DELIMITER);
		if (tokens.length > 1) {
			setAttributes(tokens[0], tokens[1]);
		} else {
			setNominalAttributes(tokens[0]);
			realValuedAttributes = new double[0];
		}
	}
	
	/**
	 * Sets both, nominal attributes and real valued attributes.
	 * @see #setNominalAttributes(String)
	 * @see #setRealValuedAttributes(String)
	 * @param nominalValues
	 * @param realValues
	 * @throws IllegalArgumentException
	 */
	public void setAttributes(String nominalValues, String realValues) throws IllegalArgumentException {
		setNominalAttributes(nominalValues);
		setRealValuedAttributes(realValues);
	}

	/**
	 * Parses a comma separated list of nominal attribute values.
	 * Note: The values are added to the list depending on the order they
	 * appear in the string.
	 * 
	 * @param values comma separated list of attributes interpreted as nominal
	 */
	public void setNominalAttributes(String values) {
		if (values.isEmpty()) {
			nominalAttributes = new Object[0];
			return;
		}
		String[] tokens = values.split(",");
		nominalAttributes = new Object[tokens.length];
		int i = 0;
		for (String t : tokens) {
			nominalAttributes[i++] = t.trim();
		}
	}
	
	/**
	 * Parses a comma separated list of nominal attribute values.
	 * Note: The values are added to the list depending on the order they
	 * appear in the string.
	 * 
	 * @param values comma separated list of attributes interpreted as real values
	 * @throws IllegalArgumentException if a value can not be interpreted as double
	 */
	public void setRealValuedAttributes(String values) throws IllegalArgumentException {
		if (values.isEmpty()) {
			realValuedAttributes = new double[0];
			return;
		}
		String[] tokens = values.split(VALUE_DELIMITER);
		realValuedAttributes = new double[tokens.length];
		int i = 0;
		for (String t : tokens) {
			realValuedAttributes[i++] = Double.valueOf(t);
		}
	}
	
	public int getNominalAttributeCount() {
		return nominalAttributes.length;
	}
	
	public int getRealValuedAttributeCount() {
		return realValuedAttributes.length;
	}
	
	public void setNominalAttributes(Object[] nominalAttributes) {
		this.nominalAttributes = nominalAttributes;
	}
	
	public Object[] getNominalAttributes() {
		return nominalAttributes;
	}
	
	public void setNominalAttribute(Object o, int i) {
		nominalAttributes[i] = o;
	}

	public Object getNominalAttribute(int i) {
		return nominalAttributes[i];
	}
	
	public void setRealValuedAttributes(double[] realValuedAttributes) {
		this.realValuedAttributes = realValuedAttributes;
	}
	
	public double[] getRealValuedAttributes() {
		return realValuedAttributes;
	}

	public void setRealValuedAttribute(double d, int i) {
		realValuedAttributes[i] = d;
	}

	public double getRealValuedAttribute(int i) {
		return realValuedAttributes[i];
	}
	
	public String getNominalAttributeString() {
		StringBuffer sb = new StringBuffer();
		if (hasNominalAttributes()) {
			for (Object o : nominalAttributes) {
				sb.append(o.toString());
				sb.append(VALUE_DELIMITER);
				sb.append(' ');
			}
			// remove last delimiter
			if (!hasRealValuedAttributes()) {
				sb.delete(sb.length()-1-VALUE_DELIMITER.length(), sb.length());
			}
		}
		return sb.toString();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (hasNominalAttributes()) {
			for (Object o : nominalAttributes) {
				sb.append(o.toString());
				sb.append(VALUE_DELIMITER);
				sb.append(' ');
			}
			// remove last delimiter
			if (!hasRealValuedAttributes()) {
				sb.delete(sb.length()-1-VALUE_DELIMITER.length(), sb.length());
			}
		}
		if (hasRealValuedAttributes()) {
			for (double d : realValuedAttributes) {
				sb.append(d);
				sb.append(VALUE_DELIMITER);
				sb.append(' ');
			}
			// remove last delimiter
			sb.delete(sb.length()-1-VALUE_DELIMITER.length(), sb.length());
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Attributes at = new Attributes(4, 5);
		
		at.setNominalAttributes("nein, ja,   vielleicht,test");
		at.setRealValuedAttributes("12, 12.453,44,42,666");
		
		System.out.println(at.toString());
		System.out.println(at.getNominalAttributeCount());
		System.out.println(at.getRealValuedAttributeCount());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Attributes) {
			Attributes other = (Attributes)obj;
			if (other.nominalAttributes.length != this.nominalAttributes.length) {
				return false;
			}
			if (other.realValuedAttributes.length != this.realValuedAttributes.length) {
				return false;
			}
			for (int i=0; i<nominalAttributes.length; i++) {
				if (!this.nominalAttributes[i].equals(other.nominalAttributes[i])) {
					return false;
				}
			}
			for (int i=0; i<realValuedAttributes.length; i++) {
				if (this.realValuedAttributes[i] != other.realValuedAttributes[i]) {
					return false;
				}
			}
			return true;	
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		// TODO more efficient implementation
		return toString().hashCode();
	}

}