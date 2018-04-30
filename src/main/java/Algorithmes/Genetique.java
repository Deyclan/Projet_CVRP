package Algorithmes;

import Utils.Client;
import Utils.RandomCollection;
import Utils.Solution;
import Utils.SolutionGenerator;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Genetique {

    private List<Solution> population;
    private Random random;

    public Genetique(List<Solution> population){
        this.population = population;
        this.random = new Random();
    }

    public List<Solution> selectionAleatoire(List<Solution> population, int nbElement){
        int distanceTotale = population.stream().mapToInt(s -> (int)s.getCoutTotal()).sum();
        List<Solution> nextGenPop = new ArrayList<>();

        // On crée une collection correspondant à la répartition des solutons avec leur poids
        RandomCollection<Solution> weightedCollection = new RandomCollection<>();
        for (Solution solution:population) {
            weightedCollection.add(1-(solution.getCoutTotal())/distanceTotale, solution);
        }

        // On ajoute a la prochaine génération les éléments séléctionnés dans la liste
        for (int i=0 ; i< nbElement ; i++){
            nextGenPop.add(weightedCollection.next());
        }

        return nextGenPop;
    }


    /*
    public List<Solution> selectionCroisements(List<Solution> population, int nbElement, int nbEtapeMax) {
        List<Solution> nextGenPop = new ArrayList<>();
        int count = 0;
        while (nextGenPop.size() < nbElement || count < nbEtapeMax) {
            // TODO
        }
    }
    */

    private Solution[] croisement(Solution solution1, Solution solution2){
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
