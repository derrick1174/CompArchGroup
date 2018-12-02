public class directObject {

    int value; //dirty or valid bit
    int tag;

    public void setValue(int value) {
        this.value = value;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getValue() {
        return value;
    }

    public int getTag() {
        return tag;
    }

}
