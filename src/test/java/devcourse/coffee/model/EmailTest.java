package devcourse.coffee.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void tesInvalidEmail() {
        assertThatThrownBy(() -> {
            new Email("acccc");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testValidEmail() {
        Email email = new Email("hello@gmail.com");
        assertThat(email).isEqualTo(new Email("hello@gmail.com"));
    }

    @Test
    void testEmailEquality() {
        // given
        Email email1 = new Email("hello@gmail.com");
        Email email2 = new Email("hello@gmail.com");

        // then
        assertThat(email1).isEqualTo(email2);
    }
}