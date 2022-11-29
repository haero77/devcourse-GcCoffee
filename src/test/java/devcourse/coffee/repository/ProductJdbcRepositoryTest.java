package devcourse.coffee.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import devcourse.coffee.model.Category;
import devcourse.coffee.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductJdbcRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    private final Product newProduct =
            new Product(UUID.randomUUID(), Category.COFFEE_BEAN_PACKAGE, "new-product", 1000L);

    @Autowired
    private ProductRepository repository;

    @BeforeAll
    static void setup() {
        MysqldConfig config = aMysqldConfig(Version.v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser(" test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("상품을 추가할 수 있다.")
    void testInsertProduct() {
        // when
        repository.insert(newProduct);

        // then
        List<Product> allProducts = repository.findAll();
        assertThat(allProducts).isNotEmpty();
    }

    @Test
    @Order(2)
    @DisplayName("상품을 이름으로 조회할 수 있다.")
    void testFindByName() {
        Optional<Product> product = repository.findByName(newProduct.getProductName());
        assertThat(product.isPresent()).isTrue();
    }

    @Test
    @Order(3)
    @DisplayName("상품을 아이디로 조회할 수 있다.")
    void testFindById() {
        Optional<Product> product = repository.findById(newProduct.getProductId());
        assertThat(product.isPresent()).isTrue();
    }

    @Test
    @Order(4)
    @DisplayName("상품을 카테고리로 조회할 수 있다.")
    void testFindByCategory() {
        List<Product> product = repository.findByCategory(Category.COFFEE_BEAN_PACKAGE);
        assertThat(product).isNotEmpty();
    }

    @Test
    @Order(5)
    @DisplayName("상품을 수정할 수 있다.")
    void testUpdate() {
        newProduct.setProductName("updated-product");

        repository.update(newProduct);

        Optional<Product> product = repository.findById(newProduct.getProductId());
        assertThat(product.isPresent()).isTrue();
        assertThat(product.get()).usingRecursiveComparison()
                .isEqualTo(newProduct);
    }

    @Test
    @Order(6)
    @DisplayName("전체 상품을 삭제할 수 있다.")
    void testDeleteAll() {
        repository.deleteAll();
        List<Product> allProducts = repository.findAll();
        assertThat(allProducts).isEmpty();
    }
}