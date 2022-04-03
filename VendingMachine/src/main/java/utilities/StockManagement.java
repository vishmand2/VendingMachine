package utilities;

import java.util.HashMap;
import java.util.Map;

import constants.Constants;
import enums.Products;

public class StockManagement {
	
	
	public static Map<String,Integer> stockMap = new HashMap<>();
	
	/**
	 * This method reset the value of stock for the product to default value
	 */
	public void resetDefaultStockForItems() {
		
		for (Products product : Products.values()) {
			stockMap.put(product.name(), Constants.MIN_QUANTITY_OF_ITEM);
		}
	}
	
	/**
	 * @param product object
	 * This method add back stock to the product
	 */
	public void addStockBackForItem(Products product) {
		
		if(stockMap.containsKey(product.name())) {
			int quantity = stockMap.get(product.name());
			stockMap.put(product.name(), (quantity + 1));
		}
		
	}
	
	
	/**
	 * @param product object
	 * This method deduct the stock for the product
	 */
	public void reduceStockForItem(Products product) {
		
		if(stockMap.containsKey(product.name())) {
			int quantity = stockMap.get(product.name());
			stockMap.put(product.name(), (quantity - 1));
		}
		
	}
	
	/**
	 * 
	 * @param product
	 * @return true if stock is available for the product else it will return false
	 */
	public boolean checkStockForItem(Products product) {
		
		boolean isStockAvailable = false;
		
		if(stockMap.containsKey(product.name())) {
			int quantity = stockMap.get(product.name());
			
			if(quantity > 0 ) {
				isStockAvailable = true;
			}
		}
		
		return isStockAvailable;
	}
	
	
	/**
	 * This method will print current stock level in the Console.
	 */
	public void printStockForAllItems() {
		
		System.out.println("Current stock of products:");
		for (Products product : Products.values()) {
			
			System.out.println( product.name() + " - " + stockMap.get(product.name()));
		}
	}
	
}
