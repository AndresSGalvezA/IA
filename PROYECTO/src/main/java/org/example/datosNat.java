package org.example;

public class datosNat {
    private String id; // private = restricted access
    private int value; // private = restricted access

    public datosNat(String id, int Value)
    {
        this.id = id;
        this.value = Value;
    }


    // Getter
    public String getId() {
        return id;
    }

    // Getter
    public int getVal() {
        return value;
    }

    public void setId(String newValue) {
        this.id= newValue;
    }

    // Setter
    public void setValue(int newValue) {
        this.value= newValue;
    }
}
