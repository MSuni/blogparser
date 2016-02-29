package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Main {

  public static void main(String[] args) {

    // BlogivirtaParser virtaParser = new BlogivirtaParser();

    testWriter();
    
//    Cumulus cumulus = new Cumulus();
//    cumulus.usehtmlunit();
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

      HtmlPage page = webClient.getPage(
	  "https://www.tripadvisor.fi/ShowUserReviews-g189908-d249055-r69627551-Cumulus_Kuopio_Hotel-Kuopio_Northern_Savonia.html#CHECK_RATES_CONT");

      
       List<DomAttr> rating2 = (List) page.getByXPath("//img[@class='sprite-rating_no_fill rating_no_fill no30']/@alt");

       System.out.println("about to");
//       rating2.get(0);
//       bw.write(rating2.get(0).toString());
       System.out.println("get");

       
//       List<DomElement> divs = (List) page.getByXPath("//div[@class='  reviewSelector ']");
       
//       for (int i = 0; i < divs.size(); i++){
//	 bw.write(divs.get(i) + System.getProperty("line.separator"));
//	 
//       }

       
//       List<DomElement> textDiv = (List) page.getByXPath("//div[@class='entry']");
//       textDiv.get(0);
//       for (int i = 0; i < textDiv.size(); i++){
//
//	    System.out.println("haloo " + textDiv.get(i).getTextContent());
//	 
//       }
       
//	if (!textDiv.isEmpty()) {
//	    System.out.println("got the text");
//	    System.out.println(textDiv.get(0).getTextContent());
//
//	    bw.write(textDiv.get(0).getTextContent() + System.getProperty("line.separator"));
//	} else {
//	    System.out.println("no text found");
//	}
      
      
      
      
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
