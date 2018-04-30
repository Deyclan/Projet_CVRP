package Main;

import DataHelper.Serializer;
import Utils.Client;
import Utils.Solution;
import Utils.SolutionGenerator;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Serializer serializer = new Serializer(1);
        SolutionGenerator generator = new SolutionGenerator();
        List<Client> clients = serializer.serialize();

        try{
            System.out.println("Nombre de voitures minimal : " + generator.getNbMinVoiture(clients));
            System.out.println("Entrez le nombre de voitures pour la génération aléatoire : ");
            int nbVoiture = scanner.nextInt();
            Solution solution = generator.generateSolutionAleatoire(clients, nbVoiture);
            solution.printTournees();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
