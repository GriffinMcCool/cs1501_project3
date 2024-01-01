/**
 * A driver for CS1501 Project 3
 * @author	Dr. Farnan
 */
package cs1501_p3;

public class App {
	public static void main(String[] args) {
		//CarsPQ cpq = new CarsPQ("build/resources/main/cars.txt");
		CarsPQ cpq = new CarsPQ("build/resources/test/cars.txt");
		Car c;
		String testVIN = "PUAF85WU5R6L6H1P9";
		String testVIN2 = "UTJYU67091B71NGZ3";
		String testVIN3 = "5DZ623ZRW0C4N80YZ";
		String testVIN4 = "Y9BXE6H7957YNKD2C";
		String testVIN5 = "678PL45NTNWRED0RJ";
		String testVIN6 = "X1U2PEJSC361L10MZ";
		String testVIN7 = "8BSM1K0A6GXY2CHD7";
		String testVIN8 = "1Y5NWYGLY5F4PX4HH";
		String testVIN9 = "SM0G8H2WXK466CRCA";
		String testVIN10 = "M750UYC6G01AN7590";
		String testVIN11 = "RAMM7ZJBSFZ0HRTTN";
		String testVIN12 = "SY719WJ4MMYVN0XNG";
		String testVIN13 = "GNX5TS04SM5V5EXP8";
		String testVIN14 = "16Z2DPEHSUK5KCMEH";

		// Had to comment out the tests bc i made variables that were used for testing private

		// c = cpq.getLowPrice();
		// System.out.println("Lowest price car VIN: " + c.getVIN());
		// c = cpq.getLowMileage();
		// System.out.println("Lowest mileage car VIN: " + c.getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());
		// System.out.println("Lowest price Ford Fiesta: " + cpq.getLowPrice("Ford", "Fiesta").getVIN());
		// System.out.println("Lowest mileage Ford Fiesta: " + cpq.getLowMileage("Ford", "Fiesta").getVIN());
		// System.out.println("Lowest price Ford Escape: " + cpq.getLowPrice("Ford", "Escort").getVIN());
		// System.out.println("Lowest mileage Ford Escape: " + cpq.getLowMileage("Ford", "Escort").getVIN());
		// cpq.updatePrice("UTJYU67091B71NGZ3", 20000);
		// System.out.println("Lowest price Ford Fiesta: " + cpq.getLowPrice("Ford", "Fiesta").getVIN());
		// System.out.println("Lowest mileage Ford Fiesta: " + cpq.getLowMileage("Ford", "Fiesta").getVIN());

		// System.out.println("Lowest price Honda Civic: " + cpq.getLowPrice("Honda", "Civic").getVIN());
		// System.out.println("Lowest mileage Honda Civic: " + cpq.getLowMileage("Honda", "Civic").getVIN());
		// cpq.remove("8BSM1K0A6GXY2CHD7");
		// System.out.println("Lowest price Honda Civic: " + cpq.getLowPrice("Honda", "Civic").getVIN());
		// System.out.println("Lowest mileage Honda Civic: " + cpq.getLowMileage("Honda", "Civic").getVIN());

		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());

