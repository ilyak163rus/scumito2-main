package com.svarto.sitespringredis;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("product")
public class Product implements Serializable {
    @Id
    private Long id;
    @Indexed
    private Long user_id;
    @Indexed
    private String title;
    private String author;
    private int price;
    private String city;
    private User user;
    private String description;
    private String image_url;
    private boolean active;
    @Indexed
    private int Category_id;
    private boolean boost;
}
