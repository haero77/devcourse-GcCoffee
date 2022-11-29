package devcourse.coffee.repository;

import devcourse.coffee.model.Category;
import devcourse.coffee.model.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static devcourse.coffee.JdbcUtils.toLocalDateTime;
import static devcourse.coffee.JdbcUtils.toUUID;

@Repository
public class ProductJdbcRepository implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product insert(Product product) {
        String sql = "insert into product " +
                "values (UUID_TO_BIN(:productId), :productName, :category, :price, :description, :createdAt, :updatedAt);";

        int update = jdbcTemplate.update(sql, toParmaMap(product));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }

        return product;
    }

    @Override
    public List<Product> findAll() {
        String sql = "select * from product";
        return jdbcTemplate.query(sql, productRowMapper());
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        String sql = "select * from product where product_id = UUID_TO_BIN(:productId)";

        try {
            Map<String, byte[]> paramMap = Collections.singletonMap("productId", productId.toString().getBytes());
            Product product = jdbcTemplate.queryForObject(sql, paramMap, productRowMapper());
            return Optional.of(Objects.requireNonNull(product));
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> findByName(String productName) {
        String sql = "select * from product where product_name = :productName";

        try {
            Map<String, String> paramMap = Collections.singletonMap("productName", productName);
            Product product = jdbcTemplate.queryForObject(sql, paramMap, productRowMapper());
            return Optional.of(Objects.requireNonNull(product));
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return jdbcTemplate.query(
                "select * from product where category = :category",
                Collections.singletonMap("category", category.toString()),
                productRowMapper()
        );
    }

    @Override
    public Product update(Product product) {
        String sql = "update product set product_id=UUID_TO_BIN(:productId), product_name=:productName, category=:category, " +
                "price=:price, description=:description, created_at=:createdAt, updated_at=:updatedAt";
        int update = jdbcTemplate.update(sql, toParmaMap(product));

        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }

        return product;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from product", Collections.emptyMap());
    }

    private RowMapper<Product> productRowMapper() {
        return (resultSet, rowNum) -> {
            UUID productId = toUUID(resultSet.getBytes("product_id"));
            String productName = resultSet.getString("product_name");
            Category category = Category.valueOf(resultSet.getString("category"));
            long price = resultSet.getLong("price");
            String description = resultSet.getString("description");
            LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
            LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));

            return new Product(productId, createdAt, category, productName, price, description, updatedAt);
        };
    }

    private Map<String, Object> toParmaMap(Product product) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("productId", product.getProductId().toString().getBytes());
        paramMap.put("productName", product.getProductName());
        paramMap.put("category", product.getCategory().toString());
        paramMap.put("price", product.getPrice());
        paramMap.put("description", product.getDescription());
        paramMap.put("createdAt", product.getCreatedAt());
        paramMap.put("updatedAt", product.getUpdatedAt());

        return paramMap;
    }
}
