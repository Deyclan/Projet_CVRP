import Utils.Client;
import Utils.Solution;

import java.util.ArrayList;
import java.util.List;

public class TestSolution {

    public static void main(String[] args) {

        Client depot = new Client(0,2,2,0);
        Client client1 = new Client(1,0,0,0);
        Client client2 = new Client(2,0,2,0);
        Client client3 = new Client(3,0,4,0);
        Client client4 = new Client(4,4,4,0);
        Client client5 = new Client(5,4,2,0);
        Client client6 = new Client(6,4,0,0);

        List<Client> clients = new ArrayList<Client>();
        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
        clients.add(client4);
        clients.add(client5);
        clients.add(client6);
        clients.add(depot);

        List<Client> tournee1 = new ArrayList<Client>();
        tournee1.add(depot);
        tournee1.add(client1);
        tournee1.add(client2);
        tournee1.add(client3);
        tournee1.add(depot);

        List<Client> tournee2 = new ArrayList<Client>();
        tournee2.add(depot);
        tournee2.add(client4);
        tournee2.add(client5);
        tournee2.add(client6);
        tournee2.add(depot);

        List<List<Client>> tournees = new ArrayList<List<Client>>();
        tournees.add(tournee1);
        tournees.add(tournee2);

        Solution solution = new Solution(clients, 2, tournees, 100);
        solution.printTournees();

    }
}
