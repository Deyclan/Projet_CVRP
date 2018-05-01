import Algorithmes.Genetique;
import Utils.Client;
import Utils.Constants;
import Utils.Solution;
import Utils.SolutionGenerator;

import java.util.*;

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
            solution3 = solutionGenerator.generateSolutionAleatoire(clients, nbVoiture);
            solution4 = solutionGenerator.generateSolutionAleatoire(clients, nbVoiture);
            solution5 = solutionGenerator.generateSolutionAleatoire(clients, nbVoiture);

            System.out.println("Solution1");
            solution1.printTourneesId();
            System.out.println("Solution2");
            solution2.printTourneesId();
            System.out.println("Solution3");
            solution3.printTourneesId();
            System.out.println("Solution4");
            solution4.printTourneesId();
            System.out.println("Solution5");
            solution5.printTourneesId();
            System.out.println();

        }catch (Exception e){
            e.printStackTrace();
        }

        List<Solution> population = new ArrayList<>();
        population.add(solution1);
        population.add(solution2);
        population.add(solution3);
        population.add(solution4);
        population.add(solution5);

        Genetique genetique = new Genetique(population);
        List<Solution> evolved = genetique.lancerGenetique(5,5,3,2,50);
        System.out.println("EVOLVED SOLUTIONS");
        for (Solution solution: evolved) {
            solution.printTourneesId();
        }
        Solution bestEvolue = evolved.stream().sorted(Comparator.comparing(Solution::getCoutTotal)).findFirst().get();
        System.out.println(Constants.ANSI_RED + "Meilleure solution : " + Constants.ANSI_RESET);
        bestEvolue.printTourneesId();
        System.out.println(Constants.ANSI_CYAN + "Cout : " + bestEvolue.getCoutTotal() + Constants.ANSI_RESET);
        System.out.println();
        System.out.println(Constants.ANSI_RED + "Optimisation de la meilleur solution" + Constants.ANSI_RESET);
        bestEvolue.optimiserTournee();
        bestEvolue.printTourneesId();
        System.out.println(Constants.ANSI_CYAN + "Cout : " + bestEvolue.getCoutTotal() + Constants.ANSI_RESET);
        System.out.println();

    }

}
