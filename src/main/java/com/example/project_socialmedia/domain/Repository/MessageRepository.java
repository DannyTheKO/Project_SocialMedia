package com.example.project_socialmedia.domain.Repository;

import com.example.project_socialmedia.domain.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
