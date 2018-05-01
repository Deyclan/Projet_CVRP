package Utils;

/**
 * Classe representant l'arc d'un graphe.
 */
public class Arc {

    Client depart;
    Client arrivee;
    double distance;

    public Arc(Client depart, Client arrivee) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.distance = Math.sqrt(Math.pow((depart.getX() - arrivee.getX()), 2) + Math.pow((depart.getY() - arrivee.getY()), 2));
    }

    public Client getArrivee() {
        return arrivee;
    }

    public Client getDepart() {
        return depart;
    }

    public double getDistance() {
        return distance;
    }
}
