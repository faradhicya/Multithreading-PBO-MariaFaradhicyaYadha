// Menggunakan Runnable untuk fleksibilitas
class Koki implements Runnable {
    private SharedData data;

    public Koki(SharedData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) { // Memproses 3 pesanan
            try {
                String order = data.getOrderForCooking();
                System.out.println("KOKI mulai memasak: " + order);
                Thread.sleep(2000); // Simulasi waktu memasak
                data.putBill(order + " (Tagihan)"); // Setelah selesai, taruh tagihan
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("KOKI: Selesai shift.");
    }
}

class Pelayan implements Runnable {
    private SharedData data;

    public Pelayan(SharedData data) {
        this.data = data;
    }

    @Override
    public void run() {
        try {
            data.takeOrder(); // Pesanan 1
            Thread.sleep(500);
            data.takeOrder(); // Pesanan 2
            Thread.sleep(500);
            data.takeOrder(); // Pesanan 3
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("PELAYAN: Selesai mengambil pesanan.");
    }
}

class Kasir implements Runnable {
    private SharedData data;

    public Kasir(SharedData data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) { // Memproses 3 tagihan
            try {
                String bill = data.getBillForPayment();
                System.out.println("KASIR mulai memproses: " + bill);
                Thread.sleep(1500); // Simulasi waktu pembayaran
                System.out.println("KASIR selesai: " + bill + " dibayar.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("KASIR: Selesai shift.");
    }
}