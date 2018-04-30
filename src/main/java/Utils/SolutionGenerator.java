package Utils;

import java.util.*;

public class SolutionGenerator {

    private final static int CAPACITE = 100;


    public Solution generateSolutionAleatoire(List<Client> clientList, int nombreVoiture) throws Exception {
        List<List<Client>> tournees = new ArrayList<List<Client>>();
        List<Integer> capaciteTournees = new ArrayList<Integer>();
        Random random = new Random();
        for (int i = 0 ; i<nombreVoiture ; i++) {
            List<Client> tmp = new LinkedList<Client>();
            tmp.add(clientList.get(0));                         // On ajoute le dépot au départ
            tournees.add(tmp);
            capaciteTournees.add(0);
        }
        for (Client client : clientList) {
            if (!client.equals(clientList.get(0))) {    // Si c'est le dépôt on l'ajoute pas
                int randomIndex = random.nextInt(nombreVoiture);
                fillTournee(randomIndex, client, nombreVoiture, capaciteTournees, tournees, 0);
            }
        }
        for (int i = 0 ; i<nombreVoiture ; i++) {               // On ajoute le dépot à la fin
            tournees.get(i).add(clientList.get(0));
        }

        return new Solution(clientList, nombreVoiture, tournees,  CAPACITE);
    }

    public List<Solution> generateListeSolutionAleatoire(List<Client> clientList, int nombreVoiture, int tailleListe) throws Exception {
        List<Solution> population = new ArrayList<>();
        for (int i=0 ; i<tailleListe ; i++){
            population.add(generateSolutionAleatoire(clientList, nombreVoiture));
        }
        return population;
    }

    public int getNbMinVoiture(List<Client> clientList){
        return (int) Math.floor(clientList.stream()
                .mapToInt(client -> client.getQuatiteCommande())
                .sum() / CAPACITE )+1;
    }

    public Solution generateSolutionAleatoireRepartie(){
        // TODO
        return null;
    }
    public Solution permuteClientFromPosi(Solution solution, int numTournee1, int numTournee2,int posiClient1, int posiClient2){
        Client premierClient = solution.getTournees().get(numTournee1).get(posiClient1);
        Client deuxiemeClient = solution.getTournees().get(numTournee2).get(posiClient2);
        solution.getTournees().get(numTournee1).set(posiClient1, deuxiemeClient);
        solution.getTournees().get(numTournee2).set(posiClient2, premierClient);
        return solution;
    }

    private void fillTournee(int index, Client client, int nombreVoiture, List<Integer> capaciteTournees, List<List<Client>> tournees, int conditionArret) throws Exception {
        if (capaciteTournees.get(index) + client.getQuatiteCommande() < CAPACITE){
            tournees.get(index).add(client);
            capaciteTournees.set(index, capaciteTournees.get(index) + client.getQuatiteCommande());
        }
        else {
            if ( conditionArret > nombreVoiture ){
                throw new Exception("la quantité a livrer est trop grande pour le nombre de voiture disponible ( la génération de solution a échouée )");
            }
            fillTournee((index + 1) % nombreVoiture, client, nombreVoiture, capaciteTournees, tournees, conditionArret + 1);
        }
    }
}
