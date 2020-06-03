package com.example.shortenurl;

import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Response> PostShorten(@RequestBody Map<String, String> payload) {
        String url = payload.get("url");
        String shortcode = payload.get("shortcode");

        Response result = new Response("something went wrong");

        if (url == null) {
            result.message = "url is required";
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        
        if (shortcode == null) {
            result.message = "shortcode is required";
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            result.message = "url prefix must be started with http(s):// !";
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        if (!Pattern.matches("^[0-9a-zA-Z_]{6}$", shortcode)) {
            result.message = "invalid shortcode pattern";
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        Shorten data = new Shorten(url, shortcode);
        shortenRepository.save(data);

        result.message = "successfully insert data";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}