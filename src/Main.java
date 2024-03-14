import main.forms.RegistrationForm;
import main.User;
import main.forms.WelcomeForm;


public class Main {


    public static void main(String[] args) {
        WelcomeForm welcomeForm = new WelcomeForm(null);
        welcomeForm.setVisible(true);


        //RegistrationForm myform = new RegistrationForm(null);
//        User user = myform.user;
//        if(user != null)
//            System.out.println(user.getNume()+ user.getPrenume() + " s-a inregistrat cu succes!");
//        else
//            System.out.println("Inregistrarea a fost anulata!");
    }
}