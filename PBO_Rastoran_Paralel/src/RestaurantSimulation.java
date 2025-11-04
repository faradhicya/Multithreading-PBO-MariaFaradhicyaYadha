public class RestaurantSimulation {
    public static void main(String[] args) {
        // Objek data bersama yang akan diakses oleh semua thread
        SharedData sharedData = new SharedData();

        // Membuat instance Runnable
        Runnable chefTask = new Koki(sharedData);
        Runnable waiterTask = new Pelayan(sharedData);
        Runnable cashierTask = new Kasir(sharedData);

        // Membuat dan memulai Thread
        Thread chefThread = new Thread(chefTask, "Koki-Thread");
        Thread waiterThread = new Thread(waiterTask, "Pelayan-Thread");
        Thread cashierThread = new Thread(cashierTask, "Kasir-Thread");

        System.out.println("--- Restoran Mulai Beroperasi ---");

        // Memulai semua thread
        waiterThread.start();
        chefThread.start();
        cashierThread.start();

        // Output akhir dari main thread
        try {
            waiterThread.join();
            chefThread.join();
            cashierThread.join();
        } catch (InterruptedException e) {
            System.out.println("Simulasi terganggu.");
        }

        System.out.println("--- Restoran Tutup ---");
    }
}