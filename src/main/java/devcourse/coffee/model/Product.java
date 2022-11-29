package devcourse.coffee.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Product {

    private final UUID productId;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Category category;
    private String productName;
    private long price;
    private String description;

    public Product(UUID productId, Category category, String productName, long price) {
        this.productId = productId;
        this.category = category;
        this.productName = productName;
        this.price = price;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Product(UUID productId, LocalDateTime createdAt, LocalDateTime updatedAt, Category category,
                   String productName, long price, String description
    ) {
        this.productId = productId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category = category;
        this.productName = productName;
        this.price = price;
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }

    public void setProductName(String productName) {
        this.productName = productName;
        this.updatedAt = LocalDateTime.now();
    }

    public void setPrice(long price) {
        this.price = price;
        this.updatedAt = LocalDateTime.now();
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
}
