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

public class AppleMusicConverter extends Converter {
	static String playlistId;


	static String auth;

	
	public static void authenticate() throws IOException {

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

		return null;
	}

	public static String searchSong(String title, String artist) throws IOException {

		return null;
	}


	private static void getSongsSpotify(String playlistId) throws IOException {
	
	}



//	private static String putSongsApple() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	private static String putSongsGoogle() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	private static String putSongsSpotify() {
//		// TODO Auto-generated method stub
//		return null;
//	}

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
	
	public static void getAllSongs(String id) throws IOException {
		Process p = Runtime.getRuntime().exec("python -c \"from Python_Test.AppleMusicConverter import getAllSongs; getAllSongs(\'" + id + "\')\"");
		
		String s = null;
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

//		BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

		// read the output from the command
//		System.out.println("Here is the standard output of the command:");
		while ((s = stdInput.readLine()) != null) {
//			System.out.println(s);
			
			String[] songInfo = s.split(", ");
			songs.add(new Song(songInfo[0], songInfo[1]));
			s = null;
		}
		
		// read any errors from the attempted command
//		System.out.println("\nHere is the standard error of the command (if any):");
//		while ((s = stdError.readLine()) != null) {
//			System.out.println(s);
//		}
	}

	public static void main(String[] args) throws IOException {
		getAllSongs("https://music.apple.com/ca/playlist/summer-2019/pl.u-PDb40YATLAz4XN");
		printSongs();
	}

}

