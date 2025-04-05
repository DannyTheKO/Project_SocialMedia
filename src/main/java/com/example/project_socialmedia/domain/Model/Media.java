package com.example.project_socialmedia.domain.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mediaId;

    private String url;
    private String fileName;
    private LocalDateTime uploadedDate;

    public enum fileType {
        VIDEO,
        IMAGE,
        GIF,
        UNKNOWN
    }

    private Enum<fileType> fileTypeEnum;

    @OneToMany(mappedBy = "media", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MediaAssociation> associations = new ArrayList<>();

    public Media(String filePath, String fileName, Enum<fileType> fileTypeEnum,  LocalDateTime uploadedDate) {
        this.url = filePath;
        this.fileName = fileName;
        this.fileTypeEnum = fileTypeEnum;
        this.uploadedDate = uploadedDate;
    }

}
