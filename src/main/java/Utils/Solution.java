package Utils;

import Algorithmes.Dijkstra;

import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    private final static int CAPACITE = 100;


    private List<Client> clients;
    private int nombreVoiture;
    private List<List<Client>> tournees;
    private double coutTotal;
    private int capacite;

    public Solution(List<Client> clients, int nombreVoiture, List<List<Client>> tournees, double coutTotal, int capacite){
        this.clients = clients;
        this.nombreVoiture = nombreVoiture;
        this.tournees = tournees;
        this.coutTotal = coutTotal;
        this.capacite = capacite;
    }

    public Solution(List<Client> clients, int nombreVoiture, List<List<Client>> tournees, int capacite){
        this.clients = clients;
        this.nombreVoiture = nombreVoiture;
        this.tournees = tournees;
        this.capacite = capacite;
        calculerCoutTotal();
    }

    public Solution(List<Client> clients, int nombreVoiture, int capacite){
        this.clients = clients;
        this.nombreVoiture = nombreVoiture;
        this.capacite = capacite;
        calculerCoutTotal();
    }

    public Solution(){}

    /**
     *  Utils
     */

    public double calculerCoutTotal(){
        if (tournees == null){
            return 0;
        }
        double cout = 0;
        for (List<Client> tournee : tournees) {
            for (int i=1 ; i < tournee.size() ; i++){
                cout += calculateDistance(tournee.get(i-1), tournee.get(i));
            }
        }
        this.coutTotal = cout;
        return coutTotal;
    }

    private double calculateDistance(Client a, Client b){
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    public boolean isSolutionValide(){
        List<Client> totalClients = new ArrayList<Client>();
        for (List<Client> clients : tournees) {
            int capaciteTournee = 0;
            for (Client client: clients) {
                capaciteTournee += client.getQuatiteCommande();
                if (client.getId() != 0) {
                    if (totalClients.contains(client)){
                        return false;
                    }
                    totalClients.add(client);
                }
                else {
                    if (!totalClients.contains(client)){
                        totalClients.add(client);
                    }
                }
            }
            if (capaciteTournee > capacite){
                return false;
            }
        }
        return totalClients.size() == clients.size() ;
    }

    public void repareSolution() throws Exception {
        List<Client> clientsParcourus = new ArrayList<>();
        Map<Client, Integer> clientsASupprimer = new HashMap<>();
        List<Client> clientsARajouter = new ArrayList<>();

        // On parcourt toutes les tournées et on regarde quels clients sont en double
        for (int i=0; i< tournees.size() ; i++) {
            List<Client> tournee = tournees.get(i);
            for (Client client: tournee){
                if (client.getId() != 0) {
                    if (clientsParcourus.contains(client)) {
                        clientsASupprimer.put(client, i);
                    } else {
                        clientsParcourus.add(client);
                    }
                }
            }
        }

        // On regarde quels clients ont étés oubliés
        for (Client client: clients) {
            if (!clientsParcourus.contains(client) && client.getId() != 0){
                clientsARajouter.add(client);
            }
        }

        // Pour chaque client a supprimer, on retire le doublons
        for (Map.Entry<Client, Integer> entry : clientsASupprimer.entrySet()) {
            tournees.get(entry.getValue()).remove(entry.getKey());

            // Si il y'a des clients a rajouter, on en rajoute un ici si possible
            if (clientsARajouter.size() > 0 && coutTournee(tournees.get(entry.getValue()))+clientsARajouter.get(0).getQuatiteCommande() < CAPACITE){
                tournees.get(entry.getValue()).add(tournees.get(entry.getValue()).size()-1, clientsARajouter.get(0));
                clientsARajouter.remove(0);
            }
        }

        // S'il y'en a encore à ajouter, on le fait ici
        if( clientsARajouter.size() > 0){
            Random random = new Random();
            int count = 0;
            while (clientsARajouter.size() > 0 && count < 1000){
                int temp = random.nextInt(tournees.size());
                if (coutTournee(tournees.get(temp)) + clientsARajouter.get(0).getQuatiteCommande() <= CAPACITE) {
                    tournees.get(temp).add(tournees.get(temp).size()-1, clientsARajouter.get(0));
                    clientsARajouter.remove(0);
                }
                count++;
            }
            if (!clientsARajouter.isEmpty()){
                throw new Exception("Impossible de rajouter le ou les clients manquant aux tournées actuelles. Ajoutez une voiture au moins pour corriger le problème");
            }
        }
    }

    public void printTournees() {
        int numeroTournee = 1;

        for (List<Client> tournee : tournees) {
            System.out.println("Tournee "+numeroTournee);

            System.out.println("ID");
            System.out.println(tournee.stream()
                    .map(c -> c.getId()+"")
                    .collect(Collectors.joining(" => ")));

            System.out.println("Qt Commande");
            System.out.println(tournee.stream()
                    .map(c -> c.getQuatiteCommande()+"")
                    .collect(Collectors.joining(" => ")));

            System.out.println("Positions");
            System.out.println(tournee.stream()
                    .map(c -> "("+c.getX()+","+c.getY()+")")
                    .collect(Collectors.joining(" => ")));
            System.out.println();
            numeroTournee++;
        }
    }

    public void printTourneesId(){
        int numeroTournee = 1;

        for (List<Client> tournee : tournees) {
            System.out.println("Tournee " + numeroTournee);
            System.out.print(tournee.stream()
                    .map(c -> c.getId() + "")
                    .collect(Collectors.joining(" => ")));
            numeroTournee++;
            double dist = 0;
            Client last = null;
            for (Client client : tournee){
                if(last == null){
                    last = client;
                }
                else {
                    dist += client.distanceTo(last);
                    last = client;
                }
            }
            System.out.println(Constants.ANSI_CYAN + "\t cout tournee : " + dist + Constants.ANSI_RESET);
        }
    }

    public void printCoutTotal(){
        System.out.println(Constants.ANSI_CYAN + "Cout total solution : "+getCoutTotal() + Constants.ANSI_RESET);
    }

    public Solution deepCopy(){
        Solution deepCopy = new Solution();
        int nombreVoiture = this.getNombreVoiture();
        List<Client> newClientList = new ArrayList<>();
        List<List<Client>> newTournees = new ArrayList<>();
        double coutTotal = this.getCoutTotal();
        int capacite = this.getCapacite();

        for (List<Client> tournee : this.getTournees()) {
            List<Client> tmp = new ArrayList<>();
            for (Client client : tournee){
                if (!newClientList.contains(client)) {
                    newClientList.add(client);
                }
                tmp.add(client);
            }
            newTournees.add(tmp);
        }

        deepCopy.setCapacite(capacite);
        deepCopy.setCoutTotal(coutTotal);
        deepCopy.setTournees(newTournees);
        deepCopy.setClients(newClientList);
        deepCopy.setNombreVoiture(nombreVoiture);
        return deepCopy;
    }

    public void optimiserTournee(){
        Dijkstra dijkstra = new Dijkstra();
        List<List<Client>> tourneeOpti = new ArrayList<>();
        for (List<Client> tournee: this.tournees) {
            int sizeTournee = tournee.size();
            List<Client> opti = new ArrayList<>(tournee);
            if (sizeTournee > 2) {
                opti.remove(sizeTournee-1);
                opti = dijkstra.calculateShortestPathFromSource(opti, opti.get(0));
                opti.add(opti.get(0));
            }
            double coutTotOpti = coutTournee(opti);
            if (coutTotOpti < coutTournee(tournee)) {
                tourneeOpti.add(opti);
            }else {
                tourneeOpti.add(tournee);
            }
        }
        this.tournees = tourneeOpti;
        this.calculerCoutTotal();
    }
    /**
     * GETTERS & SETTERS
     */

    public double coutTournee(List<Client> clientList){
        return clientList.stream().mapToInt(c -> c.getQuatiteCommande()).sum();
    }
    public List<Client> getClients() {
        return clients;
    }
    public int getNombreVoiture() {
        return nombreVoiture;
    }
    public List<List<Client>> getTournees() {
        return tournees;
    }
    public void setTournees(List<List<Client>> tournees) {
        this.tournees = tournees;
    }
    public double getCoutTotal() {
        return coutTotal;
    }
    public void setCoutTotal(double coutTotal) {
        this.coutTotal = coutTotal;
    }
    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }

    private void setClients(List<Client> clients) {
        this.clients = clients;
    }

    private void setNombreVoiture(int nombreVoiture) {
        this.nombreVoiture = nombreVoiture;
    }
}
