package grid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import square.Direction;
import square.Square;
import square.obstacle.Wall;
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

    private Coordinate player1;
    private Coordinate player2;

	/**
	 * Construct a new fileGridBuilder with a given parameter
	 * @param filepath
	 */
	public FileGridBuilder(String filepath) throws IOException{
		this.file = new File(filepath);
        build();
	}


	/**
	 * Constructs the grid, reads input from file.
	 * @return a grid built from a file.
	 * @throws Exception 
	 */
	protected void build() throws IllegalStateException{
		try{
			readInput();
			constructSquares();
			placeWalls();
		}
		
		catch(Exception e){
            e.printStackTrace();
			//throw new IllegalStateException("The file isn't valid or the grid specified in the file is invalid");
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
				case  '1':		player1 = new Coordinate(x,y);
				break;
				case '2':		player2 = new Coordinate(x,y);
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
	private void placeWalls(){
		// make a copy of the initial walls array, so that it can be saved for later.
		ArrayList<Coordinate> walls = new ArrayList<Coordinate>(wall_squares);
		// we remove all the formed walls from the wall_squares array
		while(!wall_squares.isEmpty()){
			// A sequence of squares is used to construct a wall.
			ArrayList<Square> sequence = new ArrayList<Square>();
			// Get the first coordinate in the walls array
			Coordinate coordinate = wall_squares.get(0);
            Square s = getGrid().getSquare(coordinate);
            sequence.add(s);
			for(Direction direction: Direction.values()){
				ArrayList<Square> seq = getWallSequence(coordinate, direction);
				sequence.addAll(seq);
			}

			@SuppressWarnings("unused")
			Wall wall = new Wall(sequence);
		}

		wall_squares = walls;
	}


	/**
	 * Gets the wall sequence in the given direction. Should return an empty ArrayList
	 * if the wall doesn't extend in the given direction.
	 * 
	 * @param coordinate the coordinate of the first block of the wall
	 * @param direction	 the direction in which we look for wall squares
	 * @return
	 */
	public ArrayList<Square> getWallSequence(Coordinate coordinate, Direction direction){
		ArrayList<Square> sequence = new ArrayList<Square>();
		wall_squares.remove(coordinate);
        boolean finished = false;
        Square s = getGrid().getSquare(coordinate);
        while(!finished){
            try{
			s = getGrid().getNeighbor(s, direction);
			if(s != null){
				Coordinate coor = getGrid().getCoordinate(s);
				if(wall_squares.contains(coor)){
					sequence.add(s);
					wall_squares.remove(coor);
				}
				else{
					break;
				}
			}
			else{
				break;
			}
            } catch(Exception e){
                finished = true;
            }
		}

		return sequence;
	}

    @Override
    public Square getPlayerOneStart(){
        return getGrid().getSquare(player1);
    }

    @Override
    public Square getPlayerTwoStart(){
        System.out.println(player2);
        return getGrid().getSquare(player2);
    }


	@Override
	public boolean isConsistent() {
		// TODO Auto-generated method stub
		return true;
	}

}
