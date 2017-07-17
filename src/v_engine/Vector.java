package v_engine;

public class Vector {
	public double x,y;
	
	public Vector()
	{
		this(0, 0);
	}
	
	public Vector(Vector copy)
	{
		this(copy.x, copy.y);
	}
	
	public Vector(double a,double b) {
		set(a, b);
	}
	
	public Vector set(double x, double y)
	{
		this.x = x;
		this.y = y;
		
		return this;
	}
	
	public Vector set(Vector v)
	{
		return set(v.x, v.y);
	}
	
	public Vector add(Vector v)
	{
		return add(v.x, v.y);
	}
	
	public Vector add(double x, double y)
	{
		this.x += x;
		this.y += y;
		
		return this;
	}
	
	public Vector scale(double scale)
	{
		this.x *= scale;
		this.y *= scale;
		
		return this;
	}
	public Vector scale(Vector scale)
	{
		this.x *= scale.x;
		this.y *= scale.y;
		
		return this;
	}
	
	
	public Vector negate()
	{
		return scale(-1);
	}
	
	public double lengthSquare()
	{
		return x*x + y*y;
	}
	
	public double length()
	{
		return Math.sqrt(lengthSquare());
	}
	
	public Vector normalize()
	{
		return scale(1 / length());
	}
	/*
	 * ³»Àû
	 */
	public double innerProduct(Vector v)
	{
		return x * v.x + y * v.y;
	}
	
	public String toString()
	{
		return "Vector[x=" + x + ", y=" + y + "]";
	}
}
