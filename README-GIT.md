# Regime-Alimentaire
Projet trinome S4 (SI) 


### TO DO GIT
# Phase 1 : La Mise en Place
## 1. Tout le monde doit récupérer le projet sur son PC
git clone https://github.com/ton-compte/Regime-Alimentaire.git
cd Regime-Alimentaire

## 2. Créer la branche dev en local et basculer dessus
git checkout -b dev

## 3. Pousser cette branche sur GitHub pour que les autres la voient
git push origin dev

# Phase 2 : Le Flux de Travail Quotidien (Chaque membre)
1. Mettre son repo local a jour
git checkout dev
git pull origin dev

2. Creer une branche de fonctionnalite
### On part de dev, on crée une branche qui part de ce point
git checkout -b feature/connexion
ex: feature/nom-fonction, fix/bug-typo

3. Commit reguliers
### Sauvegarde du travail localement
git add .
git commit -m "Ajout du formulaire de connexion"

### Coder la suite...
git add .
git commit -m "Vérification du mot de passe"

4. Envoi la branche sur GitHub
git push origin feature/connexion

5. Créer la Pull Request (PR)
- Allez sur la page GitHub du projet.
- Une bannière jaune devrait apparaître : "feature/connexion had recent pushes".
- Cliquez sur "Compare & pull request" .
- Base : dev (là où on veut que le code aille).
- Compare : feature/connexion.
- Cliquez sur "Create pull request".
- À ce stade, les autres membres peuvent commenter votre code .

6. Merger la PR (et la supprimer)
- SUPPRIMEZ LA BRANCHE en cliquant sur le bouton "Delete branch" qui apparaît juste après .

# Phase 3 :  Le Nettoyage Local (Après fusion)
 1. On retourne sur la branche principale de travail (dev)
git checkout dev

 2. On récupère les changements qui ont été mergés (y compris ta feature)
git pull origin dev

 3. On supprime la branche localement (comme on l'a fait sur GitHub)
git branch -d feature/connexion
- (Le -d ne marche que si la branche a bien été mergée, sécurité intégrée)

 4. (Optionnel mais propre) On dit à Git d'oublier le lien avec la branche distante
git remote prune origin


# Phase 4 : La Finale (Merge de dev vers main)

Rappel
![alt text](image.png)


# Comment abandonner les modifications et forcer le changement de branche
### Étape 1 : Annuler les modifications des fichiers suivis
```bash
# Voir ce qui a été modifié
git status

# Pour annuler TOUS les fichiers modifiés suivis
git restore .

# Ou fichier par fichier
git restore writable/logs/log-2026-05-09.log
```

### Étape 2 : Supprimer les fichiers non suivis
```bash
# Voir quels fichiers non suivis vont être supprimés (simulation)
git clean -fdn

# Si ça te convient, supprimer vraiment
git clean -fd

# Pour supprimer aussi les fichiers ignorés (.gitignore)
git clean -fdx  # Attention plus agressif !
```

### Étape 3 : Changer de branche
```bash
git checkout ta-branche
```