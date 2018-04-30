import Utils.Client;
import Utils.RandomCollection;
import Utils.Solution;
import Utils.SolutionGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestSelectionAleatoire {
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
        Solution solution3=null;
        Solution solution4=null;
        Solution solution5=null;

        SolutionGenerator solutionGenerator = new SolutionGenerator();
        try {
            System.out.println("Nombre de voiture minimal : " + solutionGenerator.getNbMinVoiture(clients));
            System.out.println("Entrez le nombre de voiture pour la génération aléatoire : ");
            int nbVoiture = scanner.nextInt();
            solution1 = solutionGenerator.generateSolutionAleatoire(clients, nbVoiture);
            solution2 = solutionGenerator.generateSolutionAleatoire(clients, nbVoiture);
            //solution3 = solutionGenerator.generateSolutionAleatoire(clients, nbVoiture);
            //solution4 = solutionGenerator.generateSolutionAleatoire(clients, nbVoiture);
            //solution5 = solutionGenerator.generateSolutionAleatoire(clients, nbVoiture);

        }catch (Exception e){
            e.printStackTrace();
        }

        List<Solution> population = new ArrayList<>();
        population.add(solution1);
        population.add(solution2);
        //population.add(solution3);
        //population.add(solution4);
        //population.add(solution5);

        List<Solution> populationReproduite = selectionAleatoire(population, 10);
        System.out.println();
        System.out.println("Solutions Reproduites");
        for (Solution solution : populationReproduite){
            solution.printTourneesId();
        }
    }


    public static List<Solution> selectionAleatoire(List<Solution> population, int nbElement){
        int distanceTotale = population.stream().mapToInt(s -> (int)s.getCoutTotal()).sum();
        List<Solution> nextGenPop = new ArrayList<>();

        // On crée une collection correspondant à la répartition des solutons avec leur poids
        RandomCollection<Solution> weightedCollection = new RandomCollection<>();
        for (Solution solution:population) {
            weightedCollection.add(((solution.getCoutTotal())/distanceTotale)*100, solution);
            solution.printTourneesId();
            System.out.println("p = " + ((solution.getCoutTotal())/distanceTotale)*100);
            System.out.println();
        }
        // On ajoute a la prochaine génération les éléments séléctionnés dans la liste
        for (int i=0 ; i< nbElement ; i++){
            nextGenPop.add(weightedCollection.next());
        }
        return nextGenPop;
    }
}
