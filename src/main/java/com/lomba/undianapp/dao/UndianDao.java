package com.lomba.undianapp.dao;

import com.lomba.undianapp.entity.Peserta;
import com.lomba.undianapp.entity.Undian;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UndianDao extends PagingAndSortingRepository<Undian, String> {
    Undian findByWinnerId(String pesertaId);

    Long countByCategory(Peserta.Category category);

    Page<Undian> findByCategory(Peserta.Category category, Pageable pageable);

    void deleteByCategory(Peserta.Category category);
}
