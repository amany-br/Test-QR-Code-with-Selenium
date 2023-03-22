# QR-Code-Test-with-Selenium
This project demonstrates how to read the QRCode content from a web app using Selenium and ZXing
# Technologies in use
- [Java](https://www.oracle.com/java/technologies/downloads/) as the programming language
- [Selenium](https://www.selenium.dev/) as the web test automation tool
- [AssertJ](https://joel-costigliola.github.io/assertj/) as the assertion library
- [JUnit 5](https://junit.org/junit5/) as the test tool to support the test automation script
- [WebDriverManager](https://github.com/bonigarcia/webdrivermanager) as the browser binary management library
- [Zxing](https://github.com/zxing/zxing) as the library to decode the QRCode content
 <br/> <b> The ZXing </b> (“zebra crossing”) is an open-source, multi-format 1D/2D barcode image processing library implemented in Java, with ports to other languages. One of supported 2D format is the QR Code.

The first thing we did was add the ZXing library to our project classpath. Bellow the snippet in a pom.xml file (Maven)

 
  
    <dependency>
      <groupId>com.google.zxing</groupId>
      <artifactId>core</artifactId>
      <version>3.3.0</version>
    </dependency>

    <dependency>
      <groupId>com.google.zxing</groupId>
      <artifactId>javase</artifactId>
      <version>3.3.0</version>
    </dependency>
       



# How to run this project
  - <b>Preconditions</b>
<br/>Java JDK >=11
<br/>Google Chrome installed

# Running in your IDE
- Open this project in your preferred IDE
- Open the QRCodeTest class placed in src/test/java
- Run the test
# Expected result
You can expect a successful execution. The test will read the QRCode content and assert by its expectation.

# What does the tests do
- Open the browser in a page that has a QRCode
- In the test that reads the image src as path ``readQRCodeFromURL``
  -  Get the image path and transform it in a ``BufferedImage``
- In the test that reads the image src as Base64 ``adQRCodeFromBase64``
  - Get the Base64 content and transform it in a ``BufferedImage``
- Process the image and decode it using ZXing
- Return the QRCode content
- Assert the QRCode content
