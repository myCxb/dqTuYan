package com.dqtuyan.community.provider;

import com.alibaba.fastjson.JSON;
import com.dqtuyan.community.dto.AccessTokenDto;
import com.dqtuyan.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDto accessTokenDto){
        MediaType Json = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        String url = "https://github.com/login/oauth/access_token";

        RequestBody body = RequestBody.create(Json , JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String text = response.body().string();
            System.out.println(text);
            String [] split = text.split("&");
            String [] split2 = split[0].split("=");
            return split2[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String token){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + token)
                .build();
        try  {
            Response response = client.newCall(request).execute();
            String user = request.body().toString();
            GithubUser githubUser = JSON.parseObject(user , GithubUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
