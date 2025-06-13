package com.payetonkawa.client.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.payetonkawa.client.dto.PatchClientDto;
import com.payetonkawa.client.dto.PostClientDto;
import com.payetonkawa.client.entity.Client;
import com.payetonkawa.client.exception.NotAnInsertException;
import com.payetonkawa.client.mapper.ClientMapper;
import com.payetonkawa.client.service.ClientService;

@RestController
@RequestMapping("/clientcontroller")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @GetMapping()
    public ResponseEntity<List<Client>> findAll() {
        try {
            return new ResponseEntity<>(clientService.findall(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> find(@PathVariable Integer id) {
        try {
            Optional<Client> client = clientService.findById(id);
            if (client.isPresent()) {
                return new ResponseEntity<>(client.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<Client> create(@RequestBody PostClientDto dto) {
        try {
            return new ResponseEntity<>(clientService.insert(clientMapper.fromPostDto(dto)), HttpStatus.OK);
        } catch (NotAnInsertException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity<Client> update(@RequestBody PatchClientDto dto) {
        try {
            return new ResponseEntity<>(clientService.update(clientMapper.fromPatchDto(dto)), HttpStatus.OK);
        } catch (NotAnInsertException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
