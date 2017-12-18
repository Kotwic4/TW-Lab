package lab4;

public class ProductBuffer {

    FactoryProduct[] factoryProducts;
    int end;
    public Integer count;

    ProductBuffer(int size){
        this.factoryProducts = new FactoryProduct[size];
        for(int i = 0; i < size; i++){
            factoryProducts[i] = new FactoryProduct(-1);
        }
        this.count = 0;
        this.end = 0;
    }

    public FactoryProduct[] getFactoryProducts() {
        return factoryProducts;
    }

    public int getEnd() {
        return end;
    }
}
