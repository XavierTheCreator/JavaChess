package com.chess.engine.piece;

import java.util.List;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;


public abstract class Piece {

	protected final int piecePosition;
	protected final Team pieceTeam;
	
	Piece(final int piecePosition, final Team pieceTeam){
		this.piecePosition = piecePosition;
		this.pieceTeam = pieceTeam;
	}
	
	public Team getPieceTeam() {
			return this.pieceTeam;
	}
	
	public abstract List<Move> calculateLegalMoves(final Board board);
	
}
