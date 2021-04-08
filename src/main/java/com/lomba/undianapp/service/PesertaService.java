package com.lomba.undianapp.service;

import com.lomba.undianapp.dao.PesertaDao;
import com.lomba.undianapp.dao.UndianDao;
import com.lomba.undianapp.dto.CommonSearch;
import com.lomba.undianapp.entity.Peserta;
import com.lomba.undianapp.helper.CsvReaderProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import org.thymeleaf.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class PesertaService {

    @Autowired private PesertaDao pesertaDao;
    @Autowired private UndianDao undianDao;
    @Autowired private FileService fileService;

    private static final CsvPreference PIPE_DELIMITED = new CsvPreference.Builder('"', '|', "\n").build();

    public Page<Peserta> getPesertaPage(CommonSearch params, Pageable pageable){
        if(params.getCategory() == null) params.setCategory(Peserta.Category.FIVE_KM);

        if(StringUtils.isEmpty(params.getValue())){
            return pesertaDao.findByCategory(params.getCategory(),pageable);
        }else{
            return pesertaDao.findByCategoryAndFirstNameContainingIgnoreCase(params.getCategory(),params.getValue(),pageable);
        }
    }

    public void importData(String category, MultipartFile fileCsv) throws IOException {

        File file = fileService.moveCsv(fileCsv);
        ICsvBeanReader beanReader = null;
        List<Peserta> listPeserta = new ArrayList<>();

        try {
            beanReader = new CsvBeanReader(new FileReader(file), PIPE_DELIMITED);
            // the header elements are used to map the values to the bean (names must match)
            final String[] header = beanReader.getHeader(true);
            final CellProcessor[] processors = CsvReaderProcessor.getpesertaProcessor();

            Peserta peserta;
            while( (peserta = beanReader.read(Peserta.class, header, processors)) != null ) {
                    peserta.setCategory(Peserta.Category.valueOf(category));
                    listPeserta.add(peserta);
                }

        } finally {
            pesertaDao.saveAll(listPeserta);
            if( beanReader != null ) { beanReader.close(); }
            if(file != null){ file.delete(); }
        }
    }

    @Transactional
    public void deleteByCategory(String category) throws Exception {
        Long undianCount = undianDao.countByCategory(Peserta.Category.valueOf(category));
        if(undianCount > 0){
            throw new Exception("Harap hapus undian di history undian terlebih dahulu");
        }
        pesertaDao.deleteByCategory(Peserta.Category.valueOf(category));
    }

}
