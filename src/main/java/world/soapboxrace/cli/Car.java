package world.soapboxrace.cli;

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Car {

	private int dx;
	private int dy;
	private int x;
	private int y;
	private Image image;
	private int playerId;

	public Car(int playerId, int intialX, int initialy) {
		this.playerId = playerId;
		this.x = intialX;
		this.y = initialy;
		int carNumber = playerId % 5;
		ImageIcon ii = new ImageIcon("car-" + carNumber + ".png");
		image = ii.getImage();
	}

	public void move() {
		x += dx;
		y += dy;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Image getImage() {
		return image;
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = -1;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 1;
		}

		if (key == KeyEvent.VK_UP) {
			dy = -1;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 1;
		}
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_UP) {
			dy = 0;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 0;
		}
	}

	public int getPlayerId() {
		return playerId;
	}

}
