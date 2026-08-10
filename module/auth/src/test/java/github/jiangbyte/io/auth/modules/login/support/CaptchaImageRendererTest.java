package github.jiangbyte.io.auth.modules.login.support;

/**
 * Author: Charlie
 **/

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CaptchaImageRendererTest {

    @Test
    void svgContainsNoiseLinesAndRotatedGlyphs() {
        String b64 = CaptchaImageRenderer.svgBase64("A2B3", new SecureRandom(new Random(1).nextLong()));
        String svg = new String(Base64.getDecoder().decode(b64));
        assertTrue(svg.contains("<line "));
        assertTrue(svg.contains("transform=\"rotate("));
        assertTrue(svg.contains(">A</text>"));
        assertTrue(svg.contains("viewBox=\"0 0 140 44\""));
    }

    @Test
    void pngHasValidSignature() {
        byte[] png = Base64.getDecoder().decode(
                CaptchaImageRenderer.pngBase64("A2B3", new SecureRandom(new Random(2).nextLong())));
        assertTrue(png.length > 8);
        assertTrue(png[0] == (byte) 0x89 && png[1] == 'P' && png[2] == 'N' && png[3] == 'G');
    }
}
