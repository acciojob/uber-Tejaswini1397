import java.util.*;
@Entity

public class Admin {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int adminId;
    String username;
    String password;

    public Admin() {
    }

    public Admin(int adminId, String username, String password) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
    }
}