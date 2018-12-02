public class TwoWayObject {

    int value; //dirty or valid bit
    String tag;

    public void setValue(int value) {
        this.value = value;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getValue() {
        return value;
    }

    public String getTag() {
        return tag;
    }

    int value2; //dirty or valid bit
    String tag2;

    public void setValue2(int value) {
        this.value2 = value;
    }

    public void setTag2(String tag) {
        this.tag2 = tag;
    }

    public int getValue2() {
        return value2;
    }

    public String getTag2() {
        return tag2;
    }
}

