import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;



import java.util.ArrayList;

/**
 * Created by gwjense on 6/18/17.
 */
public class Product {

    ArrayList<Part> parts;
    SimpleIntegerProperty productID = new SimpleIntegerProperty(0);
    SimpleStringProperty name = new SimpleStringProperty("");
    SimpleDoubleProperty price = new SimpleDoubleProperty(0.00);
    SimpleIntegerProperty instock = new SimpleIntegerProperty(0);
    SimpleIntegerProperty min = new SimpleIntegerProperty(0);
    SimpleIntegerProperty max = new SimpleIntegerProperty(0);

    public Product()
    {
        parts = new ArrayList<Part>();
    }

    public Product(int productId, String name, double price, int instock, int min, int max)
    {
        setProductID(productId);
        setName(name);
        setPrice(price);
        setInstock(instock);
        setMin(min);
        setMax(max);
    }

    public ArrayList<Part> getParts() {
        return parts;
    }

    public int getProductID() {
        return productID.get();
    }

    public SimpleIntegerProperty productIDProperty() {
        return productID;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public int getInstock() {
        return instock.get();
    }

    public SimpleIntegerProperty instockProperty() {
        return instock;
    }

    public int getMin() {
        return min.get();
    }

    public SimpleIntegerProperty minProperty() {
        return min;
    }

    public int getMax() {
        return max.get();
    }

    public SimpleIntegerProperty maxProperty() {
        return max;
    }

    public void setParts(ArrayList<Part> parts) {
        this.parts = parts;
    }

    public void setProductID(int productID) {
        this.productID.set(productID);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public void setInstock(int instock) {
        this.instock.set(instock);
    }

    public void setMin(int min) {
        this.min.set(min);
    }

    public void setMax(int max) {
        this.max.set(max);
    }
}
