package datastructure;

public abstract class AbstractMatrix<T> implements Matrix<T> {
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<getRowDimension(); i++) {
			for (int j=0; j<getColumnDimension(); j++) {
				sb.append(get(i, j)+"\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public void fill(T value) {
		for (int i=0; i<getRowDimension(); i++) {
			for (int j=0; j<getColumnDimension(); j++) {
				set(i, j, value);
			}
		}
	};
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Matrix)) return false;
		Matrix<?> other = (Matrix<?>)obj;
		if (getRowDimension() != other.getRowDimension()) return false;
		if (getColumnDimension() != other.getColumnDimension()) return false;
		for (int i=0; i<getRowDimension(); i++) {
			for (int j=0; j<getColumnDimension(); j++) {
				if (!get(i, j).equals(other.get(i, j))) return false;
			}
		}

		return true;
	}

}
