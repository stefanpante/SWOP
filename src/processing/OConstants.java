package processing;

public class OConstants {

	/**
	 * The margin used in the layout throughout the application
	 */
	public static final int MARGIN = 5;
	
	/**
	 * White color
	 */
	public static int WHITE = -1;
	
	/**
	 * Black color
	 */
	public static int BLACK = -16777216;
	/**
	 * Integer representation of color(241,241,241)
	 */
	public static int LIGHTER_GREY = -921103;

	/**
	 *  Integer representation of color(204,204,204)
	 */
	public static int LIGHT_GREY = -3355444;
	
	/**
	 * The color of the red player.
	 */
	public static int PLAYERRED = -48060;
	
	/*
	 * The color of the red player, opacity 0.75.
	 */
	public static int PLAYERRED_80 = 1358906436;
	
	/*
	 * The color of the red player, opacity 0.50.
	 */
	public static int PLAYERRED_60 = 1023362116;
	
	/*
	 * The color of the red player, opacity 0.25.
	 */
	public static int PLAYERRED_40 = 687817796;
	
	/**
	 * The color of the blue player.
	 */
	public static int PLAYERBLUE = -13388315;
	
	/**
	 * The color of the blue player, opacity 0.75
	 */
	public static int PLAYERBLUE_80 = 1345566181;
	
	/**
	 * The color of the blue player, opacity 0.50
	 */
	public static int PLAYERBLUE_60 = 1010021861;
	
	/**
	 * The color of the blue player, opacity 0.25
	 */
	public static int PLAYERBLUE_40 = 674477541;
	
	/**
	 * The color of the labels.
	 */
	
	public static int[] PLAYERCOLORS = new int[]{PLAYERBLUE, PLAYERRED};
	
	/**
	 * Convenience.
	 */
	public static int[][] LIGHTTRAILCOLORS = new int[][]{{PLAYERRED_80, PLAYERRED_60, PLAYERRED_40},{PLAYERBLUE_80, PLAYERBLUE_60, PLAYERBLUE_40}};
 	public static int GREEN = -6697984;
	
	/**
	 * darker green
	 */
	
	public static int DARKER_GREEN = -9332992;
	/**
	 * standard square width
	 */
	
	public static int SQUARE_WIDTH = 45;

}
