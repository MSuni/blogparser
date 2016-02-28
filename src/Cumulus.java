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

//28
public class Cumulus {

    String url = "https://www.tripadvisor.fi/Search?q=cumulus&geo=189896&pid=3826&typeaheadRedirect=true&redirect=&startTime=1456377062958&uiOrigin=MASTHEAD&returnTo=https%25253A__2F____2F__www__2E__tripadvisor__2E__fi__2F__";

    File file = new File("/users/user/cumulus_reviews_threestar.txt");

    public Cumulus() {

    }

    public void usejsoup() {

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

	// Connect to the web site
	try {
	    Document document = Jsoup.connect(url).get();

	    List<String> linkArray3 = new ArrayList<>();
	    List<String> linkArray2 = new ArrayList<>();
	    List<String> linkArray = new ArrayList<>();

	    System.out.println("Getting links");
	    Elements links = document.select("a[class=review-count]");

	    for (int i = 0; i < 2; i++) {
		try {
		    Thread.sleep(100);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		linkArray.add(links.get(i).attr("href"));
		System.out.println("Connecting...");
		System.out.println("https://www.tripadvisor.fi/" + linkArray.get(i));
		document = Jsoup.connect("https://www.tripadvisor.fi/" + linkArray.get(i)).timeout(60 * 1000).get();

		// Get Page links
		Elements pages = document.select("a[data-page-number]");
		// for (int a = 1; a < pages.size(); a++) {
		// System.out.println("adding page: " +
		// pages.get(a).attr("href"));
		// linkArray3.add(pages.get(a).attr("href"));
		// }

		// from each page
		for (int b = 0; b < pages.size(); b++) {
		    System.out.println("adding page: " + pages.get(b).attr("href"));
		    System.out.println("going to page " + b);
		    linkArray3.add(pages.get(b).attr("href"));

		    document = Jsoup.connect("https://www.tripadvisor.fi/" + linkArray3.get(b)).timeout(60 * 1000)
			    .get();

		    // Select reviews
		    Elements links2 = document.select("div.innerBubble").select("a[href]");

		    for (int a = 0; a < links2.size(); a++) {

			linkArray2.add(links2.get(a).attr("href"));
			System.out.println("Connecting to review...");
			System.out.println("https://www.tripadvisor.fi/" + linkArray2.get(a));
			document = Jsoup.connect("https://www.tripadvisor.fi/" + linkArray2.get(a)).timeout(60 * 1000)
				.get();

			// hae tähdet
			// Elements stars =
			// document.select("img[class=sprite-rating_s_fill
			// rating_s_fill s30]");
			if (document.select("img[class=sprite-rating_s_fill rating_s_fill s30]")
				.attr("property") != "") {
			    // if (stars.get(0).attr("property") != ""){
			    // Haetaan teksti
			    System.out.println("review: " + document.select("div.entry").select("p").text());
			    bw.write(document.select("div.entry").select("p").text()
				    + System.getProperty("line.separator"));
			} else {
			    System.out.println("not correct amount of stars");
			}

		    }
		}

	    }

	    bw.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    public void usehtmlunit() {
	final WebClient webClient = new WebClient();
	webClient.getOptions().setThrowExceptionOnScriptError(false);
	
	webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
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
		    "https://www.tripadvisor.fi/Search?q=cumulus&geo=189896&pid=3826&typeaheadRedirect=true&redirect=&startTime=1456377062958&uiOrigin=MASTHEAD&returnTo=https%25253A__2F____2F__www__2E__tripadvisor__2E__fi__2F__");
	    // webClient.getPage("http://www.google.com")
	    System.out.println("success");

	    List<DomAttr> hotelLinks = (List) page.getByXPath("//a[@class='review-count']/@href");
	    System.out.println("Links here");

	    // example hotel links
	    // System.out.println(hotelLinks.get(0).getValue().toString());
	    // System.out.println(hotelLinks.get(1).getValue().toString());
	    // System.out.println(hotelLinks.get(2).getValue().toString());
	    // System.out.println(hotelLinks.get(3).getValue().toString());
	    for (int hotelFor = 0; hotelFor < 28; hotelFor++) {
		// Going to hotel
		page = webClient.getPage("https://www.tripadvisor.fi" + hotelLinks.get(0).getValue().toString());
		System.out.println("success");

		// get page links
		List<DomAttr> pageLinks = (List) page.getByXPath("//a[@class='pageNum taLnk']/@href");

		for (int pageFor = 0; pageFor < pageLinks.size(); pageFor++) {

		    if (pageFor != 0) {
			page = webClient
				.getPage("https://www.tripadvisor.fi" + pageLinks.get(pageFor).getValue().toString());
			System.out.println("going to page " + pageFor);

			// List<DomAttr> reviewLinks2 = (List)
			// page.getByXPath("//div[@class='innerBubble']//a/@href");
		    }

		    // get review links
		    List<DomAttr> reviewLinks = (List) page.getByXPath("//div[@class='innerBubble']//a/@href");
		    System.out.println("reviews inc");
		    // example review links

		    for (int reviewFor = 0; reviewFor < reviewLinks.size(); reviewFor++) {
			// get the text!!
			page = webClient.getPage(
				"https://www.tripadvisor.fi" + reviewLinks.get(reviewFor).getValue().toString());

			List<DomElement> textDiv = (List) page.getByXPath("//div[@class='entry']");
			if (!textDiv.isEmpty()) {
			    System.out.println("got the text");
			    System.out.println(textDiv.get(0).getTextContent());

			    bw.write(textDiv.get(0).getTextContent() + System.getProperty("line.separator"));
			} else {
			    System.out.println("no text found");
			}
		    }

		}
	    }

	} catch (FailingHttpStatusCodeException e2) {
	    e2.printStackTrace();
	} catch (MalformedURLException e2) {
	    e2.printStackTrace();
	} catch (IOException e2) {
	    e2.printStackTrace();
	}

	try {
	    bw.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

}
