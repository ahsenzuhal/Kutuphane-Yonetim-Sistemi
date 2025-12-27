package com.kutuphane.AkilliKutuphane.service;

import com.kutuphane.AkilliKutuphane.model.Yazar;
import com.kutuphane.AkilliKutuphane.repository.YazarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class YazarService {

    private final YazarRepository yazarRepository;

    @Autowired
    public YazarService(YazarRepository yazarRepository) {
        this.yazarRepository = yazarRepository;
    }

    // Tüm yazarları getir (READ)
    public List<Yazar> tumYazarlariGetir() {
        return yazarRepository.findAll();
    }

    // Yeni yazar ekle veya güncelle (CREATE / UPDATE)
    public Yazar yazarKaydet(Yazar yazar) {
        return yazarRepository.save(yazar);
    }

    // ID'ye göre yazar getir (READ)
    public Yazar yazarGetir(Long id) {
        return yazarRepository.findById(id).orElse(null);
    }

    // Yazar sil (DELETE)
    public void yazarSil(Long id) {
        if (!yazarRepository.existsById(id)) {
            throw new RuntimeException("Yazar bulunamadı");
        }
        yazarRepository.deleteById(id);
    }
}