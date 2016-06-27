package com.theironyard.entities;

import org.hibernate.annotations.CollectionId;

import javax.persistence.*;

/**
 * Created by jonathandavidblack on 6/27/16.
 */
@Entity
@Table(name = "files")
public class AnonFile {
    @Id
    @GeneratedValue
    Integer id;

    @Column(nullable = false)
    String originalFileName;

    @Column(nullable = false)
    String realFileName;

    public AnonFile() {
    }

    public AnonFile(String originalFileName, String realFileName) {
        this.originalFileName = originalFileName;
        this.realFileName = realFileName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getRealFileName() {
        return realFileName;
    }

    public void setRealFileName(String realFileName) {
        this.realFileName = realFileName;
    }
}

