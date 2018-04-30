import Algorithmes.RecuitSimule;
import DataHelper.Serializer;
import Utils.Client;
import Utils.Solution;
import Utils.SolutionGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestRecuit {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Serializer serializer = new Serializer(1);

        List<Client> clients = serializer.serialize();
        Solution solution1 = null;

        SolutionGenerator solutionGenerator = new SolutionGenerator();
        try {
            System.out.println("Nombre de voitures minimal : " + solutionGenerator.getNbMinVoiture(clients));
            System.out.println("Entrez le nombre de voitures pour la génération aléatoire : ");
            int nbVoiture = scanner.nextInt();
            solution1 = solutionGenerator.generateSolutionAleatoire(clients, nbVoiture);
            System.out.println("Solution1");
            solution1.printTourneesId();
            System.out.println("Cout total : "+solution1.calculerCoutTotal());
        } catch (Exception e) {
            e.printStackTrace();
        }

        RecuitSimule recuitSimule = new RecuitSimule(10, 0.3, solution1, solutionGenerator);
        Solution bestSolution = recuitSimule.lancerRecuit();
        System.out.println("SolutionRecuit");
        bestSolution.printTourneesId();
        System.out.println("Cout total : "+bestSolution.calculerCoutTotal());
    }
}
