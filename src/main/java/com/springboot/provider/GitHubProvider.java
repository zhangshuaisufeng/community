package com.springboot.provider;

import com.alibaba.fastjson.JSON;
import com.springboot.dto.AccessToken;
import com.springboot.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {
    //获取gitHub返回的token
    public String getAccessToken(AccessToken accessToken){
        MediaType mediaType= MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessToken));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String str=response.body().string();
                System.out.println(str);
                String token=str.split("&")[0].split("=")[1];
                return token;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    //通过token获取用户信息
    public GitHubUser getGitHubUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+accessToken)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String string=response.body().string();
                GitHubUser gitHubUser= JSON.parseObject(string,GitHubUser.class);
                return gitHubUser;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }
}
