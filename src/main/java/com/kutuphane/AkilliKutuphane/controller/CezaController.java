package com.kutuphane.AkilliKutuphane.controller;

import com.kutuphane.AkilliKutuphane.model.Ceza;
import com.kutuphane.AkilliKutuphane.service.CezaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cezalar")
@CrossOrigin("*")
public class CezaController {

    private final CezaService cezaService;

    public CezaController(CezaService cezaService) {
        this.cezaService = cezaService;
    }

    @GetMapping
    public List<Ceza> liste() {
        return cezaService.tumCezalar();
    }

    @PatchMapping("/{id}/odeme")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Ceza> odemeGuncelle(@PathVariable Integer id, @RequestParam(defaultValue = "Odenmedi") String durum) {
        Ceza ceza = cezaService.odemeDurumuGuncelle(id, durum);
        return ceza != null ? ResponseEntity.ok(ceza) : ResponseEntity.notFound().build();
    }
}

