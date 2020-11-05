package edu.neu.info5100;

public class ReverseHello extends Thread {

    static final int TO_BOUND = 49;
    private int num;

    public ReverseHello(int c) {
        this.num = c;
    }


    private void runner(int c) throws InterruptedException {
        ReverseHello hello = new ReverseHello(num);
        hello.setName("" + (num + 1));
        hello.start();
        hello.join();
        if (!getName().equals("Thread-0")) {
            System.out.printf("Hello from Thread %s! %n", getName());
        }

    }

    @Override
    public void run() {
        try {
            if (num <= TO_BOUND) {
                this.runner(num++);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ReverseHello hello = new ReverseHello(-1);
        hello.start();
    }
}