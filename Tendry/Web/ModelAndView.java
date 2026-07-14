package Tendry.Web;

import java.util.*;
public class ModelAndView {
    private String Url;
    private Map<String , Object> Object = new HashMap<>();
    public String getUrl() {
        return Url;
    }
    public void setUrl(String url) {
        Url = url;
    }
    public Map<String, Object> getObject() {
        return Object;
    }
    // public void setObject(String url , Object object) {
    //     Object.put(url, object);
    // }
    public ModelAndView(String Url) {
        this.Url = Url;
    }

    public void setAttribute(String Url , Object Object) {
        getObject().put(Url, Object);
    }
}
