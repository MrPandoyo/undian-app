package com.lomba.undianapp.dto;

import com.lomba.undianapp.entity.Peserta;
import lombok.Data;

@Data
public class CommonSearch {

    private String value;
    private Peserta.Category category = Peserta.Category.FIVE_KM;

}
