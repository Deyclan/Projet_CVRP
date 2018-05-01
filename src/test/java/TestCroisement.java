import Utils.Client;
import Utils.Solution;
import Utils.SolutionGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TestCroisement {

    private static Random random = new Random();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Client depot = new Client(0,2,2,0);
        Client client1 = new Client(1,0,0,1);
        Client client2 = new Client(2,0,2,2);
        Client client3 = new Client(3,0,4,3);
        Client client4 = new Client(4,4,4,4);
        Client client5 = new Client(5,4,2,5);
        Client client6 = new Client(6,4,0,6);

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
            solution2 = solutionGenerator.generateSolutionAleatoire(clients, nbVoiture);
            System.out.println("solution1");
            solution1.printTourneesId();
            System.out.println("solution2");
            solution2.printTourneesId();

        }catch (Exception e){
            e.printStackTrace();
        }

        List<Solution> population = new ArrayList<>();
        population.add(solution1);
        population.add(solution2);

        System.out.println("Next Gen");
        List<Solution> nextGen = selectionCroisements(population, 2,100);
        for (Solution solution: nextGen){
            solution.printTourneesId();
        }
    }

    public static List<Solution> selectionCroisements(List<Solution> population, int nbElement, int nbEtapeMax) {
        List<Solution> nextGenPop = new ArrayList<>();
        int popSize = population.size();
        int count = 0;
        while (nextGenPop.size() < nbElement && count < nbEtapeMax) {
            Solution[] croisement = croisement(population.get(random.nextInt(popSize)), population.get(random.nextInt(popSize)));
            try {
                croisement[0].repareSolution();
                croisement[1].repareSolution();
                nextGenPop.add(croisement[0]);
                nextGenPop.add(croisement[1]);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return checkLength(nextGenPop, nbElement, population);
    }
    private static List<Solution> checkLength(List<Solution> nextGenPop, int nextGenSize, List<Solution> population){
        if (nextGenPop.size() > nextGenSize){
            return nextGenPop.subList(0, nextGenSize);
        }
        else if (nextGenPop.size() < nextGenSize){
            nextGenPop.addAll(population.subList(0, nextGenSize - nextGenPop.size()));
            return nextGenPop;
        }
        return nextGenPop;
    }
    private static Solution[] croisement(Solution solution1, Solution solution2){
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
