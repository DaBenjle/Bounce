import static java.awt.Color.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Sprite
{
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	private BufferedImage image;
	private Coordinate pos;
	private double xVelocityRadians, yVelocityRadians, xVelocity, yVelocity, velocityMulti = 10;
	private Dimension imageSize;
	
	public static Color[] colors = {white, red, black, green, yellow, blue, pink, cyan, magenta, orange, gray, lightGray};
	
	public void addActionListener(ActionListener al)
	{
		listeners.add(al);
	}
	
	public void changeColor(Color color)
	{
		for(int x = 0; x < image.getWidth(); x++)
		{
			for(int y = 0; y < image.getHeight(); y++)
			{
				if(((image.getRGB(x, y) >> 24) & 0xff) != 0)
				{
					image.setRGB(x, y, color.getRGB());
				}
			}
		}
	}
	
	public void corner()
	{
		Timer timer = new Timer();
		Box<Integer> counter = new Box<Integer>(Integer.valueOf(0));
		timer.scheduleAtFixedRate(new TimerTask()
				{
					@Override
					public void run()
					{
						changeColor(colors[counter.obj]);
						counter.obj++;
						if(counter.obj == 12)
						{
							timer.cancel();
						}
					}
				}
			, 0, 100);
		for(ActionListener cur : listeners)
		{
			cur.actionPerformed(new ActionEvent(this, 0, "corner"));
		}
	}
	
	public void updateVelocity()
	{
		xVelocity = xVelocityRadians * velocityMulti;
		yVelocity = yVelocityRadians * velocityMulti;
	}
	
	public void move(int xMin, int yMin, int xMax, int yMax)
	{
		boolean tooFarLeft = (pos.x + xVelocity) <= xMin;
		boolean tooFarRight = (pos.x + xVelocity + imageSize.getWidth()) >= xMax;
		boolean tooFarUp = (pos.y + yVelocity) <= yMin;
		boolean tooFarDown = (pos.y + yVelocity + imageSize.getHeight()) >= yMax;
		boolean tooFarX = tooFarLeft || tooFarRight;
		boolean tooFarY = tooFarUp || tooFarDown;
		if(tooFarX && tooFarY)
		{
			//corner
			corner();
		}
		if(tooFarX)
		{
			xVelocityRadians *= -1;
			updateVelocity();
		}
		if(tooFarY)
		{
			yVelocityRadians *= -1;
			updateVelocity();
		}
		pos.x += xVelocity;
		pos.y += yVelocity;
	}
	
	/*
	 * @param angle should be in radians.
	 */
	public void setAngle(double angle)
	{
		xVelocityRadians = Math.cos(angle);
		yVelocityRadians = Math.sin(angle);
		updateVelocity();
	}
	
	public void setPos(Coordinate pos)
	{
		this.pos = pos;
	}
	
	public Sprite(BufferedImage image)
	{
		this(image, new Dimension(0, 0), new Coordinate(0, 0));
	}
	
	public Sprite(BufferedImage image, Dimension imageSize)
	{
		this(image, imageSize, new Coordinate(0, 0));
	}
	
	public Sprite(BufferedImage image, Coordinate pos)
	{
		this(image, new Dimension(0, 0), pos);
	}
	
	public Sprite(BufferedImage image, Dimension imageSize, Coordinate pos)
	{
		this.image = image;
		this.imageSize = imageSize;
		this.pos = pos;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public Coordinate getPos()
	{
		return pos;
	}
	
	public double getXVelocity()
	{
		return xVelocity;
	}
	
	public double getYVelocity()
	{
		return yVelocity;
	}
	
	/*
	 * @return A double array with a length of 2. Index 0 is the xVelocity and index 1 is the yVelocity.
	 */
	public double[] getVolocities()
	{
		return new double[] {xVelocity, yVelocity};
	}
	
	/*
	 * @return The size that the image is scaled to.
	 */
	public Dimension getSize()
	{
		return imageSize;
	}
}
