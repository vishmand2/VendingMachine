package unitTests;

import static org.junit.Assert.assertEquals;

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
	
	@Test
	public void testDisplayEnterMoneyMessage() {
		MachineUtils.selectedProductId = 1;
		machineUtilsObj.displayEnterMoneyMessage();
		assertEquals("Please enter the money to complete your purchase or press 0 to Exit: ", machineUtilsObj.message);
	}
	
	@Test
	public void testPerformMoneyManagementForFalseResponse() {
		MachineUtils.changeAmount = -1;
		MachineUtils.productObj = Products.COKE;
		boolean status = machineUtilsObj.performMoneyManagement();
		assertEquals(false, status);
	}
	
	@Test
	public void testPerformMoneyManagementForTrueResponse() {
		MachineUtils.changeAmount = 0;
		MachineUtils.productObj = Products.COKE;
		boolean status = machineUtilsObj.performMoneyManagement();
		assertEquals(true, status);
	}

	@Test
	public void testCheckForAllowedCoinsForTrueResponse() {
		assertEquals(false, machineUtilsObj.checkForAllowedCoins(3));
	}
	
	@Test
	public void testCheckForAllowedCoinsForFalseResponse() {
		assertEquals(true, machineUtilsObj.checkForAllowedCoins(5));
	}
	
	
	@Test
	public void testValidateProductSelectionForResponseZero() {
		int value = machineUtilsObj.validateProductSelection(0);
		assertEquals(0, value);
	}
	
	@Test
	public void testValidateProductSelectionForResponseOne() {
		MachineUtils.numberOfProduct = 3;
		int value = machineUtilsObj.validateProductSelection(1);
		assertEquals(1, value);
	}
	
	@Test
	public void testValidateProductSelectionForResponseNegativeOne() {
		int value = machineUtilsObj.validateProductSelection(9);
		assertEquals(-1, value);
	}
	
	@Test
	public void testValidateProductSelectionForResponseNegativeTwo() {
		int value = machineUtilsObj.validateProductSelection(Constants.RESET_CODE);
		assertEquals(-2, value);
	}
	
	@Test
	public void testResetLocalVariables() {
		
		machineUtilsObj.resetLocalVariables();
		
		assertEquals(0, MachineUtils.selectedProductId);
		assertEquals(0, MachineUtils.enteredAmount);
		assertEquals(0, MachineUtils.changeAmount);
		assertEquals(0, MachineUtils.productPrice);
		assertEquals("", machineUtilsObj.message);
	}
	
	
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
	
	@Test
	public void testCalculateTotalAmount() {
		int price = calculateAmountObj.calculateTotalAmount(Products.SODA);
		assertEquals(45, price);
	}
	
	@Test
	public void testCalculateChangeAmountWhenProductPriceGreaterThanZero() {
		
		int change = calculateAmountObj.calculateChangeAmount(10, 25);
		assertEquals(15, change);
		
	}
	
	@Test
	public void testCalculateChangeAmountWhenProductPriceIsZero() {
		
		int change = calculateAmountObj.calculateChangeAmount(0, 10);
		assertEquals(10, change);
	
	}
	
	@Test
	public void testCalculateChangeAmountWhenProductPriceIsNegative() {
		
		int change = calculateAmountObj.calculateChangeAmount(-1, 10);
		assertEquals(0, change);
	
	}
	
	//StockManagement.java tests
	
	@Test
	public void testResetDefaultStockForItems() {
		stockManagementObj.resetDefaultStockForItems();
		int stock = StockManagement.stockMap.get(Products.COKE.name());
		assertEquals(Constants.MIN_QUANTITY_OF_ITEM, stock);
	}
	
	
	@Test
	public void testAddStockBackForItem() {
		
		StockManagement.stockMap.put(Products.PEPSI.name(), 1);
		stockManagementObj.addStockBackForItem(Products.PEPSI);
		int stockValue = StockManagement.stockMap.get(Products.PEPSI.name());
		assertEquals(2, stockValue);
	}
	
	@Test
	public void testReduceStockForItem() {
		
		StockManagement.stockMap.put(Products.PEPSI.name(), 2);
		stockManagementObj.reduceStockForItem(Products.PEPSI);
		int stockValue = StockManagement.stockMap.get(Products.PEPSI.name());
		assertEquals(1, stockValue);
	}
	
	@Test
	public void testCheckStockForItemForTrueResponse() {
		StockManagement.stockMap.put(Products.COKE.name(), 1);		
		boolean status = stockManagementObj.checkStockForItem(Products.COKE);
		assertEquals(true, status);
	}
	
	@Test
	public void testCheckStockForItemForFalseResponse() {
		StockManagement.stockMap.put(Products.COKE.name(), 0);
		boolean status = stockManagementObj.checkStockForItem(Products.COKE);
		assertEquals(false, status);
	}

	//MoneyManagement.java tests
	
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
	
	@Test
	public void testAddMoneyToLocalMap() {
		
		moneyManagementObj.addMoneyToLocalMap(Money.PENNY);
		int moneyCount = MoneyManagement.moneyLocalMap.get(Money.PENNY.name());
		assertEquals(1, moneyCount);
		
	}
	
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
	
	@Test
	public void testDeductMoneyFromGlobalMap() {
		
		moneyManagementObj.resetMoneyInTheMachine();
		moneyManagementObj.deductMoneyFromGlobalMap(Money.DIME);
		
		int dimeCount = MoneyManagement.moneyGlobalMap.get(Money.DIME.name());
		
		assertEquals(Constants.MIN_QUANTITY_OF_COINS - 1, dimeCount);
	}
	
	@Test
	public void testCheckForChangeAvailableForTrueResponse() {
		
		moneyManagementObj.resetMoneyInTheMachine();
		boolean status = moneyManagementObj.checkForChangeAvailable(Money.QUATER);
		assertEquals(true, status);
		
	}
	
	@Test
	public void testCheckForChangeAvailableForFalseResponse() {
		
		MoneyManagement.moneyGlobalMap.put(Money.QUATER.name(), 0);
		
		boolean status = moneyManagementObj.checkForChangeAvailable(Money.QUATER);
		assertEquals(false, status);
		
	}
	
	@Test
	public void testAdjustGlobalMapWhenRefundingMoneyForZeroChangeAmount() {
		
		boolean status = moneyManagementObj.adjustGlobalMapWhenRefundingMoney(0);
		assertEquals(true, status);
	}
	
	@Test
	public void testAdjustGlobalMapWhenRefundingMoneyWhenChangeAvailable() {
		
		moneyManagementObj.resetMoneyInTheMachine();
		boolean status = moneyManagementObj.adjustGlobalMapWhenRefundingMoney(5);
		assertEquals(true, status);
	}
	
	@Test
	public void testAdjustGlobalMapWhenRefundingMoneyWhenChangeNotAvailable() {
		
		moneyManagementObj.resetMoneyInTheMachine();
		MoneyManagement.moneyGlobalMap.put(Money.NICKEL.name(), 0);
		boolean status = moneyManagementObj.adjustGlobalMapWhenRefundingMoney(5);
		assertEquals(false, status);
	}

}
