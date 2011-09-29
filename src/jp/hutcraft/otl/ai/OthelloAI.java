package jp.hutcraft.otl.ai;

import jp.hutcraft.otl.entity.Board;
import jp.hutcraft.otl.entity.Decide;
import jp.hutcraft.otl.entity.Piece;

/**
 * 
 * @author almirage
 *
 */
public interface OthelloAI {
	/**
	 * placing a piece.
	 * you can return Piece.NONE if you haven't any place.
	 * 
	 * @param board
	 * @return not null
	 */
	Decide move(Board board, Piece yourPiece);
	
	/**
	 * your ai name
	 * @return
	 */
	String getNameLabel();
}
