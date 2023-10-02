package ru.flamexander.slow.service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.flamexander.slow.service.dtos.SomeDataDto;

@RestController
@RequestMapping("/api/v1/data")
public class SomeDataController {
    @GetMapping("/{id}")
    public SomeDataDto getSomeDataById(@PathVariable Long id) throws InterruptedException {
        if (id > 100) {
            throw new RuntimeException();
        }
        Thread.sleep(5000);
        return new SomeDataDto(id, "Data Package");
    }
}
