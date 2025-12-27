package com.kutuphane.AkilliKutuphane.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cezalar")
public class Ceza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ceza_id")
    private Integer id;

    @Column(name = "ceza_miktari", nullable = false)
    private Double cezaMiktari;

    @Column(name = "odeme_durumu", nullable = false)
    private String odemeDurumu; // "Ödendi", "Ödenmedi"

    // --- İlişkiler ---

    @ManyToOne
    @JoinColumn(name = "ogrenci_id", referencedColumnName = "ogrenci_id", nullable = false)
    private Ogrenci ogrenci;

    @ManyToOne
    @JoinColumn(name = "kitap_id", referencedColumnName = "kitap_id", nullable = false)
    private Kitap kitap;

    // --- Constructor'lar ---
    public Ceza() {
    }

    public Ceza(Double cezaMiktari, String odemeDurumu, Ogrenci ogrenci, Kitap kitap) {
        this.cezaMiktari = cezaMiktari;
        this.odemeDurumu = odemeDurumu;
        this.ogrenci = ogrenci;
        this.kitap = kitap;
    }

    // --- Getter ve Setter Metotları ---
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCezaMiktari() {
        return cezaMiktari;
    }

    public void setCezaMiktari(Double cezaMiktari) {
        this.cezaMiktari = cezaMiktari;
    }

    public String getOdemeDurumu() {
        return odemeDurumu;
    }

    public void setOdemeDurumu(String odemeDurumu) {
        this.odemeDurumu = odemeDurumu;
    }

    public Ogrenci getOgrenci() {
        return ogrenci;
    }

    public void setOgrenci(Ogrenci ogrenci) {
        this.ogrenci = ogrenci;
    }

    public Kitap getKitap() {
        return kitap;
    }

    public void setKitap(Kitap kitap) {
        this.kitap = kitap;
    }

    @Override
    public String toString() {
        return "Ceza{" +
               "id=" + id +
               ", cezaMiktari=" + cezaMiktari +
               ", odemeDurumu='" + odemeDurumu + '\'' +
               '}';
    }
}