package Tendry.Utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.net.*;
import java.text.Annotation;

import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import Tendry.Annotation.*;
import Tendry.Exception.*;
import Tendry.Utils.*;

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

    // public static  Map<String , Mapping> getAllFunctions(Class clazz) {
    //     Map<String , Mapping> ret = new HashMap<>();
    //     Method[] m = clazz.getDeclaredMethods();
    //     for(Method function : m) {
    //         UrlMapping ann = function.getAnnotation(UrlMapping.class);
    //         if(ann!=null) {
    //             Mapping map = new Mapping();
    //             map.setController(clazz.getSimpleName());
    //             map.setMethod(function.getName());
    //             ret.put(ann.path() , map);
    //         }
    //     }
    //     return ret ;
    // }


    public static Map<UrlMethod, Mapping> UrlMapping(String URL , List<Class<?>> classes , Class<? extends UrlMapping> a) throws URLException {
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
                    if(!matched.containsKey(urlMethod)) {
                        matched.put(urlMethod , map);

                    }else {
                        throw new URLException("Plusieurs fonctions ont cette URL");
                    }
                }
            }
        }
    }
    if (!matched.isEmpty()) {

        return matched;
    }
    return all;
}

    // public  Object invokeFunction(Mapping map) throws Exception{
    //     Object ret = map.getController().
    // } 

}
