package com.reacconmind.reacconmind.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Hashtag")
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHashtag")
    @JsonProperty("idHashtag")
    private int idHashtag;

    @NotBlank(message = "The content must not be null and must contain at least one non-whitespace character")
    @Size(min = 1, max = 50, message = "The content must be at most 50 characters, and has at least one character")
    @Column(unique = true, nullable = false, name = "name")
    @JsonProperty("name")
    private String name;

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PublicationHashtag> publicationHashtags;

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TendencyHashtag> tendencyHashtags;

    public Hashtag() {
    }

    public Hashtag(int idHashtag, String name) {
        this.idHashtag = idHashtag;
        this.name = name;
    }

    public int getIdHashtag() {
        return idHashtag;
    }

    public void setIdHashtag(int idHashtag) {
        this.idHashtag = idHashtag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
