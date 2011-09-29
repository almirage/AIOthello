package jp.hutcraft.otl.ctl;

import jp.hutcraft.otl.entity.Board;
import jp.hutcraft.otl.entity.Piece;

/**
 * 
 * @author almirage
 *
 */
public interface OthelloController {
	/**
	 * 
	 * @param currentBoard
	 * @param yourTurn
	 * @return
	 */
	Board proceed(Board currentBoard, Piece yourTurn);
}
