package com.lomba.undianapp.helper;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class CsvReaderProcessor {

    public static CellProcessor[] getpesertaProcessor() {


        final CellProcessor[] processors = new CellProcessor[] {
                new NotNull(), // bbib
                new Optional(), // ktp
                new NotNull(), // firstname
                new NotNull(), // lastname
                new NotNull(), // email
                new NotNull(), // phone
                new NotNull(), // province
                new NotNull(), // city
                new NotNull(), // address
        };

        return processors;
    }

}
