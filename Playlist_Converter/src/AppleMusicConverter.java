
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class AppleMusicConverter {
	static ArrayList<Song> songs = new ArrayList<Song>();
	static String playlistId;

	private enum Service {
		SPOTIFY, GOOGLE, APPLE
	};

	static String auth = "BQDvG8Y47e31-rBTlzxFSRA9aGbBaoKS1p3fFy3YuJdHgU8XltSMLXOK3FG-MfLYx1a1ZcTE0foMQkuNQxrIvJKZAvt9svKafcaEfyDSnsSmak0T7nAxtxTXtDi1IT-eOl4yP2s6SJbYUhKeJT7mPp-UWJ-qx-ga0a-h0FXbI0W4osZU6sZ3SXQOd6GJD9yQby6kCg";


	private static void getSongsApple(String id) {
		// TODO Auto-generated method stub

	}

	public static void printSongs() {
		for (Song s : songs) {
			System.out.println(s.toString());
		}
	}

	public static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();

		for (Map.Entry<String, String> entry : params.entrySet()) {
			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			result.append("&");
		}

		String resultString = result.toString();
		return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
	}

	public static String getAuthToken() throws IOException {
		String testurl = "https://accounts.spotify.com/authorize";

		URL url = new URL(testurl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");

		Map<String, String> parameters = new HashMap<>();
		parameters.put("q", "summer%20keshi");
		parameters.put("type", "track");

		con.setDoOutput(true);
		DataOutputStream out = new DataOutputStream(con.getOutputStream());
		out.writeBytes(getParamsString(parameters));
		out.flush();
		out.close();

		// System.out.println("TEST URL: " + testurl);

//		int status = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		con.disconnect();

		System.out.println(content);
		return null;
	}

	public static String searchSong(String title, String artist) throws IOException {
		String testurl = "https://api.spotify.com/v1/search?q=" + URLEncoder.encode(title, "UTF-8").replace(" ", "%20")
				+ "%20" + URLEncoder.encode(artist, "UTF-8").replace(" ", "%20") + "&type=track";
		// System.out.println(testurl);
		URL endpoint = new URL(testurl);
		HttpURLConnection con = (HttpURLConnection) endpoint.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", "Bearer " + auth);
		con.setRequestProperty("Content-Type", "application/json");

		// System.out.println("TEST URL: " + testurl);

		// int status = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		con.disconnect();

		// System.out.println(content);
		return content.toString();
	}


	private static void getSongsSpotify(String playlistId) throws IOException {
	
	}



	private static String putSongsApple() {
		// TODO Auto-generated method stub
		return null;
	}

	private static String putSongsGoogle() {
		// TODO Auto-generated method stub
		return null;
	}

	private static String putSongsSpotify() {
		// TODO Auto-generated method stub
		return null;
	}

	private static String getUserId() throws IOException {
		String testurl = "https://api.spotify.com/v1/me";

		URL testURL = new URL(testurl);
		HttpURLConnection con = (HttpURLConnection) testURL.openConnection();
		con.setRequestProperty("Authorization", "Bearer " + auth);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}

		JSONObject obj = new JSONObject(content.toString());
		return obj.getString("id");
	}

	public static String findSong(String pTitle, String pArtist) throws IOException {
		String jsonString = searchSong(pTitle, pArtist);
		JSONObject obj = new JSONObject(jsonString);
		JSONObject tracks = obj.getJSONObject("tracks");
		JSONArray songs = tracks.getJSONArray("items");
		// System.out.println(songs.length());
		String title = "";
		String artist = "";
		String uri = "";

		for (int i = 0; i < songs.length(); i++) {
			artist = songs.getJSONObject(i).getJSONArray("artists").getJSONObject(0).getString("name");
			title = songs.getJSONObject(i).getString("name");
//			songId = songs.getJSONObject(i).getString("id");
			uri = songs.getJSONObject(i).getString("uri");

			if (pTitle.equalsIgnoreCase(title) && pArtist.equalsIgnoreCase(artist)) {
				return uri;
			}

//			System.out.print(title + "by ");
//			System.out.print(artist + " ");
//			System.out.println("ID: " + uri);
		}

		return "";
	}

	public static String createPlaylist() throws IOException {
		String userId = getUserId();

		String endpoint = "https://api.spotify.com/v1/users/" + userId + "/playlists";

		URL url = new URL(endpoint);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Authorization", "Bearer " + auth);
		con.setRequestProperty("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("name", "Copied playlist");
		body.put("description", "copied existing spotify playlist to test functionality");
		body.put("public", false);
		con.setDoOutput(true);
		con.setRequestProperty("Content-Length", Integer.toString(body.length()));
		con.getOutputStream().write(body.toString().getBytes("UTF8"));

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}

		// System.out.println(content);
		JSONObject obj = new JSONObject(content.toString());
		playlistId = obj.getString("id");
		return playlistId;
	}

	public static void addSongs(ArrayList<String> uris) throws IOException {
		String endpoint = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks?uris=";

		endpoint += uris.toString();
		endpoint = endpoint.replace("[", "").replace("]", "").replace(" ", "");

//		System.out.println(endpoint);
		URL url = new URL(endpoint);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		// con.setRequestMethod("POST");
		con.setRequestProperty("Authorization", "Bearer " + auth);
		con.setRequestProperty("Content-Type", "application/json");
		con.setDoOutput(true);
		con.setRequestProperty("Content-Length", Integer.toString(0));
		con.getOutputStream().write(("").getBytes("UTF8"));

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}

		// System.out.println(content);
	}

	public static void copyPlaylist(String url) throws IOException {
		getSongsSpotify(url);
//		for (Song s : songs) {
//			System.out.println(s.name + " " + s.artist);
//		}

		createPlaylist();
		ArrayList<String> uris = new ArrayList<String>();
		// System.out.println("ID: " + playlistId);
		int counter = 0;
		for (Song s : songs) {
			uris.add(findSong(s.name, s.artist));
			counter++;

			if (counter == 50) {
				addSongs(uris);
				uris.clear();
			}
		}
		addSongs(uris);
		System.out.println("DONE");
	}

	public static void main(String[] args) throws IOException {
		// URL url = new
		// URL("https://open.spotify.com/playlist/0MMPezm4ZGpLDVxXVtvPnT");
		// URL url = new
		// URL("https://open.spotify.com/playlist/1GkR7O3ziHXGibWfQJI2Dn?si=TTdj_pQTTBC1V1vMwa-MQw");
		// getSongs(url);

		// System.out.println(getAuthToken());
//		System.out.println(findSong("jungle", "Drake"));
		// System.out.println(getUserId());
		// System.out.println(createPlaylist());
		// System.out.println(findSong("Saturday Sun", "Vance Joy"));
		// addSong();
		copyPlaylist("7xAQ6VoGM9pd5HHEccRGKP");
		// getSongsSpotify("7xAQ6VoGM9pd5HHEccRGKP");
//		searchSong("Jackie Chan", "Tiësto");
	}

}

