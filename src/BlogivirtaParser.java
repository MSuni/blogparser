
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//Hakee blogien linkkej‰ blogivirta.fi -sivustosta

public class BlogivirtaParser {

  // Pienin http://blogivirta.fi/go.php?id=100000

  String url = "http://blogivirta.fi/go.php?id=";
  // aseta aloitusid
  int id = 110000;

  public BlogivirtaParser() {

  }

  public static void parseUserBlogs() throws IOException {

    String url = "http://blogivirta.fi/user/";

    File file = new File("/users/user/virtaUserBlogs.txt");

    if (!file.exists()) {
      file.createNewFile();
    }

    FileWriter fw = null;
    fw = new FileWriter(file.getAbsoluteFile());

    BufferedWriter bw = new BufferedWriter(fw);

    for (int i = 1; i < 2000; i++) {

      // Response response = Jsoup.connect(url +
      // i).followRedirects(true).ignoreHttpErrors(true).execute();
      //
      // System.out.println("id: " + i);

      Document document = Jsoup.connect(url + i).timeout(600 * 1000).ignoreHttpErrors(true).get();
      System.out.println("Connecting to: " + url + i);

      // Elements divs = document.select("div#settingsfullblock > a[href]");
      Element div = document.select("div#settingsfullblock").first();

      if (div == null) {
	System.out.println("ei blogilinkki‰");
	continue;
      }

      bw.write(div.select("a[href]").attr("href") + System.getProperty("line.separator"));
      System.out.println("hreffi = " + div.select("a[href]").attr("href"));
      
      try {
     	Thread.sleep(100);
           } catch (InterruptedException e) {
     	e.printStackTrace();
           }

    }

    System.out.println("Parser says: I'm done");
    bw.close();

  }

  public void parsePostsById() throws IOException {

    // aseta tekstitiedosto
    File file = new File("/users/user/BlogiVirtaURL_110000-120000.txt");

    if (!file.exists()) {
      file.createNewFile();
    }

    FileWriter fw = null;
    fw = new FileWriter(file.getAbsoluteFile());

    BufferedWriter bw = new BufferedWriter(fw);

    // vaihda kuinka monta seuraavaa id:t‰ k‰yd‰‰n l‰pi
    for (int i = 0; i < 30; i++) {

      id = id + 1;

      // Document document;
      // document = Jsoup.connect(url + id).get();
      try {
	Response response = Jsoup.connect(url + id).followRedirects(true).ignoreHttpErrors(true).execute();

	System.out.println("id: " + id);
	System.out.println(response.url());

	if (String.valueOf(response.url()).equals("http://blogivirta.fi/blogit/")
	    || String.valueOf(response.url()).equals("http://www.blogit.fi/")) {
	  continue;

	} else {

	  // ID:n kirjoitus (Debug)
	  // bw.write("id: " + id + System.getProperty("line.separator"));
	  bw.write(response.url() + System.getProperty("line.separator"));

	}
      } catch (IOException e2) {
	e2.printStackTrace();
	continue;
      }

      try {
	Thread.sleep(100);
      } catch (InterruptedException e) {
	e.printStackTrace();
      }
    }

    bw.close();

  }

}
