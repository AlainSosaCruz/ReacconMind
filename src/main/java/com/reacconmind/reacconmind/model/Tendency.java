package com.reacconmind.reacconmind.model;

import java.sql.Date;
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
@Table(name="Tendency")
public class Tendency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idTendency")
    @JsonProperty("idTendency")
    private int idTendency;
    @NotBlank(message = "The content must not be null and must contain at least one non-whitespace character")
    @Size(min = 1, max = 50, message = "The content must be at most 50 characters, and has at least one character")
    @Column(nullable = false, name = "name")
    @JsonProperty("name")
    private String name;
    private Date dateBegin;
    private Date dateEnd;

    @OneToMany(mappedBy = "tendency", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TendencyHashtag> tendencyHashtags;

    public int getIdTendency(){
        return idTendency;
    }

    public void setIdTendency(int idTendency){
        this.idTendency = idTendency;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Date getDateBegin(){
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin){
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd(){
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd){
        this.dateEnd = dateEnd;
    }

}
