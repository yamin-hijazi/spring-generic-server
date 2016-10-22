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
        String body = Params.URI.PREFIX_PATH+"/"+ Params.URI.USER+"/activateAccount/" + user.getActivationKey();
        sendGenericEmail(receiver, title, wrapWithHTML(body));
    }

    public static void sendForgotPasswordEmail(User user){
        String receiver=user.getEmail();
        String title = "Change password";
        String body = Params.URI.PREFIX_PATH+"/"+ Params.URI.USER+"/changePassword/" + user.getActivationKey();
        sendGenericEmail(receiver, title, wrapWithHTML(body));
    }

    private static String wrapWithHTML(String cleanLink){
        return "<a href='"+cleanLink+"'>Link</a>";
    }
}
