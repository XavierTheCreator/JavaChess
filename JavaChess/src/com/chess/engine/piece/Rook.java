package com.chess.engine.piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.google.common.collect.ImmutableList;

public class Rook extends Piece {
	
	private static final int [] CANDIDATE_MOVE_VECTOR_COORDINATES = {-8 , -1 , 1, 8 };

	Rook(int piecePosition, Team pieceTeam) {
		super(piecePosition, pieceTeam);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {

		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int candidateCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES) {
			int candidateDestinationMove = this.piecePosition;
			
			while(BoardUtils.isValidTileCoordinateDestination(candidateDestinationMove)) {
				
				if(isFirstColumnExclusion(candidateDestinationMove, candidateCoordinateOffset)|| 
						isEigthColumnExclusion(candidateDestinationMove, candidateCoordinateOffset)){
					break;
				}
				
				candidateDestinationMove += candidateCoordinateOffset;
				
				if(BoardUtils.isValidTileCoordinateDestination(candidateDestinationMove)) {
					
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
						break;

					}
					
				}
			}
		}
				
		return ImmutableList.copyOf(legalMoves);		
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {	
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);		
	
	}
	
	private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffset) {
	    return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -1);
	}

}
