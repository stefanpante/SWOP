package grid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import square.Direction;
import square.Square;
import util.Coordinate;

/**
 * Constructs a new grid from a file
 * 
 * @author Dieter Castel, Jonas Devlieghere, Vincent Reniers and Stefan Pante
 *
 */
public class FileGridBuilder extends AbstractGridBuilder{

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
	private ArrayList<Coordinate> wall_squares;

	/**
	 * Construct a new fileGridBuilder with a given parameter
	 * @param filepath
	 */
	public FileGridBuilder(String filepath) throws IOException{
		this.file = new File(filepath);
	}


	/**
	 * Constructs the grid, reads input from file.
	 * @return a grid built from a file.
	 */
	public Grid constructGrid(){
		readInput();
		constructSquares();
		return null;
	}

	/**
	 * Reads the input of the given file.
	 */
	private void readInput(){
		try {
			openFileStream();
			parseFile();
			closeFileStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 * parses the entire file
	 * @throws IOException
	 * 			When an IO error occurs.
	 */
	private void parseFile() throws IOException{
		int x = 0;
		int y = 0;

		this.setHSize(0);
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
				case  '1':		setPlayerOneStart(new Coordinate(x,y));
				break;
				case '2':		setPlayerTwoStart(new Coordinate(x,y));
				default:		break;
				}
				x++;
			}
			if(x > getHSize()){
				this.setHSize(x);
			}
			y++;
		}


	}

	/**
	 * Constructs all the squares
	 */
	private void constructSquares(){
		setGrid(new Grid(getHSize(), getVSize()));

		for(Coordinate coordinate: free_squares){
			getGrid().setSquare(coordinate, new Square());
		}

		for(Coordinate coordinate: wall_squares){
			getGrid().setSquare(coordinate, new Square());
		}
	}

	/**
	 * places all the walls in the Grid, checks if the given walls are consistent.
	 */
	private void placeWalls(){
		// make a copy of the initial walls array, so that it can be saved for later.
		ArrayList<Coordinate> walls = new ArrayList<Coordinate>(wall_squares);
		// we remove all the formed walls from the walls array
		while(!walls.isEmpty()){
			// A sequence of squares is used to construct a wall.
			ArrayList<Square> sequence = new ArrayList<Square>();
			// all the Coordinates to remove, needed because of concurrent modification
			ArrayList<Coordinate> toRemove = new ArrayList<Coordinate>();
			// Get the first coordinate in the walls array
			Coordinate coordinate = walls.get(0);
			// Get the corresponding square
			Square s = getGrid().getSquare(coordinate);
			sequence.add(s);
			toRemove.add(coordinate);
			// Find the orientation of the  wall
			Direction[] directions = Direction.values();
			Direction direction = null;
			// Iterates over all possible orientations of the wall.
			for(int i = 0; i < directions.length; i++){
				direction = directions[i];
				Square s2 = getGrid().getNeighbor(s, direction);
				Coordinate coor2 = getGrid().getCoordinate(s2);
				if(walls.contains(coor2)){
					sequence.add(s2);
					toRemove.add(coor2);
					break;
				}
			}
			
			Square s2 = getGrid().getNeighbor(s, direction);
		}
	}



	@Override
	public void checkGridConsistency() {
		// TODO Auto-generated method stub

	}
}
