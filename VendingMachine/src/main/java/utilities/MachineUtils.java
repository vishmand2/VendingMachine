package utilities;

import constants.Constants;
import enums.Money;
import enums.Products;
import myVendingMachine.MainClass;

public class MachineUtils extends CalculateAmount{
	
	public static int selectedProductId, changeAmount, enteredAmount, productPrice, numberOfProduct;
	public String message;
	public static Products productObj;
	public static Money moneyObj;
	
	/**
	 * This method print the machine interface
	 */
	public void displayProducts() {
        
		displayNextLine(" ============================================= ");
		displayNextLine("     WELCOME TO THE VENDING MACHINE           ");
		displayNextLine(" ============================================= ");
		displayNextLine("       << PRODUCT SELECTION >>              ");
		displayNextLine("");
        
        for(Products productObj: Products.values()){
            	numberOfProduct++; 
            	displayNextLine("     " + productObj.getSelectionNumber() + "  " + productObj.name() + 
                		"  -  Price: " + productObj.getPrice() + "   ");
        }
        
        displayNextLine("");
        displayNextLine("=====> To exit please press 0 <=====");
        displayNextLine("");
        displayInLine("Please select your product: ");
    }
	
	/**
	 * 
	 * @param text
	 * This method will print the text in next line
	 */
	private void displayNextLine(String text) {
		System.out.println(text);
	}
	
	/**
	 * 
	 * @param text
	 * This method will print the text in same line
	 */
	private void displayInLine(String text) {
		System.out.print(text);
	}

	
	/*
	 * This method will prompt the message to the customer 
	 * to enter the Money
	 */
	public void displayEnterMoneyMessage() {
		
		if (selectedProductId > 0) {
			message = "Please enter the money to complete your purchase or press 0 to Exit: ";
			displayInLine(message);
		}
		
    }
	
	/**
	 * 
	 * This method adjust the global money map 
	 * @return true if there is not issue encountered 
	 * else it will return false
	 */
	public boolean performMoneyManagement() {
		MoneyManagement moneyManagementObj = new MoneyManagement();
		StockManagement stockManagementObj = new StockManagement();
		boolean isOkToCompleteTransaction = false;
		
		isOkToCompleteTransaction= moneyManagementObj.adjustGlobalMapWhenRefundingMoney(changeAmount);
		
		if (isOkToCompleteTransaction) {
			moneyManagementObj.addMoneyToGlobalMap();
			moneyManagementObj.resetMoneyToLocalMap();
		} else {
			moneyManagementObj.resetMoneyToLocalMap();
			stockManagementObj.addStockBackForItem(productObj);
		}
		
		return isOkToCompleteTransaction;
	}
	
	
	/**
	 * This method displays the change messages and perform action on globalMoney hashmap.
	 * If hashmap operation is successful then it will show the variety of message in the 
	 * console according to the change amount.
	 * 
	 */
	public void displayChangeMessage() {
		
		boolean isOkToCompleteTransaction = false;
		displayNextLine("");
		
		if (selectedProductId > 0) {
			
			if (enteredAmount > productPrice) {
				
				isOkToCompleteTransaction = performMoneyManagement();
				
				if (isOkToCompleteTransaction) {
					displayNextLine( "Change due: " + changeAmount + " , Please collect your money.");
					displayNextLine("Please collect your product. Thank you for shopping with us.");
				} else {
					displayNextLine("Sorry, we can't process this request due to shortage of change available in the machine");
					displayNextLine( "Change due: " + enteredAmount + " , Please collect your money.");
				}
				
			} else if (enteredAmount == productPrice) {
				
				isOkToCompleteTransaction = performMoneyManagement();
				
				if (isOkToCompleteTransaction) {
					displayNextLine("Please collect your product. Thank you for shopping with us.");
				} else {
					displayNextLine("Sorry, we can't process this request due to shortage of change available in the machine");
					displayNextLine( "Change due: " + enteredAmount + " , Please collect your money.");
				}
				
			} else {
				
				if (changeAmount > 0 ) {
					displayNextLine( "Change due: " + changeAmount + " , Please collect your money.");
				} else {
					displayNextLine( "No change due.");
				}
				
			}
			
	        resetLocalVariables();
	        displayNextLine("Thank you for using the Vending Machine!!!");
	        displayNextLine("");
			
		}
		
		
    }
	
