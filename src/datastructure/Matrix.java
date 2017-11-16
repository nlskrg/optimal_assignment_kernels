package datastructure;
/**
 * @Deprecated use lib MTJ instead of this matrix implementation
 */ 
public interface Matrix<T> {
	
	/**
	 * Sets matrix element to the specified value.
	 * @param i row index
	 * @param j column index
	 * @param value
	 */
	public void set(int i, int j, T value);	
	
	/**
	 * Get the matrix element at the specified position.
	 * @param i row index
	 * @param j column index
	 * @return the value a the specified position
	 */
	public T get(int i, int j);
	public int getRowDimension();
	public int getColumnDimension();
	public void fill(T value);

}
