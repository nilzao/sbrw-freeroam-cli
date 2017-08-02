package world.soapboxrace.cli;

import javax.swing.JFrame;

public class MainBoard extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6347891214501660764L;

	private static Board board;

	public MainBoard(int playerId) {
		board = new Board(playerId);
		add(board);
		setSize(400, 300);
		setResizable(false);
		setTitle("SBRW - Freeroam Tests");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		new Sender();
	}

	public static void addUpdateCar(Car car) {
		board.addCar(car);
	}
}
