package Utils;

public class Client {

    private int id;
    private int x;
    private int y;
    private int quatiteCommande;

    public Client(int id, int x, int y, int quantiteCommande){
        this.id = id;
        this.x = x;
        this.y = y;
        this.quatiteCommande = quantiteCommande;
    }

    public Client() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getQuatiteCommande() {
        return quatiteCommande;
    }

    public void setQuatiteCommande(int quatiteCommande) {
        this.quatiteCommande = quatiteCommande;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id : ").append(id);
        stringBuilder.append("\t\t");
        stringBuilder.append(" x : ").append(x);
        stringBuilder.append("\t\t");
        stringBuilder.append(" y : ").append(y);
        stringBuilder.append("\t\t");
        stringBuilder.append(" q : ").append(quatiteCommande);
        return stringBuilder.toString();
    }
}
