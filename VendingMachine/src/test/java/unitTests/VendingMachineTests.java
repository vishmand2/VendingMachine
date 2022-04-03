package unitTests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import constants.Constants;
import enums.Money;
import enums.Products;
import utilities.CalculateAmount;
import utilities.MachineUtils;
import utilities.MoneyManagement;
import utilities.StockManagement;



public class VendingMachineTests {
	MachineUtils machineUtilsObj;
	CalculateAmount calculateAmountObj;
	StockManagement stockManagementObj;
	MoneyManagement moneyManagementObj;
	
	
	@Before
	public void initialise() {
		machineUtilsObj = new MachineUtils();
		calculateAmountObj = new CalculateAmount();
		stockManagementObj = new StockManagement();
		moneyManagementObj = new MoneyManagement();
	}
	
	
	@After
	public void tearDown() {
		machineUtilsObj = null;
		calculateAmountObj = null;
		stockManagementObj = null;
		moneyManagementObj = null;
	}
	
	
	/*
	 * Verify enter money message presented to user 
	 */
	@Test
	public void testDisplayEnterMoneyMessage() {
		MachineUtils.selectedProductId = 1;
		machineUtilsObj.displayEnterMoneyMessage();
		assertEquals("Please enter the money to complete your purchase or press 0 to Exit: ", machineUtilsObj.message);
	}
	
	/*
	 * performMoneyManagement() Method should return false if change amount is negative number
	 */
	@Test
	public void testPerformMoneyManagementForFalseResponse() {
		MachineUtils.changeAmount = -1;
		MachineUtils.productObj = Products.COKE;
		boolean status = machineUtilsObj.performMoneyManagement();
		assertEquals(false, status);
	}
	
	/* 
	 * performMoneyManagement() Method should return true if change amount is zero
	 */
	@Test
	public void testPerformMoneyManagementForTrueResponse() {
		MachineUtils.changeAmount = 0;
		MachineUtils.productObj = Products.COKE;
		boolean status = machineUtilsObj.performMoneyManagement();
		assertEquals(true, status);
	}

	/*
	 * checkForAllowedCoins() method should return false if entered money is not
	 * available in Money Enum.
	 */
	@Test
	public void testCheckForAllowedCoinsForTrueResponse() {
		assertEquals(false, machineUtilsObj.checkForAllowedCoins(3));
	}
	
	/*
	 * checkForAllowedCoins() method should return true if entered money is 
	 * available in Money Enum.
	 */
	@Test
	public void testCheckForAllowedCoinsForFalseResponse() {
		assertEquals(true, machineUtilsObj.checkForAllowedCoins(5));
	}
	
	/*
	 * validateProductSelection() should return zero if user select Exit
	 * option from product selection menu
	 */
	@Test
	public void testValidateProductSelectionForResponseZero() {
		int value = machineUtilsObj.validateProductSelection(0);
		assertEquals(0, value);
	}
	
	/*
	 * validateProductSelection() should return one if user select a valid product
	 * option from product selection menu
	 */
	@Test
	public void testValidateProductSelectionForResponseOne() {
		MachineUtils.numberOfProduct = 3;
		int value = machineUtilsObj.validateProductSelection(1);
		assertEquals(1, value);
	}
	
	/*
	 * validateProductSelection() should return -1 if user enter invalid product 
	 * option from product selection menu
	 */
	@Test
	public void testValidateProductSelectionForResponseNegativeOne() {
		int value = machineUtilsObj.validateProductSelection(9);
		assertEquals(-1, value);
	}
	
	/*
	 * validateProductSelection() should return -2 if Admin select Reset option
	 * option from product selection menu
	 */
	@Test
	public void testValidateProductSelectionForResponseNegativeTwo() {
		int value = machineUtilsObj.validateProductSelection(Constants.RESET_CODE);
		assertEquals(-2, value);
	}
	
	/*
	 * resetLocalVariables() method should set the variables to the default values
	 */
	@Test
	public void testResetLocalVariables() {
		
		machineUtilsObj.resetLocalVariables();
		
		assertEquals(0, MachineUtils.selectedProductId);
		assertEquals(0, MachineUtils.enteredAmount);
		assertEquals(0, MachineUtils.changeAmount);
		assertEquals(0, MachineUtils.productPrice);
		assertEquals("", machineUtilsObj.message);
	}
	
	
	/*
	 * Test method able to retrieve Money values from Enum.
	 */
	@Test
	public void testGetMoneyValue() {

		int value;
		
		value = Money.PENNY.getValue();
		assertEquals(1, value);
		value = Money.NICKEL.getValue();
		assertEquals(5, value);
		value = Money.DIME.getValue();
		assertEquals(10,value);
		value = Money.QUATER.getValue();
		assertEquals(25,value);
	}
	
	
	//CalculateAmount.java tests
	
