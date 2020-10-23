package cn.com.cworks.fixedmsg;

import java.util.Arrays;
import java.util.LinkedList;

public abstract class Msg {
    private final LinkedList<MsgField> msgFields;

    public Msg(MsgField[] msgFields) {
        this.msgFields = new LinkedList<>();
        this.msgFields.addAll(Arrays.asList(msgFields));
    }

    public String pack(String charsetName) {
        int length = this.getLength();

        return null;
    }

    private int getLength() {
        int i = 0;
        for (MsgField msgField : msgFields) {
            i += msgField.getLength();
        }
        return i;
    }
}
