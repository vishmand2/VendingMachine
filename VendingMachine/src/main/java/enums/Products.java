package enums;

public enum Products {
	
	COKE(1, 25), PEPSI(2, 35), SODA(3, 45);
	
	private int selectionNumber;
	private int price;
	
	Products(int selectionNumber, int price){
		this.selectionNumber = selectionNumber;
		this.price = price;
	}
	
	public int getSelectionNumber() {
		return selectionNumber;
	}
	
	public int getPrice() {
		return price;
	}
	
	
	/**
	 * 
	 * @param numberSelection
	 * @return the product object
	 */
	public static Products valueOf(int numberSelection){
        for(Products product: Products.values()){
            if(numberSelection == product.getSelectionNumber()){
                return product;
            }
        }
        return null;
    }

}
