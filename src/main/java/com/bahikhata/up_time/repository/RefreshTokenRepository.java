package com.bahikhata.up_time.repository;

import com.bahikhata.up_time.entity.RefreshToken;
import com.bahikhata.up_time.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUser(User user);
    void deleteByUser(User user);

    @Query("{'expiryDate': {$lte: ?0}}")
    List<RefreshToken> findExpiredTokens(LocalDateTime now);
}
