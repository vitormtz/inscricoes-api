/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.apicode.service;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import org.apicode.model.Inscricao;
import org.apicode.model.InscricaoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author vitor
 */
@RestController
@RequestMapping("/inscricoes")
public class InscricaoService {

    private RestTemplate restTemplate = new RestTemplate();

    public InscricaoService() {
    }

    public InscricaoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArrayList<InscricaoRequest>> obterInscricaoPorId(@PathVariable int id) {
        String url = "http://localhost:8080/inscricoes/{id}";

        ResponseEntity<ArrayList<InscricaoRequest>> responseEntity = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ArrayList<InscricaoRequest>>() {
        },
                id
        );

        if (responseEntity.getBody() == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(responseEntity.getBody());
        }
    }

    @PostMapping
    public ResponseEntity<String> criarInscricao(@RequestBody Inscricao inscricaoRequest) {

        String url = "http://localhost:8080/inscricoes";

        if (restTemplate.postForObject(url, inscricaoRequest, Inscricao.class) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body("OK");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelarInscricao(@PathVariable int id) {
        String url = "http://localhost:8080/inscricoes/{id}";

        if (restTemplate.exchange(url, HttpMethod.DELETE, null, Inscricao.class, id).getStatusCode() != HttpStatus.FOUND) {
            return ResponseEntity.ok("OK");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
    }
}
