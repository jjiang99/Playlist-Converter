import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

public class SpotifyConverter {
	static ArrayList<Song> songs = new ArrayList<Song>();
	static String playlistId;

	private enum Service {
		SPOTIFY, GOOGLE, APPLE
	};

	private static Service sourceService;
	private static Service destinationService;
	static String auth = "BQDvG8Y47e31-rBTlzxFSRA9aGbBaoKS1p3fFy3YuJdHgU8XltSMLXOK3FG-MfLYx1a1ZcTE0foMQkuNQxrIvJKZAvt9svKafcaEfyDSnsSmak0T7nAxtxTXtDi1IT-eOl4yP2s6SJbYUhKeJT7mPp-UWJ-qx-ga0a-h0FXbI0W4osZU6sZ3SXQOd6GJD9yQby6kCg";

	public static void getSongsSpotify1(URL url) throws IOException {
		InputStream is = url.openStream();
		int ptr = 0;
		StringBuffer buffer1 = new StringBuffer();
		while ((ptr = is.read()) != -1) {
			buffer1.append((char) ptr);
		}
		String buffer = buffer1.toString();
		String bufferCopy = buffer;

		String titleSearch = "\",\"popularity\":";
		String nameSearch = "\"name\":\"";
		String artistSearch = "\",\"type\":\"artist\"";
		String albumSearch = "\"type\":\"album\"";
		int numSongs = 0;
		// System.out.println(buffer);

		ArrayList<String> songNames = new ArrayList<String>();
		ArrayList<String> artistNames = new ArrayList<String>();
		ArrayList<String> albumNames = new ArrayList<String>();

		while (bufferCopy.indexOf(titleSearch) != -1) {
			int titleIndex = bufferCopy.indexOf(titleSearch);

			String tempBuffer = bufferCopy.substring(titleIndex - 300);
			String title = tempBuffer.split(titleSearch)[0].split(nameSearch)[1].split("\"")[0];
			// System.out.println("Title: " + title);
			songNames.add(title);
			bufferCopy = bufferCopy.substring(titleIndex + 1);
		}

		// System.out.println();
		bufferCopy = buffer;

		while (bufferCopy.indexOf(albumSearch) != -1) {
			int albumIndex = bufferCopy.indexOf(albumSearch);

			String tempBuffer = bufferCopy.substring(albumIndex - 300);
			String album = tempBuffer.split(albumSearch)[0].split(nameSearch)[1].split("\"")[0];
			// System.out.println("Album: " + album);
			albumNames.add(album);
			bufferCopy = bufferCopy.substring(albumIndex + 1);
			numSongs++;
		}

		// System.out.println();
		bufferCopy = buffer;

		while (bufferCopy.indexOf(albumSearch) != -1) {
			int artistIndex = bufferCopy.indexOf(albumSearch);

			String tempBuffer = bufferCopy.substring(artistIndex - 50);
			String artist = tempBuffer.split(artistSearch)[0].split(nameSearch)[1];
			// System.out.println("Artist: " + artist);
			artistNames.add(artist);
			bufferCopy = bufferCopy.substring(artistIndex + 1);
		}

		for (int i = 0; i < numSongs; i++) {
//			System.out.println(songNames.get(i));

			byte[] defaultBytes = artistNames.get(i).getBytes();

			System.out.println(new String(defaultBytes, "UTF8"));
//			System.out.println(albumNames.get(i));
			songs.add(new Song(songNames.get(i), artistNames.get(i), albumNames.get(i)));
		}
	}

	private static void getSongsApple(String id) {
		// TODO Auto-generated method stub

	}

	private static void getSongsGoogle(String id) {
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
//		String callbackurl = "https://www.getpostman.com/oauth2/callback";
//		String clientID = "1f70624ac6c84c93a95998e8d1f5ba98";
//		String clientSecret = "66194225c1e6478eb181fe569215059d";

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
//		String testurl = "https://api.spotify.com/v1/search?q?=" + title.replace(" ", "%20") + "%20"
//				+ artist.replace(" ", "%20") + "&type=track";
//		byte[] tmp1 = testurl.getBytes("UTF-8");
//		String tmp = new String(tmp1, "UTF-8");
//		System.out.println(testurl);
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

	public static void getAllSongs(String id) throws IOException {
		switch (sourceService) {
		case SPOTIFY:
			getSongsSpotify(id);
		case GOOGLE:
			getSongsGoogle(id);
		case APPLE:
			getSongsApple(id);
		}
	}

	private static void getSongsSpotify(String playlistId) throws IOException {
		String endpoint = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";

		URL url = new URL(endpoint);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", "Bearer " + auth);
		con.setRequestProperty("Content-Type", "application/json");
		con.getInputStream();

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

		JSONObject response = new JSONObject(content.toString());
		// System.out.println(response.toString());
		JSONArray playlistSongs = response.getJSONArray("items");

		String title = "";
		String artist = "";
		String uri = "";
		JSONObject track = null;

		for (int i = 0; i < playlistSongs.length(); i++) {
			track = playlistSongs.getJSONObject(i).getJSONObject("track");
			artist = track.getJSONArray("artists").getJSONObject(0).getString("name");
			title = track.getString("name");
			uri = track.getString("uri");

			songs.add(new Song(title, artist, uri));
//				System.out.print(title + " by ");
//				System.out.print(artist + " ");
//				System.out.println("ID: " + uri);
		}
	}

	public static String putAllSongs() throws IOException {
		switch (destinationService) {
		case SPOTIFY:
			return putSongsSpotify();
		case GOOGLE:
			return putSongsGoogle();
		case APPLE:
			return putSongsApple();
		}
		return null;
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
//		copyPlaylist("7xAQ6VoGM9pd5HHEccRGKP");
		// getSongsSpotify("7xAQ6VoGM9pd5HHEccRGKP");
//		searchSong("Jackie Chan", "Ti�sto");
		
		String s = null;
//		Process p1 = Runtime.getRuntime().exec("cd 'Playlist Converter'");
		String path = "Python_Test\\GooglePlayConverter.py";
		Process p = Runtime.getRuntime().exec("python " + path);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

		BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

		// read the output from the command
		System.out.println("Here is the standard output of the command:\n");
		while ((s = stdInput.readLine()) != null) {
			System.out.println(s);
			String[] songInfo = s.split(", ");
			songs.add(new Song(songInfo[0], songInfo[1]));
		}
		
		for (Song song : songs) {
			System.out.println(song.toString());
		}

		// read any errors from the attempted command
		System.out.println("Here is the standard error of the command (if any):\n");
		while ((s = stdError.readLine()) != null) {
			//System.out.println(s);
		}
	}

}
