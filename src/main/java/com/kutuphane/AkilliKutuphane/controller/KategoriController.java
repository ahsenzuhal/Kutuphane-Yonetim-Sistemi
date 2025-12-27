package com.kutuphane.AkilliKutuphane.controller;

import com.kutuphane.AkilliKutuphane.model.Kategori;
import com.kutuphane.AkilliKutuphane.service.KategoriService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kategoriler")
@CrossOrigin("*")
public class KategoriController {

    private final KategoriService kategoriService;

    public KategoriController(KategoriService kategoriService) {
        this.kategoriService = kategoriService;
    }

    @GetMapping
    public List<Kategori> tumu() {
        return kategoriService.tumKategoriler();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Kategori> ekle(@RequestBody Kategori kategori) {
        return new ResponseEntity<>(kategoriService.kaydet(kategori), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Kategori> guncelle(@PathVariable Long id, @RequestBody Kategori kategori) {
        Kategori mevcut = kategoriService.getir(id);
        if (mevcut != null) {
            mevcut.setKategoriAdi(kategori.getKategoriAdi());
            return ResponseEntity.ok(kategoriService.kaydet(mevcut));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> sil(@PathVariable Long id) {
        kategoriService.sil(id);
        return ResponseEntity.noContent().build();
    }
}

