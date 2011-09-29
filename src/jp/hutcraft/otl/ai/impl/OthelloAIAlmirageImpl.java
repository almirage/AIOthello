package jp.hutcraft.otl.ai.impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import jp.hutcraft.otl.ai.OthelloAI;
import jp.hutcraft.otl.entity.Board;
import jp.hutcraft.otl.entity.Decide;
import jp.hutcraft.otl.entity.Field;
import jp.hutcraft.otl.entity.Piece;
import jp.hutcraft.otl.util.OthelloUtil;

/**
 * 
 * @author almirage
 */
public class OthelloAIAlmirageImpl implements OthelloAI {

	@Override
	public String getNameLabel() {
		return "つーとんAI";
	}

	@Override
	public Decide move(final Board board, final Piece myPiece) {
		// it's a joke code. don't follow me.
		final SortedMap<Integer, List<Field>> flipables = new TreeMap<Integer, List<Field>>();
		final List<Field> allFlipables = new ArrayList<Field>();
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				final Field current = board.get(x, y);
				if (current.getPiece() != Piece.NONE) continue;
				final Field promising = new Field(x, y, myPiece);
				final Set<Field> captures = OthelloUtil.capture(board, promising);
				if (captures.isEmpty()) continue;
				if (flipables.get(captures.size()) == null) flipables.put(captures.size(), new ArrayList<Field>());
				flipables.get(captures.size()).add(promising);
				if (!captures.isEmpty()) allFlipables.add(promising);
			}
		}
		if (flipables.isEmpty()) return new Decide(Field.NONE, "計ったな、シャア！");
		if (findFirstPrime(allFlipables) != null) return new Decide(findFirstPrime(allFlipables), "にやり");
		if (findSecondPrime(allFlipables) != null) return new Decide(findSecondPrime(allFlipables), "");
		return new Decide(flipables.get(flipables.lastKey()).get(0), "");
	}

	private static final List<Point> firstPrimePoints = new ArrayList<Point>();
	static {
		firstPrimePoints.add(new Point(0, 0));
		firstPrimePoints.add(new Point(0, 7));
		firstPrimePoints.add(new Point(7, 7));
		firstPrimePoints.add(new Point(7, 0));
	}
	private static final List<Point> secondPrimePoints = new ArrayList<Point>();
	static {
		secondPrimePoints.add(new Point(0, 2));
		secondPrimePoints.add(new Point(2, 0));
		secondPrimePoints.add(new Point(5, 0));
		secondPrimePoints.add(new Point(7, 2));
		secondPrimePoints.add(new Point(7, 5));
		secondPrimePoints.add(new Point(5, 7));
		secondPrimePoints.add(new Point(2, 7));
		secondPrimePoints.add(new Point(0, 5));

	}
	private Field findFirstPrime(final List<Field> allFlipables) {
		return findPrime(allFlipables, firstPrimePoints);
	}
	private Field findSecondPrime(final List<Field> allFlipables) {
		return findPrime(allFlipables, secondPrimePoints);
	}
	private static Field findPrime(final List<Field> allFlipables, final List<Point> primePoints) {
		// 眠い
		for (final Point primePoint : primePoints) {
			for (final Field field : allFlipables) {
				if (field.getPoint().equals(primePoint)) return field;
			}
		}
		return null;
	}
}
