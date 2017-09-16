package actions;

public class Product {

    private String name;
    private String price;
    private int number;
    private String imageUrl;

    public Product(){
        this.name = "";
        this.price = "";
        this.number = 0;
        this.imageUrl = "";
    }

    public Product(String name, String price, int quantity, String imageUrl){
        this.name = name;
        this.price = price;
        this.number = quantity;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
