package com.chess.engine.player;

import java.util.Collection;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.piece.Piece;

public class BlackPlayer extends Player{


	public BlackPlayer(Board board, Collection<Move> blackStandardLegalMoves,
			Collection<Move> whiteStandardLegalMoves) {
			super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getAlivePieces() {
		// TODO Auto-generated method stub
		return this.board.getBlackPieces();
	}

	@Override
	public Team getTeam() {
		// TODO Auto-generated method stub
		return Team.BLACK;
	}

	@Override
	public Player getOpponent() {
		// TODO Auto-generated method stub
		return this.board.whitePlayer();
	}
	
	
}
