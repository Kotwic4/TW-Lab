package lab2;

public class Client extends Thread{
    private Shop shop;
    private String name;

    public Client(Shop shop, String name) {
        super();
        this.shop = shop;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + " idzie po koszyk");
        shop.takeCart();
        System.out.println(name + " robi zakupy");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + " idzie oddac koszyk domu");
        shop.returnCart();

    }
}
