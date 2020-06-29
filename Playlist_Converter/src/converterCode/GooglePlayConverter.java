package converterCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GooglePlayConverter extends Converter {

	public static void convertToGooglePlay() {

	}

	public static void getAllSongs() throws IOException {
		Process p = Runtime.getRuntime().exec("python -c \"from Python_Test.GooglePlayConverter import getAllSongs; getAllSongs()\"");
		
		String s = null;
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

//		BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

		// read the output from the command
		System.out.println("Here is the standard output of the command:");
		while ((s = stdInput.readLine()) != null) {
			//System.out.println(s);
			
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
//		 getAllSongs();
//		 printSongs();
		//deezer
	}
}
