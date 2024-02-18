package com.ecommerce.project.security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true, nullable=false)
    private Long id;

    @Column(name="name", nullable=false, length=60)
    private String name;

    @Column(name="description", length=200)
    private String description;

    @Column(name="brand", nullable=false, length=50)
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable=false, length = 20)
    private EGender gender;

    @Column(name="category", nullable=false, length=50)
    private String category;

    @Column(name="size", nullable=false, length=30)
    private String size;

    @Column(name="color", nullable=false, length=30)
    private String color;

    @Column(name="price", nullable=false, length=30)
    private BigDecimal price;

    @Column(name="is_it_in_inventory", nullable=false, length=30)
    private Boolean isItInInventory;

    @Lob
    @Column(name="image_data", nullable=false, length=30)
    private byte[] imageData;

    public Product() {
    }

    public Product(Long id, String name, String description, String brand,
                   EGender gender, String category, String size, String color,
                   BigDecimal price, Boolean isItInInventory, byte[] imageData) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.gender = gender;
        this.category = category;
        this.size = size;
        this.color = color;
        this.price = price;
        this.isItInInventory = isItInInventory;
        this.imageData = imageData;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getItInInventory() {
        return isItInInventory;
    }

    public void setItInInventory(Boolean itInInventory) {
        isItInInventory = itInInventory;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", gender='" + gender + '\'' +
                ", category='" + category + '\'' +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                ", isItInInventory=" + isItInInventory +
                ", imageData=" + Arrays.toString(imageData) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name)
                && Objects.equals(description, product.description) && Objects.equals(brand, product.brand)
                && Objects.equals(gender, product.gender) && Objects.equals(category, product.category)
                && Objects.equals(size, product.size) && Objects.equals(color, product.color)
                && Objects.equals(price, product.price) && Objects.equals(isItInInventory, product.isItInInventory)
                && Arrays.equals(imageData, product.imageData);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, description, brand, gender, category, size,
                color, price, isItInInventory);
        result = 31 * result + Arrays.hashCode(imageData);
        return result;
    }
}