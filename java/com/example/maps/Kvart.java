package com.example.maps;

import java.util.ArrayList;
import java.util.List;

public class Kvart {

    private String naziv;
    private boolean isCompleted;
    private List<Zadatak> zadaci;
    private int image;

    public Kvart(String name) {
        this.naziv = naziv;
        this.isCompleted = false;
        this.zadaci = new ArrayList<>();
    }

    public String getNaziv() {
        return naziv;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public List<Zadatak> getZadaci() {
        return zadaci;
    }

    public void setZadaci(List<Zadatak> zadaci) {
        this.zadaci = zadaci;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
