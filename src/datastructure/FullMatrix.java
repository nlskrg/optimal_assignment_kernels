package datastructure;

public class FullMatrix<T> extends AbstractMatrix<T> {
	
	private Object[][] v;
	
	/**
	 * 
	 * @param m number of rows
	 * @param n number of columns
	 */
	public FullMatrix(int m, int n) {
		v = new Object[m][n];
		for (int i=0; i<m; i++) {
			v[i] = new Object[n];
		}
	}
	
	public void set(int i, int j, T value) {
		v[i][j] = value;
	}
	
	@SuppressWarnings("unchecked")
	public T get(int i, int j) {
		return (T) v[i][j];
	}

	public int getColumnDimension() {
		return v[0].length;
	}

	public int getRowDimension() {
		return v.length;
	}

}
