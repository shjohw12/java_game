package java_battle_game;
import javax.swing.*;
import static javax.swing.JOptionPane.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.time.*;

public class BattleView extends JFrame {
	Image bufImg;
	Graphics bufGrp;
	BattleInfo info;
	
	Thread thread;
	
	File file;
	BufferedWriter writer;
	
	public BattleView(BattleInfo info, BattleControl control) {
		this.info = info;
		
		setLayout(null);
		setTitle("Battle Game!");
		setSize(400, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		addKeyListener(control);
		
		file = new File(LocalDate.now().toString() + " game result.txt");
		
		thread = new Thread(control);
		thread.setDaemon(true);
		thread.start();
	}

	
	@Override
	public void paint(Graphics g) {
		
		bufImg = createImage(getWidth(), getHeight());
		bufGrp = bufImg.getGraphics();
		update(g);
	}
	
	@Override
	public void update(Graphics g) {
		bufGrp.clearRect(0, 0, 400, 600);
		
//		Display the player's current life and mana in the form of a bar.
		bufGrp.setColor(Color.BLACK);
		bufGrp.drawRect(8, 30, 383, 20);
		bufGrp.setColor(Color.RED);
		bufGrp.fillRect(9, 31, 382 * info.player1.life / 100, 19);
		
		bufGrp.setColor(Color.BLACK);
		bufGrp.drawRect(8, 52, 383, 20);
		bufGrp.setColor(Color.BLUE);
		bufGrp.fillRect(9, 53, 382 * info.player1.mana / 100, 19);
		
		
		bufGrp.setColor(Color.BLACK);
		bufGrp.drawRect(8, 550, 383, 20);
		bufGrp.setColor(Color.RED);
		bufGrp.fillRect(9, 551, 382 * info.player2.life / 100, 19);
		
		bufGrp.setColor(Color.BLACK);
		bufGrp.drawRect(8, 572, 383, 20);
		bufGrp.setColor(Color.BLUE);
		bufGrp.fillRect(9, 573, 382 * info.player2.mana / 100, 19);
		
		
		bufGrp.drawImage(info.player1.img, info.player1.pos_x, info.player1.pos_y, info.player1.width, info.player1.height, this);
		bufGrp.drawImage(info.player2.img, info.player2.pos_x, info.player2.pos_y, info.player2.width, info.player2.height, this);
		
//		loop using iterator, since it can be removed according to condition
		Iterator<AttackUnit> it = info.attackUnits.iterator();
		
		while (it.hasNext()) {
			AttackUnit unit = it.next();
			bufGrp.drawImage(unit.img, unit.pos_x, unit.pos_y, unit.width, unit.height, this);
			
//			If there's a collision, reduce the life of player and remove the attacking unit.
			if (info.player1.collision(unit.pos_x, unit.pos_y, unit.width, unit.height)) {
				info.player1.life -= unit.damage;
				it.remove();
				if (info.player1.life == 0) {
					try {
						FileWriter writer = new FileWriter(file, true);
						writer.write(LocalDateTime.now().toString() + " Player 2 Win\n");
						writer.close();
						
					} catch (IOException e) {}
					
					showMessageDialog(null,"Player 2 Win!!");
					thread.interrupt();
				}
			}
			if (info.player2.collision(unit.pos_x, unit.pos_y, unit.width, unit.height)) {
				info.player2.life -= unit.damage;
				it.remove();
				if (info.player2.life == 0) {
					try {
						FileWriter writer = new FileWriter(file, true);
						writer.write(LocalDateTime.now().toString() + " Player 1 Win\n");
						writer.close();
						
					} catch (IOException e) {}
					
					showMessageDialog(null,"Player 1 Win!!");
					thread.interrupt();
				}
			}
			
//			If it is out of area, remove it.
			if (unit.pos_y < 0 || unit.pos_y > 600) {
				it.remove();
			}
		}
		
		g.drawImage(bufImg, 0, 0, this);
		
		repaint();
	}
}
