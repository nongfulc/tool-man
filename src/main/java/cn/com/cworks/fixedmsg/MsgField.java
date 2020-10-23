package cn.com.cworks.fixedmsg;

public class MsgField {
    /**
     * 填充位置
     */
    public enum FillSide {
        LEFT, RIGHT
    }

    /**
     * 域名称
     */
    private String name;
    /**
     * 长度
     */
    private int length;
    /**
     * 填充字符
     */
    private char fillChar;
    /**
     * 填充位置
     */
    private FillSide fillSide;
    /**
     * 类型
     */
    private Class type;

    /**
     * 值
     */
    private String value;


    public MsgField(String name, int length, char fillChar, FillSide fillSide, Class type, String value) {
        this.name = name;
        this.length = length;
        this.fillChar = fillChar;
        this.fillSide = fillSide;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public char getFillChar() {
        return fillChar;
    }

    public void setFillChar(char fillChar) {
        this.fillChar = fillChar;
    }

    public FillSide getFillSide() {
        return fillSide;
    }

    public void setFillSide(FillSide fillSide) {
        this.fillSide = fillSide;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
