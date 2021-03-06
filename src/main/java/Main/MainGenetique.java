package Main;

import Algorithmes.Genetique;
import DataHelper.Serializer;
import HIM.Graph;
import Utils.Client;
import Utils.Constants;
import Utils.Solution;
import Utils.SolutionGenerator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MainGenetique {

    private static int NUMERO_DATA = 1;
    private static int TAILLE_DE_LA_LISTE_DE_DEPART = 100;
    private static int NOMBRE_DE_GENERATION = 100000;
    private static int NOMBRE_REPRODUCTION = 60;
    private static int NOMBRE_CROISEMENT = 20;
    private static int NOMBRE_MUTATION = 20;

    public static void main(String[] args) {
        try{
            Scanner scanner = new Scanner(System.in);

            Serializer serializer = new Serializer(NUMERO_DATA);
            SolutionGenerator generator = new SolutionGenerator();
            List<Client> clients = serializer.serialize();
            List<Solution> generationEvoluee = new ArrayList<>();

            System.out.println("Nombre de voitures minimal : " + generator.getNbMinVoiture(clients));
            System.out.println("Entrez le nombre de voitures pour la génération aléatoire : ");
            int nbVoiture = scanner.nextInt();

            List<Solution> solutions = generator.generateListeSolutionAleatoire(clients, nbVoiture, TAILLE_DE_LA_LISTE_DE_DEPART);
            Solution bestDepart = solutions.stream().sorted(Comparator.comparing(Solution::getCoutTotal)).findFirst().get();


            System.out.println(Constants.ANSI_RED + "----------- Solutions Aléatoires -----------" + Constants.ANSI_RESET);
            for (Solution solution: solutions) {
                solution.printTourneesId();
                solution.printCoutTotal();
                System.out.println();
            }

            Genetique genetique = new Genetique(solutions);
            generationEvoluee = genetique.lancerGenetique(NOMBRE_DE_GENERATION,NOMBRE_REPRODUCTION,NOMBRE_CROISEMENT,NOMBRE_MUTATION,10*TAILLE_DE_LA_LISTE_DE_DEPART);
            Solution bestEvolue = generationEvoluee.stream().sorted(Comparator.comparing(Solution::getCoutTotal)).findFirst().get();

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


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
