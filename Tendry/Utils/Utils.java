package Tendry.Utils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.net.*;
import java.text.Annotation;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import Tendry.Annotation.AnnotationController;
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

    public static List<String> getAnnotationClasses(List<Class<?>> classes , Class<? extends AnnotationController> a ){
        List<String> ListClasses = new ArrayList<>();
        for (Class<?> clazz : classes) {
            if(clazz.isAnnotationPresent(a)){
                ListClasses.add(clazz.getSimpleName());
            }
        }
        return ListClasses;
    }
}
