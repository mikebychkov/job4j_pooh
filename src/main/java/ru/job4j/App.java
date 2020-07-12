package ru.job4j;

public class App {
	
    public static void main(String[] args) throws InterruptedException {

        Server srv = new Server();

        Thread t0 = new Thread(
                () -> {
                    srv.start();
                },
                "SERVER"
        );
        Thread t1 = new Thread(
                () -> {
                    Client publisher = new Client();
                    publisher.post("queue", "weather", "temperature +18 C");
                },
                "PUBLISHER"
        );
        Thread t2 = new Thread(
                () -> {
                    Client subscriber = new Client();
                    String rsl = subscriber.get("queue", "weather");
                    System.out.println("SUBSCRIBER RESULT: " + rsl);
                },
                "SUBSCRIBER"
        );

        t0.start();
        Thread.sleep(100);
        t1.start();
        Thread.sleep(100);
        t2.start();

        t1.join();
        t2.join();

        srv.stop();

        t0.join();
    }
}
