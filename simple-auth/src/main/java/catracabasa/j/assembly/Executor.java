package catracabasa.j.assembly;

import java.net.Authenticator;

import catracabasa.j.auth.SimpleAuth;
import catracabasa.j.http.HttpSimpleGet;

public class Executor {
	
	//public static String url = "http://google.co.uk";
	public static String url = "http://localhost:8080/rest/private/management";
	public static String result = "";
	
	public static void main(String[] args) throws Exception {
		
		Authenticator.setDefault(new SimpleAuth.MyAuthenticator());
		System.out.println("Hello!");
		result = HttpSimpleGet.download(url);
		System.out.println(result);
		System.out.println("AUTHEN=======================================================");
		result = "";
		result = SimpleAuth.download(url);
		System.out.println(result);
	}
}
