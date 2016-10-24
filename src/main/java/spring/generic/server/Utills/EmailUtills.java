package spring.generic.server.Utills;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import spring.generic.server.MongoDB.User.User;

/**
 * Created by gadiel on 14/10/2016.
 */
public class EmailUtills {

    public static void sendGenericEmail(String receiver, String title, String body)
    {
        Resource r=new ClassPathResource("mail-bean.xml");
        BeanFactory b=new XmlBeanFactory(r);
        MailClient m=(MailClient)b.getBean("mailClient");
        String sender="sendergmailid@gmail.com";
        m.sendMail(sender,receiver,title,body);
    }

    public static void sendConfirmationEmail(User user){
        String receiver=user.getEmail();
        String title = "Signup successfully";
        String body = Params.UI.PREFIX_PATH+"/"+"/activateAccount/" + user.getActivationKey();
        sendGenericEmail(receiver, title, wrapWithHREF(body));
    }

    public static void sendForgotPasswordEmail(User user){
        String receiver=user.getEmail();
        String title = "Change your password";
        String link = Params.UI.PREFIX_PATH+"/"+ Params.UI.FORGOT_PASSWORD +"?key="+ user.getActivationKey();
        String body = "Did you forget your password? "+getBR()+"Use this "+wrapWithHREF(link);
        sendGenericEmail(receiver, title, body);
    }

    private static String wrapWithHREF(String cleanLink){
        return "<a href='"+cleanLink+"'>Link</a>";
    }

    private static String getBR(){
        return "<br/>";
    }
}
