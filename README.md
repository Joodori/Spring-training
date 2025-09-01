## 의존성 주입
***

<https://lincoding.tistory.com/76> 를 참고했어요

***


### 강한 결합

우리가 Spring을 알기 전 까지 계속 써오던 방식 -> 생성할 때 마다 우리 손으로 구현을 해주어야함 (아이귀찮아)
```java
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
```
***
### 느슨한 결합

Spring을 알기 시작했으니 뭔가 정갈하게 하기위해 "의존성 주입"이라는 것을 명심하며 아래 코드를 관찰해보자
```java
// 인터페이스 정의
public interface Service {
    void doSomething();
}

// 인터페이스 구현체 1
public class ServiceImpl implements Service {
    @Override
    public void doSomething() {
        System.out.println("Doing something in ServiceImpl");
    }
}

// 인터페이스 구현체 2
public class AnotherServiceImpl implements Service {
    @Override
    public void doSomething() {
        System.out.println("Doing something in AnotherServiceImpl");
    }
}

// 의존성 주입을 통해 느슨한 결합 구현
public class A {
    private final Service service;

    // 생성자 주입
    public A(Service service) {
        this.service = service;
    }

    public void performAction() {
        service.doSomething();
    }

    public static void main(String[] args) {
        // ServiceImpl 주입
        Service service1 = new ServiceImpl();
        A a1 = new A(service1);
        a1.performAction();

        // AnotherServiceImpl 주입
        Service service2 = new AnotherServiceImpl();
        A a2 = new A(service2);
        a2.performAction();
    }
}
```
***

