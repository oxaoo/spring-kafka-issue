package com.github.oxaoo.springkafkaissue.repository;

import com.github.oxaoo.springkafkaissue.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
