import java.util.*;

public class Shop {
    Scanner scanner = new Scanner(System.in);
    private String name;
    private String web_address;
    private String phone_number;
    private List<User> users;
    private List<Admin> admins;
    private List<Seller> sellers;
    private HashMap<UUID, Product> products;
    private Seller current_seller;
    private User current_user;
    private Admin current_admin;
    private int wallet;

    public Shop(String name, String web_address, String phone_number) {
        this.name = name;
        this.web_address = web_address;
        this.phone_number = phone_number;
        this.users = new ArrayList<>();
        this.admins = new ArrayList<>();
        this.sellers = new ArrayList<>();
        this.current_seller = null;
        this.current_user = null;
        this.current_admin = null;
        this.wallet = 0;
    }

    public void add_product(Product product) {
        this.products.put(product.getUuid(), product);
    }

    public void view_user_profile(User user) {
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());
        System.out.println("Phone: " + user.getPhone());
        System.out.println("Address: " + user.getAddress());
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public List<Seller> getSellers() {
        return sellers;
    }

    public void add_user(User user){
        users.add(user);
    }

    public void add_seller(Seller seller){
        sellers.add(seller);
    }

    public HashMap<UUID, Integer> get_all_product() {
        for (Seller seller : sellers) {
            return seller.getInventory();
        }
        return null;
    }


    public void seller_menu(Seller seller) {
                this.current_seller = seller;
                System.out.println("You Logged In Successfully");
                System.out.println("-------Choose One Of Them-------");
                System.out.println("1)Add Product To Inventory");
                System.out.println("2)Check List Of Sold Product");
                System.out.println("3)Check List Of Inventory Product");
                System.out.println("4)Create New Product");
                System.out.println("5)Check Notfication");
                int choose = scanner.nextInt();
                switch (choose) {
                    case 1 -> {
                        Product selected_product = search_product((List<Product>) this.products.values());
                        System.out.println(selected_product.getName());
                        System.out.println("Enter Product Price");
                        int price = scanner.nextInt();
                        System.out.println("Enter Quantity");
                        int quantity = scanner.nextInt();
                        current_seller.add_to_inventory(selected_product, quantity);
                        current_seller.set_price(selected_product, price);
                    }
                    case 2 -> {
                        int i = 1;
                        for (Map.Entry<UUID, Integer> entry : current_seller.getSold_product_count().entrySet()) {
                            System.out.println(
                                    i + ") " + products.get(entry.getKey()).getName() + ": " + entry.getValue());
                            i += 1;
                        }
                    }
                    case 3 -> {
                        int i = 1;
                        for (Map.Entry<UUID,Integer> entry : current_seller.getInventory().entrySet()){
                            System.out.println(
                                    i + ") " + products.get(entry.getKey()).getName() + ": " + entry.getValue());
                            i += 1;
                        }
                    }
                    case 4 -> {
                        System.out.println("Enter Product Name");
                        String name = scanner.nextLine();
                        System.out.println("Enter Category");
                        String category_name = scanner.nextLine();
                        Category category = new Category(category_name);
                        Product new_product = new Product(name, category);
                        add_product(new_product);
                    }
                    case 5 -> {
                        seller.display_notification();
                    }
                }
    }


    public void user_menu(User user) {
                System.out.println("You Logged In Successfully");
                System.out.println("-------Choose One Of Them-------");
                System.out.println("1)Create Cart");
                System.out.println("2)Refund Your Order");
                System.out.println("3)Add Comment To Product");
                System.out.println("4)Add Rate To Product");
                System.out.println("5)See Your Request Order");
                System.out.println("6)See Product Rate");
                System.out.println("7)Check Notifications");
                int choose = scanner.nextInt();
                switch (choose) {
                    case 1 -> {
                        boolean choosing_product = true;
                        while (choosing_product){
                            Seller seller = search_seller();
                            List<Product> productList = new ArrayList<>();
                            for (UUID uuid : seller.getInventory().keySet()){
                                Product product = products.get(uuid);
                                productList.add(product);
                            }
                            Product product = search_product(productList);
                            System.out.println("PLS Enter Quantity");
                            int quantity = scanner.nextInt();
                            user.add_product_to_cart(seller,quantity,product);
                            System.out.println("Do You Want To Continue?");
                            System.out.println("1)Yes");
                            System.out.println("2)No");
                            int number = scanner.nextInt();
                            switch (number){
                                case 1 -> {
                                    choosing_product = true;
                                }
                                case 2 -> {
                                    choosing_product = false;
                                    user.add_to_request_order();
                                }
                            }
                        }
                    }
                    case 2 -> {
                        refund(user);
                    }
                    case 3 -> {
                        Order order = serach_order(user.getFinish_order());
                        System.out.println("PLS Enter Your Comment About This Product");
                        String comment = scanner.nextLine();
                        search_transaction(order).getProduct().add_comment(comment);
                    }
                    case 4 -> {
                        Order order = serach_order(user.getFinish_order());
                        search_transaction(order).getProduct().setRating(user);
                    }
                    case 5 -> {
                        user.toString();
                    }
                    case 6 -> {
                        Product product = search_product((List<Product>) products.values());
                        float view_rate = product.calculate_rating();
                        System.out.println(product.getName() + " Rates: " + view_rate);
                    }
                    case 7 -> {
                        user.display_notification();
                    }

                }
    }

