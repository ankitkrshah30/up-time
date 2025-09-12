package com.bahikhata.up_time.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection="refresh_token")
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    private String id;
    @DBRef
    private User user;
    @Indexed(unique=true)
    private String token;
    private LocalDateTime expiryDate;
    private boolean revoked=false;
    @CreatedDate
    private LocalDateTime createdAt;
}
