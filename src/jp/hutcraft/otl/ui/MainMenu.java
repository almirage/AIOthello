package jp.hutcraft.otl.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	private boolean gameStart = false;
	private JTextArea chatArea;
	private final String headPlayerName;
	private final String tailPlayerName;

	public MainMenu(final String headPlayerName, final String tailPlayerName) {
		this.headPlayerName = headPlayerName;
		this.tailPlayerName = tailPlayerName;
		
		setBounds(0, 0, 200, 600);

		registTitleLabel();
		registStartButton();
		registPlayerLabel();
		registChatArea();
	}
	
	private void registTitleLabel() {
		final JLabel titleLabel = new JLabel("えーあいおせろ");
		titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
		titleLabel.setBounds(10, 16, 180, 50);
		titleLabel.setHorizontalAlignment(JLabel.LEFT);
		titleLabel.setVerticalAlignment(JLabel.TOP);
		add(titleLabel);
	}

	private void registStartButton() {
		final JButton startButton = new JButton("start");
		startButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				gameStart = true;
				((JButton)e.getSource()).setEnabled(false);
			}});
		startButton.setBounds(0, 60, 120, 26);
		add(startButton);
	}

	private void registPlayerLabel() {
		final JLabel l = new JLabel();
		l.setBounds(0, 290, 120, 50);
		l.setText("<html>Dark: "+ headPlayerName +"<br/>Light: "+ tailPlayerName +"</html>");
		add(l);
	}

	private void registChatArea() {
	    chatArea = new JTextArea();
	    chatArea.setLineWrap(true);
	    chatArea.setEditable(false);
	    final JScrollPane chatScrollPane = new JScrollPane(
	    		chatArea,
	    		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	    		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    chatScrollPane.setBounds(0, 340, 200, 150);
	    add(chatScrollPane);
	}
	
	public boolean isGameStart() {
		return gameStart;
	}

	public void appendChat(String voice) {
		chatArea.append(voice.trim() + "\n");
	}
}
