package com.bahikhata.up_time.repository;

import com.bahikhata.up_time.entity.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
}
