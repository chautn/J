package catracabasa.j.auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

public class SimpleAuth {
	
	public static final int LEN = 500;
	
	public static class MyAuthenticator extends Authenticator {
		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			return (new PasswordAuthentication("root", "gtn".toCharArray()));
		}
	}

	public static String download(String url) throws IOException {
		
		InputStream ins = null;
		URL url_ = new URL(url);
		String result;
		String auth = new sun.misc.BASE64Encoder().encode("root:gtn".getBytes());
		//Authenticator.setDefault(new MyAuthenticator());
		try {
			HttpURLConnection conn = (HttpURLConnection) url_.openConnection();
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(15000);
			conn.setRequestMethod("GET");
			//conn.setRequestProperty("Authorization", "Basic " + auth);
			conn.connect();
			ins = conn.getInputStream();
			result = readIt(ins, LEN);
		} catch (Exception e) {
			e.printStackTrace();
			result = "Connection Failed";
		} finally {
			if (ins != null) {
				ins.close();
			}
		}
				
		return result;
	}

	public static String readIt(InputStream ins, int len) throws IOException, UnsupportedEncodingException {
		
		Reader reader = null;
		reader = new InputStreamReader(ins, "UTF-8");
		char[] buffer = new char[len];
		reader.read(buffer);
		return new String(buffer);
	}
}
