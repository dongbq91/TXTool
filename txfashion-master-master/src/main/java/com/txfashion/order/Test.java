package com.txfashion.order;

public class Test {
	
	public static void main(String[] args) {
		String name = "Type: Zip Hoodie | US Size: S | Your Name: HARRISON | Your Number: 98".replace("|", "//");
		System.out.println(name);
		System.out.println(name.split("//")[1]);
	}

}
