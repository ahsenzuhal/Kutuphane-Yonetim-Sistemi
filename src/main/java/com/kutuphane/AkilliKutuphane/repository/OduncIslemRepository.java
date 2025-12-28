package com.kutuphane.AkilliKutuphane.repository;

import com.kutuphane.AkilliKutuphane.model.OduncIslem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface OduncIslemRepository extends JpaRepository<OduncIslem, Long> {
    
    @Query("SELECT o FROM OduncIslem o JOIN FETCH o.kitap JOIN FETCH o.ogrenci WHERE o.ogrenci.id = :ogrenciId")
    List<OduncIslem> findByOgrenciId(@Param("ogrenciId") Integer ogrenciId);

    // ADMIN Paneli için: Tüm aktif işlemleri detaylarıyla (Kitap/Öğrenci) getirir
    @Query("SELECT o FROM OduncIslem o JOIN FETCH o.kitap JOIN FETCH o.ogrenci WHERE o.durum = :durum")
    List<OduncIslem> findByDurumWithDetails(@Param("durum") String durum);

    Optional<OduncIslem> findByKitapIdAndDurum(Long kitapId, String durum);
}