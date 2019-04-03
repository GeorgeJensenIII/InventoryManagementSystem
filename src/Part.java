import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by gwjense on 6/10/17.
 */
public abstract class Part {

    private final SimpleStringProperty name = new SimpleStringProperty("");
    private final SimpleIntegerProperty partID = new SimpleIntegerProperty(0);
    private final SimpleDoubleProperty price = new SimpleDoubleProperty(0.00);
    private final SimpleIntegerProperty instock = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty min = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty max = new SimpleIntegerProperty(0);


    public Part()
    {
        this("",0,0.0,0,0,0);
    }

    public Part(String name, int partID, Double price, int instock, int min, int max)
    {
        setName(name);
        setPartID(partID);
        setPrice(price);
        setInstock(instock);
        setMin(min);
        setMax(max);
        }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPartID(int partID) {
        this.partID.set(partID);
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getPartID() {
        return partID.get();
    }

    public SimpleIntegerProperty partIDProperty() {
        return partID;
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

}

