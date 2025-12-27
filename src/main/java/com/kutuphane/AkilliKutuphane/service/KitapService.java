package com.kutuphane.AkilliKutuphane.service;

import com.kutuphane.AkilliKutuphane.*;
import com.kutuphane.AkilliKutuphane.model.Kitap;
import com.kutuphane.AkilliKutuphane.model.Ogrenci;
import com.kutuphane.AkilliKutuphane.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class KitapService {

    private static final int STANDART_IADE_SURESI = 14;
    private static final double GECIKME_CEZA_KATSAYISI = 10.0;

    private final KitapRepository kitapRepository;
    private final YazarRepository yazarRepository;
    private final KategoriRepository kategoriRepository;
    private final OgrenciRepository ogrenciRepository;
    private final CezaService cezaService;
    private final EmailService emailService;

    public KitapService(KitapRepository kitapRepository,
                        YazarRepository yazarRepository,
                        KategoriRepository kategoriRepository,
                        OgrenciRepository ogrenciRepository,
                        CezaService cezaService,
                        EmailService emailService) {
        this.kitapRepository = kitapRepository;
        this.yazarRepository = yazarRepository;
        this.kategoriRepository = kategoriRepository;
        this.ogrenciRepository = ogrenciRepository;
        this.cezaService = cezaService;
        this.emailService = emailService;
    }

    public List<Kitap> tumKitaplar(String arama) {
        if (arama != null && !arama.isBlank()) {
            return kitapRepository.findByKitapAdiContainingIgnoreCase(arama);
        }
        return kitapRepository.findAll();
    }

    public Kitap kitapEkle(Kitap kitap, Long yazarId, Long kategoriId) {
        if (yazarId != null) {
            kitap.setYazar(yazarRepository.findById(yazarId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Yazar bulunamadı")));
        }
        if (kategoriId != null) {
            kitap.setKategori(kategoriRepository.findById(kategoriId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kategori bulunamadı")));
        }
        if (kitap.getDurum() == null || kitap.getDurum().isEmpty()) {
            kitap.setDurum("Rafta");
        }
        return kitapRepository.save(kitap);
    }

    public Kitap kitapGuncelle(Long id, Kitap guncel, Long yazarId, Long kategoriId) {
        Kitap mevcut = kitapRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kitap bulunamadı"));
        mevcut.setKitapAdi(guncel.getKitapAdi());
        mevcut.setIsbn(guncel.getIsbn());
        mevcut.setYayinYili(guncel.getYayinYili());
        if (yazarId != null) {
            mevcut.setYazar(yazarRepository.findById(yazarId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Yazar bulunamadı")));
        }
        if (kategoriId != null) {
            mevcut.setKategori(kategoriRepository.findById(kategoriId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kategori bulunamadı")));
        }
        return kitapRepository.save(mevcut);
    }

    public void kitapSil(Long id) {
        if (!kitapRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kitap bulunamadı");
        }
        kitapRepository.deleteById(id);
    }

    public Kitap oduncAl(Long kitapId, Integer ogrenciId) {
        Kitap kitap = kitapRepository.findById(kitapId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kitap bulunamadı"));
        if (!"Rafta".equalsIgnoreCase(kitap.getDurum())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kitap şu anda uygun değil");
        }
        Ogrenci ogrenci = ogrenciRepository.findById(ogrenciId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Öğrenci bulunamadı"));

        kitap.setDurum("Ödünç Alındı");
        kitap.setOduncAlanOgrenci(ogrenci);
        LocalDate bugun = LocalDate.now();
        kitap.setOduncTarihi(bugun);
        kitap.setIadeTarihi(bugun.plusDays(STANDART_IADE_SURESI));

        return kitapRepository.save(kitap);
    }

    public Kitap iadeEt(Long kitapId) {
        Kitap kitap = kitapRepository.findById(kitapId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kitap bulunamadı"));
        if (!"Ödünç Alındı".equalsIgnoreCase(kitap.getDurum()) && 
            !"Odunc_Alindi".equalsIgnoreCase(kitap.getDurum()) &&
            !"ODUNC_ALINDI".equalsIgnoreCase(kitap.getDurum())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kitap ödünçte değil");
        }

        LocalDate bugun = LocalDate.now();
        LocalDate iadeTarihi = kitap.getIadeTarihi();
        Ogrenci ogrenci = kitap.getOduncAlanOgrenci();

        if (iadeTarihi != null && bugun.isAfter(iadeTarihi) && ogrenci != null) {
            long gecikmeGun = ChronoUnit.DAYS.between(iadeTarihi, bugun);
            double cezaTutari = gecikmeGun * GECIKME_CEZA_KATSAYISI;
            cezaService.olustur(ogrenci, kitap, cezaTutari);

            String mesaj = String.format(
                    "Merhaba %s, %s kitabını %d gün geç iade ettiniz. Ceza tutarı: %.2f TL",
                    ogrenci.getIsim(), kitap.getKitapAdi(), gecikmeGun, cezaTutari
            );
            emailService.gecIadeBildirimiGonder(
                    ogrenci.getEmail(),
                    "Geç İade Bilgilendirmesi",
                    mesaj
            );
        }

        kitap.setDurum("Rafta");
        kitap.setOduncAlanOgrenci(null);
        kitap.setOduncTarihi(null);
        kitap.setIadeTarihi(null);
        return kitapRepository.save(kitap);
    }
}



