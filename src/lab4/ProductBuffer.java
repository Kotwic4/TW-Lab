package lab4;

public class ProductBuffer {

    Product[] products;
    int end;
    public Integer count;

    ProductBuffer(int size){
        this.products = new Product[size];
        for(int i = 0; i < size; i++){
            products[i] = new Product(-1);
        }
        this.count = 0;
        this.end = 0;
    }

    public Product[] getProducts() {
        return products;
    }

    public int getEnd() {
        return end;
    }
}
