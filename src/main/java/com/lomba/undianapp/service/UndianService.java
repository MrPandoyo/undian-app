package com.lomba.undianapp.service;

import com.lomba.undianapp.dao.PesertaDao;
import com.lomba.undianapp.dao.UndianDao;
import com.lomba.undianapp.dto.CommonSearch;
import com.lomba.undianapp.entity.Peserta;
import com.lomba.undianapp.entity.Undian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class UndianService {

    @Autowired private PesertaDao pesertaDao;
    @Autowired private UndianDao undianDao;

    public Page<Undian> getPagePemenangUndian(CommonSearch params, Pageable pageable){

        if(params.getCategory() == null) params.setCategory(Peserta.Category.FIVE_KM);

        return undianDao.findByCategory(params.getCategory(), pageable);
    }

    public Undian getById(CommonSearch params){
        return undianDao.findById(params.getValue()).get();
    }

    public Undian undiPemenang(String category) throws Exception {
        Peserta.Category cat = Peserta.Category.valueOf(category);
        Long count = pesertaDao.countByCategoryAndHasWin(cat,false);

        if(winnerIsFull(cat)){
            throw new Exception("Semua peserta telah memenangkan undian");
        }

        Random r = new Random();

        int idx = r.ints(1,count.intValue() + 1).findFirst().getAsInt();
        Page<Peserta> pesertaPage = pesertaDao.findByCategoryAndHasWin(cat,false, PageRequest.of(idx -1, 1));
        if (pesertaPage.hasContent()) {
            Peserta winner = pesertaPage.getContent().get(0);

            Undian existingPemenang = undianDao.findByWinnerId(winner.getId());
            if(existingPemenang != null){
                undiPemenang(category);
            }

            Undian undian = new Undian();
            undian.setCategory(cat);
            undian.setWinner(winner);
            undian = undianDao.save(undian);

            winner.setHasWin(true);
            pesertaDao.save(winner);

            return undian;
        }else{
            throw new Exception("Peserta tidak dapat ditemukan");
        }

    }


    @Transactional
    public void deleteByCategory(String category){
        List<Peserta> pesertas = pesertaDao.findByCategory(Peserta.Category.valueOf(category));
        for (Peserta p:pesertas) {
            p.setHasWin(false);
        }
        pesertaDao.saveAll(pesertas);
        undianDao.deleteByCategory(Peserta.Category.valueOf(category));
    }

    public boolean winnerIsFull(Peserta.Category category){
        Long countPeserta = pesertaDao.countByCategory(category);
        Long countUndian = undianDao.countByCategory(category);

        return countPeserta != 0 && countPeserta.equals(countUndian);
    }

}