    public void admin_menu(Admin admin) {
                System.out.println("You Logged In Successfully");
                System.out.println("-------Choose One Of Them-------");
                System.out.println("1) Check Request Order");
                System.out.println("2) View User Profile");
                System.out.println("3) Edit User Phone Number");
                System.out.println("4) Edit User Address");
                System.out.println("5) Add Admin");
                int choose = scanner.nextInt();
                switch (choose) {
                    case 1 -> {
                        checkout();
                    }
                    case 2 -> {
                        view_user_profile(search_user());
                    }
                    case 3 -> {
                        System.out.println("Enter New Phone Number");
                        String phone_number = scanner.nextLine();
                        search_user().setPhone(phone_number);
                    }
                    case 4 -> {
                        System.out.println("Enter New Phone Number");
                        String address = scanner.nextLine();
                        search_user().setAddress(address);
                    }
                    case 5 -> {
                        add_admin();
                    }
                }
            }



    public void add_admin() {
        System.out.println("Enter Admin Username");
        String username = scanner.nextLine();
        for (Admin admin : admins){
            if (admin.getUsername().equals(username)){
                System.out.println("This Username Is Already Taken");
                return;
            }
        }
        System.out.println("Enter Admin Password");
        String password = scanner.nextLine();
        System.out.println("Enter Admin Email");
        String email = scanner.nextLine();
        for (Admin admin : admins){
            if (admin.getEmail().equals(email)){
                System.out.println("This Email Is Already Taken");
                return;
            }
        }
        System.out.println("Enter Admin Phone Number");
        String phone = scanner.nextLine();
        System.out.println("Enter Admin Address");
        String address = scanner.nextLine();
        Admin new_admin = new Admin(username, password, email, phone, address);
        admins.add(new_admin);
    }

    private Transaction search_transaction(Order order){
        int i = 1;
        for(Transaction transaction : order.getTransactions()){
            System.out.println(i + ") " + transaction.getProduct().getName());
            i += 1;
        }
        System.out.println("PLS Choose One Transaction");
        int number = scanner.nextInt();
        if (1<=number && number<=order.getTransactions().size()){
            return order.getTransactions().get(number-1);
        }
        return null;
    }
    private Product search_product(List<Product> products) {
        while (true) {
            System.out.println("Enter Product Name");
            String query = scanner.nextLine();
            List<Product> matched_products = new ArrayList<>();
            for (Product product : products) {
                if (product.getName().contains(query)) {
                    matched_products.add(product);
                }
            }
            int i = 1;
            for (Product product : matched_products) {
                System.out.println(i + ") " + product.getName());
                i += 1;
            }
            System.out.println("Choose One Product");
            int num = scanner.nextInt();
            if (1<=num && num<=matched_products.size()){
                return matched_products.get(num - 1);
            }
        }
    }

    private User search_user() {
        while (true) {
            System.out.println("Enter username to search");
            String username = scanner.nextLine();

            List<User> potential_matches = new ArrayList<>();
            for (User selected_user : users) {
                if (selected_user.getUsername().contains(username)) {
                    potential_matches.add(selected_user);
                }
            }

            if (potential_matches.size() == 1) {
                return potential_matches.get(0);
            } else if (potential_matches.size() > 1) {
                int i = 0;
                for (User user : potential_matches) {
                    System.out.println(i + " - " + user.getUsername());
                    i++;
                }

                System.out.println("Enter User Number, or -1 to cancel");
                int user_number = scanner.nextInt();
                if (0 <= user_number && user_number < potential_matches.size()) {
                    return potential_matches.get(user_number);
                }
            }
        }
    }

