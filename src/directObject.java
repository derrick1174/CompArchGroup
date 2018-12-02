public class directObject {

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

}
