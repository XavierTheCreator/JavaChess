package com.chess.engine.piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorMove;
import com.google.common.collect.ImmutableList;

public class Pawn extends Piece {
	
	public final static int[] CANDIDATE_MOVE_COORDINATE = {8,16, 7 , 9};

	Pawn(final int piecePosition, final Team pieceTeam) {
		super(piecePosition, pieceTeam);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATE) {
			
			final int candidateDestinationMove = this.piecePosition + (this.getPieceTeam().getDirection() * currentCandidateOffset);
			
			if(!BoardUtils.isValidTileCoordinateDestination(candidateDestinationMove)) {
				continue;
			}
			
			if(currentCandidateOffset == 8 && board.getTile(candidateDestinationMove).isTileOccupied()) {
				//TODO need to add promotions 
				legalMoves.add(new MajorMove(board, this, candidateDestinationMove));
			} else if (currentCandidateOffset == 16 && this.isFirstMove() && 
					(BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceTeam().isBlack()) ||
					(BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceTeam().isWhite())) {
				
				final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceTeam.getDirection() * 8); 
				if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() && 
						!board.getTile(candidateDestinationMove).isTileOccupied()) {
					legalMoves.add(new MajorMove(board,this,candidateDestinationMove));
				}
				
				//Confusing implementation of pawn attack needs to be changed
			}else if(currentCandidateOffset == 7 &&
					 !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.getPieceTeam().isWhite() ||
					  (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.getPieceTeam().isBlack()) ))){
				if(board.getTile(candidateDestinationMove).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationMove).getPiece();
					if(this.pieceTeam != pieceOnCandidate.getPieceTeam()) {
						//TODO ATTACK IMPLEMENTATION IS NOT FINSIHED,
						legalMoves.add(new MajorMove(board,this, candidateDestinationMove));
					}
				}
				
			}else if(currentCandidateOffset == 9 &&
				!((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceTeam.isWhite() ||
					(BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceTeam.isBlack() )))){
				if(board.getTile(candidateDestinationMove).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationMove).getPiece();
					if(this.pieceTeam !=pieceOnCandidate.getPieceTeam()) {
						legalMoves.add(new MajorMove(board,this,candidateDestinationMove));
					}
				}
				
			}
			
		}
		
		return ImmutableList.copyOf(legalMoves);
	}
	
	
	/* 
	 * 
	 *  Will be implemented  
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {	
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == 7);		
	
	}
	
	private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffset) {
	    return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 9);
	}
	*/

}
