package com.chess.engine.piece;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

public class Knight extends Piece{
	
	private static final int[] MOVE_SQUARES = {-17, -15, -10, -6, 6, 10, 15, 17};

	Knight( final int piecePosition, final Team pieceTeam) {
		super(piecePosition, pieceTeam);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Move> calculateLegalMoves(Board board) {

		int candidateDestinationMove;
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCanidate : MOVE_SQUARES) {
			candidateDestinationMove = this.piecePosition + currentCanidate;
			
			if(true) {				
				final Tile candidateDestinationTile = board.getTile(candidateDestinationMove);
				
				if(!candidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new Move());
				}
				else {
					
					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Team pieceTeam = pieceAtDestination.getPieceTeam();
					
					if(this.pieceTeam != pieceTeam) {
						legalMoves.add(new Move());
					}
				}
			}
			
		}
		
		return ImmutableList.copyOf(legalMoves);
	}
}
