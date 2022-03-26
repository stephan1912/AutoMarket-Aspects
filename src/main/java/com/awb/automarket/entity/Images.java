package com.awb.automarket.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Images")
public class Images {
    @Id
    @GeneratedValue
    private int id;
    @Column(length = 150, nullable = false)
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    private Advert advert;
}
