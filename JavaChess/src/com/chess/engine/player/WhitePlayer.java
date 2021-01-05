package com.chess.engine.player;

import java.util.Collection;

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
}
