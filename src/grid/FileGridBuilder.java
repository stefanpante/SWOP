package grid;

import square.GridElement;
import square.Square;
import util.AStar;
import util.Coordinate;
import util.Direction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Constructs a new grid from a file
 *
 * @author Dieter Castel, Jonas Devlieghere   and Stefan Pante
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

	/**
	 * Free squares in the grid
	 */
	private ArrayList<Coordinate> free_squares;

    /**
	 * Squares which are covered by walls.
	 */
	private ArrayList<Coordinate> wall_squares;

	/**
	 * ArrayList containing the start positions of the players.
	 */
	private ArrayList<Coordinate> startCoordinates;

	/**
	 * Construct a new fileGridBuilder with a given parameter
     *
	 * @param   filepath
     *          The path to the file
	 */
	public FileGridBuilder(String filepath) throws IOException{
		this.file = new File(filepath);
		this.startCoordinates = new ArrayList<Coordinate>();
	
		setRandom(new Random());
		build();
	}

	void setConstraints(){
		setConstraintWall(new GridConstraint(1, new ArrayList<Coordinate>()));
	}


	/**
	 * Constructs the grid, reads input from file.
     *
	 * @throws Exception  when the build process fails.
	 */
	@Override
	protected void build() throws IllegalStateException, IllegalArgumentException{
		try{
			readInput();
			setSquares();
			placeWalls(this.getWallsLocation());
			setNeighbors();
			setConstraints();
			setGrid(new Grid(getHSize(),getVSize(), this.gridElements));
			setStartPositions();
			checkConsistency();
		}

		catch(Exception e){
			e.printStackTrace();
			throw new IllegalStateException("The file isn't valid or the grid specified in the file is invalid");
		}
	}

	/**
	 * Reads the input of the given file.
	 */
	private void readInput() throws IOException{
		openFileStream();
		parseFile();
		closeFileStream();
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
		/*
	  Squares not part of the grid
	 */
        ArrayList<Coordinate> not_squares = new ArrayList<Coordinate>();
		this.wall_squares = new ArrayList<Coordinate>();
		String line;
		while((line = br.readLine()) != null){
			char[] chars = line.toCharArray();
			x = 0;
			for(char c: chars){
				switch(c){
				case  ' ':  	free_squares.add(new Coordinate(x,y));
				break;
				case  '*':	 	not_squares.add(new Coordinate(x, y));
				break;
				case  '#':		wall_squares.add(new Coordinate(x,y));
				break;
				default:		checkDigitAddPlayerPosition(c, new Coordinate(x,y));
				}
				x++;
			}
			if(x > getHSize()){
				this.setHSize(x);
			}
			y++;
			this.setVSize(y);
		}
	}
	
	private void checkDigitAddPlayerPosition(char c, Coordinate coor){
		if(Character.isDigit(c)){
			addStartCoordinate(coor);
		}
	}
	
	void addStartCoordinate(Coordinate coor){
		this.startCoordinates.add(coor);
	}
	
	public ArrayList<Coordinate> getStartPositions(){
		return new ArrayList<Coordinate>(this.startCoordinates);
	}

	/**
	 * Constructs all the squares, sets all the neigbors of the square
	 */
	@Override
	protected void setSquares(){

		for(Coordinate coordinate: free_squares){
			this.gridElements.put(coordinate, new Square());
		}

		for(Coordinate coordinate: wall_squares){
			this.gridElements.put(coordinate, new Square());
		}

		for(Coordinate coor: startCoordinates){
			Square sq = new Square();
			this.startPositions.add(sq);
			this.gridElements.put(coor, sq);
		}
	}

	/**
	 * places all the walls in the Grid,
	 * The walls are checked for consistency after the building process.
	 */
	private ArrayList<ArrayList<Coordinate>> getWallsLocation(){
		ArrayList<ArrayList<Coordinate>> wallsLocation = new ArrayList<ArrayList<Coordinate>>();
		// make a copy of the initial walls array, so that it can be saved for later.
		ArrayList<Coordinate> walls = new ArrayList<Coordinate>(wall_squares);
		// we remove all the formed walls from the wall_squares array
		while(!wall_squares.isEmpty()){
			// A sequence of squares is used to construct a wall.
			ArrayList<Coordinate> sequence = new ArrayList<Coordinate>();
			// Get the first coordinate in the walls array
			Coordinate coordinate = wall_squares.get(0);

			sequence.add(coordinate);
			for(Direction direction: Direction.values()){
				ArrayList<Coordinate> seq = getWallSequence(coordinate, direction);
				sequence.addAll(seq);
			}

			wallsLocation.add(sequence);

		}
		wall_squares = walls;
		return wallsLocation;
	}


	/**
	 * Gets the wall sequence in the given direction. Should return an empty ArrayList
	 * if the wall doesn't extend in the given direction.
	 *
	 * @param coordinate the coordinate of the first block of the wall
	 * @param direction	 the direction in which we look for wall coordinates
	 * @return  the sequence of coordinates which make up a part of the wall.
	 */
	private ArrayList<Coordinate> getWallSequence(Coordinate coordinate, Direction direction){
		ArrayList<Coordinate> sequence = new ArrayList<Coordinate>();
		wall_squares.remove(coordinate);
		Coordinate coor = coordinate;
		boolean finished = false;
		while(!finished){
			coor = coor.getNeighbor(direction);
			if(wall_squares.contains(coor)){
				sequence.add(coor);
				wall_squares.remove(coor);
			}
			else{
				finished = true;
			}
		}

		return sequence;
	}

	void checkConsistency() throws IllegalStateException {
		// Check whether there are no islands
		for(GridElement el1: getGrid().getAllGridElements()){
			for(GridElement el2: getGrid().getAllGridElements()){
				if(el1 != el2){
					if(!el1.isObstacle() && !el2.isObstacle()){
						AStar aStar = new AStar(getGrid());
						// throws illegalStateException when there is no path
						aStar.shortestPath(el1, el2);
					}
				}
			}
		}
		

		for(Square startPlayer1: getGrid().getStartPositions()){
			for(Square startPlayer2 : getGrid().getStartPositions()){
				if(startPlayer1 != startPlayer2){
					AStar aStar = new AStar(getGrid());
					aStar.shortestPath(startPlayer1, startPlayer2);
				}
				
			}
		}
		
	}


}
