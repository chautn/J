package catracabasa.j.assembly;

import catracabasa.j.http.HttpSimpleGet;

public class Executor {
	
	public static String url = "http://google.co.uk";
	public static String result = "";
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("Hello!");
		result = HttpSimpleGet.download(url);
		System.out.println(result);
	}
}
