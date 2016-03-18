

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;

//28
public class Cumulus {

  FileWriter fw = null;
  String url = "https://www.tripadvisor.fi/Search?q=cumulus&geo=189896&pid=3826&typeaheadRedirect=true&redirect=&startTime=1456377062958&uiOrigin=MASTHEAD&returnTo=https%25253A__2F____2F__www__2E__tripadvisor__2E__fi__2F__";
  String hotelname;
  BufferedWriter bw;
  
  public Cumulus() {

  }


  public void usehtmlunit(int startHotel, int endHotel, String hotelname) {
    this.hotelname = hotelname;
    final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
    webClient.getOptions().setThrowExceptionOnScriptError(false);

    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);


    changeFile(hotelname + "1.txt");

    try {
      HtmlPage page = webClient.getPage("https://www.tripadvisor.fi/Search?q=cumulus&geo=189896&pid=3826&typeaheadRedirect=true&redirect=&startTime=1456377062958&uiOrigin=MASTHEAD&returnTo=https%25253A__2F____2F__www__2E__tripadvisor__2E__fi__2F__");
      // webClient.getPage("http://www.google.fi")
      System.out.println("success");

      List<DomAttr> hotelLinks = (List) page.getByXPath("//a[@class='review-count']/@href");
      System.out.println("Links here");

      // example hotel links
      // System.out.println(hotelLinks.get(0).getValue().toString());
      // System.out.println(hotelLinks.get(1).getValue().toString());
      // System.out.println(hotelLinks.get(2).getValue().toString());
      // System.out.println(hotelLinks.get(3).getValue().toString());
      for (int hotelFor = startHotel; hotelFor < endHotel+1; hotelFor++) {
	// Going to hotel
	page = webClient.getPage("https://www.tripadvisor.fi" + hotelLinks.get(hotelFor).getValue().toString());
	System.out.println("success");

	// get page links
	// List<DomAttr> pageLinks = (List) page.getByXPath("//a[@class='pageNum
	// taLnk']/@href");

//	boolean popupChecked = false;
	
	for (int pageFor = 0; true; pageFor++) {
	  
	//popup checkup
	  List<DomElement> titleCheck = (List) page.getByXPath("//a[@class='more taLnk']");
	  if (titleCheck.size() == 0){
	    pageFor = pageFor - 1;
	    System.out.println("checking for popup 1");
//	    popupChecked = true;
	    bw.write(page.asText());
	    bw.flush();
//	    page = webClient.getPage("https://www.google.fi");
	    continue;
	  }
	  

//	  bw.write("popup checkup done");
//	  bw.flush();
	  List<DomAttr> pageLinks = (List) page.getByXPath("//link[@rel='next']/@href");
	  if (pageLinks.size() == 0) {
	    System.out.println("Next Page not found");
	    break;
	  } else if (pageFor != 0) {
	    System.out.println("Next Page found");
	    page = webClient.getPage("https://www.tripadvisor.fi" + pageLinks.get(0).getValue().toString());
	  }
	  
	

	  // if (pageFor != 0) {
	  // page = webClient.getPage("https://www.tripadvisor.fi" +
	  // pageLinks.get(pageFor - 1).getValue().toString());
	  // System.out.println("going to page " + pageFor);
	  // bw.write("going to page " + pageFor + " " + pageLinks.get(pageFor -
	  // 1).getValue().toString());
	  // bw.flush();

	  // List<DomAttr> reviewLinks2 = (List)
	  // page.getByXPath("//div[@class='innerBubble']//a/@href");
	  // }

	  // get review links
	  List<DomAttr> reviewLinks = (List) page.getByXPath("//div[@class='innerBubble']//a/@href");
	  System.out.println("reviews inc");
	  // example review links

	  for (int reviewFor = 0; reviewFor < reviewLinks.size(); reviewFor++) {
	    // get the text!!
//	    bw.write("connecting to next review");
//		  bw.flush();
	
		  
	    page = webClient.getPage("https://www.tripadvisor.fi" + reviewLinks.get(reviewFor).getValue().toString());
	    webClient.waitForBackgroundJavaScript(1 * 1000);
	    
		  
	    // popup checkup
	    List<DomElement> title2Check = (List) page.getByXPath("//span[@class='altHeadInline']");
	    if (titleCheck.size() == 0) {
	       reviewFor = reviewFor - 1;
	      // bw.write("connecting to next review");
	      // bw.flush();
	      System.out.println("checking for popup 2");
	      continue;
	    }

//	    bw.write("connected to next review");
//		  bw.flush();
		  
	    System.out.println("getting stars");
	    System.out.println("getting stars");
	    System.out.println("getting stars");
	    System.out.println("getting stars");
	    System.out.println("getting stars");
	    System.out.println("getting stars");

	    List<DomAttr> rating1 = null;
	    List<DomAttr> rating2 = null;
	    List<DomAttr> rating3 = null;
	    List<DomAttr> rating4 = null;
	    List<DomAttr> rating5 = null;
	    // reviewRating
	    rating1 = (List) page.getByXPath("//img[@class='sprite-rating_s_fill rating_s_fill s10' or @class='sprite-rating_s_fill rating_s_fill s20' or @class='sprite-rating_s_fill rating_s_fill s30' or @class='sprite-rating_s_fill rating_s_fill s40' or @class='sprite-rating_s_fill rating_s_fill s50']/@alt");
	    try{
	    System.out.println("Rating is " + rating1.get(0).getValue());
	    
	    switch (rating1.get(0).getValue()){
	    case "1/5 tähteä":
		changeFile(hotelname + "1.txt");
	      break;
	    case "2/5 tähteä":
		changeFile(hotelname + "2.txt");
	      break;
	    case "3/5 tähteä":
		changeFile(hotelname + "3.txt");
	      break;
	    case "4/5 tähteä":
		changeFile(hotelname + "4.txt");
	      break;
	    case "5/5 tähteä":
		changeFile(hotelname + "5.txt");
	      break;
	    }
	    } catch (IndexOutOfBoundsException e) {
	      System.out.println("Stars not found");
	      changeFile(hotelname + "0.txt");
	    }
	    
	    //	    try {
//	      rating1 = (List) page.getByXPath("//img[@class='sprite-rating_s_fill rating_s_fill s10']/@alt");
//	      if (rating1.get(0).toString() != "" ) {
//		changeFile(hotelname + "1.txt");
//	      }
////		changeFile(hotelname + "1.txt");
//	    } catch (IndexOutOfBoundsException e) {
//	      System.out.println("Right amount of stars not found");
//	    }
//	    try {
//	      rating2 = (List) page.getByXPath("//img[@class='sprite-rating_s_fill rating_s_fill s20']/@alt");
//	      if (rating2.get(0).toString() != "" ) {
//		changeFile(hotelname + "2.txt");
//	      }
////		changeFile(hotelname + "2.txt");
//	    }catch (IndexOutOfBoundsException e) {
//	      System.out.println("Right amount of stars not found");
//	    } try {
//	      rating3 = (List) page.getByXPath("//img[@class='sprite-rating_s_fill rating_s_fill s30']/@alt");
//	      if (rating3.get(0).toString() != "" ) {
//		changeFile(hotelname + "3.txt");
//	      }
////		changeFile(hotelname + "3.txt");
//	    }catch (IndexOutOfBoundsException e) {
//	      System.out.println("Right amount of stars not found");
//	    } try {
//	      rating4 = (List) page.getByXPath("//img[@class='sprite-rating_s_fill rating_s_fill s40']/@alt");
//	      if (rating4.get(0).toString() != "" ) {
//		changeFile(hotelname + "4.txt");
//	      }
////		changeFile(hotelname + "4.txt");
//	    }catch (IndexOutOfBoundsException e) {
//	      System.out.println("Right amount of stars not found");
//	    } 
//	    try {
//	      rating5 = (List) page.getByXPath("//img[@class='sprite-rating_s_fill rating_s_fill s50']/@alt");
//	      if (rating5.get(0).toString() != "" ) {
//		changeFile(hotelname + "5.txt");
//	      }
////		changeFile(hotelname + "5.txt");
//	    }catch (IndexOutOfBoundsException e) {
//	      System.out.println("Right amount of stars not found");
//	    }
//	      rating2 = (List) page.getByXPath("//img[@class='sprite-rating_no_fill rating_no_fill no30']/@alt");
	       // || rating2.get(0).getValue().toString() == "3/5 tähteä") {
//	      if (rating1.get(0).toString() != "" ) {
//		changeFile(hotelname + "1.txt");
//	      } else if (rating2.get(0).toString() != "" ) {
//		changeFile(hotelname + "2.txt");
//	      } else if(rating3.get(0).toString() != "" ) {
//		changeFile(hotelname + "3.txt");
//	      } else if(rating4.get(0).toString() != "" ) {
//		changeFile(hotelname + "4.txt");
//	      } else if(rating5.get(0).toString() != "" ) {
//		changeFile(hotelname + "5.txt");
//	      }
	    
		System.out.println("rating found");
		List<DomElement> textDiv = (List) page.getByXPath("//div[@class='entry']");
		if (!textDiv.isEmpty()) {
		  System.out.println("got the text");
		  System.out.println(hotelFor + " " + textDiv.get(0).getTextContent());
		  String text = hotelFor + " " + textDiv.get(0).getTextContent() + System.getProperty("line.separator");
		  text.replaceAll("(\\r|\\n)", " ");

		  bw.write(text);
		  bw.flush();
	      }
	    

	  }

	  //get back to review list page, so next page can be checked
	  if (pageFor == 0) {
	    page = webClient.getPage("https://www.tripadvisor.fi" + hotelLinks.get(hotelFor).getValue().toString());
	  } else {
	    page = webClient.getPage("https://www.tripadvisor.fi" + pageLinks.get(0).getValue().toString());
	  }

	}
      }

    } catch (

    FailingHttpStatusCodeException e2)

