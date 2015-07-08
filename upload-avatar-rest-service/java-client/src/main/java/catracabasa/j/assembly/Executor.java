package catracabasa.j.assembly;

import java.io.DataOutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Executor {
	
	public static void main(String[] args) throws Exception {
		
		final String UPLOAD_AVATAR_URL = "http://localhost:8080/rest/private/me/updateAvatar";
		
		//Read the arguments and assign to local variables.
		final String USERNAME, PASSWORD;
		byte[] data = null;
		try {
			USERNAME = args[0];
			PASSWORD = args[1];
			data = Files.readAllBytes(Paths.get(args[2]));
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Missing argument!!!");
			return;
		} catch (Exception e) {
			System.out.println("File not found!!!");
			return;
		}
		
		//Set up the Authenticator.
		Authenticator.setDefault(new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD.toCharArray());
			}
		});
		
		//Set up the connection.
		String boundary = "*****";
		String twoHyphens = "--";
		String crlf = "\r\n";
		
		HttpURLConnection connection = (HttpURLConnection) new URL(UPLOAD_AVATAR_URL).openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setRequestProperty("Cache-Control", "no-cache");
		
		//Write to the connection output stream.
		connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
		DataOutputStream requestStream = new DataOutputStream(connection.getOutputStream());
		requestStream.writeBytes(twoHyphens + boundary + crlf);
		requestStream.writeBytes(	"Content-Disposition: form-data;"
														+	"name=\"avatar\";"
														+	"filename=\"" + Paths.get(args[2]).getFileName().toString() + "\""
														+	crlf);
		requestStream.writeBytes("Content-Type: image/*" + crlf);
		requestStream.writeBytes(crlf);
		requestStream.write(data);
		requestStream.writeBytes(crlf);
		requestStream.writeBytes(twoHyphens + boundary + twoHyphens + crlf);
		connection.getResponseCode();
		connection.disconnect();
	}
}
