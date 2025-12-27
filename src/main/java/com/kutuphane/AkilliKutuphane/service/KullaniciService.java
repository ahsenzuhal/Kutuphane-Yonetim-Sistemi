package com.kutuphane.AkilliKutuphane.service;

import com.kutuphane.AkilliKutuphane.model.Kullanici;
import com.kutuphane.AkilliKutuphane.repository.KullaniciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service // Bu sınıfın bir "Service" bileşeni olduğunu belirtir
public class KullaniciService {

    private final KullaniciRepository kullaniciRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public KullaniciService(KullaniciRepository kullaniciRepository, PasswordEncoder passwordEncoder) {
        this.kullaniciRepository = kullaniciRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Yeni bir kullanıcıyı kaydederken şifresini şifreler.
     * @param kullanici Kaydedilecek Kullanici nesnesi (düz metin şifre ile)
     * @return Kaydedilen Kullanici nesnesi (şifrelenmiş şifre ile)
     */
    public Kullanici kullaniciKaydet(Kullanici kullanici) {
        // 1. Kullanıcının düz metin şifresini al
        String duzMetinSifre = kullanici.getSifre();
        
        // 2. Şifreyi BCrypt ile şifrele
        String sifrelenmisSifre = passwordEncoder.encode(duzMetinSifre);
        
        // 3. Kullanıcının şifresini şifrelenmiş olanla güncelle
        kullanici.setSifre(sifrelenmisSifre);
        
        // 4. Veritabanına kaydet
        return kullaniciRepository.save(kullanici);
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