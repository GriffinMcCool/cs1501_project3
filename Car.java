//author Griffin McCool
package cs1501_p3;

public class Car implements Car_Inter{
    private String VIN;
    private String make;
    private String model;
    private int price;
    private int mileage;
    private String color;

    /**
    * Constructor that accepts car attributes
    */
    public Car (String VIN1, String make1, String model1, int price1, int mileage1, String color1){
        VIN = VIN1;
        make = make1;
        model = model1;
        price = price1;
        mileage = mileage1;
        color = color1;
    }

    /**
	 * Getter for the VIN attribute
	 *
	 * @return 	String The VIN
	 */
	public String getVIN(){
        return VIN;
    }

	/**
	 * Getter for the make attribute
	 *
	 * @return 	String The make
	 */
	public String getMake(){
        return make;
    }

	/**
	 * Getter for the model attribute
	 *
	 * @return 	String The model
	 */
	public String getModel(){
        return model;
    }

	/**
	 * Getter for the price attribute
	 *
	 * @return 	String The price
	 */
	public int getPrice(){
        return price;
    }

	/**
	 * Getter for the mileage attribute
	 *
	 * @return 	String The mileage
	 */
	public int getMileage(){
        return mileage;
    }

	/**
	 * Getter for the color attribute
	 *
	 * @return 	String The color
	 */
	public String getColor(){
        return color;
    }

	/**
	 * Setter for the price attribute
	 *
	 * @param 	newPrice The new Price
	 */
	public void setPrice(int newPrice){
        price = newPrice;
    }

	/**
	 * Setter for the mileage attribute
	 *
	 * @param 	newMileage The new Mileage
	 */
	public void setMileage(int newMileage){
        mileage = newMileage;
    }

	/**
	 * Setter for the color attribute
	 *
	 * @param 	newColor The new color
	 */
	public void setColor(String newColor){
        color = newColor;
    }
}