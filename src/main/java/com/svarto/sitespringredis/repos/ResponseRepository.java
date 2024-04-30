package com.svarto.sitespringredis.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.svarto.sitespringredis.Response;

@Repository
public interface ResponseRepository extends CrudRepository<Response, Long> {

    Response findByProductId(Long id);
    // List<Response> findAllByProductId(Long id);

    List<Response> findAllByCustomerId(Long id);

    // List<Response> findByMessage(String message);

    // List<Response> findAllResponses
}
