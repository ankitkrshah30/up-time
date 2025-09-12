package com.bahikhata.up_time.repository;

import com.bahikhata.up_time.entity.User;
import com.bahikhata.up_time.entity.UserSession;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserSessionRepository extends MongoRepository<UserSession, String> {
    List<UserSession> findByUserAndActiveTrue(User user);
    List<UserSession> findByUserOrderByLoginTimeDesc(User user);

    @Query("{'user': ?0, 'loginTime': {$gte:  ?1, $lte:  ?2}}")
    List<UserSession> findByUserAndLoginTimeBetween(User user, LocalDateTime start,LocalDateTime end);

    @Query("{'user': ?0, 'loginTime': {$gte:  ?1}}")
    List<UserSession> findByUserAndLoginTimeAfter(User user, LocalDateTime date);

    List<UserSession> findTop10ByUserOrderByLoginTimeDesc(User user);
}
