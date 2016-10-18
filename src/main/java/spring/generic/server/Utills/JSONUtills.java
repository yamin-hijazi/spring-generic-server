package spring.generic.server.Utills;


import org.json.JSONObject;

/**
 * Created by gadiel on 14/10/2016.
 */
public class JSONUtills {

    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";

    public static String getSuccessJSON(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",SUCCESS);
        return jsonObject.toString();
    }

    public static String getFailedJSON(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",FAILED);
        return jsonObject.toString();
    }

    public static String getBadCredentialsJSON(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",FAILED);
        jsonObject.put("reason","Wrong username and/or password");
        return jsonObject.toString();
    }

    public static String getCustomizedReasonJSON(String result ,String reason){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",result);
        jsonObject.put("reason",reason);
        return jsonObject.toString();
    }

    public static String getExceptionJson(Exception e)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",FAILED);
        jsonObject.put("exception",e.toString());
        return jsonObject.toString();
    }
}
