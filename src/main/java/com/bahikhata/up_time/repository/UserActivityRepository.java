package com.bahikhata.up_time.repository;

import com.bahikhata.up_time.entity.User;
import com.bahikhata.up_time.entity.UserActivity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserActivityRepository extends MongoRepository<UserActivity,String>{
    List<UserActivity> findByUserOrderByTimestampDesc(User user);
    List<UserActivity> findByUserAndTimestampBetween(User user, LocalDateTime start,LocalDateTime end);

    @Query("{'user': ?0, 'activityType': ?1, 'timestamp': {$gte: ?2}}")
    List<UserActivity> findByUserAndActivityTypeAndTimestampAfter(User user, String activityType ,LocalDateTime timestamp);
}
