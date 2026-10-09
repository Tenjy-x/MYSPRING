package Tendry.Utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.net.*;
import java.text.Annotation;

import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import Tendry.Annotation.*;
import Tendry.Exception.*;
import Tendry.Utils.*;
import Tendry.Utils.JsonResources;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;

public class Utils {
 public static List<Class<?>> chargerClasses(String nomPackage) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        String chemin = nomPackage.replace('.', '/');
        
        // Récupération du ClassLoader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> ressources = classLoader.getResources(chemin);

        while (ressources.hasMoreElements()) {
            URL ressource = ressources.nextElement();
            String protocole = ressource.getProtocol();

            if ("file".equals(protocole)) {
                // Scan dans les dossiers (IDE / Mode développement)
                scannerDossier(new File(ressource.getFile()), nomPackage, classLoader, classes);
            } else if ("jar".equals(protocole)) {
                // Scan dans une archive (Fichier JAR compilé)
                String cheminJar = ressource.getPath().substring(5, ressource.getPath().indexOf("!"));
                scannerJar(cheminJar, chemin, classLoader, classes);
            }
        }
        return classes;
    }

    private static void scannerDossier(File dossier, String nomPackage, ClassLoader classLoader, List<Class<?>> classes) throws ClassNotFoundException {
        if (!dossier.exists() || dossier.listFiles() == null) return;

        for (File fichier : dossier.listFiles()) {
            if (fichier.isDirectory()) {
                // Optionnel : scan récursif des sous-packages
                scannerDossier(fichier, nomPackage + "." + fichier.getName(), classLoader, classes);
            } else if (fichier.getName().endsWith(".class")) {
                String nomClasse = nomPackage + '.' + fichier.getName().substring(0, fichier.getName().length() - 6);
                // Utilisation du ClassLoader pour charger la classe
                classes.add(classLoader.loadClass(nomClasse));
            }
        }
    }

    private static void scannerJar(String cheminJar, String cheminPackage, ClassLoader classLoader, List<Class<?>> classes) throws IOException, ClassNotFoundException {
        try (JarFile jar = new JarFile(cheminJar)) {
            Enumeration<JarEntry> entrees = jar.entries();
            while (entrees.hasMoreElements()) {
                JarEntry entree = entrees.nextElement();
                String nomEntree = entree.getName();
                
                if (nomEntree.startsWith(cheminPackage) && nomEntree.endsWith(".class") && !entree.isDirectory()) {
                    String nomClasse = nomEntree.substring(0, nomEntree.length() - 6).replace('/', '.');
                    // Utilisation du ClassLoader pour charger la classe du JAR
                    classes.add(classLoader.loadClass(nomClasse));
                }
            }
        }
    }

    public static List<Class<?>> getAnnotationClasses(List<Class<?>> classes , Class<? extends AnnotationController> a ){
        List<Class<?>> ListClasses = new ArrayList<>();
        for (Class<?> clazz : classes) {
            if(clazz.isAnnotationPresent(a)){
                ListClasses.add(clazz);
            }
        }
        return ListClasses;
    }


    public static void UrlMapping(String URL , List<Class<?>> classes , Class<? extends UrlMapping> a ,  Map<UrlMethod, Mapping> MAP) throws URLException {
    Map<UrlMethod, Mapping> matched = new HashMap<>();
    Map<UrlMethod , Mapping> all = new HashMap<>();
    for(Class<?> clazz : classes) {
        Method[] methods = clazz.getDeclaredMethods();
        for(Method function : methods) {
            UrlMapping ann = function.getAnnotation(UrlMapping.class);
            if(ann != null){
                UrlMethod urlMethod = new UrlMethod();
                urlMethod.setUrl(ann.path());
                urlMethod.setMethod(ann.method());
                Mapping map = new Mapping();
                map.setMethod(function);
                map.setController(clazz);
                all.put(urlMethod , map);
                if((urlMethod.getUrl()).equals(URL)) {
                    if(matched.containsKey(urlMethod)) {
                        throw new URLException("Plusieurs fonctions ont cette URL");
                    }
                    matched.put(urlMethod , map);
                }
            }
        }
    }
    if (!matched.isEmpty()) {
        MAP.putAll(matched);;
    }
    MAP.putAll(all);
}

public static Map<UrlMethod, Mapping> UrlMapping1(String URL , List<Class<?>> classes , Class<? extends UrlMapping> a) throws URLException {
    Map<UrlMethod, Mapping> matched = new HashMap<>();
    Map<UrlMethod , Mapping> all = new HashMap<>();
    for(Class<?> clazz : classes) {
        Method[] methods = clazz.getDeclaredMethods();
        for(Method function : methods) {
            UrlMapping ann = function.getAnnotation(UrlMapping.class);
            if(ann != null){
                UrlMethod urlMethod = new UrlMethod();
                urlMethod.setUrl(ann.path());
                urlMethod.setMethod(ann.method());
                Mapping map = new Mapping();
                map.setMethod(function);
                map.setController(clazz);
                all.put(urlMethod , map);
                if((urlMethod.getUrl()).equals(URL)) {
                    if(matched.containsKey(urlMethod)) {
                        throw new URLException("Plusieurs fonctions ont cette URL");
                    }
                    matched.put(urlMethod , map);
                }
            }
        }
    }
    if (!matched.isEmpty()) {

        return matched;
    }
    return all;
}




