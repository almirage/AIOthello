package jp.hutcraft.otl.ui;

import java.awt.Container;
import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import jp.hutcraft.otl.entity.Board;
import jp.hutcraft.otl.entity.Field;

/**
 * 
 * @author almirage
 *
 */
public class BoardPane extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final JPanel bgPanel = new BoardBgPane();
	private final Container pieceContainer = new Container();

	public BoardPane() {
		setBounds(0, 0, 480, 480);
		setVisible(true);
		BevelBorder border = new BevelBorder(BevelBorder.LOWERED);
		setBorder(border);

		pieceContainer.setBounds(40, 40, 400, 400);
		add(pieceContainer);
		add(bgPanel);
	}
	
	public void update(final Board b) {
		final List<Field> fields = b.getFieldList();
		final Set<Point> capturePoints = new HashSet<Point>();
		for (final Field captured : b.getLastCapture()) {
			capturePoints.add(captured.getPoint());
		}
		pieceContainer.removeAll();
		for (final Field field : fields) {
			final JPanel tip = new TipPanel(
					field.getPiece(),
					field.equals(b.getLastDecides().getField()),
					capturePoints.contains(field.getPoint()),
					b.tellLogIndex(field));
			tip.setLocation(field.getX() * tip.getBounds().width, field.getY() * tip.getBounds().height);
			pieceContainer.add(tip);
		}
	}
}
