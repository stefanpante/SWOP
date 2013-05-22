package util;

public class Color {

	private int r;
	private int g;
	private int b;
	public Color(int r, int g, int b) {
		setRed(r);
		setGreen(g);
		setBlue(b);
	}
	
	public void setRed(int r){
		this.r = getValidComponent(r);
	}
	
	public void setGreen(int g){
		this.g = getValidComponent(g);
	}
	
	public void setBlue(int b){
		this.b = getValidComponent(b);
	}
	
	private int getValidComponent(int comp){
		if(comp > 255) return 255;
		if(comp < 0) return 0;
		return comp;
	}
	
	public int getIntColor(){
		return 0xff000000 | (r << 16) | (g << 8) | b;
	}
	
	/**
	 * Get a transparant version of this color
	 * @param alpha	the alpha in a range 0-255
	 * @return
	 */
	public int getTransparantIntColor(int alpha){
		alpha = getValidComponent(alpha);
		return (alpha << 24) | (r << 16) | (g << 8) | b;
	}

}
