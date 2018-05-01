package Algorithmes;

import Utils.Solution;
import Utils.SolutionGenerator;

public class RecuitSimule {

    private double tempActuelle;
    private double tempRefroidissement;
    private Solution solutionActuelle;
    private SolutionGenerator solutionGenerator;

    public RecuitSimule(double tempInitiale, double tempRefroidissement, Solution solutionActuelle, SolutionGenerator solutionGenerator) {
        this.tempActuelle = tempInitiale;
        this.tempRefroidissement = tempRefroidissement;
        this.solutionActuelle = solutionActuelle;
        this.solutionGenerator = solutionGenerator;
    }


    private int choisirRandTournee(Solution solution) {
        return (int) (solution.getTournees().size() * Math.random());
    }

    private int choisirRandClient(Solution solution, int numTournee) {
        return (int) (solution.getTournees().get(numTournee).size() * Math.random());
    }

    public Solution lancerRecuit(int n1, int n2) {
        Solution meilleureSolution = new Solution(solutionActuelle.getClients(), solutionActuelle.getNombreVoiture(), solutionActuelle.getTournees(), solutionActuelle.getCoutTotal(), solutionActuelle.getCapacite());
        for (int k = 0; k < n1; k++) {
            for (int l = 1; l < n2; l++) {
                // Choix du voisin
                Solution nouvelleSolution = new Solution(solutionActuelle.getClients(), solutionActuelle.getNombreVoiture(), solutionActuelle.getTournees(), solutionActuelle.getCoutTotal(), solutionActuelle.getCapacite());
                // On prend deux parcours différents aléatoiremment
                int numTournee1, numTournee2;
                numTournee1 = choisirRandTournee(nouvelleSolution);
                do {
                    numTournee2 = choisirRandTournee(nouvelleSolution);
                } while (numTournee1 == numTournee2);

                // On choisit des positions random (différemment du dépot)
                int posClient1, posClient2;
                int tailleTournee1 = nouvelleSolution.getTournees().get(numTournee1).size();
                do {
                    posClient1 = choisirRandClient(nouvelleSolution, numTournee1);
                } while (posClient1 == 0 || posClient1 == tailleTournee1 - 1);

                int tailleTournee2 = nouvelleSolution.getTournees().get(numTournee2).size();
                do {
                    posClient2 = choisirRandClient(nouvelleSolution, numTournee2);
                } while (posClient2 == 0 || posClient2 == tailleTournee2 - 1);

                // Echanger les clients de tournées différentes
                solutionGenerator.permuteClientFromPosi(nouvelleSolution, numTournee1, numTournee2, posClient1, posClient2);

                // Optimisation locale
                //Client depot = nouvelleSolution.getTournees().get(numTournee1).get(0);
                //Dijkstra dijkstra = new Dijkstra();
                //dijkstra.optimiserTournee(nouvelleSolution.getTournees().get(numTournee1), depot);
                //dijkstra.optimiserTournee(nouvelleSolution.getTournees().get(numTournee2), depot);

                // Calcule delta f
                double deltaF = nouvelleSolution.calculerCoutTotal() - solutionActuelle.calculerCoutTotal();

                if (deltaF <= 0) {
                    // Mettre à jour la meilleure solution
                    solutionActuelle = new Solution(nouvelleSolution.getClients(), nouvelleSolution.getNombreVoiture(), nouvelleSolution.getTournees(), nouvelleSolution.getCoutTotal(), nouvelleSolution.getCapacite());
                    System.out.println("F meilleure solution : " + meilleureSolution.calculerCoutTotal() + "  & f actuelle : " + solutionActuelle.calculerCoutTotal());
                    if (meilleureSolution.calculerCoutTotal() > solutionActuelle.calculerCoutTotal() && solutionActuelle.isSolutionValide()) {
                        meilleureSolution = solutionActuelle;
                        System.out.println("PASSE");
                    }
                }
                // Acceptance du voisin ou non
                else {
                    if (Math.random() <= Math.exp(-(deltaF) / tempActuelle)) {
                        solutionActuelle = nouvelleSolution;
                    }
                }
            }

            // Refroidissement
            tempActuelle *= 1 - tempRefroidissement;
        }

        return meilleureSolution;
    }
}
