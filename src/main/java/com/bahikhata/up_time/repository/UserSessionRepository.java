package com.bahikhata.up_time.repository;

import com.bahikhata.up_time.entity.UserSession;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserSessionRepository extends MongoRepository<UserSession, String> {
}
