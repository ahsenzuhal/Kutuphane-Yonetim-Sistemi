package com.kutuphane.AkilliKutuphane.controller;

import com.kutuphane.AkilliKutuphane.model.Yazar;
import com.kutuphane.AkilliKutuphane.service.YazarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/yazarlar")
@CrossOrigin("*")
public class YazarController {

    private final YazarService yazarService;

    @Autowired
    public YazarController(YazarService yazarService) {
        this.yazarService = yazarService;
    }

    // GET: Tüm yazarları listele
    @GetMapping
    public List<Yazar> tumYazarlar() {
        return yazarService.tumYazarlariGetir();
    }

    // POST: Yeni yazar ekle
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Yazar> yazarEkle(@RequestBody Yazar yazar) {
        Yazar yeniYazar = yazarService.yazarKaydet(yazar);
        return new ResponseEntity<>(yeniYazar, HttpStatus.CREATED);
    }

    // PUT: Yazar güncelle
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Yazar> yazarGuncelle(@PathVariable Long id, @RequestBody Yazar yazarBilgileri) {
        Yazar mevcutYazar = yazarService.yazarGetir(id);
        if (mevcutYazar != null) {
            mevcutYazar.setAdSoyad(yazarBilgileri.getAdSoyad() != null ? 
                yazarBilgileri.getAdSoyad() : yazarBilgileri.getAdSoyad());
            mevcutYazar.setBiyografi(yazarBilgileri.getBiyografi());
            Yazar guncellenmisYazar = yazarService.yazarKaydet(mevcutYazar);
            return new ResponseEntity<>(guncellenmisYazar, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE: Yazar sil
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> yazarSil(@PathVariable Long id) {
        yazarService.yazarSil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

