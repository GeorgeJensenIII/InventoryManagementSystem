import javafx.beans.property.SimpleStringProperty;

/**
 * Created by gwjense on 6/21/17.
 */
public class Outsourced extends Part{
    SimpleStringProperty companyName = new SimpleStringProperty("");

    Outsourced(String name, int partID, double price, int instock, int min, int max, String companyName)
    {
        super (name,partID,price,instock,min,max);
        setCompanyName(companyName);
    }


    public String getCompanyName() {
        return companyName.get();
    }

    public SimpleStringProperty companyNameProperty() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
}
