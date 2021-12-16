package java_battle_game;
import java.util.*;

public class BattleInfo {
	CharacterTypeA player1;
	CharacterTypeB player2;
	ArrayList<AttackUnit> attackUnits;
	
	public BattleInfo() {
		player1 = new CharacterTypeA(this);
		player2 = new CharacterTypeB(this);
		attackUnits = new ArrayList<>();
	}
}