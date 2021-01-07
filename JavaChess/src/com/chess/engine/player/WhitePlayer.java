package com.chess.engine.player;

import java.util.Collection;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.piece.Piece;

public class WhitePlayer extends Player {

	public WhitePlayer(final Board board,final Collection<Move> whiteStandardLegalMoves,
			final Collection<Move> blackStandardLegalMoves) {
		super(board, whiteStandardLegalMoves ,blackStandardLegalMoves );
	}

	@Override
	public Collection<Piece> getAlivePieces() {
		return this.board.getWhitePieces();
	}

	@Override
	public Team getTeam() {
		return Team.WHITE;
	}

	@Override
	public Player getOpponent() {
		return this.board.blackPlayer();
	}
}
