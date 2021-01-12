package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Team;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.piece.Piece;
import com.google.common.collect.ImmutableList;

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

		final List<Move> kingCastles = new ArrayList<>();
		
		if(this.playerKing.isFirstMove() && !this.isInCheck());
		//black king side castle
			if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(7);
				
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if(Player.calculateAttacksOnTile(5, opponentsLegal).isEmpty() &&
							Player.calculateAttacksOnTile(6, opponentsLegal).isEmpty() && 
							rookTile.getPiece().getPieceType().ROOK.isRook()){
						//TODO 
						kingCastles.add(null);
					}
					
					kingCastles.add(null); // Null will be replaced later 
				}
			}
			if(!this.board.getTile(1).isTileOccupied() && 
				!this.board.getTile(2).isTileOccupied() && 
				!this.board.getTile(3).isTileOccupied()){
					final Tile rookTile = this.board.getTile(0);
					if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
						//TODO add castle Move
						kingCastles.add(null);
					}
				}
			
		
		return ImmutableList.copyOf(kingCastles);
	}
	
}
