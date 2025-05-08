public class UserProfile {
    private String username;
    private String email;
    private int age;
    private double weight;
    private String gender;
    private String bedtime;
    private int totalSleepHours;

    //User profile constructor
    public UserProfile(String username, String email, int age, double weight, String gender, String bedtime, int totalSleepHours) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.weight = weight;
        this.gender = gender;
        this.bedtime = bedtime;
        this.totalSleepHours = totalSleepHours;
    }

    //Getters to return user informaiton
    public String getUsername() {return username;}
    public String getEmail() {return email;}
    public int getAge() {return age;}
    public double getWeight() {return weight;}
    public String getGender() {return gender;}
    public String getBedtime() {return bedtime;}
    public int getTotalSleepHours() {return totalSleepHours;}
}
