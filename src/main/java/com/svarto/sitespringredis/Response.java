package com.svarto.sitespringredis;

import java.io.Serializable;
import java.time.ZonedDateTime;

import org.springframework.data.annotation.Id;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("response")
public class Response implements Serializable {
    @Id
    @Indexed
    private Long id;
    @Indexed
    private Long CustomerId;
    @Indexed
    private Long ProductId;
    @Indexed
    private String message;
    private ZonedDateTime message_date;
}
