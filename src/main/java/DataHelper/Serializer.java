package DataHelper;

import Utils.Client;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Serializer {

    private File data;
    private String fileName;

    public Serializer(int numeroFichierData){
            fileName = "data0"+numeroFichierData+".txt";
        /*
        ClassLoader classLoader = getClass().getClassLoader();

        switch (numeroFichierData){
            case 1:
                System.out.println("loading file data01.txt");
                data =  new File(classLoader.getResource("data01.txt").getFile());
                break;
            case 2:
                System.out.println("loading file data02.txt");
                data = new File(classLoader.getResource("data02.txt").getFile());
                break;
            case 3:
                System.out.println("loading file data03.txt");
                data =  new File(classLoader.getResource("data03.txt").getFile());
                break;
            case 4:
                System.out.println("loading file data03.txt");
                data =  new File(classLoader.getResource("data04.txt").getFile());
                break;
            case 5:
                System.out.println("loading file data03.txt");
                data =  new File(classLoader.getResource("data05.txt").getFile());
                break;
            default:
                System.out.println("loading file data01.txt");
                data =  new File(classLoader.getResource("data01.txt").getFile());
                break;
        }
        */
    }

    public List<Client> serialize(){
        List<Client> clients = new ArrayList<Client>();
        try{
            //BufferedReader reader = new BufferedReader(new FileReader(new File(data.getPath().replace("%20", " "))));
            InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            List<String> clientLines = new ArrayList<String>();
            while ((line = reader.readLine()) != null){
                clientLines.add(line);
            }
            for (int i = 1 ; i < clientLines.size() ; i++){
                String[] strings = clientLines.get(i).split(";");
                clients.add(new Client(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3])));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return clients;
    }
}