	/*
	 * calculateTotalAmount() method should return the given product price. 
	 */
	@Test
	public void testCalculateTotalAmount() {
		int price = calculateAmountObj.calculateTotalAmount(Products.SODA);
		assertEquals(45, price);
	}
	
	/*
	 * calculateChangeAmount(productPrice, EneterdAmount) should return the difference of the Amount
	 * i.e. EnteredAmount - productPrice
	 */
	@Test
	public void testCalculateChangeAmountWhenProductPriceGreaterThanZero() {
		
		int change = calculateAmountObj.calculateChangeAmount(10, 25);
		assertEquals(15, change);
		
	}
	
	/*
	 * calculateChangeAmount(productPrice, EneterdAmount) should return EneterdAmount back if 
	 * product amount is 0.
	 */
	@Test
	public void testCalculateChangeAmountWhenProductPriceIsZero() {
		
		int change = calculateAmountObj.calculateChangeAmount(0, 10);
		assertEquals(10, change);
	
	}
	
	/*
	 * calculateChangeAmount(productPrice, EneterdAmount) should return 0 if product amount is invalid number
	 */
	@Test
	public void testCalculateChangeAmountWhenProductPriceIsNegative() {
		
		int change = calculateAmountObj.calculateChangeAmount(-1, 10);
		assertEquals(0, change);
	
	}
	
	//StockManagement.java tests
	
	
	/*
	 * resetDefaultStockForItems() method should reset the stock value in stock hashmap 
	 * according to the global constant value 
	 */
	@Test
	public void testResetDefaultStockForItems() {
		stockManagementObj.resetDefaultStockForItems();
		int stock = StockManagement.stockMap.get(Products.COKE.name());
		assertEquals(Constants.MIN_QUANTITY_OF_ITEM, stock);
	}
	
	/*
	 * addStockBackForItem() method update the stock back for given product
	 */
	@Test
	public void testAddStockBackForItem() {
		
		StockManagement.stockMap.put(Products.PEPSI.name(), 1);
		stockManagementObj.addStockBackForItem(Products.PEPSI);
		int stockValue = StockManagement.stockMap.get(Products.PEPSI.name());
		assertEquals(2, stockValue);
	}
	
	/*
	 * reduceStockForItem() method should reduce the given product stock by 1.
	 */
	@Test
	public void testReduceStockForItem() {
		
		StockManagement.stockMap.put(Products.PEPSI.name(), 2);
		stockManagementObj.reduceStockForItem(Products.PEPSI);
		int stockValue = StockManagement.stockMap.get(Products.PEPSI.name());
		assertEquals(1, stockValue);
	}
	
	/*
	 * checkStockForItem() method should return true if the stock is greater than 0 for 
	 * given product.
	 */
	@Test
	public void testCheckStockForItemForTrueResponse() {
		StockManagement.stockMap.put(Products.COKE.name(), 1);		
		boolean status = stockManagementObj.checkStockForItem(Products.COKE);
		assertEquals(true, status);
	}
	
	/*
	 * checkStockForItem() method should return false if the stock is less than or equal
	 * to 0 for given product.
	 */
	@Test
	public void testCheckStockForItemForFalseResponse() {
		StockManagement.stockMap.put(Products.COKE.name(), 0);
		boolean status = stockManagementObj.checkStockForItem(Products.COKE);
		assertEquals(false, status);
	}

	//MoneyManagement.java tests
	
	/*
	 * resetMoneyInTheMachine() method should reset the coins value according to
	 * the global constant value.
	 */
	@Test
	public void testResetMoneyInTheMachine() {
		
		moneyManagementObj.resetMoneyInTheMachine();
		
		int moneyCount = MoneyManagement.moneyGlobalMap.get(Money.PENNY.name());
		assertEquals(Constants.MIN_QUANTITY_OF_COINS , moneyCount);
		
		moneyCount = MoneyManagement.moneyGlobalMap.get(Money.NICKEL.name());
		assertEquals(Constants.MIN_QUANTITY_OF_COINS , moneyCount);
		
		moneyCount = MoneyManagement.moneyGlobalMap.get(Money.DIME.name());
		assertEquals(Constants.MIN_QUANTITY_OF_COINS , moneyCount);
		
		moneyCount = MoneyManagement.moneyGlobalMap.get(Money.QUATER.name());
		assertEquals(Constants.MIN_QUANTITY_OF_COINS , moneyCount);
	}
	
