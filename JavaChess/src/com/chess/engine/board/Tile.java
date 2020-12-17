package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Tile { 
	
	int tileCoordinate;
	
	Tile(int tileCoordinate){
		this.tileCoordinate = tileCoordinate;
	}
	
	public abstract boolean isTileOccupied();
	
	public abstract Piece getPiece();
	
	
	//-------------------------------------------------------------------	

	public static final class EmptyTile extends Tile{
		
		EmptyTile(int coordinate){
			super(coordinate);
		}
		
		@Override
		public boolean isTileOccupied() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public Piece getPiece() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	
	public static final class OccupiedTile extends Tile{
		
		Piece pieceOnTile;
		
		public OccupiedTile(int tileCoordinate, Piece pieceOnTile) {
			// TODO Auto-generated constructor stub
			super(tileCoordinate);
			this.pieceOnTile = pieceOnTile;
	
		}
		
		@Override
		public boolean isTileOccupied() {
			return true;
		}
		
		@Override
		public Piece getPiece() {
			return this.pieceOnTile;
		}
		
	}
	
}