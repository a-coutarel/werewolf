# Projet Java ESIREM
Architecture MVC

***

## I – Les Loups-Garous

Chaque nuit, ils dévorent un Villageois.
Le jour, ils essaient de masquer leur identité nocturne pour échapper à la vindicte populaire.
Ils sont 2 ou 3 si le nombres de joueurs est supérieur pou égal à 12.
(En aucun cas un Loup-Garou ne peut dévorer un Loup-Garou).

## II – Les Villageois

###Tous ceux qui ne sont pas Loups-Garous :
Chaque nuit, l’un d’entre eux est dévoré par les Loups-Garous. Ce joueur est éliminé du jeu.
Les Villageois survivants se réunissent le lendemain matin et essaient de remarquer, chez les autres joueurs, les signes qui trahiraient leur identité nocturne de mangeur d’homme.
Après discussions, ils votent l’exécution d’un suspect qui sera éliminé du jeu.


### Simple Villageois

Il n’a aucune compétence particulière.
Ses seules armes sont la capacité d’analyse des comportements pour identifier les Loups-Garous, et la force de conviction pour empêcher l’exécution de l’innocent qu’il est.


### Voyante

Chaque nuit, elle découvre la vraie personnalité d’un joueur de son choix.
Elle doit aider les autres Villageois, mais rester discrète pour ne pas être démasquée par les Loups-Garous.


### Chasseur

S’il se fait dévorer par les Loups-Garous ou exécuter malencontreusement par les joueurs, le Chasseur doit répliquer avant de rendre l’âme, en éliminant immédiatement n’importe quel autre joueur de son choix.


### Cupidon

En décochant ses célèbres flèches magiques, Cupidon a le pouvoir de rendre 2 personnes amoureuses à jamais.
La première nuit (tour préliminaire), il désigne les 2 joueurs amoureux. Cupidon peut, s’il le veut, se désigner comme l’un des deux Amoureux.
Si l’un des Amoureux est éliminé, l’autre meurt de chagrin immédiatement.
Un Amoureux ne doit jamais voter contre son aimé, ni lui porter aucun préjudice (même pour faire semblant !).
Attention : si l’un des deux Amoureux est un Loup-Garou et l’autre un Villageois, le but de la partie change pour eux.
Pour vivre en paix leur amour et gagner la partie, ils doivent éliminer tous les autres joueurs, Loups-Garous et Villageois, en respectant les règles de jeu.


### Sorcière

Elle sait concocter 2 potions extrêmement puissantes :
une potion de guérison, pour ressusciter le joueur tué par les Loups-Garous, une potion d’empoisonnement, utilisée la nuit pour éliminer un joueur.
La Sorcière doit utiliser chaque potion 1 seule fois dans la partie. Elle peut se servir des ses 2 potions la même nuit.
Le matin suivant l’usage de ce pouvoir, il pourra donc y avoir soit 0 mort, 1 mort ou 2 morts.
La Sorcière peut utiliser les potions à son profit, et donc se guérir elle-même si elle vient d’être attaquée par les Loups-Garous.


### Voleur

La première nuit, le Voleur echange son rôle avec celui du joueur qu'il désignera.
Il jouera désormais ce personnage jusqu’à la fin de la partie.
Le joueur qui s'est fait volé son rôle devient un simple villageois.


### Capitaine

Cette carte est confiée à un des joueurs, en plus de sa carte personnage.
Le Capitaine est élu par vote, à la majorité relative. On ne peut refuser l’honneur d’être Capitaine.
S'il y a égalité dans les votes éliminatoires à la fin d'un tour, c'est au capitaine de trancher et de choisir le joueur a éliminé parmi tous les joueurs ex aequo. Si ce joueur se fait éliminer, dans son dernier souffle il désigne son successeur.

### Garde

Le garde a le pouvoir de protéger une personne durant la nuit. Il désigne un joueur qui sera intouchable, même s'il a déjà été attaqué pendant la nuit, il ne mourra pas le matin. 
Le garde ne peut pas protéger la même personne 2 nuits successives.


## III. Conditions de Victoire

Les Villageois gagnent dès qu’ils réussissent à éliminer le dernier Loup-Garou.
Les Loups-Garous gagnent s’ils éliminent le dernier des Villageois.
Cas particulier : Si les Amoureux sont 1 Loup-Garou + 1 Villageois, ils gagnent dès qu’ils ont éliminé tous les autres.
Il peut aussi y avoir égalité (ex: les deux derniers joueurs en vie sont un loup-garou et le chasseur, le loup tue le chasseur pendant la nuit et au réveil le chasseur tire sur le loup...).

## IV. Déroulement du jeu

Le meneur de jeu n'a qu'à suivre les informations affichées à l'écran pour faire avancer la partie.

Résumé (des étapes en plus ou moins peuvent apparaitre suivant l'avancée de la partie (ex: élection du capitaine, coup de fusil du chasseur...)) :

IV.1. Tour de préparation

- 1 - Le meneur appelle le Voleur.
- 2 - Le meneur appelle Cupidon.

## IV.2. Tour normal

- 1 – Le meneur appelle la Voyante
- 2 – Le meneur appelle les Loups-Garous
- 3 – Le meneur appelle la Sorcière
- 3 – Le meneur appelle le guarde
- 4 – Le meneur réveille tout le village
- 5 – Le village débat des suspects
- 6 – Le village vote
- 7 – Le village s’endort

inspiré de https://www.regledujeu.fr/loup-garou-regle/
