import org.lexize.jpk.JPK;
import org.lexize.jpk.models.JPKSystemModel;

public class Test {
    public static void main(String[] args) throws Exception {
        JPK jpk = new JPK("no token?");
        var accessor = jpk.getSystemsAccessor();
        var autoproxy = accessor.GetSystemAutoproxySettings("no id?");
        System.out.println(autoproxy.AutoproxyMode);
        System.out.println(autoproxy.AutoproxyMember);
    }
}
