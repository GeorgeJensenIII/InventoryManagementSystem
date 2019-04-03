import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by gwjense on 6/21/17.
 */
public class Inhouse extends Part {

    SimpleIntegerProperty machineID = new SimpleIntegerProperty(0);

    Inhouse(String name, int partID, double price, int instock, int min, int max, int machineID)
    {
        super (name,partID,price,instock,min,max);
        setMachineID(machineID);
    }

    public int getMachineID() {
        return machineID.get();
    }

    public SimpleIntegerProperty machineIDProperty() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID.set(machineID);
    }
}

