import Utils.Client;
import Utils.Solution;
import Utils.SolutionGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TestGenetique {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Client depot = new Client(0,2,2,0);
        Client client1 = new Client(1,0,0,10);
        Client client2 = new Client(2,0,2,20);
        Client client3 = new Client(3,0,4,30);
        Client client4 = new Client(4,4,4,40);
        Client client5 = new Client(5,4,2,50);
        Client client6 = new Client(6,4,0,60);

        List<Client> clients = new ArrayList<Client>();
        clients.add(depot);
        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);
        clients.add(client5);
        clients.add(client6);

        Solution solution1=null;
        Solution solution2=null;

        SolutionGenerator solutionGenerator = new SolutionGenerator();
        try {
            System.out.println("Nombre de voiture minimal : " + solutionGenerator.getNbMinVoiture(clients));
            System.out.println("Entrez le nombre de voiture pour la génération aléatoire : ");
            int nbVoiture = scanner.nextInt();
            solution1 = solutionGenerator.generateSolutionAleatoire(clients, nbVoiture);
            System.out.println("Solution1");
            solution1.printTourneesId();
            solution2 = solutionGenerator.generateSolutionAleatoire(clients, nbVoiture);
            System.out.println("Solution2");
            solution2.printTourneesId();

        }catch (Exception e){
            e.printStackTrace();
        }

        Solution[] solutionsCroisees = croisement(solution1,solution2);
        System.out.println("Solution1");
        solutionsCroisees[0].printTourneesId();
        System.out.println("Solution2");
        solutionsCroisees[1].printTourneesId();

        System.out.println();
        System.out.println("Réparation Solution1");
        try {
            solution1.repareSolution();
            solution1.printTourneesId();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Solution[] croisement(Solution solution1, Solution solution2){
        Random random = new Random();
        int nbTournee1 = solution1.getTournees().size();
        int nbTournee2 = solution2.getTournees().size();

        // Selection Aléatoire dans la solution1
        int idSelectionTournee1 = random.nextInt(nbTournee1);
        List<Client> selectionTournee1 = solution1.getTournees().get(idSelectionTournee1);

        // Selection Aléatoire dans la solution2
        int idSelectionTournee2 = random.nextInt(nbTournee2);
        List<Client> selectionTournee2 = solution2.getTournees().get(idSelectionTournee2);

        solution1.getTournees().set(idSelectionTournee1, selectionTournee2);
        solution2.getTournees().set(idSelectionTournee2, selectionTournee1);

        return new Solution[]{solution1, solution2};

    }
}
