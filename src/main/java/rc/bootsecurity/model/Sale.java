package rc.bootsecurity.model;

import javax.persistence.*;

@Entity
@Table(name = "SALES")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "ITEM")
    private String item;
    @Column(name = "QUANTITY")
    private int quantity;
    @Column(name = "AMOUNT")
    private float amount;

    public Sale() {
    }

    public Sale(String item, int quantity, float amount) {
        this.item = item;
        this.quantity = quantity;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}