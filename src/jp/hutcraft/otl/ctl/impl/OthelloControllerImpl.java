package jp.hutcraft.otl.ctl.impl;

import jp.hutcraft.otl.ai.OthelloAI;
import jp.hutcraft.otl.ctl.OthelloController;
import jp.hutcraft.otl.entity.Board;
import jp.hutcraft.otl.entity.Decide;
import jp.hutcraft.otl.entity.Piece;

/**
 * 想定よりもずっとシンプルというか、存在意義の薄いクラスになっちゃったな。
 * ホントはこのクラスでコントローラーしたかったけど今のところUIが全部やってしまっている。
 * @author almirage
 *
 */
public class OthelloControllerImpl implements OthelloController {
	private OthelloAI headPlayer;
	private OthelloAI tailPlayer;
	
	public Board proceed(final Board currentBoard, final Piece yourTurn) {
		final OthelloAI currentPlayer = (yourTurn == Piece.DARK) ? headPlayer : tailPlayer;
		
		final Decide nextMove = currentPlayer.move(currentBoard, yourTurn);
		if (!currentBoard.isAcceptable(nextMove, yourTurn)) {
			// どうしてくれよう
			throw new IllegalStateException();
		}
		return currentBoard.move(nextMove);
	}

	public void setHeadPlayer(OthelloAI headPlayer) {
		this.headPlayer = headPlayer;
	}

	public void setTailPlayer(OthelloAI tailPlayer) {
		this.tailPlayer = tailPlayer;
	}
}
