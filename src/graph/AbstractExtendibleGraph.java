package graph;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedHashSet;

import concepts.AbstractAttributedObject;

/**
 * Implements Observer functionality for editable graphs.
 * 
 * @author kriege
 */
public abstract class AbstractExtendibleGraph extends AbstractAttributedObject implements ExtendibleGraph {
	
	WeakList<GraphObserver<Vertex>> vertexObserers;
	WeakList<GraphObserver<Edge>> edgeObserers;
	
	public AbstractExtendibleGraph() {
		vertexObserers = new WeakList<GraphObserver<Vertex>>();
		edgeObserers = new WeakList<GraphObserver<Edge>>();
	}
	
	public int getNextVertexIndex() {
		return getVertexCount();
	}

	public int getNextEdgeIndex() {
		return getEdgeCount();
	}

	public void addEdgeObserver(GraphObserver<Edge> o) {
		edgeObserers.add(o);
	}

	public void addVertexObserver(GraphObserver<Vertex> o) {
		vertexObserers.add(o);
	}

	public boolean removeEdgeObserver(GraphObserver<Edge> o) {
		return edgeObserers.remove(o);
	}

	public boolean removeVertexObserver(GraphObserver<Vertex> o) {
		return vertexObserers.remove(o);
	}
	
	protected void notifyVertexCreated(Vertex v) {
		for (GraphObserver<Vertex> o : vertexObserers) {
			o.created(v);
		}
	}
	
	protected void notifyEdgeCreated(Edge e) {
		for (GraphObserver<Edge> o : edgeObserers) {
			o.created(e);
		}
	}

	protected void notifyVertexDeleted(Vertex v) {
		for (GraphObserver<Vertex> o : vertexObserers) {
			o.deleted(v);
		}
	}
	
	protected void notifyEdgeDeleted(Edge e) {
		for (GraphObserver<Edge> o : edgeObserers) {
			o.deleted(e);
		}
	}

	
	/**
	 * Stores a list of objects but does not prevent
	 * them from being removed by garbage collection.
	 * 
	 * @author kriege
	 *
	 * @param <T> type of the objects stored
	 */
	class WeakList<T> implements Iterable<T> {
		
		ReferenceQueue<T> refQ = new ReferenceQueue<T>();
		LinkedHashSet<WeakReference<T>> list;
		
		public WeakList() {
			this.list = new LinkedHashSet<WeakReference<T>>();
		}
		
		public void add(T o) {
			expungeStaleEntries();
			list.add(new WeakReference<T>(o, refQ));
		}
		
		public boolean remove(T o) {
			expungeStaleEntries();
			Iterator<WeakReference<T>> it = list.iterator();
			while (it.hasNext()) {
				WeakReference<T> ref = it.next();
				T t = ref.get();
				if (o.equals(t)) {
					it.remove();
					ref.clear();
					return true;
				}
			}
			return false;
		}
		
	    /**
	     * Expunges stale entries from the table.
	     */
	    private void expungeStaleEntries() {
	    	Reference<? extends T> ref = null;
	    	while ((ref = refQ.poll()) != null) {
	    		list.remove(ref);
	    	}
	    }

		public Iterator<T> iterator() {
			return new Iterator<T>() {
				
				Iterator<WeakReference<T>> it = list.iterator();
				T current = null;
				T next = getNext();

				public boolean hasNext() {
					return next != null;
				}

				public T next() {
					current = next;
					next = getNext();
					return current;
				}

				public void remove() {
					throw new UnsupportedOperationException();
				}
				
				private T getNext() {
					T next = null;
					while (next == null && it.hasNext()) {
						WeakReference<T> w = it.next();
						next = w.get();
						if (next == null) {
							it.remove();
						}
					}
					return next;
				}
			};
		}
	}

}
