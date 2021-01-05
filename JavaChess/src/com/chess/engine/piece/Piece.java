package com.chess.engine.piece;

import java.util.Collection;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;


public abstract class Piece {

	protected final PieceType pieceType;
	protected final int piecePosition;
	protected final Team pieceTeam;
	protected final boolean isFirstMove;
	
	
	Piece(final int piecePosition, final Team pieceTeam, final PieceType pieceType){
		this.pieceType = pieceType;
		this.piecePosition = piecePosition;
		this.pieceTeam = pieceTeam;
		
		this.isFirstMove = false;
	}
	
	public int getPiecePosition() {
		return this.piecePosition;
	}
	
	public Team getPieceTeam() {
			return this.pieceTeam;
	}
	
	public boolean isFirstMove() {
		return this.isFirstMove;
	}
	
	public PieceType getPieceType() {
		return this.pieceType;
	}

	public abstract Collection<Move> calculateLegalMoves(final Board board);
	
	
	public enum PieceType{
		
		PAWN("P"),
		KNIGHT("N"),
		BISHOP("B"),
		ROOK("R"),
		QUEEN("Q"),
		KING("K"){
			@Override
			public boolean isKing(){
				return true;
			}
		};
		
		private String pieceName;

		PieceType(final String pieceName){
			this.pieceName = pieceName;
		}
		
		@Override
		public String toString() {
			return this.pieceName;
		}
		
		public boolean isKing() {
			return false;
		};
	}
}
