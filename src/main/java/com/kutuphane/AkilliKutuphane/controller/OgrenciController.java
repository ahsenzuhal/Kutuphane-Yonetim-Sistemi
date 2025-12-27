package com.kutuphane.AkilliKutuphane.controller;

import com.kutuphane.AkilliKutuphane.model.Ogrenci;
import com.kutuphane.AkilliKutuphane.repository.OgrenciRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ogrenciler")
@CrossOrigin("*")
public class OgrenciController {

    private final OgrenciRepository ogrenciRepository;

    public OgrenciController(OgrenciRepository ogrenciRepository) {
        this.ogrenciRepository = ogrenciRepository;
    }

    @GetMapping
    public List<Ogrenci> tumOgrenciler() {
        return ogrenciRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> ogrenciEkle(@RequestBody Ogrenci ogrenci) {
        try {
            ogrenciRepository.save(ogrenci);
            return ResponseEntity.ok("Öğrenci başarıyla eklendi.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Hata: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> ogrenciSil(@PathVariable Integer id) {
        try {
            ogrenciRepository.deleteById(id);
            return ResponseEntity.ok("Öğrenci silindi.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Silinemedi.");
        }
    }
}