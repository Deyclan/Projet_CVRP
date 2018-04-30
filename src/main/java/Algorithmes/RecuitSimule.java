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

    private double probabiliteAcceptation(int distanceActuelle, int nvDistance, double temperature) {
        // Si distance meilleure
        if (nvDistance < distanceActuelle) {
            return 1;
        }
        // Sinon, calculer le degré d'acceptation
        return Math.exp((distanceActuelle - nvDistance) / temperature);
    }

    private int choisirRandTournee(Solution solution){
        return (int) (solution.getTournees().size() * Math.random());
    }

    private int choisirRandClient(Solution solution, int numTournee){
        return (int) (solution.getTournees().get(numTournee).size() * Math.random());
    }

    public Solution lancerRecuit(){
        Solution meilleureSolution = this.solutionActuelle;
        while(tempActuelle > 1){
            // Créer nouvelle solution
            Solution nouvelleSolution = this.solutionActuelle;

            // On prend deux parcours différents aléatoiremment
            int numTournee1, numTournee2;
            numTournee1 = choisirRandTournee(nouvelleSolution);
            do{
                numTournee2 = choisirRandTournee(nouvelleSolution);
            } while( numTournee1==numTournee2);

            // On choisit des positions random (différemment du dépot)
            int posClient1, posClient2;
            int tailleTournee1 = nouvelleSolution.getTournees().get(numTournee1).size();
            int tailleTournee2 = nouvelleSolution.getTournees().get(numTournee2).size();
            do{
                posClient1 = choisirRandClient(nouvelleSolution, numTournee1);
                posClient2 = choisirRandClient(nouvelleSolution, numTournee2);
            } while( posClient1==0 || posClient1==tailleTournee1-1 || posClient2==0 || posClient2==tailleTournee2-1);

            // Echanger les clients de tournées différentes
            solutionGenerator.permuteClientFromPosi(nouvelleSolution, numTournee1, numTournee2, posClient1, posClient2);

            // TODO algo optimisation locale

            // Calcule delta f
            int currentEnergy = (int) solutionActuelle.calculerCoutTotal();
            int neighbourEnergy = (int) nouvelleSolution.calculerCoutTotal();

            // Acceptance du voisin ou non
            if (probabiliteAcceptation(currentEnergy, neighbourEnergy, tempActuelle) > Math.random()) {
                solutionActuelle = nouvelleSolution;
            }

            // Mettre à jour la meilleure solution
            if (solutionActuelle.calculerCoutTotal() < meilleureSolution.calculerCoutTotal()) {
                meilleureSolution = solutionActuelle;
            }

            // Refroidissement
            tempActuelle *= 1-tempRefroidissement;
        }
        return meilleureSolution;
    }
}
