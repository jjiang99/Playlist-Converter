package converterCode;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

public class Converter {
	static ArrayList<Song> songs = new ArrayList<Song>();
	static ArrayList<Song> unmatchedSongs = new ArrayList<Song>();
	static ArrayList<Song> potentialMismatches = new ArrayList<Song>();
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

	public static void getAllSongs(String link) throws IOException {
		switch (sourceService) {
		case SPOTIFY:
			SpotifyConverter.getAllSongs(link);
		case GOOGLE:
//			GooglePlayConverter.getAllSongs();
		case APPLE:
			AppleMusicConverter.getAllSongs(link);
		}
	}

	public static String putAllSongs(String name) throws IOException {
		switch (destinationService) {
		case SPOTIFY:
			return SpotifyConverter.putAllSongs(name);
		case GOOGLE:
			return null;
//			return GooglePlayConverter.putAllSongs(name);
		case APPLE:
			return AppleMusicConverter.putAllSongs(name);
		}
		return name;
	}


//	private static void convertList() throws IOException {
//		for (Song s : songs) {
//			String tmp = "python -c \"from Python_Test.AppleMusicConverter import convertList; convertList(\'"
//					+ s.name.replace("'", "\\\'") + "\', \'" + s.artist + "\')\"";
//			System.out.println(tmp);
//			Process p = Runtime.getRuntime().exec(tmp);
//			String str = null;
//			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
//
//			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
//
//			// read the output from the command
//			System.out.println("Here is the standard output of the command:");
//			while ((str = stdInput.readLine()) != null) {
//				System.out.println(str);
//			}
//
//			// read any errors from the attempted command
////			System.out.println("\nHere is the standard error of the command (if any):");
////			while ((str = stdError.readLine()) != null) {
////				System.out.println(str);
////			}
//		}
//	}

//	private static void printPythonSongs() throws IOException {
//		Process p = Runtime.getRuntime()
//				.exec("python -c \"from Python_Test.AppleMusicConverter import printSongs; printSongs()\"");
//		String str = null;
//		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
//
//		BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
//
//		// read the output from the command
//		System.out.println("Here is the standard output of the command:");
//		while ((str = stdInput.readLine()) != null) {
//			System.out.println(str);
//		}
//
//		// read any errors from the attempted command
//		System.out.println("\nHere is the standard error of the command (if any):");
//		while ((str = stdError.readLine()) != null) {
//			System.out.println(str);
//		}
//
//	}
	
	public static String convert(String link, String name) throws IOException {
		getAllSongs(link);
		return putAllSongs(name);
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
//		SpotifyConverter.authenticate();
//		GooglePlayConverter.getAllSongs();
		SpotifyConverter.getAllSongs("https://open.spotify.com/playlist/087Qsv7zTq8cbDu4bocMy8?si=JKOh1ANTRZy4K11KohKlWg");
		
		
//		System.out.println(songs.size());
//		SpotifyConverter.putAllSongs("GOOG-SPOT CONVERT");
		AppleMusicConverter.putAllSongs("asd");
//		convertList();
		// AppleMusicConverter.getAllSongs("https://music.apple.com/ca/playlist/summer-2020/pl.u-b3b8RX7syNGrgR");
//		printSongs();
//		//GooglePlayConverter.printSongs();
//		System.out.println(SpotifyConverter.putAllSongs("Copy From Apple"));
		
		
	}

}