    private Seller search_seller(){
        while (true) {
            System.out.println("Enter Shop to search");
            String username = scanner.nextLine();

            List<Seller> potential_matches = new ArrayList<>();
            for (Seller selected_seller : sellers) {
                if (selected_seller.getUsername().contains(username)) {
                    potential_matches.add(selected_seller);
                }
            }

            if (potential_matches.size() == 1) {
                return potential_matches.get(0);
            } else if (potential_matches.size() > 1) {
                int i = 0;
                for (Seller seller : potential_matches) {
                    System.out.println(i + " - " + seller.getUsername());
                    i++;
                }

                System.out.println("Enter Seller Number, or -1 to cancel");
                int seller_number = scanner.nextInt();
                if (0 <= seller_number && seller_number < potential_matches.size()) {
                    return potential_matches.get(seller_number);
                }
            }
        }
    }


    public Order serach_order(List<Order> orders) {
        int currently_selected = 0;
        while (true) {
            Order order = orders.get(currently_selected);
            order.display_summary(5);

            System.out.println("1) Select this order");
            System.out.println("2) Display more rows");
            System.out.println("3) Next one");

            int selected_option = scanner.nextInt();
            if (selected_option == 1) {
                return order;
            } else if (selected_option == 2) {
                order.display_summary(order.getTransactions().size());

                System.out.println("1) Select this order");
                System.out.println("2) Next one");
                if (scanner.nextInt() == 1) {
                    return order;
                } else {
                    selected_option += 1;
                }
            } else if (selected_option == 3) {
                selected_option += 1;
            }
        }
    }

    public boolean handle_transaction(User user , Transaction transaction){
        int price = transaction.get_transaction_cost();
        int balance_user = user.getBalance();
        if (balance_user >= price && transaction.getSeller().sell(transaction.getProduct(),transaction.getQuantity())){
            balance_user -= price;
            user.setBalance(balance_user);
            wallet += price*0.1;
            return true;
        }
        return false;
    }

    public void checkout(){
        User selected_user = search_user();
        Order selected_order = serach_order(selected_user.getRequest_order());
        for (Transaction transaction : selected_order.getTransactions()){
            if (handle_transaction(selected_user,transaction)){
                String notification_user = "You Have Received " + transaction.toString();
                selected_user.add_notification(notification_user);
                String notification_seller = "You Have Sold " + transaction.getProduct().getName() + "*" + transaction.getQuantity();
                transaction.getSeller().add_notification(notification_seller);
            }
            else {
                selected_user.add_notification(transaction + " Has Failed");
            }
        }
    }


    public Transaction refund_transaction(User user){
        Order refund_order = serach_order(user.getRequest_order());
        user.refund_order(refund_order);
        int i = 1;
        for (Transaction transaction : refund_order.getTransactions()){
            System.out.println(i + ") " + transaction.getProduct().getName());
            i += 1;
        }
        System.out.println("PLS Choose One Product");
        int number = scanner.nextInt();
        if (1<=number && number<=refund_order.getTransactions().size()){
            return refund_order.getTransactions().get(number-1);
        }
        else {
            return null;
        }
    }

    public void refund(User user){
        Transaction transaction = refund_transaction(user);
        transaction.getSeller().refund_to_seller(transaction);
    }

