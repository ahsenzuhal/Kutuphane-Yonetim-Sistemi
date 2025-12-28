package com.kutuphane.AkilliKutuphane.service;

import com.kutuphane.AkilliKutuphane.model.*;
import com.kutuphane.AkilliKutuphane.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class OduncIslemService {
    private final OduncIslemRepository oduncIslemRepository;
    private final KitapRepository kitapRepository;
    private final OgrenciRepository ogrenciRepository;

    public OduncIslemService(OduncIslemRepository oduncIslemRepository, KitapRepository kitapRepository, OgrenciRepository ogrenciRepository) {
        this.oduncIslemRepository = oduncIslemRepository;
        this.kitapRepository = kitapRepository;
        this.ogrenciRepository = ogrenciRepository;
    }

    public List<OduncIslem> ogrenciyeGoreGetir(Integer ogrenciId) {
        return oduncIslemRepository.findByOgrenciId(ogrenciId);
    }

    public List<OduncIslem> tumAktifOduncIslemleri() {
        // "Aktif" durumundaki tüm kayıtları detaylarıyla getiriyoruz
        return oduncIslemRepository.findByDurumWithDetails("Aktif");
    }

    @Transactional
    public void oduncVer(Long kitapId, Integer ogrenciId) {
        Kitap kitap = kitapRepository.findById(kitapId).orElseThrow(() -> new RuntimeException("Kitap yok"));
        Ogrenci ogrenci = ogrenciRepository.findById(ogrenciId).orElseThrow(() -> new RuntimeException("Öğrenci yok"));

        kitap.setDurum("Ödünç Verildi");
        kitapRepository.save(kitap);

        OduncIslem islem = new OduncIslem();
        islem.setKitap(kitap);
        islem.setOgrenci(ogrenci);
        islem.setAlisTarihi(LocalDate.now());
        islem.setPlanlananIadeTarihi(LocalDate.now().plusDays(14));
        islem.setDurum("Aktif");
        oduncIslemRepository.save(islem);
    }

    @Transactional
public void iadeAl(Long kitapId) { // Controller burayı çağırıyor
    // 1. Kitabı bul ve durumunu 'Rafta' yap
    Kitap kitap = kitapRepository.findById(kitapId)
            .orElseThrow(() -> new RuntimeException("Kitap bulunamadı"));
    kitap.setDurum("Rafta");
    kitapRepository.save(kitap);

    // 2. Aktif ödünç kaydını bul ve kapat (Repository'deki yeni metodu kullanıyor)
    OduncIslem islem = oduncIslemRepository.findByKitapIdAndDurum(kitapId, "Aktif")
            .orElseThrow(() -> new RuntimeException("Aktif ödünç kaydı bulunamadı!"));
    
    islem.setDurum("İade Edildi");
    islem.setGercekIadeTarihi(java.time.LocalDate.now());
    oduncIslemRepository.save(islem);
}
}