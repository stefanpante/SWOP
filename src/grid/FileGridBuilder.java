package grid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import util.Coordinate;

/**
 * Constructs a new grid from a file
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class FileGridBuilder{

	/**
	 * Contains the path to the file.
	 */
	private String filepath;

	/**
	 * the file to be parsed.
	 */
	private File file;

	/**
	 * the buffered reader used to read the file.
	 */
	private BufferedReader br;

	private ArrayList<Coordinate> free_squares;
	private ArrayList<Coordinate> not_squares;
	private Coordinate start_player1;
	private Coordinate start_player2;
	private ArrayList<Coordinate> wall_squares;

	/**
	 * Construct a new fileGridBuilder with a given parameter
	 * @param filepath
	 */
	public FileGridBuilder(String filepath) throws IOException{
		this.filepath = filepath;
		this.file = new File(filepath);
	}
	

	/**
	 * Opens the fileStream
	 * @throws IOException
	 * 			When it is not possible to create a 
	 * 			bufferedReader with the given file.
	 */
	private void openFileStream() throws IOException{
		FileReader fr = new FileReader(file);
		br = new BufferedReader(fr);

	}

	/**
	 * Closes the filestream
	 * @throws IOException
	 * 			if an I/O exception occurs
	 */
	private void closeFileStream() throws IOException{
		br.close();
	}


	/**
	 * parse a single line of the file based grid.
	 * @param line
	 * @throws IOException 
	 */
	private void parseFile() throws IOException{
		int x = 0;
		int y = 0;

		this.free_squares = new ArrayList<Coordinate>();
		this.not_squares = new ArrayList<Coordinate>();
		this.wall_squares = new ArrayList<Coordinate>();
		String line = "";
		while((line = br.readLine()) != null){
			char[] chars = line.toCharArray();
			x = 0;
			for(char c: chars){
				switch(c){
					case  ' ':  	free_squares.add(new Coordinate(x,y));
									break;
					case  '*':	 	not_squares.add(new Coordinate(x,y));
									break;
					case  '#':		wall_squares.add(new Coordinate(x,y));
									break;
					case  '1':		start_player1 = new Coordinate(x,y);
									break;
					case '2':		start_player2 = new Coordinate(x,y);
					default:		break;
				}
				x++;
			}
			y++;
		}


	}
}