	/*
	 * resetMoneyToLocalMap() method should reset the coins values to 0.
	 */
	@Test
	public void testResetMoneyToLocalMap() {
		
		moneyManagementObj.resetMoneyToLocalMap();
		
		int moneyCount = MoneyManagement.moneyLocalMap.get(Money.PENNY.name());
		assertEquals(0, moneyCount);
		
		moneyCount = MoneyManagement.moneyLocalMap.get(Money.NICKEL.name());
		assertEquals(0, moneyCount);
		
		moneyCount = MoneyManagement.moneyLocalMap.get(Money.DIME.name());
		assertEquals(0, moneyCount);
		
		moneyCount = MoneyManagement.moneyLocalMap.get(Money.QUATER.name());
		assertEquals(0, moneyCount);		
	}
	
	
	/*
	 * addMoneyToLocalMap() method should update the local money hashmap value 
	 * for given money enum object.
	 */
	@Test
	public void testAddMoneyToLocalMap() {
		
		moneyManagementObj.addMoneyToLocalMap(Money.PENNY);
		int moneyCount = MoneyManagement.moneyLocalMap.get(Money.PENNY.name());
		assertEquals(1, moneyCount);
		
	}
	
	/*
	 * addMoneyToGlobalMap() should update the values of global money hashmap from
	 * local money hashmap.
	 */
	@Test
	public void testAddMoneyToGlobalMap() {
		
		moneyManagementObj.resetMoneyToLocalMap();
		moneyManagementObj.resetMoneyInTheMachine();
		
		moneyManagementObj.addMoneyToLocalMap(Money.PENNY);
		int localMapPennyCount = MoneyManagement.moneyLocalMap.get(Money.PENNY.name());
		
		moneyManagementObj.addMoneyToGlobalMap();
		int globalMapPennyCount = MoneyManagement.moneyGlobalMap.get(Money.PENNY.name());
		
		assertEquals(localMapPennyCount + Constants.MIN_QUANTITY_OF_COINS, globalMapPennyCount);
	}
	
	/*
	 * deductMoneyFromGlobalMap() should update the values of global money hashmap for
	 * given Money object.
	 */
	@Test
	public void testDeductMoneyFromGlobalMap() {
		
		moneyManagementObj.resetMoneyInTheMachine();
		moneyManagementObj.deductMoneyFromGlobalMap(Money.DIME);
		
		int dimeCount = MoneyManagement.moneyGlobalMap.get(Money.DIME.name());
		
		assertEquals(Constants.MIN_QUANTITY_OF_COINS - 1, dimeCount);
	}
	
	/*
	 * checkForChangeAvailable() should return true if value for given money object is
	 * greater than 0 in global money hashmap.
	 */
	@Test
	public void testCheckForChangeAvailableForTrueResponse() {
		
		moneyManagementObj.resetMoneyInTheMachine();
		boolean status = moneyManagementObj.checkForChangeAvailable(Money.QUATER);
		assertEquals(true, status);
		
	}
	
	/*
	 * checkForChangeAvailable() should return false if value for given money object is
	 * less than or equal 0 in global money hashmap.
	 */
	@Test
	public void testCheckForChangeAvailableForFalseResponse() {
		
		MoneyManagement.moneyGlobalMap.put(Money.QUATER.name(), 0);
		
		boolean status = moneyManagementObj.checkForChangeAvailable(Money.QUATER);
		assertEquals(false, status);
		
	}
	
	/*
	 * adjustGlobalMapWhenRefundingMoney() method should return true if changeAmount
	 * is zero.
	 */
	@Test
	public void testAdjustGlobalMapWhenRefundingMoneyForZeroChangeAmount() {
		
		boolean status = moneyManagementObj.adjustGlobalMapWhenRefundingMoney(0);
		assertEquals(true, status);
	}
	
	/*
	 * adjustGlobalMapWhenRefundingMoney() method should return true if changeAmount
	 * is successfully updated in global money hashmap.
	 */
	@Test
	public void testAdjustGlobalMapWhenRefundingMoneyWhenChangeAvailable() {
		
		moneyManagementObj.resetMoneyInTheMachine();
		boolean status = moneyManagementObj.adjustGlobalMapWhenRefundingMoney(5);
		assertEquals(true, status);
	}
	
	/*
	 * adjustGlobalMapWhenRefundingMoney() method should return false if changeAmount
	 * is not successfully updated in global money hashmap.
	 */
	@Test
	public void testAdjustGlobalMapWhenRefundingMoneyWhenChangeNotAvailable() {
		
		moneyManagementObj.resetMoneyInTheMachine();
		MoneyManagement.moneyGlobalMap.put(Money.NICKEL.name(), 0);
		boolean status = moneyManagementObj.adjustGlobalMapWhenRefundingMoney(5);
		assertEquals(false, status);
	}

}
