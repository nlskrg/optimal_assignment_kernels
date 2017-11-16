package datastructure;

/**
 * Represents a tuple of three elements.
 * 
 * @param <S>
 * @param <T>
 * @param <U>
 */
//TODO: allow null values and fix methods not expecting null
public class Triple<S,T,U> implements Comparable<Triple<S,T,U>> {

	protected final S first;
	protected final T second;
	protected final U third;
	
//	public Triple() {
//	}
	
	public Triple(S first, T second, U third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

//	public void setFirst(S first) {
//		this.first = first;
//	}

	public S getFirst() {
		return first;
	}

//	public void setSecond(T second) {
//		this.second = second;
//	}

	public T getSecond() {
		return second;
	}
	
//	public void setThird(U third) {
//		this.third = third;
//	}

	public U getThird() {
		return third;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Triple<?,?,?>) {
			Triple<?,?,?> t = (Triple<?,?,?>)obj;
			return first.equals(t.getFirst()) &&
			       second.equals(t.getSecond()) &&
			       third.equals(t.getThird());
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return first.hashCode()+31*second.hashCode()+31*31*third.hashCode();
	}
	
	@Override
	public String toString() {
		return "("+String.valueOf(first)+", "+String.valueOf(second)+", "+String.valueOf(third)+")";
	}

	/**
	 * {@inheritDoc}
	 * Comparison is performed in element-order. Note: A ClassCastException
	 * is thrown if either S, T or U do no implement Comparable<S>, Comparable<T>
	 * and Comparable<U>, respectively.
	 */
	@SuppressWarnings("unchecked")
	public int compareTo(Triple<S, T, U> o) {
		int r = ((Comparable<S>)first).compareTo(o.first);
		if (r != 0) return r;
		r = ((Comparable<T>)second).compareTo(o.second);
		return r != 0 ? r : ((Comparable<U>)third).compareTo(o.third);
	}
	
}
