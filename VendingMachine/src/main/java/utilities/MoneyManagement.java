package utilities;

import java.util.HashMap;
import java.util.Map;

import constants.Constants;
import enums.Money;

public class MoneyManagement {
	
	public static Map<String,Integer> moneyGlobalMap = new HashMap<>();
	public static Map<String,Integer> moneyLocalMap = new HashMap<>();
	
	/**
	 * This method to reset the global map value to default values
	 */
	public void resetMoneyInTheMachine() {
		
		for(Money money : Money.values()) {
			moneyGlobalMap.put(money.name(), Constants.MIN_QUANTITY_OF_COINS);
		}
		
	}
	
	/**
	 * 
	 * @param money
	 * This method to update the local map
	 */
	public void addMoneyToLocalMap(Money money) {
		
		int value = moneyLocalMap.get(money.name());
		moneyLocalMap.put(money.name(), value + 1);
		
	}
	
	/**
	 * This method reset the local map to 0
	 */
	public void resetMoneyToLocalMap() {
		
		for(Money money : Money.values()) {
			moneyLocalMap.put(money.name(), 0);
		}
	}
	
	/**
	 * This method update the global map by adding the local map values
	 */
	public void addMoneyToGlobalMap() {
		
		for(Map.Entry<String, Integer> entry : moneyLocalMap.entrySet()) {
			moneyGlobalMap.put(entry.getKey(), moneyGlobalMap.get(entry.getKey()) + entry.getValue());
		}
		
	}
	
	
	/**
	 * This method to print the available machine money in console
	 */
	public void printAvailableMoney() {
		
		System.out.println("Avaliable Money in the Machine to collect:");
		for (Money money : Money.values()) {
			System.out.println(money.name() + " - " + moneyGlobalMap.get(money.name()));
		}
	}
	
	/**
	 * 
	 * @param changeAmount
	 * @param money object
	 * @return true if we able to adjust the change Amount else
	 * 			it will return false
	 */
	public boolean adjustGlobalMapWhenRefundingMoney(int changeAmount) {
		
		boolean isOkToProcess = false;
		Map<String, Integer> tempMap = new HashMap<>();
		//creating a copy of global map current status
		tempMap.putAll(moneyGlobalMap);
		
		if (changeAmount == 0) {
			isOkToProcess = true;
		} 
		
		while(!isOkToProcess && changeAmount > 0) {
			
			if (changeAmount >= 25) {
				
				if (checkForChangeAvailable(Money.QUATER)) {
					deductMoneyFromGlobalMap(Money.QUATER);
					changeAmount -= 25;
					if (changeAmount == 0) {
						isOkToProcess = true;
					} 
				} else {
					isOkToProcess = false;
					break;
				}
			} else if (changeAmount >= 10) {
				if (checkForChangeAvailable(Money.DIME)) {
					deductMoneyFromGlobalMap(Money.DIME);
					changeAmount -= 10;
					if (changeAmount == 0) {
						isOkToProcess = true;
					} 
				} else {
					isOkToProcess = false;
					break;
				}
				
			} else if (changeAmount >= 5) {
				
				if (checkForChangeAvailable(Money.NICKEL)) {
					deductMoneyFromGlobalMap(Money.NICKEL);
					changeAmount -= 5;
					if (changeAmount == 0) {
						isOkToProcess = true;
					} 
				} else {
					isOkToProcess = false;
					break;
				}
				
			} else if (changeAmount >= 1) {
				
				if (checkForChangeAvailable(Money.PENNY)) {
					deductMoneyFromGlobalMap(Money.PENNY);
					changeAmount -= 1;
					if (changeAmount == 0) {
						isOkToProcess = true;
					} 
				} else {
					isOkToProcess = false;
					break;
				}
			} 
		}
		
		if (!isOkToProcess) {
			moneyGlobalMap.putAll(tempMap);
		}
		
		return isOkToProcess;
	}
	
	/**
	 * 
	 * @param money object
	 * This method deduct the coin from global money hash map
	 */
	public void deductMoneyFromGlobalMap(Money money) {
		int value = moneyGlobalMap.get(money.name());
		moneyGlobalMap.put(money.name(), (value - 1));
	}
	
	/**
	 * 
	 * @param money object
	 * @return true if there is sufficient coin available to give to customer else 
	 * 			it will return false
	 */
	public boolean checkForChangeAvailable(Money money) {
		
		int coinAvailable = moneyGlobalMap.get(money.name());
		
		if (coinAvailable >= 1) {
			return true;
		} else {
			return false;
		}
		
	}

}
