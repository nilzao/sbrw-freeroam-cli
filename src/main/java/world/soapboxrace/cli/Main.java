package world.soapboxrace.cli;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6347891214501660764L;

	public Main() {
		initUI();
	}

	private void initUI() {
		Board board = new Board(1);
		add(board);
		setSize(400, 300);
		setResizable(false);
		setTitle("SBRW - Freeroam Tests");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				Main ex = new Main();
				ex.setVisible(true);
			}
		});
	}
}
