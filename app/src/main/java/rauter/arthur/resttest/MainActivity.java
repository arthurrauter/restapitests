package rauter.arthur.resttest;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity {

    ArrayList<Commentary> comments;
    private static final String URL = "http://jsonplaceholder.typicode.com/posts/6/comments";

    class Verbindung extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            parseJSON(s, comments);
        }

        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0];
            InputStream in;
            String result = null;
            try{
                java.net.URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
                result = IOUtils.toString(in, "UTF-8");

            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comments = new ArrayList<>();

        new Verbindung().execute(URL);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
