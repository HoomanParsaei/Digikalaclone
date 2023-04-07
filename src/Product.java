import java.util.*;

public class Product {
    private String name;
    private List<String> comments;
    private Category category;
    private HashMap<String , Integer> rating;
    private UUID uuid;
    private User current_user;

    Scanner scanner = new Scanner(System.in);

    public Product(String name, Category category) {
        this.name = name;
        this.comments = new ArrayList<>();
        this.category = category;
        this.rating = new HashMap<>();
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getComments() {
        return comments;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void add_comment(String comment) {
        comments.add(comment);
    }

    public void setRating(User user){
        System.out.println("PLS Rate This Product From 1 To 5");
        int rate = scanner.nextInt();
        if (rate>=1 && rate<=5){
            rating.put(user.getUsername(), rate);
        }
        else {
            System.out.println("Your Rate Is Not Between 1 to 5");
        }
    }

    public float calculate_rating(){
        int sum = 0;
        for (Integer integer : rating.values()){
            sum += integer;
        }
        return sum/rating.size();
    }
}
