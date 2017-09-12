package actions;

public class Product {

    private String name;
    private String price;
    private int number;

    public Product(){
        this.name = "";
        this.price = "";
        this.number = 0;
    }

    public Product(String name, String price, int quantity){
        this.name = name;
        this.price = price;
        this.number = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
