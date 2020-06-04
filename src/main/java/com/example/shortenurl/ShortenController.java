package com.example.shortenurl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.regex.Pattern;

import com.example.constants.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<Object> GetURLFromShortcode(@PathVariable String code) throws URISyntaxException {
        Shorten data = shortenRepository.findByShortcode(code);

        if (data == null) {
            Message message = new Message();
            Response result = new Response(message.SHORTCODE_NOT_EXISTS_IN_DATABASE);

            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        URI url = new URI(data.getUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(url);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Response> PostShorten(@RequestBody Map<String, String> payload) {
        Message message = new Message();

        String url = payload.get("url");
        String shortcode = payload.get("shortcode");

        Response result = new Response(message.INTERNAL_SERVER_ERROR_MESSAGE);

        if (url == null) {
            result.message = message.REQUIRED_URL_INPUT;
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        if (shortcode == null) {
            result.message = message.REQUIRED_SHORTCODE_INPUT;
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            result.message = message.INVALID_URL_PREFIX;
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        if (!Pattern.matches("^[0-9a-zA-Z_]{6}$", shortcode)) {
            result.message = message.INVALID_REGEX_SHORTCODE_INPUT;
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        Shorten data = new Shorten(url, shortcode);
        shortenRepository.save(data);

        result.message = message.SUCCESS_INSERT_DATA;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}