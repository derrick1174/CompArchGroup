public class CacheObject {

    int value; //dirt or valid bit
    String data;
    String tag;

    public void setValue(int value) {
        this.value = value;
    }

    public void setData(String data){
        this.data = data;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getValue() {
        return value;
    }

    public String getData() {
        return data;
    }

    public String getTag() {
        return tag;
    }

}
