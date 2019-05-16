import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

public class Game implements ActionListener
{	
	public int xOffSet, yOffSet, width, height;
	public boolean alive = true;
	private Sprite sprite;
	
	public Game(int xOffSet, int yOffSet, int width, int height)
	{
		this.width = width;
		this.height = height;
		this.xOffSet = xOffSet;
		this.yOffSet = yOffSet;
		Random random = new Random();
		try
		{
			sprite = new Sprite(ImageIO.read(new File("assets/DVD_VIDEO_logo.png")), new Dimension(150, 100));
			sprite.setAngle(random.nextInt((int)((Math.PI * 2) * 1000)) / 1000.0);
			sprite.setPos(new Coordinate(random.nextInt(width - (int)sprite.getSize().getWidth()), random.nextInt(height - (int)sprite.getSize().getHeight())));
			sprite.addActionListener(this);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask()
				{
					@Override
					public void run()
					{
						loop();
						if(!alive)
						{
							timer.cancel();
						}
					}
				}, 0, 50);
	}
	
	public synchronized void loop()
	{
		sprite.move(xOffSet, yOffSet, width, height);
	}
	
	public BufferedImage update()
	{
		BufferedImage pixels = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = pixels.createGraphics();
		g2.setColor(Color.WHITE);
		g2.fillRect(xOffSet, yOffSet, width, height);
		g2.drawImage(sprite.getImage(), sprite.getPos().x, sprite.getPos().y, sprite.getSize().width, sprite.getSize().height, null);
		g2.setColor(Color.RED);
		return pixels;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		new Timer().schedule(new TimerTask()
				{
					@Override
					public void run()
					{
						alive = false;
					}
				}, 3000);
	}
}
