package enums;

public enum Money {
	
	PENNY(1), NICKEL(5), DIME(10), QUATER(25);
	
	private int value;
	
	Money(int value){
		this.value = value;
	}
	
	
	public int getValue() {
		return value;
	}

}
