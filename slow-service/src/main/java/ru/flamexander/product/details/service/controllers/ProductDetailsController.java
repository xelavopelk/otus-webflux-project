package ru.flamexander.product.details.service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.flamexander.product.details.service.dtos.DetailsRequest;
import ru.flamexander.product.details.service.dtos.ProductDetailsDto;

@RestController
@RequestMapping("/api/v1/details")
public class ProductDetailsController {
    private final static Integer MAX_ID = 100;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsDto> getProductDetailsById(@PathVariable Long id) throws InterruptedException {
        Thread.sleep(2500 + (int) (Math.random() * 2500));
        if (id > MAX_ID) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(new ProductDetailsDto(id, "Product description.."), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDetailsDto> getIds(@RequestBody DetailsRequest request) throws InterruptedException {
        var res = request
                .getIds()
                .stream()
                .filter(id -> id <= MAX_ID)
                .map(v -> new ProductDetailsDto(v, String.format("Product №%d description..", v))).toList();
        Thread.sleep(2500 + (int) (Math.random() * 2500));
        return new ResponseEntity(res, HttpStatus.OK);
    }
}
