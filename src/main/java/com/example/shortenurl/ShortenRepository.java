package com.example.shortenurl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortenRepository extends JpaRepository<Shorten, Integer> {
    Shorten findByShortcode(String code);
}