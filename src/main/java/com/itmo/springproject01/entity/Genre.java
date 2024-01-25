package com.itmo.springproject01.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_genres")
public class Genre {
    @Id
    private String url; // PK
    @NotNull
    @Size(min = 3, max = 100)
    @Column(nullable = false)
    private String name;
    @NotNull
    @Column(nullable = false)
    private String description;
    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;
    @Column(name = "in_archive", nullable = false)
    private boolean inArchive;
    @OneToMany(mappedBy = "genre")
    private List<Picture> pictures = new ArrayList<>();

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public boolean isInArchive() {
        return inArchive;
    }
}
