package myjdbc;

public class VariableObject {
	public static void main(String[] args) {
		Product product1; // -> stack영역에 product1이라는 객체의 주소값이 할당됨
		product1 = new Product(); // -> heap영역에 객체의 속성값을 넣어놓는데 이것을 꺼내오기위해서는 stack영역의 객체에대한 주소값을 가져와야함
		Product product2 = new Product();
		product2.price = 5000;
		System.out.println(product1.price);
		product1 = product2;
		// 여기서부터 product1은 product2의 주소값을 참조하게됨
		System.out.println(product1.price);
	}
}


class Product {
	
	// Product의 객체변수들
	int price;
	String name;
	String maker;
}