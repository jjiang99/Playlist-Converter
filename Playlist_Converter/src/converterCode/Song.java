package converterCode;

import java.io.IOException;
import java.util.ArrayList;

public class Song {
	String name;
	String artist;
	String album;
	ArrayList<String> features;
	String uri;
	ArrayList<Tag> tags;

//	public Song(String name) {
//		this.name = name;
//		this.features = new ArrayList<String>();
//	}
	
	public Song(String name, String artist) {
		this.name = name;
		this.artist = new String(artist);
		this.features = new ArrayList<String>();
	}
	
	public Song(String name, String artist, String uri) {
		this.name = name;
		this.artist = new String(artist);
		this.features = new ArrayList<String>();
		this.uri = uri;
	}

	public Song(String name, String artist, String album, String uri) {
		this.name = name;
		this.artist = new String(artist);
		this.album = album;
		this.uri = uri;
	}
	
	public Song(String name, String artist, String uri, ArrayList<Tag> tags) {
		this.name = name;
		this.artist = new String(artist);
		this.uri = uri;
		this.tags = tags;
	}

	public String getAlbum() {
		return album;
	}

	public String getName() {
		return name;
	}

	public String getArtist() {
		return artist;
	}

	public ArrayList<String> getFeatures() {
		return features;
	}

	public String toString() {
		if (tags != null) {
			return "Name: " + name + "\t\tArtist: " + artist + "\t\tTags: " + tags.toString();
		} 
		return "Name: " + name + "\t\tArtist: " + artist + "\t\tAlbum: " + album;
	}
	
	public String format() {
		return name + ", " + artist;
	}

	public static void main(String[] args) throws IOException {
//		try (PythonInterpreter pyInterp = new PythonInterpreter()) {
//			pyInterp.exec("def printHello():\n"
//					+ "\tprint \"hello\"\r\n"
//					+ "printHello()\n"
//					+ "printHello()");
//		}

		
		
				
//		PythonInterpreter interpreter = new PythonInterpreter();
//		interpreter.exec("import sys");
//		interpreter.exec("import sys\nsys.path.append('C:\\Users\\justi\\AppData\\Local\\Programs\\Python\\Python38-32\\Lib\\site-packages')");
//		interpreter.exec("import sys\nsys.path.append('C:\\Users\\justi\\AppData\\Local\\Programs\\Python\\Python38-32\\Lib')");
//		interpreter.exec("import sys\nsys.path.append('C:\\Users\\justi\\AppData\\Local\\Programs\\Python\\Python38-32\\jython2.7.2\\Lib')");
//		interpreter.exec("import sys\nsys.path.append('C:\\Users\\justi\\AppData\\Local\\Programs\\Python\\Python38-32\\jython2.7.2\\Lib\\site-packages')");
//		interpreter.exec("import sys\nsys.path.append('C:\\Users\\justi\\Playlist Converter\\Python Test')\nimport GooglePlayConverter");

//		 execute a function that takes a string and returns a string
//		PyObject someFunc = interpreter.get("funcName");
//		PyObject result = someFunc.__call__(new PyString("Test!"));
//		String realResult = (String) result.__tojava__(String.class);
//		System.out.println(realResult);
	}

}
