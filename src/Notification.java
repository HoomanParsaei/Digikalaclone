import java.util.Date;
import java.util.List;

public class Notification {
    private String text;
    private Date date;
    private boolean viewed;

    public Notification(String text) {
        this.text = text;
        this.viewed = false;
    }

    public void setViewed(){
        viewed = true;
    }

    public boolean isViewed() {
        return viewed;
    }

    @Override
    public String toString() {
        return text + " : " + date;
    }
}
