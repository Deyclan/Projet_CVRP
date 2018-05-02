package Main;

import Algorithmes.Genetique;
import DataHelper.Serializer;
import HIM.Graph;
import Utils.Client;
import Utils.Constants;
import Utils.Solution;
import Utils.SolutionGenerator;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MainGenetiqueGraph extends Application{

    private static int NUMERO_DATA = 1;
    private static int TAILLE_DE_LA_LISTE_DE_DEPART = 10000;
    private static int NOMBRE_DE_GENERATION = 100000;
    private static int NOMBRE_REPRODUCTION = 2000;
    private static int NOMBRE_CROISEMENT = 3000;
    private static int NOMBRE_MUTATION = 5000;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            Scanner scanner = new Scanner(System.in);

            Serializer serializer = new Serializer(NUMERO_DATA);
            SolutionGenerator generator = new SolutionGenerator();
            List<Client> clients = serializer.serialize();
            List<Solution> generationEvoluee = new ArrayList<>();

            System.out.println("Nombre de voitures minimal : " + generator.getNbMinVoiture(clients));
            System.out.println("Entrez le nombre de voitures pour la génération aléatoire : ");
            int nbVoiture = scanner.nextInt();

            double startTime = System.currentTimeMillis();
            List<Solution> solutions = generator.generateListeSolutionAleatoire(clients, nbVoiture, TAILLE_DE_LA_LISTE_DE_DEPART);
            Solution bestDepart = solutions.stream().sorted(Comparator.comparing(Solution::getCoutTotal)).findFirst().get();
            new Graph(bestDepart, "Meilleur solution de départ. Cout : " + bestDepart.calculerCoutTotal());

            System.out.println(Constants.ANSI_RED + "----------- Solutions Aléatoires -----------" + Constants.ANSI_RESET);
            for (Solution solution: solutions) {
                solution.printTourneesId();
                solution.printCoutTotal();
                System.out.println();
            }

            Genetique genetique = new Genetique(solutions);
            generationEvoluee = genetique.lancerGenetique(NOMBRE_DE_GENERATION,NOMBRE_REPRODUCTION,NOMBRE_CROISEMENT,NOMBRE_MUTATION,10*TAILLE_DE_LA_LISTE_DE_DEPART);
            Solution bestEvolue = generationEvoluee.stream().sorted(Comparator.comparing(Solution::getCoutTotal)).findFirst().get();

            double stopTime = System.currentTimeMillis();

            System.out.println(Constants.ANSI_RED + "----------- Solutions Evoluées -----------" + Constants.ANSI_RESET);
            for (Solution solution: generationEvoluee) {
                solution.printTourneesId();
                solution.printCoutTotal();
                System.out.println();
            }

            System.out.println(Constants.ANSI_RED+"Meilleur au Départ : "+ Constants.ANSI_RESET);
            bestDepart.printTourneesId();
            bestDepart.printCoutTotal();
            System.out.println();
            System.out.println(Constants.ANSI_RED+"Meilleur à l'arrivée : "+ Constants.ANSI_RESET);
            bestEvolue.printTourneesId();
            bestEvolue.printCoutTotal();


            new Graph(bestEvolue, "Résultat Genetique. Cout : " + bestEvolue.calculerCoutTotal());

            bestEvolue.optimiserTournee();

            /*
            System.out.println();
            System.out.println(Constants.ANSI_RED+"Meilleur à l'arrivée avec optimisation locale: "+ Constants.ANSI_RESET);
            bestEvolue.printTourneesId();
            bestEvolue.printCoutTotal();

            new Graph(bestEvolue, "Résultat Genetique après optimisation. Cout : " + bestEvolue.calculerCoutTotal());
            */


            double coutDepart = bestDepart.calculerCoutTotal();
            double coutArrivee = bestEvolue.calculerCoutTotal();
            System.out.println();
            System.out.println(Constants.ANSI_RED + "------------------- RECAP -------------------" + Constants.ANSI_RESET);
            System.out.println("Jeux de data no : " + Constants.ANSI_CYAN + NUMERO_DATA + Constants.ANSI_RESET);
            System.out.println("Nombre de voitures choisis : "+ Constants.ANSI_CYAN + nbVoiture + Constants.ANSI_RESET);
            System.out.println("Taille solution aléatoire de départ : " + Constants.ANSI_CYAN + TAILLE_DE_LA_LISTE_DE_DEPART + Constants.ANSI_RESET);
            System.out.println("\t Nombre de solutions reproduites : " + Constants.ANSI_CYAN + NOMBRE_REPRODUCTION + Constants.ANSI_RESET);
            System.out.println("\t Nombre de solutions croisées : " + Constants.ANSI_CYAN + NOMBRE_CROISEMENT + Constants.ANSI_RESET);
            System.out.println("\t Nombre de solutions mutées : " + Constants.ANSI_CYAN + NOMBRE_MUTATION + Constants.ANSI_RESET);
            System.out.println("Nombre de générations : " + Constants.ANSI_CYAN + NOMBRE_DE_GENERATION + Constants.ANSI_RESET);
            System.out.println("Temps d'execution : " + Constants.ANSI_CYAN + (stopTime-startTime) + " ms" + Constants.ANSI_RESET);
            System.out.println("Temps d'execution en secondes : " + Constants.ANSI_CYAN + (stopTime-startTime)/1000 + " s" + Constants.ANSI_RESET);
            System.out.println("Cout meilleur solution départ : " + Constants.ANSI_CYAN + coutDepart + Constants.ANSI_RESET);
            System.out.println("Cout meilleur solution évoluées : " + Constants.ANSI_CYAN + coutArrivee + Constants.ANSI_RESET);
            System.out.println("Gain de distance : " + Constants.ANSI_CYAN + (coutDepart-coutArrivee) + " km " + Constants.ANSI_RESET);
            System.out.println("Ratio Gain de distance : " + Constants.ANSI_CYAN + (100 - (coutArrivee/coutDepart)*100) + " %" + Constants.ANSI_RESET );

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
