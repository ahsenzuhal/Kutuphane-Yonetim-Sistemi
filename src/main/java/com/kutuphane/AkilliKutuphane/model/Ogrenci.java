package com.kutuphane.AkilliKutuphane.model;

import jakarta.persistence.*; // JPA anotasyonları için gerekli
import java.util.List; // Eğer daha sonra ödünç aldığı kitapları listelemek istersek

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity // Bu sınıfın bir veritabanı varlığı olduğunu belirtir
@Table(name = "ogrenciler") // Hangi tabloya karşılık geldiğini belirtir
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ogrenci {

    @Id // Bu alanın birincil anahtar (Primary Key) olduğunu belirtir
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID'nin veritabanı tarafından otomatik artırılacağını belirtir
    @Column(name = "ogrenci_id") // Veritabanındaki kolon adını belirtir
    private Integer id; 

    @Column(name = "isim", nullable = false) // Boş olamaz (NOT NULL)
    private String isim;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "bolum")
    private String bolum;

    @Column(name = "sinif")
    private String sinif;

    // --- Constructor'lar ---
    public Ogrenci() {
    }

    public Ogrenci(String isim, String email, String bolum, String sinif) {
        this.isim = isim;
        this.email = email;
        this.bolum = bolum;
        this.sinif = sinif;
    }

    // --- Getter ve Setter Metotları ---
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getBolum() {
        return bolum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBolum(String bolum) {
        this.bolum = bolum;
    }

    public String getSinif() {
        return sinif;
    }

    public void setSinif(String sinif) {
        this.sinif = sinif;
    }

    @Override
    public String toString() {
        return "Ogrenci{" +
               "id=" + id +
               ", isim='" + isim + '\'' +
               ", email='" + email + '\'' +
               ", bolum='" + bolum + '\'' +
               ", sinif='" + sinif + '\'' +
               '}';
    }
}