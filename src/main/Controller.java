package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import message.Message;
import message.MessageTitle;

public class Controller extends Observable {
	
	private boolean launchable;
	private File toListDirectory;
	private File exportFile;

	public void initiateFrame() {
		JFrame frame = new JFrame(Main.SOFTWARE_NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		this.addMainPanelToFrame(frame);
		this.setMenu(frame);
		
		frame.pack();
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
		try {
			exportFileList(files);
			
			this.setChanged();
			this.notifyObservers(new Message(MessageTitle.PROCESSED));
		} catch (IOException e) {

			this.setChanged();
			this.notifyObservers(new Message(MessageTitle.ERROR_ON_EXPORT));
		}
		
	}
	
	private void addMainPanelToFrame(JFrame frame) {
		View panel = new View(this);
		frame.getContentPane().add(panel, "Center");
		frame.setSize(panel.getPreferredSize());
	}

	private void setMenu(JFrame frame) {
//		System.setProperty("apple.laf.useScreenMenuBar", "true");
//      System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Test");
        
		JMenuBar menuBar = new JMenuBar();
		
		JMenuItem aboutMenuItem = new JMenuItem("About");
		menuBar.add(aboutMenuItem);
		
		frame.setJMenuBar(menuBar);
	}
	
	private void updateLaunchable() {
		launchable = this.exportFile != null && this.toListDirectory != null;
		if (launchable) {
			this.setChanged();
			this.notifyObservers(new Message(MessageTitle.LAUNCHABLE));
		}
	}
	
	private void exportFileList(File[] files) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(exportFile));
		for (File f : files) {
			bw.write(f.getName());
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
}
