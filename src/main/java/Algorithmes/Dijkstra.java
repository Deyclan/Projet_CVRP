package Algorithmes;

import Utils.Arc;
import Utils.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dijkstra {

    public void optimiserTournee(List<Client> clientsList, Client depart) {
        ArrayList<Client> clients = new ArrayList<>();
        for (Client c : clientsList) {
            clients.add(c);
        }
        Client clientActuel = depart;
        Arc tmp;
        HashMap<Client, Double> lambda = new HashMap<>();
        for (Client c : clients) {
            lambda.put(c, Double.POSITIVE_INFINITY);
        }
        lambda.replace(depart, (double) 0);

        for (Client c : clients) {
            tmp = new Arc(c, clientActuel);
            // if (c.getClientVoisinsArcs().contains(tmp)) {
            lambda.replace(c, tmp.getDistance());
            // } else {
            //     lambda.replace(c, Double.POSITIVE_INFINITY);
            // }
        }

        double temp = Double.POSITIVE_INFINITY;
        Client tempCommune = new Client();
        while (clients.size() != 0) {
            for (Client x : lambda.keySet()) {
                if (lambda.get(x) < temp) {
                    temp = lambda.get(x);
                    tempCommune = x;
                }
            }
            clients.remove(tempCommune);

            for (Client i : clients) {
                Arc arc = new Arc(tempCommune, i);
                // if (tempCommune.getClientVoisinsArcs().contains(arc)) {
                if ((lambda.get(tempCommune) + arc.getDistance()) < lambda.get(i)) {
                    lambda.replace(i, lambda.get(tempCommune) + arc.getDistance());
                }
                // }
            }
        }

        printLambda(lambda);

    }

    public void printLambda(HashMap<Client, Double> lambda) {
        for (Client c : lambda.keySet()) {
            System.out.println(c.getId() + " => " + lambda.get(c));
        }
    }
}
