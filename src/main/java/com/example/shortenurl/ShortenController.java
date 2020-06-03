package com.example.shortenurl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shorten")
public class ShortenController {
    
    @Autowired
    ShortenRepository shortenRepository;

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public Shorten GetURLFromShortcode(@PathVariable String code) {
        return shortenRepository.findByShortcode(code);
    }
}