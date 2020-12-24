package com.chess.engine.piece;

import java.util.Collection;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;


public abstract class Piece {

	protected final int piecePosition;
	protected final Team pieceTeam;
	protected final boolean isFirstMove;
	
	
	Piece(final int piecePosition, final Team pieceTeam){
		this.piecePosition = piecePosition;
		this.pieceTeam = pieceTeam;
		
		this.isFirstMove = false;
	}
	
	public Team getPieceTeam() {
			return this.pieceTeam;
	}
	
	public boolean isFirstMove() {
		return this.isFirstMove;
	}
	

	public abstract Collection<Move> calculateLegalMoves(final Board board);
	
}
