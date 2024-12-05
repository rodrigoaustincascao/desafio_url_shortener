package com.example.url_shortener.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.url_shortener.dtos.ShortenUrlRequestDTO;
import com.example.url_shortener.dtos.ShortenUrlResponseDTO;
import com.example.url_shortener.entities.UrlEntity;
import com.example.url_shortener.repositories.UrlRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
public class UrlController {

    private final UrlRepository urlRepository;

    
    
    public UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }



    @PostMapping("/shorten-url")
    public ResponseEntity<ShortenUrlResponseDTO> shortenUrl(@RequestBody ShortenUrlRequestDTO request, HttpServletRequest servletRequest) {
        
        String id;
        
        do {

            id = RandomStringUtils.randomAlphanumeric(5,10);

        } while (urlRepository.existsById(id));
        
        
        urlRepository.save(new UrlEntity(id, request.url(), LocalDateTime.now().plusMinutes(1)));

        var redirectUrl = servletRequest.getRequestURL().toString().replace("shorten-url", id);
        
        return ResponseEntity.ok(new ShortenUrlResponseDTO(redirectUrl));
    }

    @GetMapping("{id}")
    public ResponseEntity redirect(@PathVariable("id") String id) {

        var url = urlRepository.findById(id);

        if(url.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setLocation(URI.create(url.get().getFullUrl()));

        
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
    
    
}
