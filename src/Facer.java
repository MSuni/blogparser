package src;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;

public class Facer {

  String pageURL;
  String filename;
  String username;
  BufferedWriter bw;
  FileWriter fw = null;
  final WebClient webClient;

  public Facer(String username) {
    this.username = username;
    webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_11);
  }

  public ArrayList<String> getUsers() {

    ArrayList<String> users = new ArrayList<String>();

    // final WebClient webClient = new WebClient();
    webClient.getOptions().setThrowExceptionOnScriptError(false);
    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

    webClient.getCurrentWindow().setInnerHeight(60000);
    try {
      HtmlPage page = webClient.getPage(pageURL);

      List<DomAttr> profileLinks = (List) page.getByXPath("//a[@class='profileLink']/@href");
      System.out.println("here: " + profileLinks.get(0).asText());

      for (int i = 0; i < profileLinks.size(); i++) {
	System.out.println("here: " + profileLinks.get(i).getValue());
	users.add(profileLinks.get(i).getValue());
      }

    } catch (FailingHttpStatusCodeException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return users;
  }

  public void login(String username, String password) {

    System.out.println("Logging in...");

    webClient.setJavaScriptEngine(new JavaScriptEngine(webClient));
    webClient.getOptions().setThrowExceptionOnScriptError(false);
    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    CookieManager cookieManager = webClient.getCookieManager();
    cookieManager.setCookiesEnabled(true);

    HtmlPage page1 = null;
    try {
      page1 = webClient.getPage("https://www.facebook.com");
    } catch (IOException e) {
      e.printStackTrace();
    }
    final HtmlForm form = (HtmlForm) page1.getElementById("login_form");

    final HtmlSubmitInput button = (HtmlSubmitInput) form.getInputsByValue("Log In").get(0);
    System.out.println("button: " + button);
    final HtmlTextInput textField = (HtmlTextInput) page1.getElementById("email");
    System.out.println("textfield1: " + textField);
    textField.setValueAttribute("eerotuomisto@luukku.com");
    final HtmlPasswordInput textField2 = (HtmlPasswordInput) page1.getElementById("pass");
    System.out.println("textField2: " + textField2);

    textField2.setValueAttribute("testisalasana");

    System.out.println("textField1: " + textField.getValueAttribute());
    System.out.println("textField2: " + textField2.getValueAttribute());
    try {
      HtmlPage page2 = button.click();
      webClient.waitForBackgroundJavaScript(5 * 1000);
      System.out.println(page2.asText());
//      page1 = webClient.getPage("https://www.facebook.com");
//      System.out.println(page1.asText());

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void getUserInfo(String profileURL, int type) {

    // final WebClient webClient = new WebClient();
//    webClient.getOptions().setThrowExceptionOnScriptError(false);
//    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//
//    webClient.getCurrentWindow().setInnerHeight(60000);
    
    
    try {
      // Get work and education
      HtmlPage page = null;
      if (type == 1) {
	page = webClient.getPage(profileURL + "/about?section=education&pnref=about");
      } else {
	page = webClient.getPage(profileURL + "&sk=about&section=education&pnref=about");
      }
      webClient.waitForBackgroundJavaScript(1 * 1000);

      bw.write("getting user: " + profileURL + " type: " + type + System.getProperty("line.separator"));
      bw.write("page: " + page.getTitleText() + System.getProperty("line.separator"));
//      bw.write(page.asText() + System.getProperty("line.separator"));
      bw.flush();
      try {
	Thread.sleep(1000);
      } catch (InterruptedException e) {
	e.printStackTrace();
      }
      // Overview
      // List<DomElement> divs = (List) page.getByXPath("//div[@class='clearfix
      // _5y02']");

      // EDUCATION AND WORK
      List<DomElement> eduDivs = (List) page.getByXPath("//li[@class='_43c8 _5f6p fbEditProfileViewExperience experience']");
      // List<DomElement> eduDivs = (List) page.getByXPath("//div");

      System.out.println("Edu coming");
      System.out.println("size: " + eduDivs.size());
      for (int i = 0; i < eduDivs.size(); i++) {
	System.out.println(eduDivs.get(i).getTextContent());
	List<DomAttr> category = (List) page.getByXPath(eduDivs.get(i).getCanonicalXPath() + "/parent::*/parent::*/@data-pnref");
	if (category.get(0).getTextContent().equals("work")) {
	  bw.write("Work: ");
	} else {
	  bw.write("Education: ");
	}
	bw.write(eduDivs.get(i).getTextContent() + System.getProperty("line.separator"));
	bw.flush();
      }

      // LIVING PLACES
      if (type == 1) {
	page = webClient.getPage(profileURL + "/about?section=living&pnref=about");
      } else {
	page = webClient.getPage(profileURL + "&sk=about&section=living&pnref=about");
      }
      webClient.waitForBackgroundJavaScript(1 * 1000);
      List<DomElement> livDivs = (List) page.getByXPath("//span[@class='_50f5 _50f7']");
      //
      System.out.println("Livs coming");
      System.out.println("size: " + livDivs.size());
      for (int i = 0; i < livDivs.size(); i++) {
	System.out.println(livDivs.get(i).getTextContent());
	bw.write("lived: " + livDivs.get(i).getTextContent() + System.getProperty("line.separator"));
	bw.flush();
      }

      // CONTACT INFO
      if (type == 1) {
	page = webClient.getPage(profileURL + "/about?section=contact-info&pnref=about");
      } else {
	page = webClient.getPage(profileURL + "&sk=about&section=contact-info&pnref=about");
      }
      webClient.waitForBackgroundJavaScript(2 * 1000);
      List<DomElement> contactDivs = (List) page.getByXPath("//span[@class='_50f4']");

      System.out.println("contacts coming");
      System.out.println("size: " + contactDivs.size());
      for (int i = 0; i < contactDivs.size(); i++) {
	System.out.println(contactDivs.get(i).getTextContent());
	List<DomElement> category = (List) page.getByXPath(contactDivs.get(i).getCanonicalXPath() + "/parent::*/parent::*/parent::*/parent::*/descendant::div");
	bw.write(category.get(0).getTextContent() + ": " + contactDivs.get(i).getTextContent() + System.getProperty("line.separator"));
	bw.flush();
      }

      // RELATIONSHIP
      if (type == 1) {
	page = webClient.getPage(profileURL + "/about?section=relationship&pnref=about");
      } else {
	page = webClient.getPage(profileURL + "&sk=about&section=relationship&pnref=about");
      }
      webClient.waitForBackgroundJavaScript(1 * 1000);
      List<DomElement> relationDivs = (List) page.getByXPath("//li[@class='_3pw9 _2pi4 _2ge8']");

      System.out.println("familyDivs coming");
      System.out.println("size: " + relationDivs.size());
      for (int i = 0; i < relationDivs.size(); i++) {
	System.out.println(relationDivs.get(i).getTextContent());
	bw.write("RelationShip: " + relationDivs.get(i).getTextContent() + System.getProperty("line.separator"));
	bw.flush();
      }

      // RELATIONSHIP 2
      List<DomElement> relation2Divs = (List) page.getByXPath("//li[@class='_173e _50f8 _50f3']");

      System.out.println("familyDivs coming");
      System.out.println("size: " + relation2Divs.size());
      for (int i = 0; i < relation2Divs.size(); i++) {
	System.out.println(relation2Divs.get(i).getTextContent());
	bw.write("2 " + relation2Divs.get(i).getTextContent() + System.getProperty("line.separator"));
	bw.flush();
      }

      // FAMILY MEMBERS
      List<DomElement> familyDivs = (List) page.getByXPath("//li[@class='_43c8 _2ge8']");

      System.out.println("familyDivs coming");
      System.out.println("size: " + familyDivs.size());
      for (int i = 0; i < familyDivs.size(); i++) {
	System.out.println(familyDivs.get(i).getTextContent());
	bw.write("Family Member: " + familyDivs.get(i).getTextContent() + System.getProperty("line.separator"));
	bw.flush();
      }

      // BIO
      //
      if (type == 1){
      page = webClient.getPage(profileURL + "/about?section=bio&pnref=about");
      } else {
	      page = webClient.getPage(profileURL + "&sk=about&section=bio&pnref=about");
      }
      webClient.waitForBackgroundJavaScript(1 * 1000);
      List<DomElement> bioDivs = (List) page.getByXPath("//li[@class='_3pw9 _2pi4']");

      System.out.println("bioDivs coming");
      System.out.println("size: " + bioDivs.size());
      for (int i = 0; i < bioDivs.size(); i++) {
	System.out.println(bioDivs.get(i).getTextContent());
	if (!bioDivs.get(i).getTextContent().equals("No additional details to show") || !bioDivs.get(i).getTextContent().equals("No favorite quotes to show")){
		bw.write(bioDivs.get(i).getTextContent() + System.getProperty("line.separator"));
		bw.flush();
	}
      }

      // LIFE EVENTS
      if (type == 1){
      page = webClient.getPage(profileURL + "/about?section=year-overviews&pnref=about");
      } else {
	      page = webClient.getPage(profileURL + "&sk=about&section=year-overviews&pnref=about");
      }
      webClient.waitForBackgroundJavaScript(1 * 1000);
      List<DomElement> eventDivs = (List) page.getByXPath("//li[@class='_2pi4']");

      System.out.println("eventDivs coming");
      System.out.println("size: " + eventDivs.size());
      for (int i = 0; i < eventDivs.size(); i++) {
	System.out.println(eventDivs.get(i).getTextContent());
	bw.write("Event: " + eventDivs.get(i).getTextContent() + System.getProperty("line.separator"));
	bw.flush();
      }


    } catch (FailingHttpStatusCodeException | IOException e) {
      e.printStackTrace();
    }

  }

  public void getLivs() {
    // LIVING PLACES
    HtmlPage page;
    try {
      page = webClient.getPage("file:///C:/Users/Blackstorm/Desktop/shen/livings.htm");

      // page = webClient.getPage(profileURL +
      // "/about?section=living&pnref=about");
      List<DomElement> livDivs = (List) page.getByXPath("//span[@class='_50f5 _50f7']");
      //
      System.out.println("Livs coming");
      System.out.println("size: " + livDivs.size());
      for (int i = 0; i < livDivs.size(); i++) {
	System.out.println(livDivs.get(i).getTextContent());
	bw.write(livDivs.get(i).getTextContent() + System.getProperty("line.separator"));
	bw.flush();
      }
    } catch (FailingHttpStatusCodeException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void nodeTest() {

    HtmlPage page;
    try {
      page = webClient.getPage("https://www.facebook.com/manmppa/about?section=contact-info&pnref=about");
      webClient.waitForBackgroundJavaScript(5 * 1000);
      System.out.println("Gettind divs");
      List<DomElement> divs1 = (List) page.getByXPath("//span[@class='_50f4']");
      // List<DomAttr> parentDivs = (List) page.getByXPath("//li[@class='_43c8
      // _5f6p fbEditProfileViewExperience
      // experience']/parent::*/parent::*/@data-pnref");
      System.out.println("divs gotten");
      // or //span[@class='_h72 lfloat _ohe _50f8 _50f7']
      for (int i = 0; i < divs1.size(); i++) {
	// System.out.println("here: " + divs1.get(i).getCanonicalXPath());
	List<DomElement> category = (List) page.getByXPath(divs1.get(i).getCanonicalXPath() + "/parent::*/parent::*/parent::*/parent::*/descendant::div");
	bw.write("data: " + divs1.get(i).getTextContent() + System.getProperty("line.separator"));
	bw.write("Mah Parent1: " + category.get(0) + System.getProperty("line.separator"));
	bw.write("Mah Parent2: " + category.get(0).getTextContent() + System.getProperty("line.separator"));
	// bw.write(livDivs.get(i).getTextContent() +
	// System.getProperty("line.separator"));
	bw.flush();
      }
      // for (int i = 0; i < parentDivs.size(); i++){
      // bw.write("parent: " + parentDivs.get(i) +
      // parentDivs.get(i).getTextContent() +
      // System.getProperty("line.separator"));
      // bw.write("right: " + parentDivs.get(i).getTextContent() +
      // System.getProperty("line.separator"));
      // bw.flush();
      // }
      bw.close();

    } catch (FailingHttpStatusCodeException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void setpage(String pageURL) {
    this.pageURL = pageURL;
  }

  public void changeFile(String filename) {

    File file = new File("/users/" + username + "/" + filename);
    System.out.println("changing file to: /users/"+ username +"/" + filename);

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

}
