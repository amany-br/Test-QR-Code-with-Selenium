package qrCode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class QRCodeTest {

    private static final Logger log = LoggerFactory.getLogger(QRCodeTest.class);
    private static WebDriver driver;

    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.get("https://eliasnogueira.github.io/selenium-read-qrcode/");
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    /**
     * Decode a QR Code image using zxing
     *
     * @param qrCodeImage the image URL
     * @return the content
     */
    private static String decodeQRCode(Object qrCodeImage) {
        Result result = null;

        try {
            BufferedImage bufferedImage;

            // if not (probably it is a URL
            if (((String) qrCodeImage).contains("http")) {
                bufferedImage = ImageIO.read((new URL((String) qrCodeImage)));

                // if is a Base64 String
            } else {
                byte[] decoded = Base64.decodeBase64((String) qrCodeImage);
                bufferedImage = ImageIO.read(new ByteArrayInputStream(decoded));
            }

            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            result = new MultiFormatReader().decode(bitmap);
        } catch (NotFoundException | IOException e) {
            log.error("Error reading the QR Code", e);
        }
        return result.getText();
    }

    @Test
    void readQRCodeFromURL() {

        String qrCodeFile = driver.findElement(By.id("qr")).getAttribute("src");

        // get the qr code content and assert the result
        String qrCodeResult = decodeQRCode(qrCodeFile);
        assertThat(qrCodeResult).isEqualTo("c72a0de5-eba3-4bf0-bde2-fc709e71df29");
    }

    @Test
    void readQRCodeFromBase64() {
        String qrCodeFile = driver.findElement(By.id("qr-base64")).getAttribute("src");

        /*
         * Split the content of src attribute from qr-base64 image to get only the Base64 String
         * Because the src starts with 'data:image/png;base64,' and the following text is the Base64 String
         */
        String base64String = qrCodeFile.split(",")[1];

        // get the qr code content and assert the result
        String qrCodeResult = decodeQRCode(base64String);
        assertThat(qrCodeResult).isEqualTo("https://twitter.com/eliasnogueira");
    }
}