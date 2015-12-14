
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


//hakee blogilinkit blogipolku.fi -sivustosta

public class Parser {
	
	// 293200

//	public static void main(String[] args) {
	
	public Parser(){

		String url = "http://blogipolku.fi/selaa/blogeja";
		String title;
		String desc;
		String divc;

		File file = new File("/users/user/BlogPolku.txt");

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
			// Connect to the web site
			Document document = Jsoup.connect(url).get();
			// Using Elements to get the Meta data
			Elements description = document.select("meta[name=description]");

			List<String> linkArray2 = new ArrayList<>();
			List<String> linkArray = new ArrayList<>();

			Elements links = document.select("a[class=blog-card__title-link]");

			//tekee nyt 100 sivun verran. Pitäisi vaihtaa tunnistamaan loppu - (disabled arrow)
			for (int page = 1; page <= 101; page++) {

				document = Jsoup.connect(url + "?page=" + page).timeout(120 * 1000).get();

				System.out.println("changing page document: " + url + "?page=" + page);
				
				
				
				
				linkArray = new ArrayList<>();
				links = document.select("a[class=blog-card__title-link]");

				for (int i = 0; i < links.size(); i++) {
					
					linkArray2 = new ArrayList<>();

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					linkArray.add(links.get(i).attr("href"));
					System.out.println("Connecting...");
					document = Jsoup.connect("http://blogipolku.fi" + linkArray.get(i)).timeout(600 * 1000).get();

					System.out.println("changing document: " + "http://blogipolku.fi" + linkArray.get(i));
					Elements links2 = document.select("a[class=blog-view__title-link]");
//					linkArray2.add("http://blogipolku.fi" + links2.get(0).attr("href"));
					linkArray2.add(links2.get(0).attr("href"));
					
					System.out.println("Response Connecting to link:" + linkArray2.get(0) + " ...");
//					linkArray2.add("http://blogipolku.fi" + links2.get(u).attr("href"));
					try{
					Response response = Jsoup.connect(linkArray2.get(0)).followRedirects(true).ignoreHttpErrors(true).execute();

					bw.write(response.url() + System.getProperty("line.separator"));
					} catch(Exception e){
						continue;
					}
					
//					Elements links2 = document.select("a[class=post-card__title-link]");
//					System.out.println("links 2 size" + links2.size());
//					
					
					

//					bw.write("Page: " + page + System.getProperty("line.separator"));

//					for (int u = 0; u < links2.size(); u++) {
//						
//						System.out.println("Response Connecting to link:" + u + " ...");
//						linkArray2.add("http://blogipolku.fi" + links2.get(u).attr("href"));
//						try{
//						Response response = Jsoup.connect(linkArray2.get(u)).followRedirects(true).ignoreHttpErrors(true).execute();
//
//						bw.write(response.url() + System.getProperty("line.separator"));
//						} catch(Exception e){
//							continue;
//						}
//					}

				}
			}

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
