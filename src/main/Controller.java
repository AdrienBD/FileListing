package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;

import javax.swing.JFrame;

import message.Message;
import message.MessageTitle;

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
		this.setChanged();
		this.notifyObservers(new Message(MessageTitle.LAUNCHED));
		
		File[] files = toListDirectory.listFiles();
		exportFileList(files);
		
		this.setChanged();
		this.notifyObservers(new Message(MessageTitle.PROCESSED));
	}
	
	private void updateLaunchable() {
		launchable = this.exportFile != null && this.toListDirectory != null;
		if (launchable) {
			this.setChanged();
			this.notifyObservers(new Message(MessageTitle.LAUNCHABLE));
		}
	}
	
	private void exportFileList(File[] files) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(exportFile));
			for (File f : files) {
				bw.write(f.getName());
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
