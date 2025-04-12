package com.example.project_socialmedia.domain.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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