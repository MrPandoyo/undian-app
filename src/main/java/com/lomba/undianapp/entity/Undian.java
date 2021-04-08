package com.lomba.undianapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table
public class Undian extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @NotNull private Peserta.Category category;

    @CreatedDate
    private LocalDateTime createdDate = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "id_peserta", nullable = false, columnDefinition = "varchar(36)",unique = true)
    private Peserta winner;
}
