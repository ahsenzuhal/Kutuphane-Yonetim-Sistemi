package com.kutuphane.AkilliKutuphane.controller;

import com.kutuphane.AkilliKutuphane.dto.BorrowRequest;
import com.kutuphane.AkilliKutuphane.dto.OduncIslemResponseDTO;
import com.kutuphane.AkilliKutuphane.model.OduncIslem;
import com.kutuphane.AkilliKutuphane.service.OduncIslemService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/odunc-islemler")
@CrossOrigin("*")
public class OduncIslemController {
    private final OduncIslemService oduncIslemService;

    public OduncIslemController(OduncIslemService oduncIslemService) {
        this.oduncIslemService = oduncIslemService;
    }

    @GetMapping("/aktif")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OduncIslemResponseDTO>> tumAktifOduncIslemleri() {
        List<OduncIslem> islemler = oduncIslemService.tumAktifOduncIslemleri();
        List<OduncIslemResponseDTO> dtos = islemler.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/odunc-ver")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> oduncVer(@RequestBody BorrowRequest request) {
        oduncIslemService.oduncVer(request.getKitapId(), request.getOgrenciId());
        // String yerine JSON dönüyoruz, böylece 'Unexpected token' hatası çözülür
        return ResponseEntity.ok(Map.of("message", "Kitap başarıyla ödünç verildi."));
    }

    @PostMapping("/{kitapId}/iade")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> iadeAl(@PathVariable Long kitapId) {
        oduncIslemService.iadeAl(kitapId);
        return ResponseEntity.ok(Map.of("message", "Kitap başarıyla iade edildi."));
    }

    @GetMapping("/ogrenci/{ogrenciId}")
    public ResponseEntity<List<OduncIslemResponseDTO>> ogrenciIslemleri(@PathVariable Integer ogrenciId) {
        List<OduncIslem> islemler = oduncIslemService.ogrenciyeGoreGetir(ogrenciId);
        List<OduncIslemResponseDTO> dtos = islemler.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    private OduncIslemResponseDTO entityToDTO(OduncIslem oduncIslem) {
        OduncIslemResponseDTO dto = new OduncIslemResponseDTO();
        dto.setId(oduncIslem.getId());
        dto.setKitapId(oduncIslem.getKitap().getId());
        dto.setKitapAdi(oduncIslem.getKitap().getKitapAdi());
        dto.setOgrenciId(oduncIslem.getOgrenci().getId());
        dto.setOgrenciAdi(oduncIslem.getOgrenci().getIsim()); // Admin listesi için önemli!
        dto.setAlisTarihi(oduncIslem.getAlisTarihi());
        dto.setPlanlananIadeTarihi(oduncIslem.getPlanlananIadeTarihi());
        dto.setDurum(oduncIslem.getDurum());
        return dto;
    }
}