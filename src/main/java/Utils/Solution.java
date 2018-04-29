package Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {

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

    /**
     * GETTERS & SETTERS
     */

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
}
