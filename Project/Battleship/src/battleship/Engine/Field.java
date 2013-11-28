package battleship.Engine;


public class Field {

	private eFieldState fieldState;
	private eFieldBattleState battleState;

	public eFieldState getFieldState() {
		return this.fieldState;
	}

	/**
	 * 
	 * @param fieldState
	 */
	public void setFieldState(eFieldState fieldState) {
		this.fieldState = fieldState;
	}

	public eFieldBattleState getBattleState() {
		return this.battleState;
	}

	/**
	 * 
	 * @param battleState
	 */
	public void setBattleState(eFieldBattleState battleState) {
		this.battleState = battleState;
	}

}