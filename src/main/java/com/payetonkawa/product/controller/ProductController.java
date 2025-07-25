package com.payetonkawa.product.controller;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.payetonkawa.product.dto.PatchProductDto;
import com.payetonkawa.product.dto.PostProductDto;
import com.payetonkawa.product.entity.Product;
import com.payetonkawa.product.mapper.ProductMapper;
import com.payetonkawa.product.service.ProductService;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping()
    public ResponseEntity<List<Product>> findAll() {
        try {
            return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> find(@PathVariable Integer id) {
        try {
            Optional<Product> product = productService.findById(id);
            if (product.isPresent()) {
                return new ResponseEntity<>(product.get(), HttpStatus.OK);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<Product> create(@RequestBody PostProductDto dto) {
        try {
            return new ResponseEntity<>(productService.insert(productMapper.fromPostDto(dto)), HttpStatus.OK);
        } catch (IllegalStateException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity<Product> update(@RequestBody PatchProductDto dto) {
        try {
            return new ResponseEntity<>(productService.update(productMapper.fromPatchDto(dto)), HttpStatus.OK);
        } catch (IllegalStateException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            productService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalStateException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
