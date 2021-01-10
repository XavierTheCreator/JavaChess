package com.chess.engine.player;

import java.util.Collection;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.piece.Piece;

public class BlackPlayer extends Player{


	public BlackPlayer(final Board board, final Collection<Move> blackStandardLegalMoves,
			final Collection<Move> whiteStandardLegalMoves) {
			super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getAlivePieces() {
		return this.board.getBlackPieces();
	}

	@Override
	public Team getTeam() {
		return Team.BLACK;
	}

	@Override
	public Player getOpponent() {
		return this.board.whitePlayer();
	}

	@Override
	protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegal) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
