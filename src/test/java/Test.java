import org.lexize.jpk.JPK;
import org.lexize.jpk.models.JPKSystemModel;

public class Test {
    public static void main(String[] args) throws Exception {
        JPK jpk = new JPK("no token?");
        var accessor = jpk.getSystemsAccessor();
        JPKSystemModel model = accessor.GetSystem("no id?");
        //System.out.println(model.Id);
        //System.out.println(model.Name);
        model.Name = "New name";
        //System.out.println("Trying to update system");
        JPKSystemModel newModel = accessor.UpdateSystem("no id?", model);
        //System.out.println("Succesfully updated.");
        //System.out.println(newModel.Id);
        //System.out.println(newModel.Name);
    }
}
