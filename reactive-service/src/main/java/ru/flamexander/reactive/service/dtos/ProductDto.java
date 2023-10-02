package ru.flamexander.reactive.service.dtos;

public class ProductDto {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductDto() {
    }

    public ProductDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
