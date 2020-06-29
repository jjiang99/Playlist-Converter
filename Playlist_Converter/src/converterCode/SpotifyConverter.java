package converterCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class SpotifyConverter extends Converter {
	static String playlistId;
	static String auth = "BQBSvxgPfWOMZ7-WJ1USdj0tboKJJZ2SyLFF9gl6UO2bcV7qTN1Q3YHuI251rUnYYMrtk8R30bIAGgRuaVUyOFlcRVW0jBnHh47Ep1HvSBu3Q1KbJvGHqQEgvMDVTDXLIS9T28zA0qQZBVaOvsT0rrrmnEZD9s4n7YeVCfV_T6Kf3OZXfg";

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

	public static void authenticate() throws IOException {
//		System.out.println("AUTH");
		String s = null;
		String path = "Python_Test\\spotifyAuthenticator.py";
		Process p = Runtime.getRuntime().exec("python " + path);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

		BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

		// read the output from the command
		// System.out.println("Here is the standard output of the command:\n");
		while ((s = stdInput.readLine()) != null) {
			auth = s;
		}

		// read any errors from the attempted command
		// System.out.println("Here is the standard error of the command (if any):\n");
		while ((s = stdError.readLine()) != null) {
			System.out.println(s);
		}
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

	public static void getSongs(String link) throws IOException {
		playlistId = (link.split("playlist/")[1]).split("\\?")[0];
		authenticate();
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
		System.out.println(response.toString());
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

	public static String findSong(Song song) throws IOException {
		String pTitle = song.getName();
		String pArtist = song.getArtist();
		String jsonString = searchSong(pTitle, pArtist);
		JSONObject obj = new JSONObject(jsonString);
		JSONObject tracks = obj.getJSONObject("tracks");
		JSONArray searchResults = tracks.getJSONArray("items");
		// System.out.println(songs.length());
		String title = "";
		String artist = "";
		String uri = "";

		for (int i = 0; i < searchResults.length(); i++) {
			artist = searchResults.getJSONObject(i).getJSONArray("artists").getJSONObject(0).getString("name");
			title = searchResults.getJSONObject(i).getString("name");
//			songId = songs.getJSONObject(i).getString("id");
			uri = searchResults.getJSONObject(i).getString("uri");

			if (pTitle.equalsIgnoreCase(title) && pArtist.equalsIgnoreCase(artist)) {
				return uri;
			} else if (pTitle.toLowerCase().contains(title.toLowerCase()) && pArtist.equalsIgnoreCase(artist)) {
				potentialMismatches.add(song);
				return uri;
			} else if (title.toLowerCase().contains(pTitle.toLowerCase()) && pArtist.equalsIgnoreCase(artist)) {
				potentialMismatches.add(song);
				return uri;
			} else {
				unmatchedSongs.add(song);
				break;
			}

//			System.out.print(title + "by ");
//			System.out.print(artist + " ");
//			System.out.println("ID: " + uri);
		}
		
		return "";
	}

	public static String createPlaylist(String playlistName) throws IOException {
		String userId = getUserId();

		String endpoint = "https://api.spotify.com/v1/users/" + userId + "/playlists";

		URL url = new URL(endpoint);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Authorization", "Bearer " + auth);
		con.setRequestProperty("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("name", playlistName);
		body.put("description", "https://music.apple.com/ca/playlist/summer-2019/pl.u-PDb40YATLAz4XN");
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
		return "https://open.spotify.com/playlist/" + playlistId;
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
		getSongs(url);
//		for (Song s : songs) {
//			System.out.println(s.name + " " + s.artist);
//		}

		createPlaylist("hello");
		ArrayList<String> uris = new ArrayList<String>();
		// System.out.println("ID: " + playlistId);
		int counter = 0;
		for (Song s : songs) {
			uris.add(findSong(s));
			counter++;

			if (counter == 50) {
				addSongs(uris);
				uris.clear();
			}
		}
		addSongs(uris);
		System.out.println("DONE");
	}

	public static String putAllSongs(String name) throws IOException {
		authenticate();

		String playlistLink = createPlaylist(name);

		ArrayList<String> uris = new ArrayList<String>();
		// System.out.println("ID: " + playlistId);
		int counter = 0;
		int count = 0;
		for (Song s : songs) {
//			System.out.println(s.toString());
			String searchResult = findSong(s);
			if (!searchResult.equals("")) {
				uris.add(searchResult);
				counter++;
				count++;
			} else {
//				System.out.println(s.toString());
			}

			if (counter == 50) {
				addSongs(uris);
				uris.clear();
				counter = 0;
			}

		}
		addSongs(uris);
		System.out.println("Attempted: " + songs.size());
		System.out.println("Added: " + count);
		
		
		System.out.println("Mismatches:");
		for (Song s : potentialMismatches) {
			System.out.println(s.toString());
		}
		System.out.println("Unmatched:");
		for (Song s : unmatchedSongs) {
			System.out.println(s.toString());
		}
		return playlistLink;
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
//		searchSong("Jackie Chan", "Tiësto");

		// authenticate();
		// copyPlaylist("7xAQ6VoGM9pd5HHEccRGKP");
		getSongs("https://open.spotify.com/playlist/0MMPezm4ZGpLDVxXVtvPnT?si=Aq2IVdoQRaGrDkzOcOciuw");
		printSongs();
//		String s = null;
//		String path = "Python_Test\\GooglePlayConverter.py";
//		Process p = Runtime.getRuntime().exec("python " + path);
//		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
//
//		BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
//
//		// read the output from the command
//		System.out.println("Here is the standard output of the command:\n");
//		while ((s = stdInput.readLine()) != null) {
//			System.out.println(s);
//			String[] songInfo = s.split(", ");
//			songs.add(new Song(songInfo[0], songInfo[1]));
//		}
//		
//		for (Song song : songs) {
//			System.out.println(song.toString());
//		}
//
//		// read any errors from the attempted command
//		System.out.println("Here is the standard error of the command (if any):\n");
//		while ((s = stdError.readLine()) != null) {
//			//System.out.println(s);
//		}
	}

}
