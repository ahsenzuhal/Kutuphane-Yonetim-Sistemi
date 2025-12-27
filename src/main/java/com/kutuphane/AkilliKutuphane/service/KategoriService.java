package com.kutuphane.AkilliKutuphane.service;

import com.kutuphane.AkilliKutuphane.model.Kategori;
import com.kutuphane.AkilliKutuphane.repository.KategoriRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KategoriService {

    private final KategoriRepository kategoriRepository;

    public KategoriService(KategoriRepository kategoriRepository) {
        this.kategoriRepository = kategoriRepository;
    }

    public List<Kategori> tumKategoriler() {
        return kategoriRepository.findAll();
    }

    public Kategori kaydet(Kategori kategori) {
        return kategoriRepository.save(kategori);
    }

    public Kategori getir(Long id) {
        return kategoriRepository.findById(id).orElse(null);
    }

    public void sil(Long id) {
        if (!kategoriRepository.existsById(id)) {
            throw new RuntimeException("Kategori bulunamadı");
        }
        kategoriRepository.deleteById(id);
    }
}



