package com.kutuphane.AkilliKutuphane.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kutuphane.AkilliKutuphane.model.OduncIslem;

import java.util.List;
import java.util.Optional;

/**
 * Ödünç işlemleri için Repository
 */
@Repository
public interface OduncIslemRepository extends JpaRepository<OduncIslem, Long> {
    
    /**
     * Belirli bir öğrencinin aktif ödünç işlemlerini getirir
     */
    List<OduncIslem> findByOgrenciIdAndDurum(Integer ogrenciId, String durum);
    
    /**
     * Belirli bir kitabın aktif ödünç işlemini getirir
     */
    Optional<OduncIslem> findByKitapIdAndDurum(Long kitapId, String durum);
    
    /**
     * Tüm aktif ödünç işlemlerini getirir
     */
    List<OduncIslem> findByDurum(String durum);
    
    /**
     * Belirli bir öğrencinin tüm ödünç işlemlerini getirir (geçmiş dahil)
     */
    List<OduncIslem> findByOgrenciIdOrderByAlisTarihiDesc(Integer ogrenciId);
}


