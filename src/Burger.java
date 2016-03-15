

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Burger {

  FileWriter fw = null;
  String url = "file:///C:/Users/Blackstorm/Desktop/bks/Bks.htm";
  // String url = "https://www.facebook.com/BurgerKingFI/reviews";
  String hotelname;
  BufferedWriter bw;

  public Burger() {

  }

  public void usejsoup() {

    try {
      Document document = Jsoup.connect(url).timeout(600 * 1000).ignoreHttpErrors(true).get();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void getReviews() {

    changeFile("burgerReviewsAll.txt");

    final WebClient webClient = new WebClient();
    webClient.getOptions().setThrowExceptionOnScriptError(false);
    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

    try {
      webClient.getCurrentWindow().setInnerHeight(60000);
      HtmlPage page = webClient.getPage(url);

      List<DomElement> reviewDivs = (List) page.getByXPath("//div[@class='_5pbx userContent']");

      System.out.println("Here they come");

      System.out.println(reviewDivs.size());

      // 1 tähti = _51mq img sp_6GmSnUtbHfM sx_1657c8
      // 2 tähti = _51mq img sp_6GmSnUtbHfM sx_5cecf2
      // 3 tähti = _51mq img sp_6GmSnUtbHfM sx_40e881
      // 4 tähti = _51mq img sp_6GmSnUtbHfM sx_dd875a
      // 5 tähti = _51mq img sp_0t0xqs127Wa sx_0f45fb

      List<DomElement> starDivs = (List) page.getByXPath(("//div[@class='_1dwg']"));

    List<DomElement> allstars = (List) page.getByXPath(("//div[@class='_1dwg']//i[@class='_51mq img sp_6GmSnUtbHfM sx_1657c8' or @class='_51mq img sp_6GmSnUtbHfM sx_5cecf2' or @class='_51mq img sp_6GmSnUtbHfM sx_40e881' or @class='_51mq img sp_6GmSnUtbHfM sx_dd875a' or @class='_51mq img sp_0t0xqs127Wa sx_0f45fb']"));

      System.out.println("stars found: " + allstars.size());

      int counter = 0;
       for (int i = 0; i < reviewDivs.size(); i++) {
	 String text = reviewDivs.get(i).getTextContent();
	 String newText1 = text.replaceAll("(\\r|\\n)", " ");
	 String newText2 = text.replaceAll("Näytä lisää", " ");
	 bw.write(allstars.get(i).asText() + " " + newText2 + System.getProperty("line.separator"));
//	 bw.flush();
	 counter++;
       }

       System.out.println("counter before rest: " + counter);
       for (int i = counter; i < allstars.size(); i++){
	 bw.write(allstars.get(i).asText() + System.getProperty("line.separator"));
       }
       
       bw.close();

    } catch (

    FailingHttpStatusCodeException e)

    {
      e.printStackTrace();
    } catch (

    MalformedURLException e)

    {
      e.printStackTrace();
    } catch (

    IOException e)

    {
      e.printStackTrace();
    }

  }

  private void changeFile(String filename) {

    File file = new File("/users/blackstorm/" + filename);
    System.out.println("changing file to: /users/user/" + filename);

    if (!file.exists()) {
      try {
	file.createNewFile();
      } catch (IOException e) {
	e.printStackTrace();
      }
    }

    try {
      fw = new FileWriter(file.getAbsoluteFile(), false);
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    bw = new BufferedWriter(fw);

  }

}
