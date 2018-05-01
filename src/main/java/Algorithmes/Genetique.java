package Algorithmes;

import Utils.Client;
import Utils.RandomCollection;
import Utils.Solution;

import java.util.*;
import java.util.stream.Collectors;

public class Genetique {

    private List<Solution> population;
    private List<Solution> populationEvoluee;
    private Random random;

    public Genetique(List<Solution> population){
        this.population = population;
        this.populationEvoluee = population;
        this.random = new Random();
    }

    public List<Solution> lancerGenetique(int nombreGeneration, int nbReproduction, int nbCroisement, int nbMutation, int nbMaxBoucle){
        for (int x=0 ; x<nombreGeneration ; x++){
            List<Solution> tempPopulation = new ArrayList<>();
            List<Solution> reproduites = selectionReproduction(populationEvoluee, nbReproduction);
            List<Solution> croisees = selectionCroisements(reproduites, nbCroisement, nbMaxBoucle);
            List<Solution> mutantes = selectionMutation(reproduites, nbMutation, nbMaxBoucle);
            tempPopulation.addAll(reproduites);
            tempPopulation.addAll(croisees);
            tempPopulation.addAll(mutantes);
            populationEvoluee = tempPopulation;
        }
        return populationEvoluee;
    }

    // Reproduction
    /***
     *
     * @param population Liste des solutions de la génération à faire évoluer
     * @param nbElement Nombre d'éléments à l'issue de la reproduction.
     * @return
     */
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

    public List<Solution> selectionReproduction(List<Solution> population, int nbElement){
        List<Solution> nextGenPop = new ArrayList<>();
        List<Solution> temp = population.stream()
                .sorted(Comparator.comparing(Solution::getCoutTotal))
                .collect(Collectors.toList());
        if (temp.size() > nbElement){
            return temp.subList(0,nbElement);
        }
        else {
            Solution best = population.stream().sorted(Comparator.comparing(Solution::getCoutTotal)).findFirst().get();
            for (int i=0 ; i< nbElement-temp.size() ; i++){
                temp.add(best);
            }
            return temp;
        }
    }

    // Croisement
    /***
     *
     * @param population Solutions issues de la reproduction
     * @param nbElement nombre de solution issue du croisement à retourner
     * @param nbEtapeMax nombre de passage dans la boucle while
     * @return
     */
    public List<Solution> selectionCroisements(List<Solution> population, int nbElement, int nbEtapeMax) {
        List<Solution> nextGenPop = new ArrayList<>();
        int popSize = population.size();
        int count = 0;
        while (nextGenPop.size() < nbElement && count < nbEtapeMax) {
            Solution tmp1 = population.get(random.nextInt(popSize));
            Solution tmp2 = population.get(random.nextInt(popSize));
            Solution solution1 = new Solution(tmp1.getClients(), tmp1.getNombreVoiture(), tmp1.getTournees(), tmp1.getCoutTotal(), tmp1.getCapacite());
            Solution solution2 = new Solution(tmp1.getClients(), tmp1.getNombreVoiture(), tmp1.getTournees(), tmp1.getCoutTotal(), tmp1.getCapacite());
            Solution[] croisement = croisement(solution1, solution2);
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

    // Mutation
    /***
     *
     * @param population Solutions issues de la reproduction
     * @param nbElement nombre de solution issue de mutations à retourner
     * @param nbEtapeMax nombre de passage dans la boucle while
     * @return
     */
    public List<Solution> selectionMutation(List<Solution> population, int nbElement, int nbEtapeMax){
        List<Solution> nextGenPop = new ArrayList<>();
        int count = 0;
        while (nextGenPop.size() < nbElement && count < nbEtapeMax){
            Solution tmp = population.get(random.nextInt(population.size()));
            Solution solution = new Solution(tmp.getClients(), tmp.getNombreVoiture(), tmp.getTournees(), tmp.getCoutTotal(), tmp.getCapacite());
            Solution mutante = mutation(solution);
            if (mutante != null){
                nextGenPop.add(mutante);
            }
            count++;
        }
        return checkLength(nextGenPop, nbElement, population);
    }

    private List<Solution> checkLength(List<Solution> nextGenPop, int nextGenSize, List<Solution> population){
        if (nextGenPop.size() > nextGenSize){
            return nextGenPop.subList(0, nextGenSize);
        }
        else if (nextGenPop.size() < nextGenSize){
            nextGenPop.addAll(population.subList(0, nextGenSize - nextGenPop.size()));
            return nextGenPop;
        }
        return nextGenPop;
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

        solution1.calculerCoutTotal();
        solution2.calculerCoutTotal();

        return new Solution[]{solution1, solution2};
    }

}
