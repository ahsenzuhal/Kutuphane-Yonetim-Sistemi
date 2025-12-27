package com.kutuphane.AkilliKutuphane.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "yazarlar")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Yazar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yazar_id")
    private Long id;

    // --- DÜZELTME BURADA ---
    // Veritabanındaki dolu sütunun adı 'ad_soyad'.
    // Eğer buraya 'isim' yazarsan boş sütunu çeker ve 'İsimsiz' hatası alırsın.
    @Column(name = "ad_soyad", nullable = false) 
    private String adSoyad;

    @Column(name = "biyografi", columnDefinition = "TEXT")
    private String biyografi;

    // Getter ve Setterlar
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAdSoyad() { return adSoyad; }
    public void setAdSoyad(String adSoyad) { this.adSoyad = adSoyad; }

    public String getBiyografi() { return biyografi; }
    public void setBiyografi(String biyografi) { this.biyografi = biyografi; }
}