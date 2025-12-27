package com.kutuphane.AkilliKutuphane.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kutuphane.AkilliKutuphane.model.Ceza;

@Repository
public interface CezaRepository extends JpaRepository<Ceza, Integer> {
}