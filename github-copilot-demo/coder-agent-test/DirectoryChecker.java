import java.io.File;

/**
 * DirectoryChecker
 *
 * A simple command-line utility that accepts a single file path as an argument
 * and reports whether that path is a directory, a regular file, or does not exist.
 *
 * Usage:
 *   java DirectoryChecker <path>
 */
public class DirectoryChecker {

    public static void main(String[] args) {
        // No argument provided -> print usage and exit.
        if (args.length == 0) {
            System.out.println("Usage: java DirectoryChecker <path>");
            System.out.println("Example: java DirectoryChecker /home/user/documents");
            return;
        }

        String path = args[0];
        File file = new File(path);

        // Path does not exist -> report gracefully and exit.
        if (!file.exists()) {
            System.out.println("The path does not exist: " + path);
            return;
        }

        // Path exists -> report whether it is a directory or a file.
        if (file.isDirectory()) {
            System.out.println("The path is a directory: " + path);
        } else if (file.isFile()) {
            System.out.println("The path is a file: " + path);
        } else {
            System.out.println("The path exists but is neither a regular file nor a directory: " + path);
        }
    }
}