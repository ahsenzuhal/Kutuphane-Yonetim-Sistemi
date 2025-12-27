package com.kutuphane.AkilliKutuphane.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank(message = "Kullanıcı adı boş olamaz")
    @Size(min = 4, max = 50, message = "Kullanıcı adı en az 4 karakter olmalıdır")
    private String kullaniciAdi;

    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 6, message = "Şifre en az 6 karakter olmalıdır")
    private String sifre;

    private String rol;

    @NotBlank(message = "İsim boş olamaz")
    @Size(max = 100)
    private String isim;

    @NotBlank(message = "E-posta boş olamaz")
    @Email(message = "Geçerli bir e-posta giriniz")
    private String email;

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}



