package com.udacity.sandwichclub.utils;
import android.util.Log;
import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
//region Description of abreviations
/*
p==pic/image	t==array/tensor
k==aka		    u==URL
n==name		    m==mainName
g==ingredients	v==value
o==placeOfOrigin e==error
d==description	s==sandwich
		        x==data
*/
//endregion o o
// To Xray JSON used output in https://jsonformatter.curiousconcept.com/
public class JsonUtils {
    public static Sandwich parseSandwichJson(String json)throws JSONException{
        JSONObject SX = new JSONObject(json);
        JSONObject N = SX.getJSONObject("name");
        String M = N.getString("mainName");
        List<String> K = new ArrayList<>();
        JSONArray KT = N.getJSONArray("alsoKnownAs");
        if (KT.length() == 0){
            K.add("_");
        }else{
            for (int i=0; i<KT.length(); i++){
                K.add(KT.getString(i));}}
        String O = SX.getString("placeOfOrigin");
        if (O.equals("")){O = "_";}
        String D = SX.getString("description");
        String P = SX.getString("image");
        List<String> G = new ArrayList<>();
        JSONArray GT = SX.getJSONArray("ingredients");
        for (int i = 0; i < GT.length(); i++){
            G.add(GT.getString(i));}
//region logv
int logv=1; if (logv>0) {
    Log.v("parseSandwichJson","***json in\n");
    Log.v("parseSandwichJson",json);
}
//endregion
        return new Sandwich(M,K,O,D,P,G);
}}
