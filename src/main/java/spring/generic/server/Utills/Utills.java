package spring.generic.server.Utills;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import spring.generic.server.MongoDB.User;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by gadiel on 14/10/2016.
 */
public class Utills {

    public static String createActivationKey() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String IV = dateFormat.format(date);
            Random rand = new Random();
            int n = rand.nextInt(500) + 1;
            String salt = String.valueOf(n);
            String password = IV.concat(salt);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

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
        String body = Constants.PREFIX_PATH+"/"+Constants.USER+"/activateAccount/" + user.getActivationKey();
        sendGenericEmail(receiver, title, wrapWithHTML(body));
    }

    public static void sendForgotPasswordEmail(User user){
        String receiver=user.getEmail();
        String title = "Change password";
        String body = Constants.PREFIX_PATH+"/"+Constants.USER+"/changePassword/" + user.getActivationKey();
        sendGenericEmail(receiver, title, wrapWithHTML(body));
    }

    private static String wrapWithHTML(String cleanLink){
        return "<a href='"+cleanLink+"'>Link</a>";
    }
}
