package catracabasa.j.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpSimpleGet {
	
	public static final int LEN = 500;
	
	public static String download(String url) throws IOException {
		
		InputStream ins = null;
		URL url_ = new URL(url);
		String result;
		try {
			HttpURLConnection conn = (HttpURLConnection) url_.openConnection();
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(15000);
			conn.setRequestMethod("GET");
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
