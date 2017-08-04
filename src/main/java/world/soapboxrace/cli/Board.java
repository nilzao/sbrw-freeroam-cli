package world.soapboxrace.cli;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4341997790459453857L;
	private Timer timer;
	private List<Car> cars = new LinkedList<>();
	private static Car playerCar;
	private int currentPlayer = 0;
	private final int DELAY = 10;

	public Board(int playerId) {
		playerCar = new Car(playerId, 40, 60);
		cars.add(playerCar);
		for (int i = 0; i < 4; i++) {
			cars.add(null);
		}
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.WHITE);
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void addUpdateCar(int idx, Car car) {
		cars.set(idx, car);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
		Toolkit.getDefaultToolkit().sync();
	}

	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (Car car : cars) {
			if (car != null) {
				g2d.drawImage(car.getImage(), car.getX(), car.getY(), this);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		playerCar.move();
		repaint();
	}

	private class TAdapter extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_0) {
				cars.add(++currentPlayer, new Car(currentPlayer, playerCar.getX(), playerCar.getY()));
			}
			if (key == KeyEvent.VK_9) {
				removeCar(currentPlayer--);
			}
			playerCar.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			playerCar.keyPressed(e);
		}
	}

	public static Car getPlayerCar() {
		return playerCar;
	}

	private void removeCar(int i) {
		cars.set(i, null);
	}

}
