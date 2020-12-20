package com.chess.engine.piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import static com.chess.engine.board.Move.*;

public class Knight extends Piece{
	
	private static final int[] MOVE_SQUARES = {-17, -15, -10, -6, 6, 10, 15, 17};

	Knight( final int piecePosition, final Team pieceTeam) {
		super(piecePosition, pieceTeam);
		// TODO Auto-generated constructor stub
	}


	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {

		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset : MOVE_SQUARES) {
			int candidateDestinationMove = this.piecePosition + currentCandidateOffset;
			
			if(BoardUtils.isValidTileCoordinateDestination(candidateDestinationMove) ) {		
				
				if(isFirstColumnExclusion(this.piecePosition,currentCandidateOffset)||
						isSecondColumnExclusion(this.piecePosition, currentCandidateOffset)||
						isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset)||
						isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
					continue;
				}
				final Tile candidateDestinationTile = board.getTile(candidateDestinationMove);
				if(!candidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new MajorMove(board, this, candidateDestinationMove));
				}
				else {
					
					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Team pieceTeam = pieceAtDestination.getPieceTeam();
					
					if(this.pieceTeam != pieceTeam) {
						legalMoves.add(new AttackMove(board,this, candidateDestinationMove,pieceAtDestination));
					}
				}
			}
			
		}
		
		return ImmutableList.copyOf(legalMoves);
	}

	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
			candidateOffset == 6 || candidateOffset == 15);		
	
	}
	
	private static boolean isSecondColumnExclusion(final int currentPosition,final int candidateOffset) {
		return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
	}
	
	
	private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == -6
				|| candidateOffset == 10 || candidateOffset == 17);
	}
	
}