    public void setup(){
        Category tablet = new Category("Electronic.Tablet");
        Category laptop = new Category("Electronic.Laptop");
        Category phone = new Category("Electronic.Phone");
        Category console = new Category("Electronic.Console");
        Category toys = new Category("Toys");
        Category man_shoes = new Category("Clothes.Shoe.Man");
        Category women_shoes = new Category("Clothes.Shoe.Women");
        Category shirt = new Category("Clothes.Shirt");
        Category book = new Category("Book");
        Category sport_shoe = new Category("Sport.Shoe");
        Category sport_clothes = new Category("Sport.shirt");
        Category supermarket_fruit = new Category("Supermarket.Fruit");
        Category supermarket_snaks = new Category("Supermarket.Snaks");
        Product tablet_apple = new Product("IPAD",tablet);
//        products.put(tablet_apple.getUuid(),tablet_apple);
        Product tablet_samsung = new Product("Galaxy Tab" , tablet);
//        products.put(tablet_samsung.getUuid(),tablet_samsung);
        Product tablet_lenovo = new Product("TAB M7",tablet);
//        products.put(tablet_lenovo.getUuid(),tablet_lenovo);
        Product iphone14 = new Product("Iphone 14",phone);
//        products.put(iphone14.getUuid(),iphone14);
        Product iphone13 = new Product("Iphone 13",phone);
//        products.put(iphone13.getUuid(),iphone13);
        Product s22 = new Product("S22",phone);
//        products.put(s22.getUuid(),s22);
        Product s21 = new Product("S21",phone);
//        products.put(s21.getUuid(),s21);
        Product tuf_gaming = new Product("TUF gaming" , laptop);
//        products.put(tuf_gaming.getUuid(),tuf_gaming);
        Product mac = new Product("MAC",laptop);
//        products.put(mac.getUuid(),mac);
        Product ps5 = new Product("PS5",console);
//        products.put(ps5.getUuid(),ps5);
        Product ps4 = new Product("PS4",console);
//        products.put(ps4.getUuid(),ps4);
        Product xbox = new Product("XBOX One",console);
//        products.put(xbox.getUuid(),xbox);
        Product lego = new Product("Lego",toys);
//        products.put(lego.getUuid(),lego);
        Product wedding_man_shoes = new Product("Wedding Shoes",man_shoes);
//        products.put(wedding_man_shoes.getUuid(),wedding_man_shoes);
        Product outfit_men_shoes = new Product("Outfit Shoes",man_shoes);
//        products.put(outfit_men_shoes.getUuid(),outfit_men_shoes);
        Product high_hells = new Product("Hgh Hells",women_shoes);
//        products.put(high_hells.getUuid(),high_hells);
        Product sport_shirt = new Product("Sport Shirt",shirt);
//        products.put(sport_shirt.getUuid(),sport_shirt);
        Product lord_book = new Product("The Lord of the Rings",book);
//        products.put(lord_book.getUuid(),lord_book);
        Product lion_book = new Product("The Lion",book);
//        products.put(lion_book.getUuid(),lion_book);
        Product mountain_shoe = new Product("Mountain Shoe",sport_shoe);
//        products.put(mountain_shoe.getUuid(),mountain_shoe);
        Product apple = new Product("Apple",supermarket_fruit);
//        products.put(apple.getUuid(),apple);
        Product orange = new Product("Orange",supermarket_fruit);
//        products.put(orange.getUuid(),orange);
        Product chips = new Product("Chips",supermarket_snaks);
//        products.put(chips.getUuid(),chips);
        Product pofak = new Product("Poofak",supermarket_snaks);
//        products.put(pofak.getUuid(),pofak);
        Admin hooman = new Admin("Hooman","24Parsaei@","www.hoomanparsaei9@gmail.com","09129999999","Tehran,Ponak");
        admins.add(hooman);
        Seller mahdi = new Seller("Mahdi","khodabande80#","www.mehdikhodabande@gmail.com","09129999999","Rasht");
        sellers.add(mahdi);
        Seller farid = new Seller("Farid","Karimi83#","www.faridkarimi@gmail.com","09129999999","Tehran,Saadat Abad");
        sellers.add(farid);
        Seller kian = new Seller("Kian","AryaDoost84#","www.kianaryadoost@gmail.com","09129999999","Tabriz,Valiasr");
        sellers.add(kian);
        Seller mahla = new Seller("Mahla","Entezari82#","www.mahlaentezari@gmail.com","09129999999","Neyshabor");
        sellers.add(mahla);
        User shayan = new User("Shayan","Shahrabi82#","www.shayanshahrabi@gmail.com","09129999999","Tehran,Pasdaran");
        users.add(shayan);
        User arshia = new User("Arshia","aalaei82#","www.arshiaaalaei@gmail.com","09129999999","Ardabil");
        users.add(arshia);
//        User hasti = new User("Hasti","javadzade83#","www.hastijavadzade@gmailcom","09129999999","Ghaem Shahr");
        User amirhossein = new User("Amirhossein","Nazarnejad80#","www.amirhosseinnazarnejad@gmail.com","09129999999","Bandar Abbas");
        users.add(amirhossein);
        mahdi.add_to_inventory(iphone14,10);
        mahdi.add_to_inventory(iphone13,30);
        mahdi.add_to_inventory(tablet_apple,6);
        mahdi.add_to_inventory(tablet_samsung,2);
        mahdi.add_to_inventory(tablet_lenovo,9);
        mahla.add_to_inventory(iphone14,100);
        mahla.add_to_inventory(iphone13,24);
        mahla.add_to_inventory(lego,16);
        mahla.add_to_inventory(mac,21);
        mahla.add_to_inventory(high_hells,47);
        kian.add_to_inventory(ps5,100);
        kian.add_to_inventory(ps4,100);
        kian.add_to_inventory(xbox,21);
        kian.add_to_inventory(outfit_men_shoes,12);
        kian.add_to_inventory(sport_shirt,18);
        kian.add_to_inventory(mountain_shoe,19);
        kian.add_to_inventory(chips,23);
        farid.add_to_inventory(s22,11);
        farid.add_to_inventory(s21,36);
        farid.add_to_inventory(tuf_gaming,73);
        farid.add_to_inventory(wedding_man_shoes,5);
        farid.add_to_inventory(lord_book,1);
        farid.add_to_inventory(lion_book,16);
        farid.add_to_inventory(pofak,24);
    }
}
