package Main;

import Algorithmes.Genetique;
import Algorithmes.RecuitSimule;
import DataHelper.Serializer;
import HIM.Graph;
import Utils.Client;
import Utils.Solution;
import Utils.SolutionGenerator;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.*;

public class MainConsoleSansCouleurs extends Application {

    private static int NUMERO_FICHIER_DATA = 1;

    // Genetique
    private static int TAILLE_DE_LA_LISTE_DE_DEPART = 100;
    private static int NOMBRE_DE_GENERATION = 10000;
    private static int NOMBRE_REPRODUCTION = 20;
    private static int NOMBRE_CROISEMENT = 40;
    private static int NOMBRE_MUTATION = 40;

    // Recuit
    private static int TEMPERATURE_INITIALE_RECUIT = 100;
    private static double TEMPS_REFROIDISSEMENT = 0.01;
    private static int NOMBRE_DE_REFROIDISSEMENT = 100000;
    private static int NOMBRE_DE_TIRAGE_ALEATOIRE = 1000;

    public static int COLORS = 0;

    Scanner scanner = new Scanner(System.in);
    Serializer serializer;
    List<Client> clients;

    @Override
    public void start(Stage primaryStage) throws Exception {

        COLORS = 0;

        System.out.println("Application Launched" );
        System.out.println("Veuillez entrer le numéro du jeu de données à utiliser (entre 1 et 5 inclus): ");
        NUMERO_FICHIER_DATA = scanner.nextInt();
        serializer = new Serializer(NUMERO_FICHIER_DATA);
        clients = serializer.serialize();

        int algo = -1;
        System.out.println("Quel algorithme voulez vous utiliser ? ( 0 = Recuit, 1 = Génétique) ");
        algo = scanner.nextInt();
        while (algo < 0 || algo > 1) {
            System.out.println("Numéro invalide.");
            System.out.println("Quel algorithme voulez vous utiliser ? ( 0 = Recuit, 1 = Génétique) ");
            algo = scanner.nextInt();
        }

        SolutionGenerator solutionGenerator = new SolutionGenerator();
        System.out.println();
        System.out.println("Nombre de voitures minimal : " + solutionGenerator.getNbMinVoiture(clients) );
        System.out.println("Il est possible que le nombre de voiture minimum ne soit pas suffisant pour certains jeux de données " +
                "( A cause de l'aléatoire lors de la génération des solutions )." +
                " Si c'est le cas, entrez un plus grand nombre de voiture.");
        System.out.println("Entrez le nombre de voitures pour la génération aléatoire : ");
        int nbVoiture = scanner.nextInt();


        if (algo == 0){
            Solution solution1 = null;
            try {
                System.out.println("Entrez une température initiale (Entier positif) :");
                TEMPERATURE_INITIALE_RECUIT = scanner.nextInt();
                System.out.println("Entrez un temps de refroidissement (Compris entre 0 et 1 exclus, exemple : 0.01 ) :");
                scanner.useLocale(Locale.US);
                TEMPS_REFROIDISSEMENT = scanner.nextDouble();
                System.out.println("Entrez un nombre de refroidissement (nombre d'étapes N1, entier positif) :");
                NOMBRE_DE_REFROIDISSEMENT = scanner.nextInt();
                System.out.println("Entrez un nombre de tirage aléatoire pour chaque refroidissement (Entier positif): ");
                NOMBRE_DE_TIRAGE_ALEATOIRE = scanner.nextInt();

                System.out.println("Lancement du Recuit" );

                double startTime = System.currentTimeMillis();
                solution1 = solutionGenerator.generateSolutionAleatoire(clients, nbVoiture);
                double coutDepart = solution1.calculerCoutTotal();
                System.out.println("Solution initiale générée");
                solution1.printTourneesId();
                System.out.println("Cout total : "+ coutDepart + "\r\n");

                RecuitSimule recuitSimule = new RecuitSimule(TEMPERATURE_INITIALE_RECUIT, TEMPS_REFROIDISSEMENT, solution1, solutionGenerator);
                Solution bestSolution = recuitSimule.lancerRecuit(NOMBRE_DE_REFROIDISSEMENT, NOMBRE_DE_TIRAGE_ALEATOIRE);
                double stopTime = System.currentTimeMillis();
                System.out.println("Solution finale recuit" );
                bestSolution.printTourneesId();
                System.out.println("Cout total : "+bestSolution.calculerCoutTotal());

                double coutArrivee = bestSolution.calculerCoutTotal();

                new Graph(solution1, "Solution aléatoire de départ. Cout : " + coutDepart);
                new Graph(bestSolution, "Résultat Recuit. Cout : " + coutArrivee );


                System.out.println();
                System.out.println("------------------- RECAP -------------------" );
                System.out.println("Jeux de data no : " + NUMERO_FICHIER_DATA );
                System.out.println("Nombre de voitures choisis : "+ nbVoiture );
                System.out.println("Température initiale : " + TEMPERATURE_INITIALE_RECUIT );
                System.out.println("Temps refroidissement : " + TEMPS_REFROIDISSEMENT );
                System.out.println("Nombre de refroidissement : " + NOMBRE_DE_REFROIDISSEMENT );
                System.out.println("Nombre de tirages aléatoires " + NOMBRE_DE_TIRAGE_ALEATOIRE );
                System.out.println("Temps d'execution : " + (stopTime-startTime) + " ms" );
                System.out.println("Temps d'execution en secondes : " + (stopTime-startTime)/1000 + " s" );
                System.out.println("Cout solution départ : " + coutDepart + "km" );
                System.out.println("Cout solution finale : " + coutArrivee + "km" );
                System.out.println("Gain de distance : " + (coutDepart-coutArrivee) + " km " );
                System.out.println("Ratio Gain de distance : " + (100 - (coutArrivee/coutDepart)*100) + " %"  );


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            List<Solution> generationEvoluee = new ArrayList<>();

            System.out.println("Entrez le nombre de génération voulu (Entier positif)");
            NOMBRE_DE_GENERATION = scanner.nextInt();
            System.out.println("Entrez la taille de la population de départ (liste de solution générées aléatoirement) (Entier Positif) :");
            TAILLE_DE_LA_LISTE_DE_DEPART = scanner.nextInt();
            System.out.println("Entrez le nombre de solutions à reproduire à chaque génération (Entier Positif) :");
            NOMBRE_REPRODUCTION = scanner.nextInt();
            System.out.println("Entrez le nombre de solutions à croiser à chaque génération (Entier Positif) :");
            NOMBRE_CROISEMENT = scanner.nextInt();
            System.out.println("Entrez le nombre de solutions à muter à chaque génération (Entier Positif) :");
            NOMBRE_MUTATION = scanner.nextInt();

            System.out.println( "Lancement de l'Algo Gen" );

            double startTime = System.currentTimeMillis();
            List<Solution> solutions = solutionGenerator.generateListeSolutionAleatoire(clients, nbVoiture, TAILLE_DE_LA_LISTE_DE_DEPART);
            Solution bestDepart = solutions.stream().sorted(Comparator.comparing(Solution::getCoutTotal)).findFirst().get();

            System.out.println( "----------- Solutions Aléatoires -----------" );
            for (Solution solution : solutions) {
                solution.printTourneesId();
                solution.printCoutTotal();
                System.out.println();
            }


            Genetique genetique = new Genetique(solutions);
            generationEvoluee = genetique.lancerGenetique(NOMBRE_DE_GENERATION, NOMBRE_REPRODUCTION, NOMBRE_CROISEMENT, NOMBRE_MUTATION, 10 * TAILLE_DE_LA_LISTE_DE_DEPART);
            Solution bestEvolue = generationEvoluee.stream().sorted(Comparator.comparing(Solution::getCoutTotal)).findFirst().get();

            double stopTime = System.currentTimeMillis();

            System.out.println("----------- Solutions Evoluées -----------" );
            for (Solution solution : generationEvoluee) {
                solution.printTourneesId();
                solution.printCoutTotal();
                System.out.println();
            }

            System.out.println("Meilleur solution de la population au Départ : " );
            bestDepart.printTourneesId();
            bestDepart.printCoutTotal();
            System.out.println();
            System.out.println("Meilleur solution de la population à l'arrivée : " );
            bestEvolue.printTourneesId();
            bestEvolue.printCoutTotal();

            new Graph(bestDepart, "Meilleur solution de départ. Cout : " + bestDepart.calculerCoutTotal());
            new Graph(bestEvolue, "Résultat Genetique. Cout : " + bestEvolue.calculerCoutTotal());


            double coutDepart = bestDepart.calculerCoutTotal();
            double coutArrivee = bestEvolue.calculerCoutTotal();
            System.out.println();
            System.out.println( "------------------- RECAP -------------------" );
            System.out.println("Jeux de data no : " + NUMERO_FICHIER_DATA );
            System.out.println("Nombre de voitures choisis : " + nbVoiture );
            System.out.println("Taille solution aléatoire de départ : " + TAILLE_DE_LA_LISTE_DE_DEPART );
            System.out.println("\t Nombre de solutions reproduites : " + NOMBRE_REPRODUCTION );
            System.out.println("\t Nombre de solutions croisées : " + NOMBRE_CROISEMENT );
            System.out.println("\t Nombre de solutions mutées : " + NOMBRE_MUTATION );
            System.out.println("Nombre de générations : " + NOMBRE_DE_GENERATION );
            System.out.println("Temps d'execution : " + (stopTime - startTime) + " ms" );
            System.out.println("Temps d'execution en secondes : " + (stopTime - startTime) / 1000 + " s" );
            System.out.println("Cout meilleur solution départ : " + coutDepart + "km" );
            System.out.println("Cout meilleur solution évoluées : " + coutArrivee + "km" );
            System.out.println("Gain de distance : " + (coutDepart - coutArrivee) + " km " );
            System.out.println("Ratio Gain de distance : " + (100 - (coutArrivee / coutDepart) * 100) + " %" );
        }

    }



}
