package com.example.project_socialmedia.domain.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "media_id")
    private Long mediaId;

    @Column(name = "file_path")
    private String filePath;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "uploaded_date")
    private LocalDateTime uploadedDate;

    @OneToMany(mappedBy = "media", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MediaAssociation> associations = new ArrayList<>();

    @Column(name = "file_type")
    private String fileType;

    public Media(String filePath, String fileName, String fileType, LocalDateTime uploadedDate) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileType = fileType;
        this.uploadedDate = uploadedDate;
    }
}
