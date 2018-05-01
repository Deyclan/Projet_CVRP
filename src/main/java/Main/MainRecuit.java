package Main;

import Algorithmes.RecuitSimule;
import DataHelper.Serializer;
import Utils.Client;
import Utils.Constants;
import Utils.Solution;
import Utils.SolutionGenerator;

import java.util.List;
import java.util.Scanner;

public class MainRecuit {

    private static int NUMERO_FICHIER_DATA = 1;
    private static int TEMPERATURE_INITIALE_RECUIT = 100;
    private static double TEMPS_REFROIDISSEMENT = 0.2;
    private static int NOMBRE_DE_REFROIDISSEMENT = 100000;
    private static int NOMBRE_DE_TIRAGE_ALEATOIRE = 2000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Serializer serializer = new Serializer(NUMERO_FICHIER_DATA);

        List<Client> clients = serializer.serialize();
        Solution solution1 = null;

        SolutionGenerator solutionGenerator = new SolutionGenerator();
        try {
            System.out.println("Nombre de voitures minimal : " + solutionGenerator.getNbMinVoiture(clients));
            System.out.println("Entrez le nombre de voitures pour la génération aléatoire : ");
            int nbVoiture = scanner.nextInt();
            solution1 = solutionGenerator.generateSolutionAleatoire(clients, nbVoiture);
            System.out.println(Constants.ANSI_RED + "Solution1"+ Constants.ANSI_RESET);
            solution1.printTourneesId();
            System.out.println(Constants.ANSI_CYAN + "Cout total : "+solution1.calculerCoutTotal()+ Constants.ANSI_RESET + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        RecuitSimule recuitSimule = new RecuitSimule(TEMPERATURE_INITIALE_RECUIT, TEMPS_REFROIDISSEMENT, solution1, solutionGenerator);
        Solution bestSolution = recuitSimule.lancerRecuit(NOMBRE_DE_REFROIDISSEMENT, NOMBRE_DE_TIRAGE_ALEATOIRE);
        System.out.println(Constants.ANSI_RED + "SolutionRecuit" + Constants.ANSI_RESET);
        bestSolution.printTourneesId();
        System.out.println(Constants.ANSI_CYAN + "Cout total : "+bestSolution.calculerCoutTotal()+Constants.ANSI_RESET);
    }

}