    {
      e2.printStackTrace();
    } catch (

    MalformedURLException e2)

    {
      e2.printStackTrace();
    } catch (

    IOException e2)

    {
      e2.printStackTrace();
    }

    try

    {
      bw.close();
    } catch (

    IOException e)

    {
      e.printStackTrace();
    }
  }
  
  public void getOneHotel(String url, String hotelname){
    
    this.hotelname = hotelname;
    final WebClient webClient = new WebClient();
    webClient.getOptions().setThrowExceptionOnScriptError(false);

    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);


    changeFile(hotelname + "1.txt");
    
    
    try {
      HtmlPage page = webClient.getPage(url);
      // webClient.getPage("http://www.google.fi")
      System.out.println("success");

//      List<HtmlRadioButtonInput> radioButton = (List<HtmlRadioButtonInput>) page.getByXPath("//input[@id='taplc_prodp13n_hr_sur_review_filter_controls_0_filterLang_more_fi']");
//      radioButton.get(0).setChecked(true);

	for (int pageFor = 0; true; pageFor++) {

	  List<DomAttr> pageLinks = (List) page.getByXPath("//link[@rel='next']/@href");
	  if (pageLinks.size() == 0) {
	    System.out.println("Next Page not found");
	    break;
	  } else if (pageFor != 0) {
	    System.out.println("Next Page found");
	    page = webClient.getPage("https://www.tripadvisor.fi" + pageLinks.get(0).getValue().toString());
	  }

	  // get review links
	  List<DomAttr> reviewLinks = (List) page.getByXPath("//div[@class='innerBubble']//a/@href");
	  System.out.println("reviews inc");
	  // example review links

	  for (int reviewFor = 0; reviewFor < reviewLinks.size(); reviewFor++) {
	    // get the text!!
	    if (("https://www.tripadvisor.fi" + reviewLinks.get(reviewFor).getValue().toString()).equals("https://www.tripadvisor.fi/apps")){
	      continue;
	    }
	    
	    page = webClient.getPage("https://www.tripadvisor.fi" + reviewLinks.get(reviewFor).getValue().toString());
	    webClient.waitForBackgroundJavaScript(1 * 1000);
	    
	    
	    System.out.println("getting stars");
	    System.out.println("getting stars");
	    System.out.println("getting stars");
	    System.out.println("getting stars");
	    System.out.println("getting stars");
	    System.out.println("getting stars");

	    List<DomAttr> rating1 = null;
	    List<DomAttr> rating2 = null;
	    List<DomAttr> rating3 = null;
	    List<DomAttr> rating4 = null;
	    List<DomAttr> rating5 = null;
	    // reviewRating
	    try {
	      rating1 = (List) page.getByXPath("//img[@class='sprite-rating_s_fill rating_s_fill s10']/@property");
	      if (rating1.get(0).toString() != "" ) {
		changeFile(hotelname + "1.txt");
	      }
	    } catch (IndexOutOfBoundsException e) {
	      System.out.println("Right amount of stars not found");
	    }
	    try {
	      rating2 = (List) page.getByXPath("//img[@class='sprite-rating_s_fill rating_s_fill s20']/@property");
	      if (rating2.get(0).toString() != "" ) {
		changeFile(hotelname + "2.txt");
	      }
	    }catch (IndexOutOfBoundsException e) {
	      System.out.println("Right amount of stars not found");
	    } try {
	      rating3 = (List) page.getByXPath("//img[@class='sprite-rating_s_fill rating_s_fill s30']/@property");
	      if (rating3.get(0).toString() != "" ) {
		changeFile(hotelname + "3.txt");
	      }
	    }catch (IndexOutOfBoundsException e) {
	      System.out.println("Right amount of stars not found");
	    } try {
	      rating4 = (List) page.getByXPath("//img[@class='sprite-rating_s_fill rating_s_fill s40']/@property");
	      if (rating4.get(0).toString() != "" ) {
		changeFile(hotelname + "4.txt");
	      }
	    }catch (IndexOutOfBoundsException e) {
	      System.out.println("Right amount of stars not found");
	    } try {
	      rating5 = (List) page.getByXPath("//img[@class='sprite-rating_s_fill rating_s_fill s50']/@property");
	      if (rating5.get(0).toString() != "" ) {
		changeFile(hotelname + "5.txt");
	      }
	    }catch (IndexOutOfBoundsException e) {
	      System.out.println("Right amount of stars not found");
	    }
	    
		System.out.println("rating found");
		List<DomElement> textDiv = (List) page.getByXPath("//div[@class='entry']");
		if (!textDiv.isEmpty()) {
		  System.out.println("got the text");
		  System.out.println(textDiv.get(0).getTextContent());
		  String text = textDiv.get(0).getTextContent() + System.getProperty("line.separator");
		  text.replaceAll("(\\r|\\n)", " ");

		  bw.write(text);
		  bw.flush();
	      } else {
//		bw.write("not found " + "https://www.tripadvisor.fi" + reviewLinks.get(reviewFor).getValue().toString() + System.getProperty("line.separator"));
//		bw.flush();
		System.out.println("Text not found. Shutting down");
		
		System.exit(0);
	      }
	    

	  }

	  //get back to review list page, so next page can be checked
	  if (pageFor == 0) {
	    page = webClient.getPage(url);
	  } else {
	    page = webClient.getPage("https://www.tripadvisor.fi" + pageLinks.get(0).getValue().toString());
	  }

	}
      

    } catch (

    FailingHttpStatusCodeException e2)

    {
      e2.printStackTrace();
    } catch (

    MalformedURLException e2)

    {
      e2.printStackTrace();
    } catch (

    IOException e2)

    {
      e2.printStackTrace();
    }

    try

    {
      bw.close();
    } catch (

    IOException e)

    {
      e.printStackTrace();
    }
  }
    
  
  
  private void changeFile(String filename){
    
    File file = new File("/users/user/" + filename);
    System.out.println("changing file to: /users/Blackstorm/" + filename);
    
    if (!file.exists()) {
      try {
	file.createNewFile();
      } catch (IOException e) {
	e.printStackTrace();
      }
    }

    try {
      fw = new FileWriter(file.getAbsoluteFile(), true);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    

    bw = new BufferedWriter(fw);
    
  }
  
  
  //
//public void usejsoup() {
//
//  if (!file.exists()) {
//    try {
//	file.createNewFile();
//    } catch (IOException e) {
//	e.printStackTrace();
//    }
//  }
//
//  FileWriter fw = null;
//  try {
//    fw = new FileWriter(file.getAbsoluteFile());
//  } catch (IOException e1) {
//    e1.printStackTrace();
//  }
//  BufferedWriter bw = new BufferedWriter(fw);
//
//  // Connect to the web site
//  try {
//    Document document = Jsoup.connect(url).get();
//
//    List<String> linkArray3 = new ArrayList<>();
//    List<String> linkArray2 = new ArrayList<>();
//    List<String> linkArray = new ArrayList<>();
//
//    System.out.println("Getting links");
//    Elements links = document.select("a[class=review-count]");
//
//    for (int i = 0; i < 2; i++) {
//	try {
//	  Thread.sleep(100);
//	} catch (InterruptedException e) {
//	  e.printStackTrace();
//	}
//	linkArray.add(links.get(i).attr("href"));
//	System.out.println("Connecting...");
//	System.out.println("https://www.tripadvisor.fi/" + linkArray.get(i));
//	document = Jsoup.connect("https://www.tripadvisor.fi/" + linkArray.get(i)).timeout(60 * 1000).get();
//
//	// Get Page links
//	Elements pages = document.select("a[data-page-number]");
//	// for (int a = 1; a < pages.size(); a++) {
//	// System.out.println("adding page: " +
//	// pages.get(a).attr("href"));
//	// linkArray3.add(pages.get(a).attr("href"));
//	// }
//
//	// from each page
//	for (int b = 0; b < pages.size(); b++) {
//	  System.out.println("adding page: " + pages.get(b).attr("href"));
//	  System.out.println("going to page " + b);
//	  linkArray3.add(pages.get(b).attr("href"));
//
//	  document = Jsoup.connect("https://www.tripadvisor.fi/" + linkArray3.get(b)).timeout(60 * 1000).get();
//
//	  // Select reviews
//	  Elements links2 = document.select("div.innerBubble").select("a[href]");
//
//	  for (int a = 0; a < links2.size(); a++) {
//
//	    linkArray2.add(links2.get(a).attr("href"));
//	    System.out.println("Connecting to review...");
//	    System.out.println("https://www.tripadvisor.fi/" + linkArray2.get(a));
//	    document = Jsoup.connect("https://www.tripadvisor.fi/" + linkArray2.get(a)).timeout(60 * 1000).get();
//
//	    // hae tähdet
//	    // Elements stars =
//	    // document.select("img[class=sprite-rating_s_fill
//	    // rating_s_fill s30]");
//	    if (document.select("img[class=sprite-rating_s_fill rating_s_fill s30]").attr("property") != "") {
//	      // if (stars.get(0).attr("property") != ""){
//	      // Haetaan teksti
//	      System.out.println("review: " + document.select("div.entry").select("p").text());
//	      bw.write(document.select("div.entry").select("p").text() + System.getProperty("line.separator"));
//	    } else {
//	      System.out.println("not correct amount of stars");
//	    }
//
//	  }
//	}
//
//    }
//
//    bw.close();
//
//  } catch (IOException e) {
//    e.printStackTrace();
//  }
//
//}

}
