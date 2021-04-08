package com.lomba.undianapp.dao;

import com.lomba.undianapp.entity.Peserta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PesertaDao extends PagingAndSortingRepository<Peserta, String> {

    Page<Peserta> findByCategory(Peserta.Category category, Pageable pageable);
    Page<Peserta> findByCategoryAndHasWin(Peserta.Category category, Boolean hasWin, Pageable pageable);
    Page<Peserta> findByCategoryAndFirstNameContainingIgnoreCase(Peserta.Category category, String firstName, Pageable pageable);

    List<Peserta> findByCategory(Peserta.Category category);

    Long countByCategory(Peserta.Category category);
    Long countByCategoryAndHasWin(Peserta.Category category, Boolean hasWin);

//    List<Peserta> findBy
    void deleteByCategory(Peserta.Category category);
}
