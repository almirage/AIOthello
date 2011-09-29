package jp.hutcraft.otl.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

import jp.hutcraft.otl.entity.Piece;

/**
 * 
 * @author almirage
 *
 */
public class TipPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 50;
	private static final Color bgColor = new Color(0x20, 0x80, 0x20);
	private static final Color flipColor = new Color(0xcc, 0xaa, 0xaa);
	private static final Color captureColor = new Color(0x99, 0x99, 0xcc);
	private static final Font numberFont = new Font("SansSerif", Font.PLAIN, 11);

	private final Piece piece;
	private final boolean fliped;
	private final boolean captured;
	private final Integer index;
	
	public TipPanel(final Piece p, final boolean fliped, final boolean captured, Integer index) {
		setBounds(0, 0, SIZE, SIZE);
		this.piece = p;
		this.fliped = fliped;
		this.captured = captured;
		this.index = index;
	}
	
	@Override
	public void paint(Graphics g) {
		Rectangle rect = getBounds();
		g.setColor(fliped ? flipColor : captured ? captureColor: bgColor);
		g.fillRect(0, 0, SIZE, SIZE);
		
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, rect.width, rect.height);
		g.drawRect(1, 1, rect.width - 2, rect.height - 2);

		if (piece == null || piece == Piece.NONE) return;
		g.setColor(piece == Piece.DARK ? Color.BLACK : Color.WHITE);
		g.fillOval(5, 5, 40, 40);
		
		if (index == null) return;
		g.setFont(numberFont);
		g.setColor(piece == Piece.DARK ? Color.WHITE : Color.BLACK);
		g.drawString(index.toString(), 15, 20);
	}
	
}
