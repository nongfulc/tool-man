package cn.com.cworks.fixedmsg;

public class TestMsg extends Msg {

    public TestMsg(MsgField[] msgFields) {
        super(msgFields);
    }

    static {
        MsgField[] msgFields = new MsgField[]{
                new MsgField("trxSeqNo", 31, ' ', MsgField.FillSide.LEFT, String.class, "123456789"),
                new MsgField("name", 10, ' ', MsgField.FillSide.LEFT, String.class, "bob")
        };
    }




}
