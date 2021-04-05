package realimpact.moonbug.study;

import org.junit.Test;

import java.net.URLEncoder;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class URLEncodeDecodeTest {
    @Test
    public void testEncoding() throws Exception {
        String src = "name=강인수,age=40,tall=170";
        String tgt = URLEncoder.encode(src, StandardCharsets.UTF_8.toString());
        String tgt2src = URLDecoder.decode(tgt, StandardCharsets.UTF_8.toString());

        assertThat(src).isEqualTo(tgt2src);
    }
}
