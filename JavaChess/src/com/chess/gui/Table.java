package com.chess.gui;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;


public class Table {
	
	private final JFrame gameFrame;
	private final BoardPanel boardPanel;
	
	private final Color lightTileColor = Color.decode("#FFFACD");
	private final Color darkTileColor = Color.decode("#F93E1A");
	
	private final static Dimension OUTER_FRAME_DIMENSIONS = new Dimension(600,600);
	private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400,350);
	private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);
	
	public Table() {
		this.gameFrame = new JFrame("JChess");
		this.gameFrame.setLayout(new BorderLayout());
		final JMenuBar tableMenuBar = createTableMenuBar();
		this.gameFrame.setJMenuBar(tableMenuBar);
		
		this.gameFrame.setSize(OUTER_FRAME_DIMENSIONS);
		
		this.boardPanel = new BoardPanel();
		this.gameFrame.add(this.boardPanel,BorderLayout.CENTER);
		
		this.gameFrame.setVisible(true);
	}

	private JMenuBar createTableMenuBar() {
		final JMenuBar tableMenuBar = new JMenuBar();
		tableMenuBar.add(createFileMenu());
		return tableMenuBar;
	}

	private JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu("File");
		
		final JMenuItem openPGN = new JMenuItem("Load PGN File");
		openPGN.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Open the PGN file");
			}
		});
		
		fileMenu.add(openPGN);
		
		
		final JMenuItem exitMenuItem = new JMenuItem("Quit");
		exitMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		fileMenu.add(exitMenuItem);
		return fileMenu;
	}
	
	private class BoardPanel extends JPanel{
		final List<TilePanel> boardTiles;
		
		BoardPanel(){
			super(new GridLayout(8,8));
			this.boardTiles = new ArrayList<>();	
			for(int i = 0; i < BoardUtils.NUM_TILES;i++) {
				final TilePanel tilePanel = new TilePanel(this,i);
				this.boardTiles.add(tilePanel);
				add(tilePanel);
			}
			setPreferredSize(BOARD_PANEL_DIMENSION);
			validate();
		}
	}
	
	private class TilePanel extends JPanel {
		private final int tileId;
		
		TilePanel(final BoardPanel boardPanel, final int tileId){
			super(new GridBagLayout());
			this.tileId = tileId;
			setPreferredSize(TILE_PANEL_DIMENSION);
			assignTileColor();
			validate();
		}
		
		private void assignTilePieceIcon(final Board board) {
			this.removeAll();
			if(board.getTile(this.tileId).isTileOccupied()) {
				String iconPath = "";
				try {
					// Add images(GIF ) into project
					final BufferedImage image = 
							ImageIO.read(new File(pathname + board.getTile(this.tileId).getPiece().getPieceTeam().toString().substring(0,1) + 
									board.getTile(this.tileId).getPiece().toString() + ".gif"));
					add(new JLabel(new ImageIcon(image)));
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		}

		private void assignTileColor() {
			if(BoardUtils.FIRST_ROW[this.tileId] || 
					BoardUtils.THIRD_ROW[this.tileId] ||
					BoardUtils.FIFTH_ROW[this.tileId] ||
					BoardUtils.SEVENTH_ROW[this.tileId]) {
				setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
			}
			else if (BoardUtils.SECOND_ROW[this.tileId] || 
					BoardUtils.FOURTH_ROW[this.tileId] ||
					BoardUtils.SIXTH_ROW[this.tileId] ||
					BoardUtils.EIGHTH_ROW[this.tileId]) {
				setBackground(this.tileId % 2 !=0 ? lightTileColor : darkTileColor);
			}
			
		}
	}
}
