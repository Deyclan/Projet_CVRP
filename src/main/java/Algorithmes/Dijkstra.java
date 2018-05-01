package Algorithmes;

import Utils.Arc;
import Utils.Client;

import java.util.*;
import java.util.stream.Collectors;

public class Dijkstra {

    public List<Client> calculateShortestPathFromSource(List<Client> clientList, Client source) {
        //source.setDistance(0);

        List<Client> voisins = new ArrayList<>();
        for (Client client : clientList){
            if (client.getId() != source.getId()){
                voisins.add(client);
            }
        }

        ArrayList<Client> settledNodes = new ArrayList<>();
        ArrayList<Client> unsettledNodes = new ArrayList<>();
        unsettledNodes.addAll(clientList);

        while (unsettledNodes.size() != 0) { // Peut etre a mettre a 1

            Client plusProcheClient = getLowestDistanceNode(unsettledNodes, source);
            unsettledNodes.remove(plusProcheClient);

            settledNodes.add(plusProcheClient);
            source = plusProcheClient;
        }
        return settledNodes;
    }

    private static Client getLowestDistanceNode(ArrayList<Client> unsettledNodes, Client source) {
        Client plusProcheClient = null;
        double plusCourteDistance = Integer.MAX_VALUE;
        for (Client client: unsettledNodes) {
            double clientDistance = client.distanceTo(source);
            if (clientDistance < plusCourteDistance) {
                plusCourteDistance = clientDistance;
                plusProcheClient = client;
            }
        }
        return plusProcheClient;
    }

    public void optimiserTournee(List<Client> clientsList, Client depart) {
        // Copie de la liste des clients
        List<Client> clients = new ArrayList<>();
        clients.addAll(clientsList);

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
