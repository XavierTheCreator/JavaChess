package com.chess.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Table {
	
	private final JFrame gameFrame;
	private static Dimension OUTER_FRAME_DIMENSIONS = new Dimension(600,600);
	
	public Table() {
		this.gameFrame = new JFrame("JChess");
		final JMenuBar tableMenuBar = new JMenuBar();
		populateMenuBar(tableMenuBar);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSIONS);
		this.gameFrame.setVisible(true);
	}

	private void populateMenuBar(final JMenuBar tableMenuBar) {
		tableMenuBar.add(createFileMenu());
	}

	private JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu("File");
		
		final JMenuItem openPGN = new JMenuItem("Load PGN File");
		openPGN.addActionListener(new ActionListner(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Open the PGN file");
			}
		});
		
		fileMenu.add(openPGN);
		
		return fileMenu;
	}
}
