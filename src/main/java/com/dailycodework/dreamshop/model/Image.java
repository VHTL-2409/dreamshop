package com.dailycodework.dreamshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;


@Getter
@Setter
@AllArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    private String fileType;
    @Lob
    private Blob image;
    private String url;
    @ManyToOne
    @JoinColumn(
            name = "product_id"
    )
    private Product product;

}
