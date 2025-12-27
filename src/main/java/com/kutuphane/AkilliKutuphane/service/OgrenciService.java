package com.kutuphane.AkilliKutuphane.service;

import com.kutuphane.AkilliKutuphane.model.Ogrenci;
import com.kutuphane.AkilliKutuphane.repository.OgrenciRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OgrenciService {

    private final OgrenciRepository ogrenciRepository;

    public OgrenciService(OgrenciRepository ogrenciRepository) {
        this.ogrenciRepository = ogrenciRepository;
    }

    public List<Ogrenci> hepsiniGetir() {
        return ogrenciRepository.findAll();
    }

    public Ogrenci kaydet(Ogrenci ogrenci) {
        return ogrenciRepository.save(ogrenci);
    }

    public Ogrenci getir(Integer id) {
        return ogrenciRepository.findById(id).orElse(null);
    }

    public void sil(Integer id) {
        ogrenciRepository.deleteById(id);
    }
}

