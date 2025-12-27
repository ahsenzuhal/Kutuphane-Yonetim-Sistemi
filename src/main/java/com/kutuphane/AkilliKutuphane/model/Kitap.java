package com.kutuphane.AkilliKutuphane.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@Entity
@Table(name = "kitaplar")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Kitap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kitap_id")
    private Long id;

    @Column(name = "kitap_adi")
    private String kitapAdi;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "yayin_yili")
    private Integer yayinYili;

    @Column(name = "durum")
    private String durum;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "yazar_id")
    private Yazar yazar;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kategori_id")
    private Kategori kategori;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "odunc_alan_ogrenci_id")
    private Ogrenci oduncAlanOgrenci;

    @Column(name = "odunc_tarihi")
    private LocalDate oduncTarihi;

    @Column(name = "iade_tarihi")
    private LocalDate iadeTarihi;

    // Getter ve Setter'lar
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getKitapAdi() { 
        return kitapAdi; 
    }
    
    public void setKitapAdi(String kitapAdi) { 
        this.kitapAdi = kitapAdi; 
    }

    public String getIsbn() { 
        return isbn; 
    }
    
    public void setIsbn(String isbn) { 
        this.isbn = isbn; 
    }

    public Integer getYayinYili() { 
        return yayinYili; 
    }
    
    public void setYayinYili(Integer yayinYili) { 
        this.yayinYili = yayinYili; 
    }

    public String getDurum() { 
        return durum; 
    }
    
    public void setDurum(String durum) { 
        this.durum = durum; 
    }

    public Yazar getYazar() { 
        return yazar; 
    }
    
    public void setYazar(Yazar yazar) { 
        this.yazar = yazar; 
    }

    public Kategori getKategori() { 
        return kategori; 
    }
    
    public void setKategori(Kategori kategori) { 
        this.kategori = kategori; 
    }

    public Ogrenci getOduncAlanOgrenci() { 
        return oduncAlanOgrenci; 
    }
    
    public void setOduncAlanOgrenci(Ogrenci oduncAlanOgrenci) { 
        this.oduncAlanOgrenci = oduncAlanOgrenci; 
    }

    public LocalDate getOduncTarihi() { 
        return oduncTarihi; 
    }
    
    public void setOduncTarihi(LocalDate oduncTarihi) { 
        this.oduncTarihi = oduncTarihi; 
    }

    public LocalDate getIadeTarihi() { 
        return iadeTarihi; 
    }
    
    public void setIadeTarihi(LocalDate iadeTarihi) { 
        this.iadeTarihi = iadeTarihi; 
    }
}