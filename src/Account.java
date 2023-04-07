import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public abstract class Account {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private List<Notification> notifications;

    public Account(String username, String password, String email, String phone, String address) {
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setPhone(phone);
        this.address = address;
        this.notifications = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (!is_valid_username(username)) {
            throw new IllegalArgumentException(
                    "Username may contain letters and digits and be at least 3 and at most 20 characters long");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (!is_valid_password(password)) {
            throw new IllegalArgumentException(
                    "Password must include at least one letter one digit and one special character(@$!%*#?&)");
        }
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!is_valid_email(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (!is_valid_phone_number(phone)) {
            throw new IllegalArgumentException("Invalid phone number.Phone number must have 11 digit");
        }
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private boolean is_valid_username(String username) {
        String regex = "^[a-zA-Z0-9_]{3,20}$";
        return Pattern.compile(regex).matcher(username).matches();
    }

    private boolean is_valid_password(String password) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$";
        return Pattern.compile(regex).matcher(password).matches();
    }

    private boolean is_valid_email(String email) {
        String regex = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return Pattern.compile(regex).matcher(email).matches();
    }

    private boolean is_valid_phone_number(String phone) {
        String regex = "^\\+98\\d{10}$|^0\\d{10}$";
        return Pattern.compile(regex).matcher(phone).matches();
    }

    public void add_notification(String text){
        notifications.add(new Notification(text));
    }

    public void display_notification(){
        for (Notification notification : notifications){
            if (!notification.isViewed()) {
                System.out.println(notification);
                notification.setViewed();
            }
        }
    }
}
