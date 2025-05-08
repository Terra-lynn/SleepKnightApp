public class CurrentUser {
    private static String currentUserName = null;

    //Getter to retrieve the current user
    public static String getCurrentUserName() {
        return currentUserName;
    }

    public static void setCurrentUserName(String username) {
        currentUserName = username;
    }

    //Checking if user is logged in
    public static boolean isLoggedIn() {
        return currentUserName != null;
    }

    //Logging out the current user
    public static void logOut() {
        currentUserName = null;
    }
}
