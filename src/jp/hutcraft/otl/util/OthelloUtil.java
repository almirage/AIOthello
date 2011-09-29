package jp.hutcraft.otl.util;

import java.awt.Point;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jp.hutcraft.otl.entity.Board;
import jp.hutcraft.otl.entity.Field;
import jp.hutcraft.otl.entity.Piece;

public class OthelloUtil {
	private OthelloUtil() {
	}
	
	private final static Set<Point> direction8 = new HashSet<Point>();
	static {
		direction8.add(new Point(-1, -1));	// 左上
		direction8.add(new Point( 0, -1));	// 上
		direction8.add(new Point( 1, -1));	// 右上
		direction8.add(new Point( 1,  0));	// 右
		direction8.add(new Point( 1,  1));	// 右下
		direction8.add(new Point( 0,  1));	// 下
		direction8.add(new Point(-1,  1));	// 左下
		direction8.add(new Point(-1,  0));	// 左
	}
	
	public static Set<Field> capture(final Board board, final Field move) {
		// 効率的な方法が思いつかないから力技で
		final Set<Field> r = new HashSet<Field>();
		for (final Point direction : direction8) {
			r.addAll(captureTo(board, move, direction.x, direction.y));
		}
		return r;
	}
	
	private static Set<Field> captureTo(final Board board, final Field base, final int additionalX, final int additionalY) {
		final Set<Field> r = new HashSet<Field>();
		for (int distance = 1; ; distance ++) {
			final int targetX = base.getX() + additionalX * distance;
			final int targetY = base.getY() + additionalY * distance;
			if (targetX < 0 || targetX >= 8 || targetY < 0 || targetY >= 8) break;
			final Field target = board.get(targetX, targetY);
			if (target.getPiece() == Piece.NONE) break;
			if (target.getPiece() == base.getPiece()) return r;
			r.add(target);
		}
		return Collections.emptySet();
	}
}
