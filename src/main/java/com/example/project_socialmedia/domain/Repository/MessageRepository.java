package com.example.project_socialmedia.domain.Repository;

import com.example.project_socialmedia.domain.Modal.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {

}
