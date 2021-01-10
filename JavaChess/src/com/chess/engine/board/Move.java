package com.chess.engine.board;

import com.chess.engine.board.Board.Builder;
import com.chess.engine.piece.Piece;

public abstract class Move {

	final Board board;
	final Piece movedPiece;
	final int destinationCoordinate;
	
	public static final Move NULL_MOVE = new NullMove();
	
	
	private Move(final Board board,final Piece movedPiece,final int destinationCoordinate){
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCoordinate = destinationCoordinate;
	}
	
	public int getCurrentCoordinate() {
		return this.movedPiece.getPiecePosition();
	}
	
	public int getDestinationCoordinate() {
		return this.destinationCoordinate;
	}
	
	
	public Piece getMovePiece() {
		return this.movedPiece;
	}
	
	
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

	public static final class MajorMove extends Move {

		public MajorMove(final Board board,final Piece movedPiece,final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
			// TODO Auto-generated constructor stub
		}
		
	}
	
	public static class AttackMove extends Move {
		
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
	
	public static final class PawnMove extends Move {

		public PawnMove(final Board board,final Piece movedPiece,final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
		
	}
	
	public static class PawnAttackMove extends AttackMove {

		public PawnAttackMove(final Board board,final Piece movedPiece,final int destinationCoordinate, final Piece attackedPiece) {
			super(board, movedPiece, destinationCoordinate, attackedPiece);
		}
		
	}
	
	public static final class PawnEnPassantAttackMove extends PawnAttackMove {

		public PawnEnPassantAttackMove(final Board board,final Piece movedPiece,final int destinationCoordinate, final Piece attackedPiece) {
			super(board, movedPiece, destinationCoordinate, attackedPiece);
		}
		
	}

	public static final class PawnJump extends Move {

		public PawnJump(final Board board,final Piece movedPiece,final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
		
	}
	
	static abstract class CastleMove extends Move {
		public CastleMove(final Board board, final Piece movePiece, final int destinationCoordinate) {
			super(board,movePiece,destinationCoordinate);
		}
	}
	
	public static final class KingSideCastleMove extends CastleMove {

		public KingSideCastleMove(final Board board,final Piece movedPiece,final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
		
	}
	
	public static final class QueenSideCastleMove extends CastleMove {

		public QueenSideCastleMove(final Board board,final Piece movedPiece,final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
		
	}
	
	public static final class NullMove extends Move {

		public NullMove() {
			super(null,null,-1);
		}
		
		@Override
		public Board execute() {
			throw new RuntimeException("cannot execute the null move!");
		}
		
	}
	
	public static class MoveFactory{
		
		private MoveFactory() {
			throw new RuntimeException("Not instantiable");
		}
		
		public static Move createMove(final Board board,final int currentCoordinate, final int destinationCoordinate) {
			for(final Move move : board.getAllLegalMoves()) {
				if(move.getCurrentCoordinate() == currentCoordinate && 
						move.getDestinationCoordinate() == destinationCoordinate) {
					return move;
				}
			}
			return NULL_MOVE;
		}
	}
	
}
