import org.opencv.core.Core;

public class Main {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        GithubAvatarGenerator githubAvatarGenerator = new GithubAvatarGenerator();
        for (int i = 1; i <= 20; i ++) {
            githubAvatarGenerator.getARandomAvatar(String.valueOf(i) + ".jpg");
        }
    }
}
