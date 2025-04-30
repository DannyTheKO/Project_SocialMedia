package com.project.social_media.domain.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

// MongoDB collection
@Document(collection = "messages")
public class Message {
    @Id
    private String id;

    private Long senderId;
    private Long receiverId;

    private String content;
    private String type;
    private String timestamp;
}