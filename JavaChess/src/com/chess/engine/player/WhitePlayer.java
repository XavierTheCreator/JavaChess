package com.chess.engine.player;

import java.util.Collection;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.piece.Piece;

public class WhitePlayer extends Player {

	public WhitePlayer(Board board, Collection<Move> whiteStandardLegalMoves,
			Collection<Move> blackStandardLegalMoves) {
		super(board, whiteStandardLegalMoves ,blackStandardLegalMoves );
	}

	@Override
	public Collection<Piece> getAlivePieces() {
		// TODO Auto-generated method stub
		return this.board.getWhitePieces();
	}

	@Override
	public Team getTeam() {
		// TODO Auto-generated method stub
		return Team.WHITE;
	}

	@Override
	public Player getOpponent() {
		// TODO Auto-generated method stub
		return this.board.blackPlayer();
	}
}
