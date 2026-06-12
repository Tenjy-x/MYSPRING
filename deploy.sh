#!/bin/bash

# Se placer automatiquement dans le dossier du script
cd "$(dirname "$0")"

NOM_SERVLET="MY_FRAMEWORK"
DOSSIER_CLASSES="build/classes"
LIB_DIR="lib"
PROJET_DIR="/home/tendry/itu/S4/MR_NAINA/TESTMYSPRING/lib"
SERVLET_API_JAR="$LIB_DIR/servlet-api.jar"

# Vérifier que le dossier Tendry existe
if [ ! -d "Tendry" ]; then
    echo "❌ Dossier 'Tendry' introuvable dans $(pwd)"
    exit 1
fi

# Trouver tous les fichiers .java dans Tendry (et sous-dossiers)
JAVA_FILES=$(find Tendry -name "*.java")
if [ -z "$JAVA_FILES" ]; then
    echo "❌ Aucun fichier .java trouvé dans Tendry"
    exit 1
fi

echo "📁 Fichiers à compiler :"
echo "$JAVA_FILES"

# Créer le dossier des classes compilées
mkdir -p $DOSSIER_CLASSES

# Compilation
javac -cp $SERVLET_API_JAR -d $DOSSIER_CLASSES $JAVA_FILES

# Vérifier le succès de la compilation
if [ $? -ne 0 ]; then
    echo "❌ Échec de la compilation"
    exit 1
fi

# Création du JAR (inclut toute l'arborescence des packages depuis build/classes)
jar -cf $NOM_SERVLET.jar -C $DOSSIER_CLASSES/ .

# Copie vers PROJET_DIR
cp $NOM_SERVLET.jar $PROJET_DIR/

# Nettoyage (optionnel)
rm -rf $DOSSIER_CLASSES

echo "✅ $NOM_SERVLET.jar copié dans $PROJET_DIR"