import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Shop shop = new Shop("HOOMAN", "HoomanParsaei.com", "09912146878");

    public static void main(String[] args) {
        shop.setup();
//        load from disc
        main_menu();
//        save to disc
    }


    public static void main_menu() {
        while (true) {
            System.out.println("-------------HELLO--------------");
            System.out.println("-----WELCOME TO HOOMAN SHOP-----");
            System.out.println("-------Choose One Of Them-------");
            System.out.println("------------1)SignUp------------");
            System.out.println("------------2)Login-------------");

            int choice1 = scanner.nextInt();
            scanner.nextLine();
            switch (choice1) {
                case 1 -> {
                    System.out.println("-------Choose One Of Them-------");
                    System.out.println("-------------1)USER-------------");
                    System.out.println("-------------2)SELLER------------");
                    int choice2 = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice2) {
                        case 1 -> {
                            signup_user();
                        }
                        case 2 -> {
                            signup_seller();
                        }
                    }
                }
                case 2 -> {
                    System.out.println("-------Choose One Of Them-------");
                    System.out.println("-------------1)USER-------------");
                    System.out.println("-------------2)SELLER------------");
                    System.out.println("-------------3)ADMIN-------------");
                    int choice2 = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice2) {
                        case 1 -> {
                            shop.user_menu(login_user());
                        }
                        case 2 -> {
                            shop.seller_menu(login_seller());
                        }
                        case 3 -> {
                            shop.admin_menu(login_admin());
                        }
                    }
                }
            }
        }
    }
    public static void signup_user(){
        System.out.println("PLS Enter Username");
        String username = scanner.nextLine();
        for (User user : shop.getUsers()) {
            if (user.getUsername().equals(username)) {
                System.out.println("This Username Is Already Taken");
                return;
            }
        }
                System.out.println("PLS Enter Password");
                String password = scanner.nextLine();
                System.out.println("PLS Enter Email Address");
                String email = scanner.nextLine();
        for (User user : shop.getUsers()) {
            if (user.getEmail().equals(email)) {
                System.out.println("This Email Is Already Taken");
                return;
            }
        }
        System.out.println("PLS Enter Phone Number");
        String phone = scanner.nextLine();
        System.out.println("PLS Enter Address");
        String address = scanner.nextLine();
        System.out.println("PLS Enter Your Money");
        int money = scanner.nextInt();
        try {
            User new_user = new User(username,password,email,phone,address,money);
            shop.add_user(new_user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void signup_seller(){
            System.out.println("PLS Enter Username");
            String username = scanner.nextLine();
            for (Seller seller : shop.getSellers()) {
                if (seller.getUsername().equals(username)) {
                    System.out.println("This Username Is Already Taken");
                    return;
                }
            }
            System.out.println("PLS Enter Password");
            String password = scanner.nextLine();
            System.out.println("PLS Enter Email Address");
            String email = scanner.nextLine();
            for (Seller seller : shop.getSellers()) {
                if (seller.getEmail().equals(email)) {
                    System.out.println("This Email Is Already Taken");
                    return;
                }
            }
            System.out.println("PLS Enter Phone Number");
            String phone = scanner.nextLine();
            System.out.println("PLS Enter Address");
            String address = scanner.nextLine();
            try {
                Seller new_seller= new Seller(username,password,email,phone,address);
                shop.add_seller(new_seller);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

    }
    public static Admin login_admin() {
        System.out.println("PLS Enter Username");
        String username = scanner.nextLine();
        System.out.println("PLS Enter Password");
        String password = scanner.nextLine();

        for (Admin admin : shop.getAdmins()) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        return null;
    }

    public static Seller login_seller() {
        System.out.println("PLS Enter Username");
        String username = scanner.nextLine();
        System.out.println("PLS Enter Password");
        String password = scanner.nextLine();

        for (Seller seller : shop.getSellers()) {
            if (seller.getUsername().equals(username) && seller.getPassword().equals(password)) {
                return seller;
            }
        }
        return null;
    }

    public static User login_user() {
        System.out.println("PLS Enter Username");
        String username = scanner.nextLine();
        System.out.println("PLS Enter Password");
        String password = scanner.nextLine();

        for (User user : shop.getUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
