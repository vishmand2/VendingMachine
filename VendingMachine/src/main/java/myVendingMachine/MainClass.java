package myVendingMachine;

import utilities.InputValidation;
import utilities.MachineUtils;
import utilities.MoneyManagement;
import utilities.StockManagement;

public class MainClass {

	public static boolean isResetRequired = false;
	
	public static void main(String[] args) {
		
		MachineUtils machineUtilsObj = new MachineUtils();
		InputValidation inputValidationObj = new InputValidation(); 
		StockManagement stockManagementObj = new StockManagement();
		MoneyManagement moneyManagementObj = new MoneyManagement();

		stockManagementObj.resetDefaultStockForItems();
		moneyManagementObj.resetMoneyInTheMachine();
		moneyManagementObj.resetMoneyToLocalMap();
		machineUtilsObj.resetLocalVariables();

		do {
			machineUtilsObj.displayProducts();
			inputValidationObj.ProductSelection();
			
			machineUtilsObj.displayEnterMoneyMessage();
			inputValidationObj.enteredMoneyValidation();
			
			machineUtilsObj.displayChangeMessage();	
			
		} while (!isResetRequired);

	}
}
