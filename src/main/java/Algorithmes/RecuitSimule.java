package Algorithmes;

import Utils.Client;
import Utils.Constants;
import Utils.Solution;
import Utils.SolutionGenerator;

import java.util.List;
import java.util.Random;

public class RecuitSimule {

    private double tempActuelle;
    private double tempRefroidissement;
    private Solution solutionActuelle;
    private SolutionGenerator solutionGenerator;
    private Random random = new Random();
    private int nombreVoiture = 0;

    private static int NOMBRE_BOUCLE_MAX = 1000000;

    public RecuitSimule(double tempInitiale, double tempRefroidissement, Solution solutionActuelle, SolutionGenerator solutionGenerator) {
        this.tempActuelle = tempInitiale;
        this.tempRefroidissement = tempRefroidissement;
        this.solutionActuelle = solutionActuelle;
        this.solutionGenerator = solutionGenerator;
        this.nombreVoiture = solutionActuelle.getNombreVoiture();
    }


    private int choisirRandTournee(Solution solution) {
        return random.nextInt(solution.getTournees().size());
    }

    private int choisirRandClient(Solution solution, int numTournee) {
        return random.nextInt(solution.getTournees().get(numTournee).size());
    }

    public Solution lancerRecuit(int n1, int n2)  {
        Solution meilleureSolution = solutionActuelle.deepCopy();
        for (int k = 0; k < n1; k++) {
            for (int l = 1; l < n2; l++) {
                // Choix du voisin
                Solution tmpSolutionActuelle = solutionActuelle.deepCopy();
                Solution nouvelleSolution = null;
                int count = 0;
                while (nouvelleSolution == null && count < NOMBRE_BOUCLE_MAX * nombreVoiture){
                    nouvelleSolution = mutation(tmpSolutionActuelle);
                    count++;
                }
                if (count == 1000 || nouvelleSolution == null){
                    System.out.println(Constants.ANSI_RED + "Impossible de trouver un voisinage de la solution actuelle." +Constants.ANSI_RESET);
                    return meilleureSolution;
                }

                // Optimisation locale
                //Client depot = nouvelleSolution.getTournees().get(numTournee1).get(0);
                //Dijkstra dijkstra = new Dijkstra();
                //dijkstra.optimiserTournee(nouvelleSolution.getTournees().get(numTournee1), depot);
                //dijkstra.optimiserTournee(nouvelleSolution.getTournees().get(numTournee2), depot);


                // Calcule delta f
                double deltaF = nouvelleSolution.calculerCoutTotal() - solutionActuelle.calculerCoutTotal();

                if (deltaF <= 0) {
                    // Mettre à jour la meilleure solution
                    solutionActuelle = nouvelleSolution.deepCopy();
                    if (meilleureSolution.calculerCoutTotal() > solutionActuelle.calculerCoutTotal() /* && solutionActuelle.isSolutionValide() */) {
                        meilleureSolution = solutionActuelle;
                    }
                }
                // Acceptance du voisin ou non
                else {
                    if (Math.random() <= Math.exp(-(deltaF) / tempActuelle)) {
                        solutionActuelle = nouvelleSolution.deepCopy();
                    }
                }
            }

            // Refroidissement
            tempActuelle *= 1 - tempRefroidissement;
        }

        return meilleureSolution;
    }

    private Solution mutation(Solution solution){
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

        solution.calculerCoutTotal();

        if (solution.isSolutionValide()){
            return solution;
        }
        else return null;
    }
}
