package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Observable;

import javax.swing.JFrame;

public class Controller extends Observable {
	
	private boolean launchable;
	private File toListDirectory;
	private File exportFile;

	public void initiateFrame() {
		JFrame frame = new JFrame("FileListing software - Adrien Blaise");
		View panel = new View(this);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.getContentPane().add(panel, "Center");
		frame.setSize(panel.getPreferredSize());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void setToListDirectory(File toListDirectory) {
		this.toListDirectory = toListDirectory;
		updateLaunchable();
	}
	
	public void setExportFile(File exportFile) {
		this.exportFile = exportFile;
		updateLaunchable();
	}
	
	public void launchProcess() {
		
	}
	
	private void updateLaunchable() {
		launchable = this.exportFile != null && this.toListDirectory != null;
		this.setChanged();
		this.notifyObservers(launchable);
	}
}
