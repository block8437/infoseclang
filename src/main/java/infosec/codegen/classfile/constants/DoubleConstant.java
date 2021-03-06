package infosec.codegen.classfile.constants;

import java.nio.ByteBuffer;

public class DoubleConstant extends Constant {
    private double value;

    public DoubleConstant(double value) {
        this.value = value;
        this.tag_id = 6;
    }

    public byte[] toBytes() {
        byte[] tmp = ByteBuffer.allocate(8).putDouble(value).array();
        byte[] out = new byte[9];

        out[0] = (byte) this.tag_id;
        out[1] = tmp[0];
        out[2] = tmp[1];
        out[3] = tmp[2];
        out[4] = tmp[3];
        out[5] = tmp[4];
        out[6] = tmp[5];
        out[7] = tmp[6];
        out[8] = tmp[7];

        return out;
    }

    public String toString() {
        return "DoubleConstant<" + value + ">";
    }
}
