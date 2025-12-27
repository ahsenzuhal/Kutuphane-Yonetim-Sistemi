package com.kutuphane.AkilliKutuphane.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "kategoriler")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Kategori {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kategori_id")
    private Long id;

    // --- DÜZELTME ---
    // Veritabanında muhtemelen 'kategori_adi' olarak geçiyor.
    @Column(name = "kategori_adi", nullable = false)
    private String kategoriAdi;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getKategoriAdi() { return kategoriAdi; }
    public void setKategoriAdi(String kategoriAdi) { this.kategoriAdi = kategoriAdi; }
}