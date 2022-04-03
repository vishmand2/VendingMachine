package utilities;

import enums.Products;

public class CalculateAmount {

	
	/**
	 * 
	 * @param product object
	 * @return the price of the product
	 */
	public int calculateTotalAmount(Products product) {

		return product.getPrice();
	
	}
	
	
	/**
	 * 
	 * @param productPrice
	 * @param enteredAmount
	 * @return calculate and return change amount 
	 */
	public int calculateChangeAmount(int productPrice, int enteredAmount) {
		
		if (productPrice > 0) {
			
			return enteredAmount - productPrice;
			
		} else if (productPrice == 0) {
			
			return enteredAmount;
			
		} else {
			
			return 0;
		}
	}
	
}
