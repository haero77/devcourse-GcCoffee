package devcourse.coffee.repository;

import devcourse.coffee.model.Category;
import devcourse.coffee.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product insert(Product product);

    Product update(Product product);

    List<Product> findAll();

    Optional<Product> findById(UUID productId);

    Optional<Product> findById(String productName);

    Optional<Product> findByCategory(Category category);

    void deleteAll();
}
