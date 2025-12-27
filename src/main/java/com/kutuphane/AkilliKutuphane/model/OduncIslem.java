package com.kutuphane.AkilliKutuphane.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

/**
 * Ödünç alma işlemlerini takip eden Entity
 * Her ödünç verme işlemi bu tabloda kayıt altına alınır
 */
@Entity
@Table(name = "odunc_islemler")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OduncIslem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "odunc_islem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kitap_id", nullable = false)
    private Kitap kitap;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ogrenci_id", nullable = false)
    private Ogrenci ogrenci;

    @Column(name = "alis_tarihi", nullable = false)
    private LocalDate alisTarihi;

    @Column(name = "planlanan_iade_tarihi", nullable = false)
    private LocalDate planlananIadeTarihi;

    @Column(name = "gercek_iade_tarihi")
    private LocalDate gercekIadeTarihi;

    @Column(name = "durum", nullable = false)
    private String durum; // "Aktif", "İade Edildi"

    // Constructor'lar
    public OduncIslem() {
    }

    public OduncIslem(Kitap kitap, Ogrenci ogrenci, LocalDate alisTarihi, LocalDate planlananIadeTarihi) {
        this.kitap = kitap;
        this.ogrenci = ogrenci;
        this.alisTarihi = alisTarihi;
        this.planlananIadeTarihi = planlananIadeTarihi;
        this.durum = "Aktif";
    }

    // Getter ve Setter'lar
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kitap getKitap() {
        return kitap;
    }

    public void setKitap(Kitap kitap) {
        this.kitap = kitap;
    }

    public Ogrenci getOgrenci() {
        return ogrenci;
    }

    public void setOgrenci(Ogrenci ogrenci) {
        this.ogrenci = ogrenci;
    }

    public LocalDate getAlisTarihi() {
        return alisTarihi;
    }

    public void setAlisTarihi(LocalDate alisTarihi) {
        this.alisTarihi = alisTarihi;
    }

    public LocalDate getPlanlananIadeTarihi() {
        return planlananIadeTarihi;
    }

    public void setPlanlananIadeTarihi(LocalDate planlananIadeTarihi) {
        this.planlananIadeTarihi = planlananIadeTarihi;
    }

    public LocalDate getGercekIadeTarihi() {
        return gercekIadeTarihi;
    }

    public void setGercekIadeTarihi(LocalDate gercekIadeTarihi) {
        this.gercekIadeTarihi = gercekIadeTarihi;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    /**
     * İade işleminin gecikip gecikmediğini kontrol eder
     */
    public boolean isGecikmis() {
        if (gercekIadeTarihi == null) {
            return LocalDate.now().isAfter(planlananIadeTarihi);
        }
        return gercekIadeTarihi.isAfter(planlananIadeTarihi);
    }

    /**
     * Gecikme gün sayısını hesaplar
     */
    public long getGecikmeGunu() {
        if (gercekIadeTarihi == null) {
            if (LocalDate.now().isAfter(planlananIadeTarihi)) {
                return java.time.temporal.ChronoUnit.DAYS.between(planlananIadeTarihi, LocalDate.now());
            }
            return 0;
        }
        if (gercekIadeTarihi.isAfter(planlananIadeTarihi)) {
            return java.time.temporal.ChronoUnit.DAYS.between(planlananIadeTarihi, gercekIadeTarihi);
        }
        return 0;
    }
}


