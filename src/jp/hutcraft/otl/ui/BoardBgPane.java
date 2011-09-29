package jp.hutcraft.otl.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

public class BoardBgPane extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final Color bgColor = new Color(0xff, 0xff, 0xff);
	private static final Color fontColor = Color.BLACK;
	private static final Font labelFont = new Font("SansSerif", Font.ITALIC, 16);

	public BoardBgPane() {
		setBounds(0, 0, 480, 480);
	}
	
	private static final List<String> xLabels =
		new ArrayList<String>(Arrays.asList(new String[]{"a", "b", "c", "d", "e", "f", "g", "h"}));
	private static final List<String> yLabels =
		new ArrayList<String>(Arrays.asList(new String[]{"1", "2", "3", "4", "5", "6", "7", "8"}));
	
	@Override
	public void paint(Graphics g) {
		g.setColor(bgColor);
		final Rectangle r = getBounds();
		g.fillRect(r.x, r.y, r.width, r.height);

		g.setFont(labelFont);
		g.setColor(fontColor);
		for (int x = 0; x < xLabels.size(); x++) {
			g.drawString(xLabels.get(x), x * 50 + 60, 28);
		}
		for (int y = 0; y < xLabels.size(); y++) {
			g.drawString(yLabels.get(y), 14, y * 50 + 70);
		}
	}
}
