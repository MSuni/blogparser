package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Main {

  public static void main(String[] args) {

//    Facer facer = new Facer();
//    facer.changeFile("face4.txt");
//    facer.setpage("https://www.facebook.com/BurgerKingFI/reviews");
//    // facer.setpage("file:///C:/Users/Blackstorm/Desktop/shen/vabootedu.htm");
//    facer.login("eerotuomisto@luukku.com", "testisalasana");
//    System.out.println("getting user links");
//    ArrayList<String> users = facer.getUsers();
//    // ArrayList<String> users = new ArrayList<String>();
//    // users.add("https://www.facebook.com/profile.php?id=100009909642941");
//
//    System.out.println("going to scrape user info");
//    System.out.println(": " + users.size());
//    for (int i = 6; i < 15; i++) {
//      if (users.get(i).equals("https://www.facebook.com/BurgerKingFI/")) {
//	continue;
//      }
//
//      System.out.println("here: " + users.get(i));
//      if (users.get(i).substring(25, 32).equals("profile")) {
//	facer.getUserInfo(users.get(i), 2);
//      } else {
//	facer.getUserInfo(users.get(i), 1);
//      }
//    }

    // testWriter();

     Cumulus cumulus = new Cumulus();
     cumulus.usehtmlunit(0, 26, "stars");
    // cumulus.getOneHotel("http://www.tripadvisor.com/Hotel_Review-g189942-d249056-Reviews-Cumulus_Jyvaskyla_Hotel-Jyvaskyla_Central_Finland.html",
    // "thisworks");
  }

  private static void testWriter() {

    final WebClient webClient = new WebClient();
    webClient.getOptions().setThrowExceptionOnScriptError(false);

    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

    File file = new File("/users/Blackstorm/bwTest.txt");

    if (!file.exists()) {
      try {
	file.createNewFile();
      } catch (IOException e) {
	e.printStackTrace();
      }
    }

    FileWriter fw = null;
    try {
      fw = new FileWriter(file.getAbsoluteFile());
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    BufferedWriter bw = new BufferedWriter(fw);

    try {

      HtmlPage page = webClient.getPage("https://www.tripadvisor.fi/ShowUserReviews-g189908-d249055-r320851008-Cumulus_Kuopio_Hotel-Kuopio_Northern_Savonia.html#CHECK_RATES_CONT");
      webClient.waitForBackgroundJavaScript(1 * 1000);

      List<DomAttr> rating1 = (List) page.getByXPath("//img[@class='sprite-rating_s_fill rating_s_fill s10' or @class='sprite-rating_s_fill rating_s_fill s20' or @class='sprite-rating_s_fill rating_s_fill s30' or @class='sprite-rating_s_fill rating_s_fill s40' or @class='sprite-rating_s_fill rating_s_fill s50']/@alt");
      System.out.println("Rating is " + rating1.get(0).getValue());
      switch (rating1.get(0).getValue()) {
      case "1/5 tähteä":
	System.out.println("case 1");
	break;
      case "2/5 tähteä":
	System.out.println("case 2");
	break;
      case "3/5 tähteä":
	System.out.println("case 3");
	break;
      case "4/5 tähteä":
	System.out.println("case 4");
	break;
      case "5/5 tähteä":
	System.out.println("case 5");
	break;
      }

      // rating2.get(0);
      // bw.write(rating2.get(0).toString());

      System.out.println("Anything here?");

      // List<DomElement> pageLinks = (List) page.getByXPath("//div");
      List<DomElement> divs = (List) page.getByXPath("//div[@class='entry']");
      // List<DomAttr> attris = (List)
      // page.getByXPath("//div[@class='entry']/@p");
      // System.out.println("list size " + pageLinks.size());
      // System.out.println("list size " + attris.size());

      // bw.write(page.asText());
      // bw.write(page.getWebResponse().getContentAsString());
      for (int i = 0; i < divs.size(); i++) {
	System.out.println("found something");
	// System.out.println("it's here " + pageLinks.get(i).getTextContent());
	// bw.write(page.asText());
	// bw.write(pageLinks.get(i).getTextContent() +
	// System.getProperty("line.separator"));
	// bw.write(divs.get(i) + System.getProperty("line.separator"));
	// bw.write(divs.get(i).asText() +
	// System.getProperty("line.separator"));
	bw.write(divs.get(i).getTextContent() + System.getProperty("line.separator"));
      }

      // List<DomElement> textDiv = (List)
      // page.getByXPath("//div[@class='entry']");
      // textDiv.get(0);
      // for (int i = 0; i < textDiv.size(); i++){
      //
      // System.out.println("haloo " + textDiv.get(i).getTextContent());
      //
      // }

      // if (!textDiv.isEmpty()) {
      // System.out.println("got the text");
      // System.out.println(textDiv.get(0).getTextContent());
      //
      // bw.write(textDiv.get(0).getTextContent() +
      // System.getProperty("line.separator"));
      // } else {
      // System.out.println("no text found");
      // }

    } catch (FailingHttpStatusCodeException e1) {
      e1.printStackTrace();
    } catch (MalformedURLException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    }

    try {

      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
