package converterCode;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class DeprecatedMethods {
	static ArrayList<Song> songs = new ArrayList<Song>();
	
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
	
}
