package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.piece.King;
import com.chess.engine.piece.Piece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.lang.RuntimeException;

public abstract class Player {

	protected final Board board;
	protected final King playerKing;
	protected final Collection<Move> legalMoves;
	private final boolean isInCheck;
	
	Player(final Board board, final Collection<Move> legalMoves,final Collection<Move> opponentMoves) {
		
		this.board = board;
		this.playerKing = establishKing();
		this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves,calculateKingCastles(legalMoves,opponentMoves)));
		this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
		
	}
	
	protected static Collection<Move> calculateAttacksOnTile(int piecePosition,Collection<Move> moves){
		final List<Move> attackMoves = new ArrayList<Move>();
		for(final Move move : moves) {
			if(piecePosition == move.getDestinationCoordinate()) {
				attackMoves.add(move);
			}
		}
		return ImmutableList.copyOf(attackMoves);
	}

	private King establishKing() {
		for(final Piece piece : getAlivePieces()) {
			if(piece.getPieceType().isKing()) {
				return (King) piece;
			}
		}
		throw new RuntimeException("Should not reach here!");
	}
	
	public King getPlayerKing() {
		return this.playerKing;
	}
	
	public Collection<Move> getLegalMoves(){
		return this.legalMoves;
	}
	
	

	public boolean isMoveLegal(final Move move) {
		return this.legalMoves.contains(move);
	}
	
	//Salvation move when king is under attack
	protected boolean hasEscapeMoves() {
		for(final Move move : this.legalMoves) {
			final MoveTransition transition = makeMove(move);
			if(transition.getMoveStatus().isDone()) {
				return true;
			}
		}
		return false;
	}

	public boolean isInStalemate() {
		return !this.isInCheck && !hasEscapeMoves();
	}
	
	public boolean isInCheck() {
		return this.isInCheck();
	}
	
	public boolean isCheckMate() {
		return this.isInCheck && !hasEscapeMoves();
	}
	

	public boolean isCastled() {
		return false;
	}
	
	public MoveTransition makeMove(final Move move) {
		if(!isMoveLegal(move)) {
			return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
		}
		
		final Board transitionBoard = move.execute();
		
		final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(), transitionBoard.currentPlayer().getLegalMoves());
		
		if(!kingAttacks.isEmpty()) {
			return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
		}
		
		return new MoveTransition(transitionBoard, move , MoveStatus.DONE);
	}
	
	
	public abstract Collection<Piece> getAlivePieces();
	
	public abstract Team getTeam();
	
	public abstract Player getOpponent();
	
	protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals,Collection<Move> opponentsLegal);
}
