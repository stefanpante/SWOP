package grid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import square.Direction;
import square.Square;
import util.AStar;
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

	/**
	 * Free squares in the grid
	 */
	private ArrayList<Coordinate> free_squares;
	/**
	 * Squares not part of the grid
	 */
	private ArrayList<Coordinate> not_squares;

	/**
	 * Squares which are covered by walls.
	 */
	private ArrayList<Coordinate> wall_squares;

	/**
	 * Start coordinate of the first player
	 */
	private Coordinate player1;
	/**
	 * Start coordinate of the second player.
	 */
	private Coordinate player2;

	/**
	 * Construct a new fileGridBuilder with a given parameter
	 * @param filepath
	 */
	public FileGridBuilder(String filepath) throws IOException{
		this.file = new File(filepath);
		setRandom(new Random());
		setConstraints();
		build();
	}

	protected void setConstraints(){
		ArrayList<Coordinate> excluded = new ArrayList<Coordinate>();
		excluded.add(player1);
		excluded.add(player2);
		setConstraintWall(new GridConstraint(1, new ArrayList<Coordinate>()));
		// TODO: Do we still need the squared location for the light grenade.
		setConstraintLightGrenade(new GridConstraint(Grid.PERCENTAGE_GRENADES, excluded));
		setConstraintIdentityDisk(new GridConstraint(Grid.PERCENTAGE_IDENTITY_DISKS, excluded));
		setConstraintTeleport(new GridConstraint(Grid.PERCENTAGE_TELEPORTS, excluded));
		setConstraintForceFieldGenerator(new GridConstraint(Grid.PERCENTAGE_FORCEFIELDGENERATORS,excluded));

	}


	/**
	 * Constructs the grid, reads input from file.
	 * @return a grid built from a file.
	 * @throws Exception
	 */
	@Override
	protected void build() throws IllegalStateException, IllegalArgumentException{
		try{
			readInput();
			setSquares();
			setConstraints();
			placeWalls(this.getWallsLocation());
			placeLightGrenade(randomLocations(getConstraintLightGrenade()));
			placeIdentityDisk(randomLocations(getConstraintIdentityDisk()));
			placeTeleports(randomLocations(getConstraintTeleport()));
			placeChargedIdentityDisk(getChargedIdentityDiskLocation());
			placeForceFieldGenerators(randomLocations(getConstraintForceFieldGenerator()));
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
				case  '1':		setPlayerOneCoordinate(new Coordinate(x,y));
				break;
				case '2':		setPlayerTwoCoordinate(new Coordinate(x, y));
				default:		break;
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

	private void setPlayerOneCoordinate(Coordinate coordinate) throws IllegalStateException {
		if(player1 != null){
			throw new IllegalStateException("There cannot be two starting positions for player 1");
		}
		player1 = coordinate;
	}

	private void setPlayerTwoCoordinate(Coordinate coordinate) throws IllegalStateException{
		if(player2 != null){
			throw new IllegalStateException("There cannot be two starting positions for player 2");
		}
		player2 = coordinate;
	}

	/**
	 * Constructs all the squares
	 */
	@Override
	protected void setSquares(){
		setGrid(new Grid(getHSize(), getVSize()));

		for(Coordinate coordinate: free_squares){
			getGrid().setSquare(coordinate, new Square());
		}

		for(Coordinate coordinate: wall_squares){
			getGrid().setSquare(coordinate, new Square());
		}
		Square p1 = new Square();
		Square p2 = new Square();

		getGrid().setSquare(player1, p1);
		getGrid().setSquare(player2, p2);
		getGrid().setStartPlayerOne(p1);
		getGrid().setStartPlayerTwo(p2);
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
	 * @return
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

	public void checkConsistency() throws IllegalStateException {
		// Check whether there are no islands
		for(Square sq: getGrid().getAllSquares()){
			for(Square sq2: getGrid().getAllSquares()){
				if(sq != sq2){
					if(!sq.isObstructed() && !sq2.isObstructed()){
						AStar aStar = new AStar(getGrid());
						// throws illegalStateException when there is no path
						aStar.shortestPath(sq, sq2);
					}
				}
			}
		}
		AStar aStar = new AStar(getGrid());
		if(getGrid().getStartPlayerOne() == null || getGrid().getStartPlayerTwo() == null){
			throw new IllegalStateException(" There should be a startposition for both players");
		}
		aStar.shortestPath(getGrid().getStartPlayerOne(), getGrid().getStartPlayerTwo());
	}

	@Override
	public Coordinate getPlayerOneCoordinate() {
		return player1;
	}

	@Override
	public Coordinate getPlayerTwoCoordinate() {
		return player2;
	}

}
