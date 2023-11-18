import java.util.Random;
import java.util.concurrent.Semaphore;

public class BathroomSync {
    private static Semaphore womenMutex = new Semaphore(1);
    private static Semaphore menMutex = new Semaphore(1);
    private static Semaphore emptyMutex = new Semaphore(1);

    public static void womanWantsToEnter() throws InterruptedException {
        emptyMutex.acquire();
        womenMutex.acquire();
        emptyMutex.release();
    }

    public static void manWantsToEnter() throws InterruptedException {
        emptyMutex.acquire();
        menMutex.acquire();
        emptyMutex.release();
    }

    public static void womanLeaves() {
        womenMutex.release();
    }

    public static void manLeaves() {
        menMutex.release();
    }

    public static void main(String[] args) {
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            Thread womanThread = new Thread(() -> {
                try {
                    womanWantsToEnter();
                    System.out.println("Woman enters the bathroom.");
                    Thread.sleep(random.nextInt(3000)); // Simulate bathroom usage
                    womanLeaves();
                    System.out.println("Woman leaves the bathroom.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Thread manThread = new Thread(() -> {
                try {
                    manWantsToEnter();
                    System.out.println("Man enters the bathroom.");
                    Thread.sleep(random.nextInt(3000)); // Simulate bathroom usage
                    manLeaves();
                    System.out.println("Man leaves the bathroom.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            womanThread.start();
            manThread.start();

            try {
                womanThread.join();
                manThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
