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

	@Override
	protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegal) {

		final List<Move> kingCastles = new ArrayList<>();
		
		if(this.playerKing.isFirstMove() && !this.isInCheck());
			if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(63);
				
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if(Player.calculateAttacksOnTile(61, opponentsLegal).isEmpty() &&
							Player.calculateAttacksOnTile(62, opponentsLegal).isEmpty() && 
							rookTile.getPiece().getPieceType().ROOK.isRook()){
						//TODO 
						kingCastles.add(null);
					}
					
					kingCastles.add(null); // Null will be replaced later 
				}
			}
			if(!this.board.getTile(59).isTileOccupied() && 
				!this.board.getTile(58).isTileOccupied() && 
				!this.board.getTile(57).isTileOccupied()){
					final Tile rookTile = this.board.getTile(56);
					if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
						//TODO add castle Move
						kingCastles.add(null);
					}
				}
			
		
		return ImmutableList.copyOf(kingCastles);
	}
}
