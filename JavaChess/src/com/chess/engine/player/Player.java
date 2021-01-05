package com.chess.engine.player;

import java.util.Collection;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.piece.King;
import com.chess.engine.piece.Piece;

import java.lang.RuntimeException;

public abstract class Player {

	protected final Board board;
	protected final King playerKing;
	protected final Collection<Move> legalMoves;
	
	Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves){
		this.board = board;
		this.playerKing = establishKing();
		this.legalMoves = legalMoves;
		
	}

	private King establishKing() {
		for(final Piece piece : getAlivePieces()) {
			if(piece.getPieceType().isKing()) {
				return (King) piece;
			}
		}
		throw new RuntimeException("Should not reach here!");
	}
	
	public abstract Collection<Piece> getAlivePieces();
}
