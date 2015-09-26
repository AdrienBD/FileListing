package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Main {

	public static void main(String s[]) {
		JFrame frame = new JFrame("FileListing software - Adrien Blaise");
		View panel = new View();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.getContentPane().add(panel, "Center");
		frame.setSize(panel.getPreferredSize());
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}
