package jp.hutcraft.otl.entity;

import static jp.hutcraft.otl.entity.Piece.DARK;
import static jp.hutcraft.otl.entity.Piece.LIGHT;
import static jp.hutcraft.otl.entity.Piece.NONE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import jp.hutcraft.otl.util.OthelloUtil;

/**
 * 盤を表す。
 * x軸がa~h、y軸が1~8、だけどデータ上は左上を0,0にする
 * @author almirage
 *
 */
public final class Board {
	private final List<List<Piece>> grid = new ArrayList<List<Piece>>(8);
	private /*final*/ Decide lastDecides = Decide.NullDecide;
	private /*final*/ Set<Field> lastCapture = new HashSet<Field>();
	private final List<Decide> playLog = new ArrayList<Decide>();

	public static Board createInitial() {
		final Board b = new Board();
		b.grid.get(3).set(3, LIGHT);
		b.grid.get(3).set(4, DARK);
		b.grid.get(4).set(3, DARK);
		b.grid.get(4).set(4, LIGHT);
		return b;
	}

	public Board() {
		for (int idx = 0; idx < 8; idx++) { // fack'in magic number
			final List<Piece> row = new ArrayList<Piece>(8);
			for (int y = 0; y < 8; y++) {
				row.add(NONE);
			}
			grid.add(idx, row);
		}
	}
	
	private static Board copy(final Board your) {
		final Board my = new Board();
		for (int idx = 0; idx < your.grid.size(); idx++) {
			my.grid.set(idx, new ArrayList<Piece>(your.grid.get(idx)));
		}
		my.playLog.addAll(your.playLog);
		return my;
	}
	
	public Field get(final int x, final int y) {
		if (x < 0 || x >= 8 || y < 0 || y >= 9) throw new IllegalArgumentException(String.format("x=%d, y=%d", x, y));
		return new Field(x, y, grid.get(x).get(y));
	}
	
	public Board move(final Decide decides) {
		final Board r = copy(this);
		final Set<Field> captures = OthelloUtil.capture(r, decides.getField());
		r.lastDecides = decides;
		r.lastCapture = captures;
		r.playLog.add(decides);

		if (decides.getField().isPass()) return r;
		
		if (captures.isEmpty()) throw new IllegalStateException();
		for (final Field capturePiece : captures) {
			r.grid.get(capturePiece.getX()).set(capturePiece.getY(), decides.getField().getPiece());
		}
		r.grid.get(decides.getField().getX()).set(decides.getField().getY(), decides.getField().getPiece());
		return r;
	}

	public List<Field> getFieldList() {
		final List<Field> fields = new ArrayList<Field>();
		for (int x = 0; x < grid.size(); x++) {
			for (int y = 0; y < grid.get(x).size(); y++) {
				fields.add(new Field(x, y, grid.get(x).get(y)));
			}
		}
		return fields;
	}

	public boolean isAcceptable(final Decide nextMove, final Piece thisTurn) {
		if (nextMove.getField().isPass() && hasMovePoint(thisTurn)) {
			return false;
		}
		if (!nextMove.getField().isPass() && !canMove(nextMove.getField())) {
			return false;
		}
		return true;
	}

	private boolean canMove(final Field nextMove) {
		if (nextMove.isPass()) return true;
		if (grid.get(nextMove.getX()).get(nextMove.getY()) != Piece.NONE) return false;

		final Set<Field> captures = OthelloUtil.capture(this, nextMove);
		if (captures.isEmpty()) return false;
		
		return true;
	}

	private boolean hasMovePoint(final Piece thisTurn) {
		return false;
	}

	public int getPieceCount(final Piece piece) {
		/*final*/ int count = 0;
		for (int x = 0; x < grid.size(); x++) {
			for (int y = 0; y < grid.get(x).size(); y++) {
				if (grid.get(x).get(y) == piece) count ++;
			}
		}
		return count;
	}
	
	/**
	 * 盤面上に双方とも置き場所が無くなった状態
	 * @return
	 */
	public boolean isFixed() {
		return getPieceCount(Piece.NONE) == 0;
	}

	public Decide getLastDecides() {
		return lastDecides;
	}

	public Set<Field> getLastCapture() {
		return new HashSet<Field>(lastCapture);
	}

	public List<Decide> getPlayLog() {
		return new ArrayList<Decide>(playLog);
	}

	/**
	 * そのfieldは何手目に指されたか。
	 * まだ指されていないfieldの場合はnullを返す
	 * @param field
	 * @return 1 origined number
	 */
	public Integer tellLogIndex(Field field) {
		for (ListIterator<Decide> it = playLog.listIterator(); it.hasNext(); ) {
			final Decide log = it.next();
			if (log.getField().getPoint().equals(field.getPoint())) {
				return it.nextIndex();
			}
		}
		return null;
	}
}
