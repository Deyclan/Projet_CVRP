import Utils.Client;
import Utils.Solution;
import Utils.SolutionGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestSolutionGenerator {

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

        SolutionGenerator solutionGenerator = new SolutionGenerator();
        try {
            System.out.println("Nombre de voiture minimal : " + solutionGenerator.getNbMinVoiture(clients));
            System.out.println("Entrez le nombre de voiture pour la génération aléatoire : ");
            int nbVoiture = scanner.nextInt();
            Solution solution = solutionGenerator.generateSolutionAleatoire(clients, nbVoiture);
            solution.printTournees();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
