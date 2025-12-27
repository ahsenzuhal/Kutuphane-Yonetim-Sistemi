package com.kutuphane.AkilliKutuphane.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kutuphane.AkilliKutuphane.model.Ogrenci;

@Repository // Bu sınıfın bir Repository bileşeni olduğunu Spring'e belirtir
// JpaRepository< Hangi Entity?, Bu Entity'nin ID tipi >
public interface OgrenciRepository extends JpaRepository<Ogrenci, Integer> {
    // save(), findById(), findAll(), delete() gibi metotlar
    // JpaRepository'den otomatik olarak miras alındı!
}