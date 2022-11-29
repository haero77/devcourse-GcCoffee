package devcourse.coffee.service;

import devcourse.coffee.model.Category;
import devcourse.coffee.model.Product;
import devcourse.coffee.repository.ProductJdbcRepository;
import devcourse.coffee.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String productName, Category category, long price) {
        Product product = new Product(UUID.randomUUID(), category, productName, price);
        return productRepository.insert(product);
    }

    @Override
    public Product createProduct(String productName, Category category, long price, String description) {
        Product product = new Product(UUID.randomUUID(), LocalDateTime.now(), category,
                productName, price, description, LocalDateTime.now());
        return productRepository.insert(product);
    }
}