	/**
	 * 
	 * @param inputAmount - entered by the user
	 * This method validate the money entered by the user.
	 * It calculate the money required to complete the transaction also call
	 * calculateChangeAmount method and set the changeAmount variable.
	 */
	public void enteredMoney(int inputAmount) {
		
		InputValidation inputValidationObj = new InputValidation();
		StockManagement stockManagementObj = new StockManagement();
		MoneyManagement moneyManagementObj = new MoneyManagement();
		
		if (selectedProductId > 0) {
			
			if (inputAmount > 0) {
				
				if (checkForAllowedCoins(inputAmount)) {
					enteredAmount += inputAmount;
					moneyManagementObj.addMoneyToLocalMap(moneyObj);
				}
				
				if (enteredAmount < productPrice) {
					
					displayNextLine("Pending amount: " + (productPrice - enteredAmount));
					displayInLine("Enter remaining money or to cancel the transaction, press 0: ");
					inputValidationObj.enteredMoneyValidation();
				
				} else {
					
					changeAmount = calculateChangeAmount(productPrice, enteredAmount);
				}
				
			} else if (inputAmount < 0) {
				
				displayNextLine("Pending amount: " + (productPrice - enteredAmount));
				displayInLine("Enter remaining money or to cancel the transaction, press 0: ");
				inputValidationObj.enteredMoneyValidation();
						
			} else if (inputAmount == 0){
				
				changeAmount = calculateChangeAmount(0, enteredAmount);
				stockManagementObj.addStockBackForItem(productObj);
				moneyManagementObj.resetMoneyToLocalMap();

			} 

		}
}
	
	/**
	 * @param accept money entered by user
	 * @return true if money is available in money enum else false
	 */
	public boolean checkForAllowedCoins(int enteredCoin) {
		
		 boolean allowedCoin = false;
		 String coins ="";
		 
		 for(Money money : Money.values()) {
			 coins += " " + money.getValue();
			 if(money.getValue() == enteredCoin){
				 allowedCoin = true;
				 moneyObj = money;
			 } 
		 }
		 
		 if(!allowedCoin) {
			 moneyObj = null;
			 message = "This machine only accept following coins -"; 
			 System.out.println(message + coins); 
		 }
		 
		 return allowedCoin;
	}
	
	/**
	 * 
	 * @param productId
	 * This method is to do perform action on selected product id
	 * it internally call validateProductSelection method to validate the selection.
	 * if selection is valid then it will check the stock of the product and then reduce  
	 * the stock.
	 */
	public void doProductSelection(int productId) {
		
		StockManagement stockManagementObj = new StockManagement();
		InputValidation inputValidationObj = new InputValidation();
		
		int productSelectionFlag = validateProductSelection(productId);
		
		if (productSelectionFlag == 0) {
		
			message = "Thank you for using Vending Machine, See you soon!!";
			displayNextLine(message);
		
		} else if (productSelectionFlag == 1) {
			
			if (stockManagementObj.checkStockForItem(productObj)) {
				
				productPrice = calculateTotalAmount(productObj);
				stockManagementObj.reduceStockForItem(productObj);
		
			} else {
				
				message = "Sorry, The product is out of stock, Please select another Product or Enter 0 to exit: ";
				displayInLine(message);
				resetLocalVariables();
				inputValidationObj.ProductSelection();
				
			}
		} else if (productSelectionFlag == -2) {
			
			displayResetMessage();
			
		} else {
			
			message = "Your selection is invalid, Please select a Product or Enter 0 to exit: ";
			displayNextLine(message);
			resetLocalVariables();
			inputValidationObj.ProductSelection();
		
		}
	}
	
	/**
	 * This method displays the Reset message in the console
	 */
	public void displayResetMessage() {
		
		StockManagement stockManagementObj = new StockManagement();
		MoneyManagement moneyManagementObj = new MoneyManagement();
		
		displayNextLine("-------------------------------------");
		displayNextLine("Machine is reseting now...");
		displayNextLine("-------------------------------------");			
		stockManagementObj.printStockForAllItems();
		displayNextLine("-------------------------------------");
		moneyManagementObj.printAvailableMoney();
		displayNextLine("-------------------------------------");
		displayNextLine("Stock and available money are reset to default now.");
		displayNextLine("-------------------------------------");
		displayNextLine("Machine shutting down now...");
		displayNextLine("-------------------------------------");
		System.exit(0);
	}
	
	/**
	 * 
	 * @param productId
	 * @return 
	 *  0 if user selects to Exit
	 *  1 if user selects a valid product selection number
	 *  -1 if user entered invalid product selection number
	 *  -2 for resetting the machine product stock and money to default value
	 */
	public int validateProductSelection(int productId) {
		
		if(productId == 0) {
			
			selectedProductId = productId;
			return 0;
		
		} else if (productId > 0 && productId <= numberOfProduct) {
			
			selectedProductId = productId;
			productObj = Products.valueOf(selectedProductId);
			return 1;
		
		} else if (productId == Constants.RESET_CODE) {
			
			MainClass.isResetRequired = true;
			return -2;
		
		} else {
			
			return -1;
		}
		
	}
	
	/**
	 * This method is resetting the class variables with default value
	 */
	public void resetLocalVariables() {
		
		selectedProductId = 0;
		changeAmount = 0;
		enteredAmount = 0;
		productPrice = 0;
		message = "";
		productObj = null;
		moneyObj = null;
		
	}

}
