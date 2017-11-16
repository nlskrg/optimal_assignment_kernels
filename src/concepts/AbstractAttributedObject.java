package concepts;

import java.util.HashMap;

public abstract class AbstractAttributedObject implements AttributedObject {

	// lazy initialization
	HashMap<String, Object> properties;
	
	public String[] getProperties() {
		if (properties == null) {
			return new String[0];
		} else {
			return properties.keySet().toArray(new String[0]);
		}
	}
	
	public Object getProperty(String key) {
		if (properties == null) {
			return null;
		} else {
			return properties.get(key);
		}
	}
	
	public void setProperty(String key, Object value) {
		if (properties == null) {
			properties = new HashMap<String, Object>();
		}
		properties.put(key, value);		
	}

}
