package org.iut.refactoring;

import java.util.UUID;

public class Employe {
    private final String id;
    private String type;
    private String nom;
    private double salaireBase;
    private int experience;
    private String equipe;

    public Employe(String type, String nom, double salaireBase, int experience, String equipe) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.nom = nom;
        this.salaireBase = salaireBase;
        this.experience = experience;
        this.equipe = equipe;
    }

    // getters
    public String getId() {return id;}
    public String getType() {return type;}
    public String getNom() {return nom;}
    public double getSalaireBase() {return salaireBase;}
    public int getExperience() {return experience;}
    public String getEquipe() {return equipe;}

    // setters
    public void setType(String type) {this.type = type;}
    public void setNom(String nom) {this.nom = nom;}
    public void setSalaireBase(double salaireBase) {this.salaireBase = salaireBase;}
    public void setExperience(int experience) {this.experience = experience;}
    public void setEquipe(String equipe) {this.equipe = equipe;}

    @Override
    public String toString() {
        return "Employe{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", nom='" + nom + '\'' +
                ", salaireBase=" + salaireBase +
                ", experience=" + experience +
                ", equipe='" + equipe + '\'' +
                '}';
    }
}
