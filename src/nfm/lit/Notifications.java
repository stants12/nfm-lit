package nfm.lit;
import java.util.List;
import java.util.ArrayList;

public class Notifications {
    private static List<String> notifications = new ArrayList<>();

    public static void addNotification(String notification) {
        notifications.add(notification);
    }

    public static List<String> getNotifications() {
        return notifications;
    }

    public static void clearNotifications() {
        notifications.clear();
    }
}
