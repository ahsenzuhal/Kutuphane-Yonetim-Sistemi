package com.kutuphane.AkilliKutuphane.controller;

import com.kutuphane.AkilliKutuphane.dto.BorrowRequest;
import com.kutuphane.AkilliKutuphane.dto.OduncIslemResponseDTO;
import com.kutuphane.AkilliKutuphane.model.OduncIslem;
import com.kutuphane.AkilliKutuphane.service.OduncIslemService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/odunc-islemler")
@CrossOrigin("*")
public class OduncIslemController {

    private final OduncIslemService oduncIslemService;

    public OduncIslemController(OduncIslemService oduncIslemService) {
        this.oduncIslemService = oduncIslemService;
    }

    /**
     * Yeni ödünç verme işlemi
     * Service void döndüğü için burada sadece Başarılı mesajı dönüyoruz.
     */
    @PostMapping("/odunc-ver")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> oduncVer(@RequestBody BorrowRequest request) {
        oduncIslemService.oduncVer(
            request.getKitapId(), 
            request.getOgrenciId()
        );
        return ResponseEntity.ok("Kitap başarıyla ödünç verildi.");
    }

    /**
     * Kitap iade işlemi
     */
    @PostMapping("/{kitapId}/iade")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> iadeAl(@PathVariable Long kitapId) {
        oduncIslemService.iadeAl(kitapId);
        return ResponseEntity.ok("Kitap başarıyla iade alındı ve ceza kontrolü yapıldı.");
    }

    /**
     * Tüm aktif ödünç işlemlerini listele
     */
    @GetMapping("/aktif")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OduncIslemResponseDTO>> tumAktifOduncIslemleri() {
        List<OduncIslem> islemler = oduncIslemService.tumAktifOduncIslemleri();
        List<OduncIslemResponseDTO> dtos = islemler.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // NOT: Öğrenciye özel getirme metodlarını (ogrencininOduncIslemleri vb.)
    // Service katmanına henüz eklemediğimiz için şimdilik kaldırdım.
    // Sistem çalıştığında o özellikleri ayrıca ekleyebiliriz.

    /**
     * Entity'yi DTO'ya dönüştürür
     */
    private OduncIslemResponseDTO entityToDTO(OduncIslem oduncIslem) {
        OduncIslemResponseDTO dto = new OduncIslemResponseDTO();
        dto.setId(oduncIslem.getId());
        
        if (oduncIslem.getKitap() != null) {
            dto.setKitapId(oduncIslem.getKitap().getId());
            dto.setKitapAdi(oduncIslem.getKitap().getKitapAdi());
        }
        
        if (oduncIslem.getOgrenci() != null) {
            dto.setOgrenciId(oduncIslem.getOgrenci().getId());
            // Eğer Ogrenci sınıfında getIsim() yoksa getAd() veya getAdSoyad() kullan
            dto.setOgrenciAdi(oduncIslem.getOgrenci().getIsim()); 
            dto.setOgrenciEmail(oduncIslem.getOgrenci().getEmail());
        }
        
        dto.setAlisTarihi(oduncIslem.getAlisTarihi());
        dto.setPlanlananIadeTarihi(oduncIslem.getPlanlananIadeTarihi());
        dto.setGercekIadeTarihi(oduncIslem.getGercekIadeTarihi());
        dto.setDurum(oduncIslem.getDurum());
        
        // Gecikme hesaplama (Basit kontrol)
        boolean gecikmis = oduncIslem.getGercekIadeTarihi() == null && 
                           java.time.LocalDate.now().isAfter(oduncIslem.getPlanlananIadeTarihi());
        dto.setGecikmis(gecikmis);
        
        return dto;
    }
}