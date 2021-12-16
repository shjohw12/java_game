package java_battle_game;

public class Main {
	
	public static void main(String[] args) {
		BattleInfo info = new BattleInfo();
		BattleControl control = new BattleControl(info);
		BattleView view = new BattleView(info, control);
	}

}
