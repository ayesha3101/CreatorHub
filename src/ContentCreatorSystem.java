package iseProject;
import java.io.File;
import java.util.Scanner;

public class ContentCreatorSystem {
    private Scanner scanner;
    private UserManager userManager;
    private AdminManager adminManager;
    private ContentManager contentManager;
    private UImanager uimanager;

    public ContentCreatorSystem() {
        this.scanner = new Scanner(System.in);
        userManager = new UserManager();
        adminManager = new AdminManager(userManager); // pass it here
        userManager.setAdminManager(adminManager);
        this.contentManager = new ContentManager();
        uimanager = new UImanager(userManager,adminManager);

    }

}