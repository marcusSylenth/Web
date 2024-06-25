
public class UserProfile {
    private String userId;
    private String userName;
    private String gender;
    private String contactNo;
    private String id;
    private int age;
    private String address;
    
    public UserProfile(String userId, String userName, String gender, String contactNo, String id, int age, String address) {
        this.userId = userId;
        this.userName = userName;
        this.gender = gender;
        this.contactNo = contactNo;
        this.id = id;
        this.age = age;
        this.address = address;
    }

    UserProfile() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
