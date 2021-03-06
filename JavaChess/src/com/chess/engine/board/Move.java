package com.chess.engine.board;

import com.chess.engine.board.Board.Builder;
import com.chess.engine.piece.Pawn;
import com.chess.engine.piece.Piece;
import com.chess.engine.piece.Rook;

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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result =1;
		
		result = prime * result + this.movedPiece.getPiecePosition();
		result = prime * result + this.destinationCoordinate;
		result = prime * result + this.movedPiece.hashCode();
		
		return result;
	}
	
	@Override
	public boolean equals(final Object other) {
		if(this == other) {
			return true;
		}
		
		if(!(other instanceof Move)) {
			return false;
		}
		final Move otherMove = (Move) other;
		return getCurrentCoordinate() == otherMove.getCurrentCoordinate() &&
				getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
				getMovePiece().equals(otherMove);
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
	
	public boolean isAttack() {
		return false;
	}
	
	public boolean isCastlingMove() {
		return false;
	}
	
	public Piece getAttackedPiece() {
		return null;
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
		public boolean equals(final Object other) {
			if(this == other) {
				return true;
			}
			if(!(other instanceof AttackMove)) {
				return false;
			}
			final AttackMove otherAttackMove = (AttackMove) other;
			return super.equals(otherAttackMove) && getAttackedPiece().equals(getAttackedPiece());
		}

		@Override
		public Board execute() {
			return null;
		}
		
		@Override
		public boolean isAttack() {
			return true;
		}
		
		@Override
		public Piece getAttackedPiece() {
			return this.attackedPiece;
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
		
		@Override
		public Board execute() {
			final Builder builder = new Builder();
			for(final Piece piece : this.board.currentPlayer().getAlivePieces()) {
				if(!this.movedPiece.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			for(final Piece piece : this.board.currentPlayer().getOpponent().getAlivePieces()) {
				builder.setPiece(piece);
			}
			final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
			builder.setPiece(movedPawn);
			builder.setEnPassantPawn(movedPawn);
			builder.setMoveMaker(this.board.currentPlayer().getOpponent().getTeam());
			
			return builder.build();
		}
		
	}
	
	static abstract class CastleMove extends Move {
		
		protected final Rook castleRook;
		protected final int castleRookStart;
		protected final int castleRookDestination;
		
		public CastleMove(final Board board, final Piece movePiece, final int destinationCoordinate, final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
			super(board,movePiece,destinationCoordinate);
			this.castleRook = castleRook;
			this.castleRookStart = castleRookStart;
			this.castleRookDestination = castleRookDestination;
		}
		
		public Rook getCastleRook() {
			return this.castleRook;
		}
		
		@Override
		public boolean isCastlingMove() {
			return true;
		}
		
		@Override 
		public Board execute() {
			
			final Builder builder = new Builder();
			for(final Piece piece : this.board.currentPlayer().getAlivePieces()) {
				if(!this.movedPiece.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			for(final Piece piece : this.board.currentPlayer().getOpponent().getAlivePieces()) {
				builder.setPiece(piece);
			}
			builder.setPiece(this.movedPiece.movePiece(this));
			//TODO look into the first move on normal pieces
			builder.setPiece(new Rook(this.castleRook.getPieceTeam(),this.castleRookDestination));
			builder.setMoveMaker(this.board.currentPlayer().getOpponent().getTeam());
			return builder.build();
					
		}
	
	}
	
	public static final class KingSideCastleMove extends CastleMove {

		public KingSideCastleMove(final Board board,final Piece movedPiece,final int destinationCoordinate, final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
			super(board, movedPiece, destinationCoordinate, castleRook, destinationCoordinate, destinationCoordinate);
		}
		
		@Override
		public String toString() {
			return "0-0";
		}
		
	}
	
	public static final class QueenSideCastleMove extends CastleMove {

		public QueenSideCastleMove(final Board board,final Piece movedPiece,final int destinationCoordinate, final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
			super(board, movedPiece, destinationCoordinate, castleRook, destinationCoordinate, destinationCoordinate);
		}
		
		@Override
		public String toString() {
			return "0-0-0";
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
