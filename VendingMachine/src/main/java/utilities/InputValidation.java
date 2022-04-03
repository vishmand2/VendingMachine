package utilities;

import java.util.Scanner;

public class InputValidation {
	public String message = "";
	Scanner input = new Scanner(System.in);
	MachineUtils machineUtilsObj = new MachineUtils();
	
	
	/**
	 * This method validate the product selection input
	 */
	public void ProductSelection() {
		
		int selection;
	
		do {
			try {
				selection = Integer.parseInt(input.nextLine());
				break;
				
			} catch (NumberFormatException ex) {
				message = "Your selection is invalid, Please select a Product or Enter 0 to exit: ";
				System.out.print(message);
				selection = -1;
			}
		} while (true);
		
		machineUtilsObj.doProductSelection(selection);
	}
	
	
	/**
	 * This method validate the coin input
	 */
	public void enteredMoneyValidation() {
		
		int enteredMoney = 0;
		
		do {
			
			if (MachineUtils.selectedProductId <=0) {
				break;
			}
			try {
				enteredMoney = Integer.parseInt(input.nextLine());
				break;
				
			} catch (NumberFormatException ex) {
				message = "Please enter valid amount or Enter 0 to exit: ";
				System.out.print(message);
				enteredMoney = -1;
			}
		} while (true);
		
		machineUtilsObj.enteredMoney(enteredMoney);
		
	}

}
