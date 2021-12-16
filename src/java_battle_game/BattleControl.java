package java_battle_game;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

public class BattleControl extends KeyAdapter implements Runnable {
	
//	Reference variable for modifying the battleinfo value according to the key state.
	BattleInfo info;

//	A variable that checks if the current key is pressed.
	boolean is_A_pressed, is_D_pressed, is_K_pressed, is_SEMICOLON_pressed;
	
//	Variables that control the rate of increase in mana.
	int manaCnt;
	
	// Initial processing
	public BattleControl(BattleInfo info) {
		this.info = info;
		is_A_pressed = false;
		is_D_pressed = false;
		is_K_pressed = false;
		is_SEMICOLON_pressed = false;
		manaCnt = 0;
	}
	
    @Override
    public void keyPressed(KeyEvent e) {
    	int code = e.getKeyCode();
    	
    	if (code == KeyEvent.VK_A) {
    		is_A_pressed = true;
    	}
    	else if (code == KeyEvent.VK_D) {
    		is_D_pressed = true;
    	}
    	else if (code == KeyEvent.VK_K) {
    		is_K_pressed = true;
    	}
    	else if (code == KeyEvent.VK_SEMICOLON) {
    		is_SEMICOLON_pressed = true;
    	}
    	else if (code == KeyEvent.VK_S) {
    		if (info.player1.speedCnt == info.player1.attackSpeed) {
    			info.player1.speedCnt = 0;
    			info.player1.defaultAttack();
    		}
    	}
    	else if (code == KeyEvent.VK_Q) {
    		info.player1.skill();
    	}
    	else if (code == KeyEvent.VK_L) {
    		if (info.player2.speedCnt == info.player2.attackSpeed) {
    			info.player2.speedCnt = 0;
    			info.player2.defaultAttack();
    		}
    	}
    	else if (code == KeyEvent.VK_P) {
    		info.player2.skill();
    	}
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    	int code = e.getKeyCode();
    	
    	if (code == KeyEvent.VK_A) {
    		is_A_pressed = false;
    	}
    	else if (code == KeyEvent.VK_D) {
    		is_D_pressed = false;
    	}
    	else if (code == KeyEvent.VK_K) {
    		is_K_pressed = false;
    	}
    	else if (code == KeyEvent.VK_SEMICOLON) {
    		is_SEMICOLON_pressed = false;
    	}
    }
	
//  Unit information is modified based on pressed key information and a collision is processed.
	public void modify() {
		++manaCnt;
		
		info.player1.speedCnt = Math.min(info.player1.speedCnt + 1, info.player1.attackSpeed);
		info.player2.speedCnt = Math.min(info.player2.speedCnt + 1, info.player2.attackSpeed);
		
		
		if (info.player1.skillActivated) {
			if (manaCnt % 4 == 0) {
				info.player1.mana -= 1;
				if (info.player1.mana == 0) {
					info.player1.skillUnactivate();
				}
			}
		}
		else {
			if (manaCnt % 8 == 0) {
				info.player1.mana = Math.min(info.player1.mana + 1, 100);
			}
		}
		
		
		if (info.player2.skillActivated) {
			if (manaCnt % 4 == 0) {
				info.player2.mana -= 1;
				if (info.player2.mana == 0) {
					info.player2.skillUnactivate();
				}
			}
		}
		else {
			if (manaCnt % 8 == 0) {
				info.player2.mana = Math.min(info.player2.mana + 1, 100);
			}
		}
		
		if (is_A_pressed) {
			info.player1.pos_x = Math.max(0, info.player1.pos_x - info.player1.speed);
		}
		if (is_D_pressed) {
			info.player1.pos_x = Math.min(320, info.player1.pos_x + info.player1.speed);
		}
		if (is_K_pressed) {
			info.player2.pos_x = Math.max(0, info.player2.pos_x - info.player2.speed);
		}
		if (is_SEMICOLON_pressed) {
			info.player2.pos_x = Math.min(320, info.player2.pos_x + info.player2.speed);
		}
		
		for (int i=0; i<info.attackUnits.size(); i++) {
			AttackUnit unit = info.attackUnits.get(i);
			unit.move();
		}
		
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				modify();
				Thread.sleep(5);
			}
		} catch (Exception e) {}
	}
}
