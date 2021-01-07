package com.chess.engine.board;

import com.chess.engine.board.Board.Builder;
import com.chess.engine.piece.Piece;

public abstract class Move {

	final Board board;
	final Piece movedPiece;
	final int destinationCoordinate;
	
	public abstract Board execute();
	
	private Move(final Board board,final Piece movedPiece,final int destinationCoordinate){
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCoordinate = destinationCoordinate;
	}
	
	public int getDestinationCoordinate() {
		return this.destinationCoordinate;
	}
	
	
	public Piece getMovePiece() {
		return this.movedPiece;
	}
	
	public static final class MajorMove extends Move {

		public MajorMove(final Board board,final Piece movedPiece,final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Board execute() {
			final Builder builder = new Board.Builder();
			
			for(final Piece piece : this.board.currentPlayer().getAlivePieces()) {
				
				//TODO HashCode and equals pieces
				if (!this.movedPiece.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			
			for(final Piece piece : this.board.currentPlayer().getOpponent().getAlivePieces()) {
				builder.setPiece(piece);
			}
			
			builder.setPiece(this.movedPiece.movePiece(this));
			builder.setMoveMaker(this.board.currentPlayer().getOpponent().getTeam());
			
			return builder.build();
			
		}
		
	}
	
	public static final class AttackMove extends Move {
		
		final Piece attackedPiece;
		
		public AttackMove(final Board board,final Piece movedPiece,final int destinationCoordinate,final Piece attackedPiece ) {
			super(board, movedPiece, destinationCoordinate);
			this.attackedPiece = attackedPiece;
		}

		@Override
		public Board execute() {
			return null;
		}
		
	}
	
	

}
