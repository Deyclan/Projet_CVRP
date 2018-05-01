import Utils.Client;
import Utils.Solution;
import Utils.SolutionGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TestMutation {

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
        List<Solution> nextGen = selectionMutation(population, 10,100);
        for (Solution solution: nextGen){
            solution.printTourneesId();
        }
    }

    public static List<Solution> selectionMutation(List<Solution> population, int nbElement, int nbEtapeMax){
        List<Solution> nextGenPop = new ArrayList<>();
        int count = 0;
        while (nextGenPop.size() < nbElement && count < nbEtapeMax){
            Solution solution = population.get(random.nextInt(population.size()));
            Solution mutante = mutation(solution);
            if (mutante != null){
                nextGenPop.add(mutante);
            }
            count++;
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
    private static Solution mutation(Solution solution){
        int nbTournee = solution.getTournees().size();
        int randTournee1 = random.nextInt(nbTournee);
        int randTournee2 = random.nextInt(nbTournee);
        List<Client> tournee1 = solution.getTournees().get(randTournee1);
        List<Client> tournee2 = solution.getTournees().get(randTournee2);

        int sizeTournee1 = tournee1.size();
        int sizeTournee2 = tournee2.size();

        if (sizeTournee1 <= 2){
            randTournee1 = (randTournee1+1)%nbTournee;
            tournee1 = solution.getTournees().get(randTournee1);
            sizeTournee1 = tournee1.size();
        }
        if (sizeTournee2 <= 2){
            randTournee2 = (randTournee2+1)%nbTournee;
            tournee2 = solution.getTournees().get(randTournee1);
            sizeTournee2 = tournee2.size();
        }

        int randClient1 = random.nextInt(sizeTournee1-2)+1;
        int randClient2 = random.nextInt(sizeTournee2-2)+1;
        Client client1 = tournee1.get(randClient1);
        Client client2 = tournee2.get(randClient2);

        tournee1.set(randClient1, client2);
        tournee2.set(randClient2, client1);

        solution.getTournees().set(randTournee1, tournee1);
        solution.getTournees().set(randTournee2, tournee2);

        if (solution.isSolutionValide()){
            return solution;
        }
        else return null;
    }

}
