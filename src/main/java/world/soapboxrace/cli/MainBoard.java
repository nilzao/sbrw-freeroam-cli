package world.soapboxrace.cli;

import javax.swing.JFrame;

public class MainBoard extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6347891214501660764L;

	public MainBoard(int playerId) {
		Board board = new Board(playerId);
		add(board);
		setSize(400, 300);
		setResizable(false);
		setTitle("SBRW - Freeroam Tests");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		new Sender();
	}

}
