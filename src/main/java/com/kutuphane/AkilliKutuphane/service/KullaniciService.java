package com.kutuphane.AkilliKutuphane.service;

import com.kutuphane.AkilliKutuphane.model.Kullanici;
import com.kutuphane.AkilliKutuphane.model.Ogrenci;
import com.kutuphane.AkilliKutuphane.repository.KullaniciRepository;
import com.kutuphane.AkilliKutuphane.repository.OgrenciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KullaniciService {

    private final KullaniciRepository kullaniciRepository;
    private final PasswordEncoder passwordEncoder;
    private final OgrenciRepository ogrenciRepository;

    @Autowired
    public KullaniciService(KullaniciRepository kullaniciRepository, 
                            PasswordEncoder passwordEncoder, 
                            OgrenciRepository ogrenciRepository) {
        this.kullaniciRepository = kullaniciRepository;
        this.passwordEncoder = passwordEncoder;
        this.ogrenciRepository = ogrenciRepository;
    }

    @Transactional // Veritabanı tutarlılığı için eklendi
    public Kullanici kullaniciKaydet(Kullanici kullanici) {
        // Şifreleme işlemi
        kullanici.setSifre(passwordEncoder.encode(kullanici.getSifre()));
        Kullanici kaydedilen = kullaniciRepository.save(kullanici);

        //  öğrenci oluşturulan yer:
    if ("USER".equalsIgnoreCase(kaydedilen.getRol())) {
        Ogrenci yeniOgrenci = new Ogrenci();
        yeniOgrenci.setIsim(kaydedilen.getIsim()); 
        yeniOgrenci.setEmail(kaydedilen.getEmail());
        yeniOgrenci.setKullaniciAdi(kaydedilen.getKullaniciAdi());
        ogrenciRepository.save(yeniOgrenci);
    }   
        
        return kaydedilen;
    }
    
    public boolean kullaniciVarMi(String kullaniciAdi) {
        return kullaniciRepository.findByKullaniciAdi(kullaniciAdi).isPresent();
    }

    public Kullanici kullaniciBul(String kullaniciAdi) {
        return kullaniciRepository.findByKullaniciAdi(kullaniciAdi).orElse(null);
    }

    public void adminYoksaOlustur(String kullaniciAdi, String sifre, String rol) {
        if (!kullaniciVarMi(kullaniciAdi)) {
            Kullanici yeni = new Kullanici(kullaniciAdi, sifre, rol);
            kullaniciKaydet(yeni);
        }
    }
}