package MyObject;

import java.io.Serializable;

/**
 * Created by hieum on 10/24/2017.
 */

public class Room implements Serializable{

    private String id;
    private String Capacity;
    private String Status;

    public Room() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCapacity() {
        return Capacity;
    }

    public void setCapacity(String capacity) {
        Capacity = capacity;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
