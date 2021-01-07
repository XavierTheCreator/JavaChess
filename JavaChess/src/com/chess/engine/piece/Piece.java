package com.chess.engine.piece;

import java.util.Collection;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;


public abstract class Piece {

	protected final PieceType pieceType;
	protected final int piecePosition;
	protected final Team pieceTeam;
	protected final boolean isFirstMove; // Specifically for pawn 
	private final int cachedHashCode;
	
	
	Piece(final int piecePosition, final Team pieceTeam, final PieceType pieceType){
		this.pieceType = pieceType;
		this.piecePosition = piecePosition;
		this.pieceTeam = pieceTeam;
		
		this.isFirstMove = false;
		this.cachedHashCode = computeHashCode();
	}
	
	private int computeHashCode() {
		int result = pieceType.hashCode();
		result = 31 * result + pieceTeam.hashCode();
		result = 31 * result + piecePosition;
		result = 31 * result + (isFirstMove ? 1 : 0);
		return result;
	}
	
	@Override
	public boolean equals(final Object other) {
		if(this == other ) {
			return true;
		}
		if(!(other instanceof Piece)) {
			return false;
		}
		final Piece otherPiece = (Piece) other;
		return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() &&
				pieceTeam == otherPiece.getPieceTeam() && isFirstMove == otherPiece.isFirstMove();
	}
	
	@Override
	public int hashCode() {
		return this.cachedHashCode;
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
	
	public abstract Piece movePiece(Move move);
	
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
