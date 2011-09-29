package jp.hutcraft.otl.entity;

import java.awt.Point;
import java.io.Serializable;

/**
 * 盤上の１マスを表す
 * @author almirage
 *
 */
public final class Field implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Field NONE = new Field(-1, -1, Piece.NONE);
	
	private final int x;
	private final int y;
	private final Piece piece;
	public Field(final int x, final int y, final Piece piece) {
		this.x = x;
		this.y = y;
		this.piece = piece;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Piece getPiece() {
		return piece;
	}
	public boolean isPass() {
		return piece == Piece.NONE;
	}
	public Point getPoint() {
		return new Point(x, y);
	}
	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof Field)) return false;
		final Field f = (Field)o;
		return x == f.x && y == f.y && piece == f.piece;
	}
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + x;
		result = 31 * result + y;
		result = 31 * result + piece.hashCode();
		return result;
	}
}