public static String toJson(Object object) throws JsonProcessingException {
    ObjectWriter ow = JsonResources.getObjectwriter();
    return ow.writeValueAsString(object);
    }

    public static void bindObjectFromRequest(Object object, HttpServletRequest req) throws Exception {
        if (object == null || req == null) {
            return;
        }

        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (paramName == null || paramName.trim().isEmpty()) {
                continue;
            }

            String[] path = paramName.split("\\.");
            Object currentObject = object;

            for (int i = 0; i < path.length; i++) {
                String propertyName = path[i];
                String setterName = "set" + capitalize(propertyName);

                if (i == path.length - 1) {
                    Method setter = findSetterMethod(currentObject.getClass(), propertyName);
                    if (setter == null) {
                        continue;
                    }
                    Object value = TypeConverter.convert(req.getParameterValues(paramName), setter.getParameterTypes()[0], setter.getGenericParameterTypes()[0]);
                    setter.invoke(currentObject, value);
                    break;
                }

                Object nestedObject = getPropertyValue(currentObject, propertyName);
                if (nestedObject == null) {
                    Method setter = findSetterMethod(currentObject.getClass(), propertyName);
                    if (setter == null) {
                        break;
                    }
                    Class<?> nestedType = setter.getParameterTypes()[0];
                    if (nestedType.isPrimitive() || nestedType.getName().startsWith("java.")) {
                        break;
                    }
                    nestedObject = nestedType.getDeclaredConstructor().newInstance();
                    setter.invoke(currentObject, nestedObject);
                }
                currentObject = nestedObject;
            }
        }
    }

    private static Method findSetterMethod(Class<?> clazz, String propertyName) {
        String setterName = "set" + capitalize(propertyName);

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(setterName) && method.getParameterCount() == 1) {
                return method;
            }
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equalsIgnoreCase(propertyName)) {
                String fieldSetterName = "set" + capitalize(field.getName());
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.getName().equals(fieldSetterName) && method.getParameterCount() == 1) {
                        return method;
                    }
                }
            }
        }

        return null;
    }

    private static Object getPropertyValue(Object object, String propertyName) throws Exception {
        String getterName = "get" + capitalize(propertyName);
        String booleanGetterName = "is" + capitalize(propertyName);

        for (Method method : object.getClass().getDeclaredMethods()) {
            if ((method.getName().equals(getterName) || method.getName().equals(booleanGetterName)) && method.getParameterCount() == 0) {
                method.setAccessible(true);
                return method.invoke(object);
            }
        }

        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.getName().equalsIgnoreCase(propertyName)) {
                field.setAccessible(true);
                return field.get(object);
            }
        }

        return null;
    }

    private static String capitalize(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    public static void Parameters(Parameter[] parameters , HttpServletRequest req , Object[] objects) throws Exception {
        Map<String , Object> temp = new HashMap<String,Object>();
        Enumeration<String> enumeration = req.getParameterNames();
        while(enumeration.hasMoreElements()) {
            String pName = enumeration.nextElement();
            Object value = req.getParameterValues(pName);
            temp.put(pName, value);
        }
        for(Parameter p : parameters) {
            if(p.isNamePresent() == false){
                throw new IllegalStateException(
                    "Nom de parametre introuvable pour '" + p.getDeclaringExecutable().getName()
);
            }
        }
        int i = 0;
        for(Parameter p : parameters) {
            Class<?> paramType = p.getType();
            if (paramType.isPrimitive() || paramType.equals(String.class)
                    || Number.class.isAssignableFrom(paramType)
                    || paramType.equals(Boolean.class)
                    || paramType.equals(Character.class)
                    || paramType.isArray()
                    || List.class.isAssignableFrom(paramType)) {
                objects[i] = TypeConverter.convert(temp.get(p.getName()), paramType, p.getParameterizedType());
            } else {
                Object object = paramType.getDeclaredConstructor().newInstance();
                bindObjectFromRequest(object, req);
                objects[i] = object;
            }
            i++;
        }   
    }

    public static Object invokeFunction(Mapping map , HttpServletRequest req) throws Exception{
        if(map == null || map.getController() == null || map.getMethod() == null){
            throw new URLException("Aucune methode ne correspond a l'URL demandee");
        }
        Object ret = map.getController().getConstructor().newInstance();
        Method fonction = map.getMethod();
        Parameter[] parameters = fonction.getParameters();
        Object[] objects = new Object[parameters.length];
        Utils.Parameters(parameters , req , objects);
        return fonction.invoke(ret , objects);
    } 
}
