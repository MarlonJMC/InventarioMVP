package com.example.inventariomvp.common.pojo;

import com.google.firebase.database.Exclude;

import java.util.Objects;

public class Product {

    public static final String ID="id";
    public static final String NAME="name";
    public static final String QUANTITY="quantity";
    public static final String PHOTOURL="photoURL";

    @Exclude
    // Para que la carga y descarga de este parametro no sea considerasa por FIREBASE, es decir lo excluya.
    private String id;
    private String name;
    private int quantity;
    private String photoURL;

    public  Product(){
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
