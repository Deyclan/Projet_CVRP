import Algorithmes.RecuitSimule;
import Utils.Client;
import Utils.Solution;
import Utils.SolutionGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestRecuit {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Client depot = new Client(0, 2, 2, 0);
        Client client1 = new Client(1, 0, 0, 10);
        Client client2 = new Client(2, 0, 2, 20);
        Client client3 = new Client(3, 0, 4, 30);
        Client client4 = new Client(4, 4, 4, 40);
        Client client5 = new Client(5, 4, 2, 50);
        Client client6 = new Client(6, 4, 0, 60);

        List<Client> clients = new ArrayList<>();
        clients.add(depot);
        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);
        clients.add(client5);
        clients.add(client6);

        Solution solution1 = null;

        SolutionGenerator solutionGenerator = new SolutionGenerator();
        try {
            System.out.println("Nombre de voitures minimal : " + solutionGenerator.getNbMinVoiture(clients));
            System.out.println("Entrez le nombre de voitures pour la génération aléatoire : ");
            int nbVoiture = scanner.nextInt();
            solution1 = solutionGenerator.generateSolutionAleatoire(clients, nbVoiture);
            System.out.println("Solution1");
            solution1.printTourneesId();
            System.out.println("Cout total"+solution1.calculerCoutTotal());
        } catch (Exception e) {
            e.printStackTrace();
        }

        RecuitSimule recuitSimule = new RecuitSimule(10000, 0.02, solution1, solutionGenerator);
        Solution bestSolution = recuitSimule.lancerRecuit();
        System.out.println("SolutionRecuit");
        bestSolution.printTourneesId();
        System.out.println("Cout total"+bestSolution.calculerCoutTotal());
    }
}
