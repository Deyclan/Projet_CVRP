import DataHelper.Serializer;
import Utils.Client;

import java.util.List;

public class TestSerializer {

    public static void main(String[] args) {
        Serializer serializer = new Serializer(1);

        List<Client> clients = serializer.serialize();
        for (Client client: clients) {
            System.out.println(client.toString());
        }
    }

}
