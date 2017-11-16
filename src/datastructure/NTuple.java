package datastructure;

import java.util.ArrayList;

public class NTuple<T> implements Comparable<NTuple<T>> {
	
	protected final ArrayList<T> elements;

	public NTuple(int n) {
		this.elements = new ArrayList<>(n);
		for (int i=0; i<n; i++) elements.add(null);
	}

	
	@SafeVarargs
	public NTuple(T... elements) {
		this.elements = new ArrayList<>(elements.length);
		for (T t : elements) {
			this.elements.add(t);
		}
	}
	
	public NTuple(NTuple<T> ntup, T element) {
		this.elements = new ArrayList<>(ntup.getN()+1);
		this.elements.addAll(ntup.elements);
		this.elements.add(element);
	}
	
	public NTuple(NTuple<T> ntup) {
		this.elements = new ArrayList<>(ntup.getN());
		this.elements.addAll(ntup.elements);
	}
	
	public T get(int i) {
		return elements.get(i);
	}
	
	public void set(int i, T t) {
		elements.set(i, t);
	}
	
	public int getN() {
		return elements.size();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NTuple<?>) {
			NTuple<?> t = (NTuple<?>) obj;
			if (t.elements.size() != this.elements.size())
				return false;
			for (int i=0; i<elements.size(); i++) {
				if (!t.elements.get(i).equals(this.elements.get(i)))
					return false;
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		int h = 0;
		for (int i=0; i<elements.size(); i++) {
			h = 31*h + elements.get(i).hashCode();
		}
		return h;
	}
	
	@Override
	public String toString() {
		if (elements.size() == 0) return "()";
		
		StringBuffer sb = new StringBuffer();
		sb.append("("+elements.get(0));
		for (int i=1; i<elements.size(); i++) {
			sb.append(", ");
			sb.append(String.valueOf(elements.get(i)));
		}
		sb.append(")");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public int compareTo(NTuple<T> o) {
		int r = 0;
		for (int i=0; i<elements.size(); i++) {
			r = ((Comparable<T>)this.elements.get(i)).compareTo((T)o.elements.get(i));
			if (r != 0) return r;
		}
		return 0;
	}

}
