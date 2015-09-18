package rauter.arthur.resttest;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Arthur on 18.09.2015.
 */
public class Verbindung extends AsyncTask<String, Void, String> {

    String url;
    ArrayList<Commentary> comments;

    public Verbindung(String url, ArrayList<Commentary> comments) {
        this.url = url;
        this.comments = comments;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //parseJSON(s, comments);
    }

    @Override
    protected String doInBackground(String... params) {
        return requestContent(url);
    }

    public String requestContent(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        String result = null;
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = null;
        InputStream inputStream = null;

        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                inputStream = entity.getContent();
                result = convertStreamToString(inputStream);
            }
        }
        catch (Exception e) {}
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception exc) {

                }
            }
        }

        return result;
    }

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }

        return sb.toString();
    }

    public void parseJSON(String string, ArrayList<Commentary> comments) {
        try{
            JSONObject json = new JSONObject(string);
            JSONObject dataObject = json.getJSONObject("data");
            JSONArray items = dataObject.getJSONArray("items");

            for(int i=0; i<items.length(); i++) {
                JSONObject commentObject = items.getJSONObject(i);
                Commentary comment = new Commentary(
                        commentObject.getString("postID"),
                        commentObject.getString("id"),
                        commentObject.getString("name"),
                        commentObject.getString("email"),
                        commentObject.getString("body"));
                comments.add(comment);
            }
        }
        catch (JSONException e) {}

    }

}
