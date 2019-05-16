import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import bensAbstract.Screen;

public class Main
{
	public static void main(String[] args)
	{
		new Main();
	}
	
	public Main()
	{
		JFrame frame = initFrame();
		Game game = new Game(10, 10, (int)frame.getPreferredSize().getWidth() - 10 - 15, (int)frame.getPreferredSize().getHeight() - 10 - 40);
		Screen sc = new Screen(50)
				{
					public void update()
					{
						this.pixels = game.update();
					}
				};
		sc.getPanel().setBackground(Color.BLACK);
		frame.add(sc.getPanel(), BorderLayout.CENTER);
		frame.pack();
		frame.repaint();
		sc.start();
	}
	
	public static JFrame initFrame()
	{
		JFrame frame = new JFrame("Bounce");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		frame.setPreferredSize(new Dimension(dm.getWidth(), dm.getHeight() - 20));
		frame.setLayout(new BorderLayout());
		frame.setBackground(Color.BLACK);
		frame.pack();
		frame.repaint();
		return frame;
	}
}
