import Algorithmes.Dijkstra;
import Utils.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestDijkstra {

    public static void main(String[] args) {

        Client depot = new Client(0,2,2,0);
        Client client1 = new Client(1,0,0,0);
        Client client2 = new Client(2,0,2,0);
        Client client3 = new Client(3,1,3,0);

        Client client4 = new Client(4,4,4,0);
        Client client5 = new Client(5,4,2,0);
        Client client6 = new Client(6,4,0,0);

        List<Client> clients = new ArrayList<Client>();
        clients.add(depot);
        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);
        clients.add(client5);
        clients.add(client6);

        Dijkstra dijkstra = new Dijkstra();
        List<Client> shortest = dijkstra.calculateShortestPathFromSource(clients, depot);

        System.out.println(shortest.stream().map(c-> c.getId()+"").collect(Collectors.joining(" => ")));

    }

}
