package realimpact.moonbug.study;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;


public class OptionalTest {
    @Test
    public void testNormal() {
        Optional<String> opStr = Optional.ofNullable("강인수");
        assertThat(opStr.get()).isEqualTo("강인수");

    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testNull() {
        Optional<String> nullStr = Optional.ofNullable(null);
        nullStr.get();
    }
}
