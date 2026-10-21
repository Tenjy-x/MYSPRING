package Tendry.Utils;

import java.util.*;
public class MySession {
    
    Map<String , Object> attributes = new HashMap<>();
    
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    public Object get(String key) {
        return attributes.get(key);
    }

    public void set(String key , Object value) {
        attributes.put(key, value);
    }

    public void remove(String key) {
        attributes.remove(key);
    }
}
