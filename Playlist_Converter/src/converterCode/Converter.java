package converterCode;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

public class Converter {
	static ArrayList<Song> songs = new ArrayList<Song>();
	static ArrayList<Song> unmatchedSongs = new ArrayList<Song>();
	static String playlistId;
	private static Service sourceService;
	private static Service destinationService;
	static String auth = "BQDvG8Y47e31-rBTlzxFSRA9aGbBaoKS1p3fFy3YuJdHgU8XltSMLXOK3FG-MfLYx1a1ZcTE0foMQkuNQxrIvJKZAvt9svKafcaEfyDSnsSmak0T7nAxtxTXtDi1IT-eOl4yP2s6SJbYUhKeJT7mPp-UWJ-qx-ga0a-h0FXbI0W4osZU6sZ3SXQOd6GJD9yQby6kCg";

	public static Service getSourceService() {
		return sourceService;
	}

	public static void setSourceService(Service sourceService) {
		Converter.sourceService = sourceService;
	}

	public static Service getDestinationService() {
		return destinationService;
	}

	public static void setDestinationService(Service destinationService) {
		Converter.destinationService = destinationService;
	}

	public static void setAuth(String auth) {
		Converter.auth = auth;
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

	public static void authenticate() throws IOException {

	}

	public static void getAllSongs(String id) throws IOException {
		switch (sourceService) {
		case SPOTIFY:
			SpotifyConverter.getSongs(id);
		case GOOGLE:
			getSongsGoogle(id);
		case APPLE:
			getSongsApple(id);
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
		// copyPlaylist("7xAQ6VoGM9pd5HHEccRGKP");
		// getSongsSpotify("7xAQ6VoGM9pd5HHEccRGKP");
//		searchSong("Jackie Chan", "Tiësto");
		
		GooglePlayConverter.getAllSongs();
		GooglePlayConverter.printSongs();
		//System.out.println(SpotifyConverter.putAllSongs("Copy From Google"));
	}

}
