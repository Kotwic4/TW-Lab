package lab3;

import lab1.IBuffer;

public class BoundedBufferString extends BoundedBuffer<String> implements IBuffer{

    public BoundedBufferString(int size) {
        super(size);
    }
}
