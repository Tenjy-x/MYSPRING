package Tendry.Utils;
import java.lang.reflect.Method;
public class Mapping {
    private Class<?> Controller;
    private Method Method;
    public Class<?> getController() {
        return Controller;
    }
    public void setController(Class<?> controller) {
        Controller = controller;
    }
    public Method getMethod() {
        return Method;
    }
    public void setMethod(Method method) {
        Method = method;
    }

    @Override
    public String toString() {
        return getMethod() + " : " + getController(); 
    }
    
}
