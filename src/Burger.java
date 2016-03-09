
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
    String url = "file:///C:/Android_work/burkeri/burkeri.htm";
//    String url = "https://www.facebook.com/BurgerKingFI/reviews";
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
	
	changeFile("burgerReviews.txt");

	final WebClient webClient = new WebClient();
	webClient.getOptions().setThrowExceptionOnScriptError(false);
	webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
	

	try {
	    webClient.getCurrentWindow().setInnerHeight(60000);
	    HtmlPage page = webClient.getPage(url);

	    List<DomElement> reviewDivs = (List) page.getByXPath("//div[@class='_5pbx userContent']");
//	    List<DomElement> starDivs = (List) page.getByXPath("//i[@class='_51mq img sp_6rp-mgRvb1V sx_9c5cb1']");
		List<DomElement> starDivs = (List)page.getByXPath(("//div[@class='_1dwg']"));
	    //_idwg
	    
		
	    
	    System.out.println("Here they come");

//	    for (int i = 0; i < reviewDivs.size(); i++) {
//
//		System.out.println("Coming");
//		
//		System.out.println("This " + stars.get(i).getTextContent());
//		  
//		System.out.println(reviewDivs.get(i));
//		System.out.println(reviewDivs.get(i).getTextContent());
//		bw.write(reviewDivs.get(i).getTextContent() + System.getProperty("line.separator"));
//		bw.flush();
//	
//	    }
	    
	    List<DomElement> stars1 = (List)page.getByXPath(("//div[@class='_1dwg']//i[@class='_51mq img sp_6rp-mgRvb1V sx_9c5cb1']"));		
	    List<DomElement> stars2 = (List)page.getByXPath(("//div[@class='_1dwg']//i[@class='_51mq img sp_6rp-mgRvb1V sx_ed9461']"));
		
	    System.out.println(stars1.size());
	    System.out.println(stars2.size());
	    
//	    for (int i = 0; i < stars1.size(); i++) {
//		
//	    }

	}catch(

    FailingHttpStatusCodeException e)

    {
	e.printStackTrace();
    } catch(

    MalformedURLException e)

    {
	e.printStackTrace();
    } catch(

    IOException e)

    {
	e.printStackTrace();
    }

    }

    private void changeFile(String filename) {

	File file = new File("/users/user/" + filename);
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
