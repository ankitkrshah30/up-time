package com.bahikhata.up_time.repository;

import com.bahikhata.up_time.entity.UserActivity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserActivityRepository extends MongoRepository<UserActivity,String>{
}
