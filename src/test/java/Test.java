import org.lexize.jpk.JPK;
import org.lexize.jpk.models.JPKSystemModel;

public class Test {
    public static void main(String[] args) throws Exception {
        JPK jpk = new JPK("no token?");
        JPKSystemModel model = jpk.getSystemsAccessor().GetSystem("no id?");
        System.out.println(model.Id);
        System.out.println(model.Name);
    }
}
