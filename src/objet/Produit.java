// src/objet/Produit.java
package objet;

public class Produit {
    private String id;
    private String nom;
    private String marque;
    private int cylindre;
    private String modele;
    private int nombrePorte;
    private String type;

    public Produit(String id, String nom, String marque, int cylindre, String modele, int nombrePorte, String type) {
        this.id = id;
        this.nom = nom;
        this.marque = marque;
        this.cylindre = cylindre;
        this.modele = modele;
        this.nombrePorte = nombrePorte;
        this.type = type;
    }

    public Produit(String id, String nom, double prix, int quantite, String type) {
        this.id = id;
        this.nom = nom;

    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public int getCylindre() {
        return cylindre;
    }

    public void setCylindre(int cylindre) {
        this.cylindre = cylindre;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public int getNombrePorte() {
        return nombrePorte;
    }

    public void setNombrePorte(int nombrePorte) {
        this.nombrePorte = nombrePorte;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", marque='" + marque + '\'' +
                ", cylindre=" + cylindre +
                ", modele='" + modele + '\'' +
                ", nombrePorte=" + nombrePorte +
                ", type='" + type + '\'' +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrix() {
        return 0;
    }

    public int getQuantite() {

        return 0;
    }
}