# Projet_qualite_dev — TD3

Présentation
------------
Simulation Java inspirée de l’univers gaulois. Le projet modélise un théâtre d’envahissement composé de lieux, de personnages (Gaulois, Romains, créatures fantastiques), d’aliments et d’une potion magique. L’objectif est de simuler les interactions, les combats et l’évolution temporelle tout en offrant un contrôle tactique via des chefs de clan.

Fonctionnalités
---------------
- Combats entre personnages (force / endurance / santé)
- Gestion de la nourriture, de la fraîcheur des aliments et des effets négatifs (poisson avarié, consommation répétée de végétaux, etc.)
- Fabrication et consommation de potion magique avec effets variés et risques
- Gestion des chefs de clan et actions sur les lieux (soin, nourriture, création et transfert de personnages)
- Simulation tour par tour avec boucle temporelle (résolution des combats, renouvellement/détérioration des aliments, états aléatoires)
- Comportements et fonctionnalités spécifiques par type de personnage (travail, combat, direction, confection de potion)

Exécution
---------
Lancer la classe principale : src/main/Main.java  
(Importer le projet dans votre IDE Java ou compiler avec votre système de build habituel.)

Interface utilisateur
---------------------
Interface en ligne de commande (terminal), mode tour par tour. Toutes les instructions et options sont affichées pendant l’exécution pour guider l’utilisateur.

Tests
-----
Tests unitaires réalisés avec JUnit.

Crédits
-------
Basé sur le sujet : "TD3 : Java, cas pratique – Qualité de développement" (Mickaël Martin Nevot).  
Contributeurs : FABRE Alexis, GHEUX Théo, UYSUN Ali.