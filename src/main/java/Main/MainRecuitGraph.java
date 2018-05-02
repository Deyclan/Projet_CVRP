package Main;

import Algorithmes.RecuitSimule;
import DataHelper.Serializer;
import HIM.Graph;
import Utils.Client;
import Utils.Constants;
import Utils.Solution;
import Utils.SolutionGenerator;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;
import java.util.Scanner;

public class MainRecuitGraph extends Application {

    private static int NUMERO_FICHIER_DATA = 1;
    private static int TEMPERATURE_INITIALE_RECUIT = 100;
    private static double TEMPS_REFROIDISSEMENT = 0.01;
    private static int NOMBRE_DE_REFROIDISSEMENT = 1000;
    private static int NOMBRE_DE_TIRAGE_ALEATOIRE = 1000;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scanner scanner = new Scanner(System.in);
        Serializer serializer = new Serializer(NUMERO_FICHIER_DATA);

        List<Client> clients = serializer.serialize();
        Solution solution1 = null;

        SolutionGenerator solutionGenerator = new SolutionGenerator();
        try {
            System.out.println();
            System.out.println("Nombre de voitures minimal : " + solutionGenerator.getNbMinVoiture(clients));
            System.out.println("Entrez le nombre de voitures pour la génération aléatoire : ");
            int nbVoiture = scanner.nextInt();

            double startTime = System.currentTimeMillis();
            solution1 = solutionGenerator.generateSolutionAleatoire(clients, nbVoiture);
            double coutDepart = solution1.calculerCoutTotal();
            System.out.println(Constants.ANSI_RED + "Solution1"+ Constants.ANSI_RESET);
            solution1.printTourneesId();
            System.out.println(Constants.ANSI_CYAN + "Cout total : "+ coutDepart+ Constants.ANSI_RESET + "\r\n");

            new Graph(solution1, "Solution aléatoire de départ. Cout : " + coutDepart);

            RecuitSimule recuitSimule = new RecuitSimule(TEMPERATURE_INITIALE_RECUIT, TEMPS_REFROIDISSEMENT, solution1, solutionGenerator);
            Solution bestSolution = recuitSimule.lancerRecuit(NOMBRE_DE_REFROIDISSEMENT, NOMBRE_DE_TIRAGE_ALEATOIRE);
            double stopTime = System.currentTimeMillis();
            System.out.println(Constants.ANSI_RED + "SolutionRecuit" + Constants.ANSI_RESET);
            bestSolution.printTourneesId();
            System.out.println(Constants.ANSI_CYAN + "Cout total : "+bestSolution.calculerCoutTotal()+Constants.ANSI_RESET);

            double coutArrivee = bestSolution.calculerCoutTotal();

            new Graph(bestSolution, "Résultat Recuit. Cout : " + coutArrivee );



            System.out.println();
            System.out.println(Constants.ANSI_RED + "------------------- RECAP -------------------" + Constants.ANSI_RESET);
            System.out.println("Jeux de data no : " + Constants.ANSI_CYAN + NUMERO_FICHIER_DATA + Constants.ANSI_RESET);
            System.out.println("Nombre de voitures choisis : "+ Constants.ANSI_CYAN + nbVoiture + Constants.ANSI_RESET);
            System.out.println("Température initiale : " + Constants.ANSI_CYAN + TEMPERATURE_INITIALE_RECUIT + Constants.ANSI_RESET);
            System.out.println("Temps refroidissement : " + Constants.ANSI_CYAN + TEMPS_REFROIDISSEMENT + Constants.ANSI_RESET);
            System.out.println("Nombre de refroidissement : " + Constants.ANSI_CYAN + NOMBRE_DE_REFROIDISSEMENT + Constants.ANSI_RESET);
            System.out.println("Nombre de tirages aléatoires " + Constants.ANSI_CYAN + NOMBRE_DE_TIRAGE_ALEATOIRE + Constants.ANSI_RESET);
            System.out.println("Temps d'execution : " + Constants.ANSI_CYAN + (stopTime-startTime) + " ms" + Constants.ANSI_RESET);
            System.out.println("Temps d'execution en secondes : " + Constants.ANSI_CYAN + (stopTime-startTime)/1000 + " s" + Constants.ANSI_RESET);
            System.out.println("Cout solution départ : " + Constants.ANSI_CYAN + coutDepart + Constants.ANSI_RESET);
            System.out.println("Cout solution finale : " + Constants.ANSI_CYAN + coutArrivee + Constants.ANSI_RESET);
            System.out.println("Gain de distance : " + Constants.ANSI_CYAN + (coutDepart-coutArrivee) + " km " + Constants.ANSI_RESET);
            System.out.println("Ratio Gain de distance : " + Constants.ANSI_CYAN + (100 - (coutArrivee/coutDepart)*100) + " %" + Constants.ANSI_RESET );


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
