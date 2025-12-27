package com.kutuphane.AkilliKutuphane.config;

import com.kutuphane.AkilliKutuphane.model.Kullanici;
import com.kutuphane.AkilliKutuphane.repository.KullaniciRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final KullaniciRepository kullaniciRepository;

    public CustomUserDetailsService(KullaniciRepository kullaniciRepository) {
        this.kullaniciRepository = kullaniciRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Aranan Kullanıcı: " + username);
        Kullanici kullanici = kullaniciRepository.findByKullaniciAdi(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + username));

        var authority = new SimpleGrantedAuthority("ROLE_" + kullanici.getRol());
        return new User(kullanici.getKullaniciAdi(), kullanici.getSifre(), Collections.singleton(authority));
    }
}



