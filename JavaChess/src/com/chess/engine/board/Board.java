package com.chess.engine.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chess.engine.Team;
import com.chess.engine.piece.*;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;
import com.google.common.collect.ImmutableList;

public class Board {
	
	private final List<Tile> gameBoard;
	private final Collection<Piece> whitePieces;
	private final Collection<Piece> blackPieces;
	
	private final WhitePlayer whitePlayer;
	private final BlackPlayer blackPlayer;
	private Player currentPlayer;
	
	private Board(Builder builder) {
		this.currentPlayer = null;
		this.gameBoard = createGameBoard(builder);
		this.whitePieces = calculateActivePieces(this.gameBoard,Team.WHITE);
		this.blackPieces = calculateActivePieces(this.gameBoard,Team.BLACK);
		
		final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
		final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);
		
		this.whitePlayer = new WhitePlayer(this,whiteStandardLegalMoves,blackStandardLegalMoves);
		this.blackPlayer = new BlackPlayer(this,blackStandardLegalMoves,whiteStandardLegalMoves);
		this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer,this.blackPlayer);

	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		for(int i = 0; i < BoardUtils.NUM_TILES;i++) {
			final String tileText = this.gameBoard.get(i).toString();
			builder.append(String.format("%3s", tileText));
			if((i+1)% BoardUtils.NUM_TILES_PER_ROW == 0) {
				builder.append("\n");
			}
		}
		
		return builder.toString();
		
	}
	
	public Player whitePlayer() {
		return this.whitePlayer;
	}
	
	public Player blackPlayer() {
		return this.blackPlayer;
	}
	
	public Player currentPlayer() {
		return this.currentPlayer;
	}
	
	public Collection<Piece> getBlackPieces(){
		return this.blackPieces;
	}
	
	public Collection<Piece> getWhitePieces(){
		return this.whitePieces;
	}
		
	private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {
		final List<Move> legalMoves = new ArrayList<>();
		for(final Piece piece: pieces) {
			legalMoves.addAll(piece.calculateLegalMoves(this));
		}
		
		return ImmutableList.copyOf(legalMoves);
	}

	private static Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, Team team) {

		final List<Piece> activePieces = new ArrayList<>();
		
		for(final Tile tile : gameBoard) {
			if(tile.isTileOccupied()) {
				final Piece piece = tile.getPiece();
				if(piece.getPieceTeam() == team ) {
					activePieces.add(piece);
				}
			}
		}
		return ImmutableList.copyOf(activePieces);
	}

	public Tile getTile(final int tileCoordinate) {
		return gameBoard.get(tileCoordinate);
		
	}
	
	private static List<Tile> createGameBoard(final Builder builder){
		final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
		for(int i = 0; i < BoardUtils.NUM_TILES;i++) {
			tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
		}
		return ImmutableList.copyOf(tiles);
	}
	 
	public static Board setupBoard() {
		final Builder builder = new Builder();
		
		//Blacks
		builder.setPiece(new Rook(Team.BLACK,0));
		builder.setPiece(new Knight(Team.BLACK,1));
		builder.setPiece(new Bishop(Team.BLACK,2));
		builder.setPiece(new Queen(Team.BLACK,3));
		builder.setPiece(new King(Team.BLACK,4));
		builder.setPiece(new Bishop(Team.BLACK,5));
		builder.setPiece(new Knight(Team.BLACK,6));
		builder.setPiece(new Rook(Team.BLACK,7));
		builder.setPiece(new Pawn(Team.BLACK,8));
		builder.setPiece(new Pawn(Team.BLACK,9));
		builder.setPiece(new Pawn(Team.BLACK,10));
		builder.setPiece(new Pawn(Team.BLACK,11));
		builder.setPiece(new Pawn(Team.BLACK,12));
		builder.setPiece(new Pawn(Team.BLACK,13));
		builder.setPiece(new Pawn(Team.BLACK,14));
		builder.setPiece(new Pawn(Team.BLACK,15));

		// Whites
		builder.setPiece(new Pawn(Team.WHITE,48));
		builder.setPiece(new Pawn(Team.WHITE,49));
		builder.setPiece(new Pawn(Team.WHITE,50));
		builder.setPiece(new Pawn(Team.WHITE,51));
		builder.setPiece(new Pawn(Team.WHITE,52));
		builder.setPiece(new Pawn(Team.WHITE,53));
		builder.setPiece(new Pawn(Team.WHITE,54));
		builder.setPiece(new Pawn(Team.WHITE,55));
		builder.setPiece(new Rook(Team.WHITE,56));
		builder.setPiece(new Knight(Team.WHITE,57));
		builder.setPiece(new Bishop(Team.WHITE,58));
		builder.setPiece(new Queen(Team.WHITE,59));
		builder.setPiece(new King(Team.WHITE,60));
		builder.setPiece(new Bishop(Team.WHITE,61));
		builder.setPiece(new Knight(Team.WHITE,62));
		builder.setPiece(new Rook(Team.WHITE,63));

		builder.setMoveMaker(Team.WHITE);
		
		
		return builder.build();
		
	}
	
	public static class Builder {
		
		Map<Integer,Piece> boardConfig;
		Team nextMoveMaker;
		
		public Builder() {
			this.boardConfig = new HashMap<>();
		}
		
		public Builder setPiece(final Piece piece) {
			this.boardConfig.put(piece.getPiecePosition(), piece);
			return this;
		}
		
		public Builder setMoveMaker(Team nextMoveMaker) {
			this.nextMoveMaker = nextMoveMaker;
			return this;
		}
		
		public Board build() {
			
			return new Board(this);
		}
	}
}
