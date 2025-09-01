## 의존성 주입
***

<https://lincoding.tistory.com/76> 를 참고했어요

***

    public class B {
    public void doSomething() {
        System.out.println("Doing something in B");
    }
}

public class A {
    private B b;

    public A() {
        this.b = new B();  // 강한 결합
    }

    public void performAction() {
        b.doSomething();
    }

    public static void main(String[] args) {
        A a = new A();
        a.performAction();
    }
}
***
