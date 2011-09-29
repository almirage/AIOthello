package jp.hutcraft.otl.ui;

import java.awt.Container;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jp.hutcraft.otl.ai.OthelloAI;
import jp.hutcraft.otl.ctl.OthelloController;
import jp.hutcraft.otl.entity.Board;
import jp.hutcraft.otl.entity.Decide;
import jp.hutcraft.otl.entity.Piece;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.util.StringUtil;

/**
 * 
 * @author almirage
 *
 */
public final class GuiMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private final Container displayContainer = new JPanel();
	private final BoardPane boardPane = new BoardPane();
	private final MainMenu menu;
	private /*final*/ Board currentBoard;
	private final List<Decide> playLog = new ArrayList<Decide>();
	private final S2Container s2container;
	private final OthelloController controller;
	private final OthelloAI headPlayer;
	private final OthelloAI tailPlayer;

	public GuiMain() {
		SingletonS2ContainerFactory.init();
		s2container = SingletonS2ContainerFactory.getContainer();
		// SingletonS2Container.getComponentはS2Tigerならキャストいらず！でも、お高いんでしょう？それがなんと（略
		controller = (OthelloController)s2container.getComponent(OthelloController.class);
		headPlayer = (OthelloAI)s2container.getComponent("headPlayer");
		tailPlayer = (OthelloAI)s2container.getComponent("tailPlayer");
		if (headPlayer == null || tailPlayer == null) {
			throw new NullPointerException("headPlayerかtailPlayerがNULLです。app.diconを見なおしてください。");
		}

		menu = new MainMenu(headPlayer.getNameLabel(), tailPlayer.getNameLabel());

		initFrame();
		initComponents();
		
		execute();
	}
	
	private void initFrame() {
		setTitle("Othello");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(new Rectangle(0, 0, 720, 560));
		setVisible(true);		
	}
	
	private void initComponents() {
		displayContainer.setBounds(0, 0, 720, 560);

		menu.setLocation(500, 0);
		displayContainer.add(menu);
		
		currentBoard = Board.createInitial();
		boardPane.update(currentBoard);
		boardPane.setLocation(10, 10);
		
		displayContainer.add(boardPane);
		getContentPane().add(displayContainer);
	}
	
	private void execute() {
		repaint();
		while (!menu.isGameStart()) sleep(500);
		/*final*/ Piece currentTurn = null;
		
		while (true) {
			currentTurn = (currentTurn == Piece.DARK) ? Piece.LIGHT : Piece.DARK;
			proceed(currentTurn);
			if (currentBoard.isFixed()) break;
			sleep(1000);
		}
		gameEnd();
	}
	
	private void gameEnd() {
		final int darkPoint = currentBoard.getPieceCount(Piece.DARK);
		final int lightPoint = currentBoard.getPieceCount(Piece.LIGHT);
		final String result = (darkPoint == lightPoint ? "draw game" : darkPoint > lightPoint ? "Dark won!" : "Light won!");
		menu.appendChat("Finished----\n");
		menu.appendChat(String.format("Dark: %d pt\n", darkPoint));
		menu.appendChat(String.format("Light: %d pt\n", lightPoint));
		menu.appendChat(String.format("%s\n", result));
	}
	
	private void proceed(final Piece currentTurn) {
		currentBoard = controller.proceed(currentBoard, currentTurn);
		final Decide currentDecide = currentBoard.getLastDecides();
		playLog.add(currentDecide);
		boardPane.update(currentBoard);
		if (!StringUtil.isEmpty(currentDecide.getVoice())) {
			menu.appendChat(getCurrentPlayerName(currentTurn) +": "+ currentDecide.getVoice());
		}
		boardPane.repaint();
	}

	private String getCurrentPlayerName(Piece currentTurn) {
		return "["+ currentTurn + "]"+ (currentTurn == Piece.DARK ? headPlayer.getNameLabel() : tailPlayer.getNameLabel());
	}

	private void sleep(final long millis) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(final String[] args) {
		new GuiMain();
	}
}
