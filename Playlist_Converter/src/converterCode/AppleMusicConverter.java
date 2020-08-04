package converterCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
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


	public static String findSong(String pTitle, String pArtist) throws IOException {
		Process p = Runtime.getRuntime()
				.exec("python -c \"from Python_Test.AppleMusicConverter import findSong; findSong(\'"
						+ pTitle.replace("'", "\\\'") + "\', \'" + pArtist.replace("'", "\\\'") + "\', \'" + "None"
						+ "\')\"");

		String s = null;
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

		BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
//
//		// read the output from the command
//		System.out.println("Here is the standard output of the command:");
		while ((s = stdInput.readLine()) != null) {
//			System.out.println(s);
			return s;
		}

//		// read any errors from the attempted command
//		System.out.println("\nHere is the standard error of the command (if any):");
//		while ((s = stdError.readLine()) != null) {
//			System.out.println(s);
//		}
		return "";
	}

	public static String createPlaylist(String playlistName) throws IOException {
		Process p = Runtime.getRuntime()
				.exec("python -c \"from Python_Test.AppleMusicConverter import createPlaylist; createPlaylist(\'"
						+ playlistName + "\')\"");

		String s = null;
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

		BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

		// read the output from the command
		System.out.println("Here is the standard output of the command:");
		while ((s = stdInput.readLine()) != null) {
			System.out.println("PLAYLIST ID: " + s);
			playlistId = s;
		}

		// read any errors from the attempted command
//		System.out.println("\nHere is the standard error of the command (if any):");
//		while ((s = stdError.readLine()) != null) {
//			System.out.println(s);
//		}
		return "https://music.apple.com/library/playlist/" + playlistId;
	}

	public static void addSongs(ArrayList<String> uris) throws IOException {
		for (String songId : uris) {
			Process p = Runtime.getRuntime()
					.exec("python -c \"from Python_Test.AppleMusicConverter import addSong; addSong(\'" + playlistId
							+ "\', \'" + songId + "\')\"");

			String s = null;
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			// read the output from the command
//			System.out.println("Here is the standard output of the command:");
//			while ((s = stdInput.readLine()) != null) {
//				System.out.println(s);
//			}

			// read any errors from the attempted command
//			System.out.println("\nHere is the standard error of the command (if any):");
//			while ((s = stdError.readLine()) != null) {
//				System.out.println(s);
//			}
		}
	}

	public static void copyPlaylist(String url) throws IOException {
		getSongsSpotify(url);
//		for (Song s : songs) {
//			System.out.println(s.name + " " + s.artist);
//		}

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
		Process p = Runtime.getRuntime().exec(
				"python -c \"from Python_Test.AppleMusicConverter import getAllSongs; getAllSongs(\'" + id + "\')\"");

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
		System.out.println(songs.size());
		// read any errors from the attempted command
//		System.out.println("\nHere is the standard error of the command (if any):");
//		while ((s = stdError.readLine()) != null) {
//			System.out.println(s);
//		}
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
			String searchResult = findSong(s.name, s.artist);
//			System.out.println(searchResult);
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
//		System.out.println(uris.toString());
//		System.out.println(uris.size());
		addSongs(uris);
		System.out.println("Attempted: " + songs.size());
		System.out.println("Added: " + count);
//
//		System.out.println("Mismatches:");
//		for (Song s : potentialMismatches) {
//			System.out.println(s.toString());
//		}
//
//		System.out.println();
//		System.out.println();
//		System.out.println("Unmatched:");
//		for (Song s : unmatchedSongs) {
//			System.out.println(s.toString());
//		}
		return playlistLink;
	}

	private static String findSong(Song s) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) throws IOException {
//		getAllSongs("https://music.apple.com/ca/playlist/summer-2019/pl.u-PDb40YATLAz4XN");
//		printSongs();
//		System.out.println(createPlaylist("a"));
		findSong("I think I'm Okay", "Machine Gun Kelly");
		playlistId = "p.pmrABA2U2LV5Kg";
		String[] list = { "1273354311", "1273354311", "1273354311" };
		ArrayList<String> uris = new ArrayList<>(Arrays.asList(list));

		addSongs(uris);
	}

}
