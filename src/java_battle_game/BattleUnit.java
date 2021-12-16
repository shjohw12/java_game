package java_battle_game;
import java.awt.*;
import java.util.*;

class BattleUnit {
//	Position variable of the unit.
	int pos_x, pos_y;
	
//	size variable of the unit.
	int width, height;
	
//	image variable of the unit.
	Image img;
	Toolkit kit = Toolkit.getDefaultToolkit();
	
//	Check whether the x-axis interval or the y-axis interval overlaps.
	public boolean collision(int x, int y, int w, int h) {
		if ( (x+w < pos_x || pos_x + width < x) || (y+h < pos_y || pos_y + height < y) ) {
			return false;
		}
		return true;
	}
}


//	Abstract class that represents the basic type of character.
abstract class CharacterDefault extends BattleUnit {
//	Variables representing the vitality of the character. If this value becomes less than 0, the character dies.
	int life;
	
//	Variables representing the character's magical ability. If mana is 100, special attacks can be used, and if special attacks are used, mana becomes 0.
	int mana;
	
//	Variables representing the character's speed;
	int speed;
	
//	Variables representing the character's attack speed;
//	If the speedCnt value is equal to attackSpeed, the attack can be executed.
//	The speedCnt variable increases constantly and zero when an attack is carried out.
//	In other words, the smaller the attack Speed value, the faster the attack speed.
	int attackSpeed;
	int speedCnt;
		
//	BattleInfo is used as a reference variable so that BattleInfo can be modified when a character's attack occurs.
	BattleInfo info;
	
	
	abstract public void defaultAttack();
	abstract public void skill();
	abstract public void skillUnactivate();
}

class CharacterTypeA extends CharacterDefault  {
	
//	Variables to check if the skill is in use.
	boolean skillActivated;
	
	public CharacterTypeA(BattleInfo info) {
		pos_x = 10;
		pos_y = 75;
		width = 80;
		height = 100;
		life = 100;
		mana = 0;
		speed = 1;
		
		attackSpeed = 50;
		speedCnt = 50;
		
		skillActivated = false;
		
		img = kit.getImage("ryan.png");
		this.info = info;
		
	}
	
	@Override
	public void defaultAttack() {
		if (skillActivated == false) {
			AttackUnit myAttack = new AttackUnit(pos_x + 30, pos_y + 105, 0, 1, 1);
			info.attackUnits.add(myAttack);
		}
		else {
			AttackUnit myAttack = new AttackUnit(pos_x + 30, pos_y + 105, 0, 1, 1);
			info.attackUnits.add(myAttack);
			if (pos_x + 30 > 40) {
				myAttack = new AttackUnit(pos_x + 30 - 50, pos_y + 105, 0, 1, 1);
				info.attackUnits.add(myAttack);
			}
			if (pos_x + 30 < 320) {
				myAttack = new AttackUnit(pos_x + 30 + 50, pos_y + 105, 0, 1, 1);
				info.attackUnits.add(myAttack);
			}
		}
		
	}
	
	
	@Override
	public void skill() {
		if (mana == 100) {
			skillActivated = true;
			
			img = kit.getImage("ryan_angry.jpg");
		}
	}
	
	@Override
	public void skillUnactivate() {
		skillActivated = false;
		
		img = kit.getImage("ryan.png");
	}
}

class CharacterTypeB extends CharacterDefault  {
	
//	Variables to check if the skill is in use.
	boolean skillActivated;
	
	public CharacterTypeB(BattleInfo info) {
		pos_x = 10;
		pos_y = 445;
		width = 80;
		height = 100;
		life = 100;
		mana = 0;
		speed = 1;
		
		attackSpeed = 50;
		speedCnt = 50;
		
		skillActivated = false;
		
		img = kit.getImage("brown.jpg");
		this.info = info;
	}
	
	
	
	@Override
	public void defaultAttack() {
		AttackUnit myAttack = new AttackUnit(pos_x + 30, pos_y - 35, 0, -1, 1);
		info.attackUnits.add(myAttack);
	}
	
	@Override
	public void skill() {
		if (mana == 100) {
			skillActivated = true;
			
			img = kit.getImage("brown_angry.png");
			
			speed = 2;
			
			attackSpeed = 20;
			speedCnt = 20;
		}
	}
	
	@Override
	public void skillUnactivate() {
		skillActivated = false;
		
		img = kit.getImage("brown.jpg");
		
		speed = 1;
		
		attackSpeed = 50;
		speedCnt = 50;
	}
}

class AttackUnit extends BattleUnit {
	
//	Variables indicating the direction in which the attacking object moves.
	int dx,dy;
	
//	Variables representing the speed at which the attacking object moves.
	int speed;
	
//	Variables representing the attack power;
	int damage;
	
	public AttackUnit(int pos_x, int pos_y, int dx, int dy, int speed) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		width = 30;
		height = 30;
		this.dx = dx;
		this.dy = dy;
		this.speed = speed;
		damage = 10;
		img = kit.getImage("bomb.jpeg");
	}
	
	public void move() {
		this.pos_x += dx * speed;
		this.pos_y += dy * speed;
	}
}

