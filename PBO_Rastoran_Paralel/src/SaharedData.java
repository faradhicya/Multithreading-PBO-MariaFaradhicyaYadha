import java.util.Queue;
import java.util.LinkedList;

class SharedData {
    // Antrean untuk pesanan yang sudah diambil oleh Pelayan, menunggu Koki
    private final Queue<String> orderQueue = new LinkedList<>();
    // Antrean untuk tagihan yang sudah siap, menunggu Kasir
    private final Queue<String> billQueue = new LinkedList<>();
    private int orderCounter = 1;

    // Metode untuk Pelayan mengambil pesanan dari pelanggan (data bersama)
    public synchronized String takeOrder() throws InterruptedException {
        String orderName = "Pesanan #" + orderCounter++;
        System.out.println("PELAYAN mengambil: " + orderName);
        orderQueue.add(orderName); // Masukkan ke antrean Koki
        notifyAll(); // Beri tahu thread yang menunggu (misal: Koki)
        return orderName;
    }

    // Metode untuk Koki mengambil pesanan dari antrean
    public synchronized String getOrderForCooking() throws InterruptedException {
        while (orderQueue.isEmpty()) {
            System.out.println("KOKI: Antrean kosong, menunggu pesanan...");
            wait(); // Koki menunggu jika tidak ada pesanan di antrean
        }
        String order = orderQueue.poll();
        return order;
    }

    // Metode untuk Koki menaruh tagihan ke antrean
    public synchronized void putBill(String bill) {
        System.out.println("KOKI selesai: " + bill + ". Siap ditagihkan.");
        billQueue.add(bill); // Masukkan ke antrean Kasir
        notifyAll(); // Beri tahu thread yang menunggu (misal: Kasir)
    }

    // Metode untuk Kasir mengambil tagihan dari antrean
    public synchronized String getBillForPayment() throws InterruptedException {
        while (billQueue.isEmpty()) {
            System.out.println("KASIR: Tidak ada tagihan, menunggu...");
            wait(); // Kasir menunggu jika tidak ada tagihan di antrean
        }
        String bill = billQueue.poll();
        return bill;
    }
}