package concepts;

/**
 * Defines a transformation of objects.
 * @author kriege
 * @param <I> input type
 * @param <O> output type
 */
public interface Transformation<I,O> {
	
	public O transform(I in);
	
}