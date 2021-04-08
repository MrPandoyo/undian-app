package com.lomba.undianapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table
public class Peserta extends BaseEntity{

    @NotEmpty private String bbib;
    private String ktp;
    @NotEmpty private String firstName;
    private String lastName;

    @NotEmpty private String email;
    @NotEmpty private String phone;

    private String province;
    private String city;
    private String address;

    @Enumerated(EnumType.STRING)
    @NotNull private Peserta.Category category;

    private Boolean hasWin = Boolean.FALSE;

    public enum Category {
        FIVE_KM,TEN_KM,TWENTY_NINE_KM
    }

    public String getFullName(){
        return this.firstName +" "+this.lastName;
    }

}
