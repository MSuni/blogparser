import java.io.IOException;

public class Main {
	

	public static void main(String[] args) {

//		BlogivirtaParser virtaParser = new BlogivirtaParser();
		
		try {
		  BlogivirtaParser.parseUserBlogs();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		new Parser();
	}

}
