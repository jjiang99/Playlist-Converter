import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.python.util.PythonInterpreter;

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
			System.out.println(s);
			
			String[] songInfo = s.split(", ");
			songs.add(new Song(songInfo[0], songInfo[1]));
			s = null;
		}
		printSongs();
		// read any errors from the attempted command
//		System.out.println("\nHere is the standard error of the command (if any):");
//		while ((s = stdError.readLine()) != null) {
//			System.out.println(s);
//		}
	}

	public static void main(String[] args) throws IOException {
		PythonInterpreter interpreter = new PythonInterpreter();
//		interpreter.execfile("Python_Test\\GooglePlayConverter.py");
//		PyObject result = interpreter.eval("hello()");
//		PyFunction pyFuntion = (PyFunction) interpreter.get("getAllSongs", PyFunction.class);
//        pyFuntion.__call__();
        
//        Properties properties = System.getProperties();
//        properties.put("python.path", "Python_Test\\GooglePlayConverter.py");
//        PythonInterpreter.initialize(System.getProperties(), properties, new String[0]);
        PythonInterpreter interp = new PythonInterpreter();
//        interp.execfile("Python_Test\\GooglePlayConverter.py");
//		PyFunction pyFuntion = (PyFunction) interp.get("getAllSongs", PyFunction.class);
//        pyFuntion.__call__();
        
//		interpreter.exec("import sys\nsys.path.append('C:\\Users\\justi\\Documents\\GitHub\\Project\\Playlist-Converter\\Playlist_Converter\\Python_Test\\GooglePlayConverter.py')\nimport GooglePlayConverter");
//		// execute a function that takes a string and returns a string
//		PyObject someFunc = interpreter.get("getAllSongs");
//		PyObject result = someFunc.__call__();
//		String realResult = (String) result.__tojava__(String.class);
		 getAllSongs();
		//deezer
		interpreter.close();
		interp.close();
	}
}
