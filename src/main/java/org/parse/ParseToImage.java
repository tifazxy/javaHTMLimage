package org.parse;

import gui.ava.html.image.generator.HtmlImageGenerator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.fit.cssbox.demo.ImageRenderer;
import org.w3c.dom.Document;
import org.xhtmlrenderer.swing.Java2DRenderer;
import org.xml.sax.SAXException;


public class ParseToImage {

  private static URL htmlFileUrl;

  public static String render2Img(String url, String saveImageLocation) {
    try {
      InputStream bin = new FileInputStream(url);
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(bin);
      Java2DRenderer renderer = new Java2DRenderer(document, 600, 800);
      BufferedImage img = renderer.getImage();
      ImageIO.write(img, "png", new File(saveImageLocation));
    } catch (ParserConfigurationException | IOException | SAXException e) {
      System.out.println(e.getLocalizedMessage());
    }
    return saveImageLocation;
  }

  public static String html2Img(String url, String saveImageLocation) {

    HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
    try {
      imageGenerator.loadUrl(url);
      // The two sleep commands is for waiting the img got rendered,
      // otherwise the img tag will not be show in the pic, there need a todo
      Thread.sleep(500);
      imageGenerator.getBufferedImage();
      Thread.sleep(500);
      imageGenerator.saveAsImage(saveImageLocation);
    } catch (Exception e) {
      System.out.println(e.getLocalizedMessage());
    }
    return saveImageLocation;
  }


  public static void cssBox2Img(String url, String saveImageLocation) {
    try {
      FileOutputStream os = new FileOutputStream(saveImageLocation);
      ImageRenderer r = new ImageRenderer();
      r.renderURL(url, os);
    } catch (IOException | SAXException e) {
      System.out.println(e.getLocalizedMessage());
    }
  }


  private void getResource() {
    this.htmlFileUrl = getClass().getClassLoader().getResource("html/test.html");
  }

  public static void main(String[] args) {
    new ParseToImage().getResource();
    String filepath = ParseToImage.htmlFileUrl.getPath();
    String fileUrl = ParseToImage.htmlFileUrl.toString();

    render2Img(filepath, "/home/test/render2Img.png");
    html2Img(fileUrl, "/home/test/html2Img.png");
    cssBox2Img(fileUrl, "/home/test/cssBox2Img.png");
  }
}
