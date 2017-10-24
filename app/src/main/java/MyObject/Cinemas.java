package MyObject;

import java.io.Serializable;

/**
 * Created by hieum on 10/24/2017.
 */

public class Cinemas implements Serializable{

    private String id;
    private String Address;
    private String Income;

    public Cinemas() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getIncome() {
        return Income;
    }

    public void setIncome(String income) {
        Income = income;
    }
}
