package com.kutuphane.AkilliKutuphane;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.kutuphane.AkilliKutuphane.service.KullaniciService;

@SpringBootApplication
public class AkilliKutuphaneApplication {

    public static void main(String[] args) {
        SpringApplication.run(AkilliKutuphaneApplication.class, args);
    }

    @Bean
    public CommandLineRunner baslangicAyari(KullaniciService kullaniciService) {
        return args -> {
            // Geliştirme ortamında admin kullanıcısı yoksa oluştur
            kullaniciService.adminYoksaOlustur("admin", "admin123", "ADMIN");
            System.out.println("-- ADMIN KULLANICI HAZIR: admin / admin123");
        };
    }
}