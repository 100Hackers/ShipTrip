package shiptrip;

public class Player {
	private double x;
	private double y;

	public Player() {
		x = 100;
		y = 100;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public void setX(double incX){
		x = incX;
	}
	
	public void setY(double incY){
		y = incY;
	}
	
}
