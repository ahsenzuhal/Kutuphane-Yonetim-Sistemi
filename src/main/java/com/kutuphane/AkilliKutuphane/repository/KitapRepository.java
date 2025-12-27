package com.kutuphane.AkilliKutuphane.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kutuphane.AkilliKutuphane.model.Kitap;

import java.util.List;

@Repository
public interface KitapRepository extends JpaRepository<Kitap, Long> {
    List<Kitap> findByKitapAdiContainingIgnoreCase(String keyword);
}