		// System.out.println("-----");
		// Car[] fort = (cpq.getmmNode("Ford:Fiesta").getmmPricePQ());
		// Car[] nite = (cpq.getmmNode("Ford:Fiesta").getmmMileagePQ());
		// for (int i = 1; i < cpq.getmmNode("Ford:Fiesta").getInsPos(); i++) System.out.println("Price: VIN[" + i + "]: " + fort[i].getVIN());
		// for (int i = 1; i < cpq.getmmNode("Ford:Fiesta").getInsPos(); i++) System.out.println("Mileage: VIN[" + i + "]: " + nite[i].getVIN());
		// System.out.println("Updating price for " + testVIN + " to 1000");
		// cpq.updatePrice(testVIN, 1000);
		// for (int i = 1; i < cpq.getmmNode("Ford:Fiesta").getInsPos(); i++) System.out.println("Price: VIN[" + i + "]: " + fort[i].getVIN());
		// for (int i = 1; i < cpq.getmmNode("Ford:Fiesta").getInsPos(); i++) System.out.println("Mileage: VIN[" + i + "]: " + nite[i].getVIN());
		// System.out.println("Updating mileage for " + testVIN + " to 9000");
		// cpq.updateMileage(testVIN, 9000);
		// for (int i = 1; i < cpq.getmmNode("Ford:Fiesta").getInsPos(); i++) System.out.println("Price: VIN[" + i + "]: " + fort[i].getVIN());
		// for (int i = 1; i < cpq.getmmNode("Ford:Fiesta").getInsPos(); i++) System.out.println("Mileage: VIN[" + i + "]: " + nite[i].getVIN());
		// System.out.println("Updating price for " + testVIN + " to 20000");
		// cpq.updatePrice(testVIN, 20000);
		// for (int i = 1; i < cpq.getmmNode("Ford:Fiesta").getInsPos(); i++) System.out.println("Price: VIN[" + i + "]: " + fort[i].getVIN());
		// for (int i = 1; i < cpq.getmmNode("Ford:Fiesta").getInsPos(); i++) System.out.println("Mileage: VIN[" + i + "]: " + nite[i].getVIN());
		// System.out.println("Updating price for " + testVIN6 + " to 1000");
		// cpq.updatePrice(testVIN6, 1000);
		// for (int i = 1; i < cpq.getmmNode("Ford:Fiesta").getInsPos(); i++) System.out.println("Price: VIN[" + i + "]: " + fort[i].getVIN());
		// for (int i = 1; i < cpq.getmmNode("Ford:Fiesta").getInsPos(); i++) System.out.println("Mileage: VIN[" + i + "]: " + nite[i].getVIN());

		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());

		// System.out.println("Updating mileage for " + testVIN6 + " to 10001");
		// cpq.updateMileage(testVIN6, 10001);
		// System.out.println("nocap");

		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());


		// for (int i = 1; i < cpq.getmmNode("Ford:Fiesta").getInsPos(); i++) System.out.println("Price: VIN[" + i + "]: " + fort[i].getVIN());
		// for (int i = 1; i < cpq.getmmNode("Ford:Fiesta").getInsPos(); i++) System.out.println("Mileage: VIN[" + i + "]: " + nite[i].getVIN());
		// System.out.println("Removing: " + testVIN6);
		// cpq.remove(testVIN6);
		//for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		//for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());
		// for (int i = 1; i < cpq.getmmNode("Ford:Fiesta").getInsPos(); i++) System.out.println("Price: VIN[" + i + "]: " + fort[i].getVIN());
		// for (int i = 1; i < cpq.getmmNode("Ford:Fiesta").getInsPos(); i++) System.out.println("Mileage: VIN[" + i + "]: " + nite[i].getVIN());
		// System.out.println("Removing: " + testVIN);
		// cpq.remove(testVIN);
		// System.out.println("Removing: " + testVIN2);
		// cpq.remove(testVIN2);
		// System.out.println("Removing: " + testVIN3);
		// cpq.remove(testVIN3);
		// System.out.println("Removing: " + testVIN4);
		// cpq.remove(testVIN4);
		// for (int i = 1; i < cpq.getmmNode("Ford:Fiesta").getInsPos(); i++) System.out.println("Price: VIN[" + i + "]: " + fort[i].getVIN());
		// for (int i = 1; i < cpq.getmmNode("Ford:Fiesta").getInsPos(); i++) System.out.println("Mileage: VIN[" + i + "]: " + nite[i].getVIN());
		
		//or (int i = 1; i < cpq.getmmNode("Ford:Fiesta").getInsPos(); i++) System.out.println("Mileage: VIN[" + i + "]: " + nite[i].getVIN());
		// String testVIN2 = "PUAF85WU5R6L6H1P9";
		// System.out.println("Mileage of car with vin (" + testVIN + "): " + cpq.get(testVIN).getMileage());
		// int updateMileage = 900;
		// System.out.println("Updating mileage to: " + updateMileage);
		// cpq.updateMileage(testVIN, updateMileage);
		// System.out.println("Mileage of car with vin (" + testVIN + "): " + cpq.get(testVIN).getMileage());
		// for (int i = 1; i < 15; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());
		// String updateColor = "green";
		// System.out.println("Color of car with vin (" + testVIN2 + "): " + cpq.get(testVIN2).getColor());
		// System.out.println("Updating color to: " + updateColor);
		// cpq.updateColor(testVIN2, updateColor);
		// System.out.println("Color of car with vin (" + testVIN2 + "): " + cpq.get(testVIN2).getColor());

		// Car c = new Car("5", "Ford", "Fiesta", 20, 200000, "White");
		// cpq.add(c);
		// System.out.println("Updating price for " + testVIN + " to 16000");
		// cpq.updatePrice(testVIN, 19);

		// c = new Car("6", "Ford", "Fiesta", 20, 200000, "White");
		// cpq.add(c);
		// c = cpq.getLowPrice();
		// System.out.println("Lowest price car VIN: " + c.getVIN());
		// c = cpq.getLowMileage();
		// System.out.println("Lowest mileage car VIN: " + c.getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());
		// String testVIN3 = "5";
		// System.out.println("Color of car with vin (" + testVIN3 + "): " + cpq.get(testVIN3).getColor());
		//System.out.println("Removing " + testVIN);
		// cpq.remove(testVIN);
		//cpq.updatePrice(testVIN, 1);
		// Car c = new Car("5", "Ford", "Fiesta", 20, 200000, "White");
		// cpq.add(c);
		//for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		// c = new Car("5", "Ford", "Fiesta", 20, 200000, "White");
		// cpq.add(c);
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());
		// cpq.remove("5");
		//cpq.remove("PUAF85WU5R6L6H1P9");
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());
		// cpq.remove(testVIN2);
		// System.out.println("removing: " + testVIN2);
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());
		// c = cpq.getLowPrice();
		// System.out.println("Lowest price car VIN: " + c.getVIN());
		// c = cpq.getLowMileage();
		// System.out.println("Lowest mileage car VIN: " + c.getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());
		// cpq.remove(testVIN3);
		// System.out.println("removing: " + testVIN3);
		// cpq.remove(testVIN4);
		// System.out.println("removing: " + testVIN4);
		// cpq.remove(testVIN5);
		//System.out.println("removing: " + testVIN5);
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());
		// System.out.println("NOCAP");
		// System.out.println("Removing: " + testVIN5);
		// cpq.remove(testVIN5);
		// cpq.remove(testVIN8);
		// System.out.println("removing: " + testVIN8);
		// cpq.remove(testVIN9);
		// System.out.println("removing: " + testVIN9);
		// cpq.remove(testVIN10);
		// System.out.println("removing: " + testVIN10);
		// cpq.remove(testVIN11);
		// System.out.println("removing: " + testVIN11);
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());
		// cpq.remove(testVIN12);
		// System.out.println("removing: " + testVIN12);
		// cpq.remove(testVIN13);
		// System.out.println("removing: " + testVIN13);
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());
		// System.out.println("removing: " + testVIN14);
		// cpq.remove(testVIN14);
		// System.out.println("REMOVAL SUCCESS");
		
		// c = new Car("FORTNITE", "Fork", "Fiesto", 1000, 2000, "Oranch");
		// cpq.add(c);
		// c = new Car("cap", "Fort", "Fieste", 900, 3000, "Black");
		// cpq.add(c);
		// c = new Car("NEWCAR", "Hyundai", "Elantra", 50, 1000, "Black");
		// cpq.add(c);
		// c = new Car("OLDCAR", "Hyundai", "Elantra", 50, 900, "Blue");
		// cpq.add(c);
		// cpq.updateMileage("OLDCAR", 1001);
		// System.out.println("LEAST Hyundai:Elantra Mileage: " + cpq.getLowMileage("Hyundai", "Elantra").getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());
		// cpq.remove("FORTNITE");
		// cpq.remove("cap");
		// cpq.remove("NEWCAR");
		// cpq.remove("OLDCAR");
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());
		// cpq.add(c);
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());
		// cpq.updateMileage("cap", 1000);
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());
		// cpq.remove("cap");
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("PRICE: VIN[" + i + "]: " + cpq.pricePQ[i].getVIN());
		// for (int i = 1; i < cpq.insertPosition; i++) System.out.println("MILEAGE: VIN[" + i + "]: " + cpq.mileagePQ[i].getVIN());
		// test remove more. Add more or less cars/remove multiple items/remove edge cases/update then remove!!!!!!!!!!!!!!!!!!!!
	}
}
