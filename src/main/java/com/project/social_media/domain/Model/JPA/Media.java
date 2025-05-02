package com.project.social_media.domain.Model.JPA;

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

    @Column(name = "file_path", nullable = false)
    private String filePath;
    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "uploaded_date", nullable = false)
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
