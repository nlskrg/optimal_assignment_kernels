package concepts;

/**
 * Provides a property mechanism.
 */
public interface AttributedObject {
	
	/**
	 * Returns a list of available properties.
	 */
	public String[] getProperties();

	/**
	 * Returns the specified property value.
	 */
	public Object getProperty(String key);
	
	/**
	 * Sets the specified property to the give value.
	 */
	public void setProperty(String key, Object value);

}
