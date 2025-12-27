package com.kutuphane.AkilliKutuphane.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "kullanicilar")
public class Kullanici {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kullanici_id")
    private Integer id;

    @Column(name = "kullanici_adi", nullable = false, unique = true)
    private String kullaniciAdi;

    @Column(name = "sifre", nullable = false)
    private String sifre; // Şifrelenmiş parola burada tutulacak

    @Column(name = "rol", nullable = false)
    private String rol; // "ADMIN", "KUTUPHANECI" gibi roller

    @Column(name = "isim", nullable = true)
    private String isim;

    @Column(name = "email", nullable = true, unique = true)
    private String email;

    // --- Constructor'lar ---
    public Kullanici() {
    }

    public Kullanici(String kullaniciAdi, String sifre, String rol) {
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
        this.rol = rol;
    }

    public Kullanici(String isim, String email, String kullaniciAdi, String sifre, String rol) {
        this.isim = isim;
        this.email = email;
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
        this.rol = rol;
    }

    // --- Getter ve Setter Metotları ---
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Kullanici{" +
               "id=" + id +
               ", kullaniciAdi='" + kullaniciAdi + '\'' +
               ", rol='" + rol + '\'' +
               '}';
    }
}