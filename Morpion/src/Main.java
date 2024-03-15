import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Saluer l'utilisateur
        System.out.println("Bonjour et bienvenue dans le jeu du Morpion !");
        //Recevoir le choix du l'utilisateur
        Scanner choixMenu = new Scanner(System.in);
        menu(choixMenu);
    }

    /**
     * fonction menu
     * @param clavier  permet de lire a quel jeu veut jouer l'utilisateur
     */
    //Fonction du MENU DU JEU
    static void menu(Scanner clavier) {

        int saisieMenu;
        for (saisieMenu = 0; saisieMenu != 1 && saisieMenu != 2 && saisieMenu != 3 && saisieMenu!=4; saisieMenu = clavier.nextInt()) {
            System.out.println("MENU DU JEU : ");
            System.out.println("Taper 1 - Regles du Jeu");
            System.out.println("Taper 2 - Jouer seul contre l'ordinateur");
            System.out.println("Taper 3 - Jouer a deux");
            System.out.println("Taper 4 - Quitter le jeu");
        }
        //PENSER A INSERER UN TRY / CATCH AU CAS OU L'USER RENTRE UNE LETTRE ICI
        switch (saisieMenu) {
            case 1:
                choixMenu1(clavier);
                break;
            case 2:
                choixMenu2(clavier);
                break;
            case 3:
                choixMenu3(clavier);
            case 4:
                break;
            default:
                System.out.println("Vous n'avez pas écrit 1, 2, 3 ou 4");
        }
    }

    //APPEL AUX REGLES
    static void choixMenu1(Scanner clavier) {
        // Appeller le doc txt des regles du jeu
        System.out.println("Voici les règles du jeu :");
        try {
            Scanner scannerFichier = new Scanner(new File("E:\\Eva\\Projets\\Java\\Morpion\\resources\\regles.txt"));
            while (scannerFichier.hasNextLine()) {
                System.out.println(scannerFichier.nextLine());
            }
            menu(clavier);
            scannerFichier.close();
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            menu(clavier);
        }
    }

    //JEU CONTRE L'ORDI
    static void choixMenu2(Scanner clavier) {

        //Demander les noms des joueurs
        System.out.print("Quel est votre Nom ? ");
        String j1 = clavier.nextLine();
        boolean finDeJeu = false;
        int scorej1 = 0;
        int scoreia = 0;

        while (!finDeJeu) {
            /*Board de Morpion en 3*3
            - = espace vide
            x = j1
            o = j2
             */
            char[][] board = new char[3][3];

            // Remplir le board de " - "

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = '-';
                }
            }
            // Savoir au tour de qui nous sommes
            boolean tourJoueur1 = true;

            //Savoir si le jeu a pris fin
            boolean finDePartie = false;

            while (!finDePartie) {
                // Dessiner le board
                dessinBoard(board);

                // Savoir quel symbole on utilise
                char symbol = '-';
                if (tourJoueur1) {
                    symbol = 'x';
                } else {
                    symbol = 'o';
                }

                // Dire au tour de quel joueur nous sommes
                if (!tourJoueur1) {
                    System.out.println("C'est au tour de l'IA : o ");
                    tourIA(board);
                } else {
                    System.out.println("C'est au tour de " + j1 + " : x ");
                    // On déclare notre ligne et colonne hors de la boucle while pour pouvoir y accéder
                    int ligne = 0;
                    int colonne = 0;
                    // Continue de demander ligne et colonne tant que ces derniers ne sont pas valides
                    while (true) {
                        // Demander colonne et ligne au joueur
                        // PENSER A INTEGRER UN TRY / CATCH
                        System.out.print("Dans quelle ligne voulez vous jouer ? (0, 1 ou 2) : ");
                        ligne = clavier.nextInt();
                        System.out.print("Dans quelle colonne voulez vous jouer ? (0, 1 ou 2) : ");
                        colonne = clavier.nextInt();
                        // On check si ligne et colonne sont valides
                        if (ligne < 0 | colonne < 0 | ligne > 2 | colonne > 2) {
                            System.out.println("Une des coordonnées est hors du board ! ");
                        }
                        // On check si quelqu'un n'a pas déjà joué à cet endroit
                        else if (board[ligne][colonne] != '-') {
                            System.out.println("Quelqu'un a déjà joué a cet emplacement !");
                        } else { //ligne et colonne sont valides
                            break;
                        }
                    }

                    // On met le symbole du joueur à la position voulue
                    board[ligne][colonne] = symbol;
                }



                // Check si un joueur a gagné
                if (victoire(board) == 'x') {
                    // Joueur 1 a gagné
                    System.out.println(j1 + " remporte la partie !");
                    scorej1 = scorej1 + 1;
                    finDePartie = true;
                } else if (victoire(board) == 'o') {
                    // Joueur 2 a gagné
                    scoreia = scoreia + 1;
                    System.out.println("L'IA remporte la partie !");
                    finDePartie = true;
                } else {
                    // Personne n'a gagné
                    if (egalite(board)) {
                        System.out.println("C'est une égalité !");
                        finDePartie = true;
                    } else {
                        // Le jeu continue et passe le tour du joueur
                        tourJoueur1 = !tourJoueur1;
                    }
                }

            }
            dessinBoard(board);
            // Afficher les scores
            System.out.println("Score de " + j1 + " : " + scorej1);
            System.out.println("Score de l'IA : " + scoreia);
            // Demander à l'utilsateur ce qu'il veut faire après la partie
            System.out.println("MENU DE FIN DE PARTIE :");
            System.out.println("TAPER 1 - Rejouer ");
            System.out.println("TAPER 2 - Retour au menu");
            System.out.println("TAPER 3 - Quitter le jeu");
            Scanner choixUtilisateur= new Scanner(System.in);
            int choixFinPartie = choixUtilisateur.nextByte();

            if (choixFinPartie == 1) {
                finDeJeu = false;
            }
            if (choixFinPartie == 2) {
                menu(clavier);
            }
            if (choixFinPartie == 3) {
                finDeJeu = true;
            }
        }

    }

    static void tourIA(char[][] board) {
        // On déclare notre ligne et colonne hors de la boucle while pour pouvoir y accéder
        int ligne = 0;
        int colonne = 0;
        // Continue de demander ligne et colonne tant que ces derniers ne sont pas valides
        while (true) {
            ligne = (int) (Math.random() * 3);
            colonne = (int) (Math.random() * 3);
            System.out.println("L'IA joue en " + ligne + " " + colonne);
            // On check si ligne et colonne sont valides
            if (ligne < 0 | colonne < 0 | ligne > 2 | colonne > 2) {
                System.out.println("Une des coordonnées est hors du board ! ");
            }
            // On check si quelqu'un n'a pas déjà joué à cet endroit
            if (board[ligne][colonne] == '-') {
                break;
            }
        }

        // On met le symbole de l'IA à la position voulue
        board[ligne][colonne] = 'o';
    }

    /**
     * Jeu à 2 Joueurs qui s'affrontent sur un plateau de 3*3 cases
     */
    static void choixMenu3(Scanner clavier) {

        //Demander les noms des joueurs
        System.out.print("Joueur 1, quel est votre Nom ? ");
        String j1 = clavier.nextLine();
        System.out.print("Joueur 2, quel est votre Nom ? ");
        String j2 = clavier.nextLine();
        boolean finDeJeu = false;
        int scorej1 = 0;
        int scorej2 = 0;

        while (!finDeJeu) {
            /*Board de Morpion en 3*3
            - = espace vide
            x = j1
            o = j2
             */
            char[][] board = new char[3][3];

            // Remplir le board de " - "

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = '-';
                }
            }
            // Savoir au tour de qui nous sommes
            boolean tourJoueur1 = true;

            //Savoir si le jeu a pris fin
            boolean finDePartie = false;

            while (!finDePartie) {
                // Dessiner le board
                dessinBoard(board);

                // Savoir quel symbole on utilise
                char symbol = '-';
                if (tourJoueur1) {
                    symbol = 'x';
                } else {
                    symbol = 'o';
                }
                // Dire au tour de quel joueur nous sommes
                if (tourJoueur1) {
                    System.out.println("C'est au tour de " + j1 + " : x ");
                } else {
                    System.out.println("C'est au tour de " + j2 + " : o ");
                }

                // On déclare notre ligne et colonne hors de la boucle while pour pouvoir y accéder
                int ligne = 0;
                int colonne = 0;
                // Continue de demander ligne et colonne tant que ces derniers ne sont pas valides
                while (true) {
                    // Demander colonne et ligne au joueur
                    // PENSER A INTEGRER UN TRY / CATCH
                    System.out.print("Dans quelle ligne voulez vous jouer ? (0, 1 ou 2) : ");
                    ligne = clavier.nextInt();
                    System.out.print("Dans quelle colonne voulez vous jouer ? (0, 1 ou 2) : ");
                    colonne = clavier.nextInt();
                    // On check si ligne et colonne sont valides
                    if (ligne < 0 | colonne < 0 | ligne > 2 | colonne > 2) {
                        System.out.println("Une des coordonnées est hors du board ! ");
                    }
                    // On check si quelqu'un n'a pas déjà joué à cet endroit
                    else if (board[ligne][colonne] != '-') {
                        System.out.println("Quelqu'un a déjà joué a cet emplacement !");
                    } else { //ligne et colonne sont valides
                        break;
                    }
                }

                // On met le symbole du joueur à la position voulue
                board[ligne][colonne] = symbol;

                // Check si un joueur a gagné
                if (victoire(board) == 'x') {
                    // Joueur 1 a gagné
                    System.out.println(j1 + " remporte la partie !");
                    scorej1 = scorej1 + 1;
                    finDePartie = true;
                } else if (victoire(board) == 'o') {
                    // Joueur 2 a gagné
                    scorej2 = scorej2 + 1;
                    System.out.println(j2 + " remporte la partie !");
                    finDePartie = true;
                } else {
                    // Personne n'a gagné
                    if (egalite(board)) {
                        System.out.println("C'est une égalité !");
                        finDePartie = true;
                    } else {
                        // Le jeu continue et passe le tour du joueur
                        tourJoueur1 = !tourJoueur1;
                    }
                }

            }
            dessinBoard(board);
            // Afficher les scores
            System.out.println("Score de " + j1 + " : " + scorej1);
            System.out.println("Score de " + j2 + " : " + scorej2);
            // Demander à l'utilsateur ce qu'il veut faire après la partie
            System.out.println("MENU DE FIN DE PARTIE :");
            System.out.println("TAPER 1 - Rejouer ");
            System.out.println("TAPER 2 - Retour au menu");
            System.out.println("TAPER 3 - Quitter le jeu");
            Scanner choixUtilisateur= new Scanner(System.in);
            int choixFinPartie = choixUtilisateur.nextByte();

            if (choixFinPartie == 1) {
                finDeJeu = false;
            }
            if (choixFinPartie == 2) {
                menu(clavier);
            }
            if (choixFinPartie == 3) {
                finDeJeu = true;
            }
        }
    }


    static void menuFinPartie(Scanner clavier) {
        int saisieMenu;
        for (saisieMenu = 0; saisieMenu != 1 && saisieMenu != 2 ; saisieMenu = clavier.nextInt()) {
            System.out.println("MENU DE FIN DE PARTIE : ");
            System.out.println("Taper 1 - Relancer une partie");
            System.out.println("Taper 2 - Quitter le jeu");
        }
        //PENSER A INSERER UN TRY / CATCH AU CAS OU L'USER RENTRE UNE LETTRE ICI
        switch (saisieMenu) {
            case 1:
                choixMenu3(clavier);
                break;
            case 2:
                break;
            default:
                System.out.println("Vous n'avez pas écrit 1 ou 2");
        }
    }
    /**
     * Fonction qui va dessiner le board
     * @param board permet de selectionner le nom du board que l'on veut dessiner
     */
    public static void dessinBoard(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
    /**
     * Fonction qui check les différentes conditions de victoires
     * @param board permet de selectionner le board a parcourir
     * @return le caractère utilisé par le joueur gagnant
     */
    public static char victoire(char[][] board) {
        // Condition de victoire en ligne
        for (int i=0; i<3 ; i++) {
            // On check si les 3 caractères de la lignes sont égaux et ne sont pas des '-'
            if ( board [i][0] == board [i][1] && board [i][1] == board [i][2] && board[i][0] != '-'){
                // On renvoie le caractère gagnant
                return board [i][0];
            }
        }
        // Condition de victoire en colonne
        for (int j=0; j<3 ; j++) {
            if (board[0][j] == board[1][j] && board[1][j] == board[2][j] && board [0][j] != '-'){
                return board [0][j];
            }
        }
        // Condition de victoire en diagonale
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-'){
            return board[0][0] ;
        }
        if (board [0][2] == board [1][1] && board[1][1] == board[2][0] && board[0][2] != '-'){
            return board[0][2];
        }
       // Personne ne gagne
        return '-' ;
    }

    /**
     * Fonction qui vérifie si le board est remplit
     * @param board permet de selectionner le board à parcourir
     * @return 'faux' si au moins un des espaces du board est vide, ou 'vrai' si le board est plein
     * ce qui veux dire qu'il y a une égalité
     */
    public static boolean egalite(char[][] board) {
        for (int i =0; i<3 ; i++){
            for (int j=0; j<3 ; j++){
                if (board[i][j] == '-'){
                    return false;
                }
            }
        }
        return true;
    }
}