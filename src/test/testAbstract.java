package test;

/**
 * Created by shishuaigang on 2017/5/25.
 */
abstract class test {
    public abstract void f();
}

public class testAbstract extends test {
    public void f() {
        System.out.println("测试抽象类和抽象方法");
    }

    public static void main(String args[]) {
        testAbstract t = new testAbstract();
        t.f();
    }
}

