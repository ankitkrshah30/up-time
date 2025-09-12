package com.bahikhata.up_time.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Document(collection="user_session")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSession {

    @Id
    private String id;
    @DBRef
    private User user;
    private String sessionId;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private String ipAddress;
    private String userAgent;
    private String deviceInfo;
    private boolean active;
    @CreatedDate
    private LocalDateTime createdAt;
    private Map<String,Object> additionalData=new HashMap<>();

}
