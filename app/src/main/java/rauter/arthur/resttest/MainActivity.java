package rauter.arthur.resttest;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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


    private static final String URL = "http://jsonplaceholder.typicode.com/posts/6/comments";

    class Verbindung extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList<Commentary> comments = new ArrayList<>();
            parseJSON(s, comments);
            TextView hello = (TextView) findViewById(R.id.hello);
            hello.setText("I got all the way here!");
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
                JSONArray jsonArray = new JSONArray(string);

                for(int i=0; i<jsonArray.length(); i++) {
                    JSONObject commentObject = jsonArray.getJSONObject(i);
                    Commentary comment = new Commentary(
                            commentObject.getString("postId"),
                            commentObject.getString("id"),
                            commentObject.getString("name"),
                            commentObject.getString("email"),
                            commentObject.getString("body"));
                    comments.add(comment);
                }
            }
            catch (JSONException e) {
                Log.d("arthur", "JSON failed");
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Verbindung gettingData = (Verbindung) new Verbindung().execute(URL);
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

/*
* [
  {
    "postId": 6,
    "id": 26,
    "name": "in deleniti sunt provident soluta ratione veniam quam praesentium",
    "email": "Russel.Parker@kameron.io",
    "body": "incidunt sapiente eaque dolor eos\nad est molestias\nquas sit et nihil exercitationem at cumque ullam\nnihil magnam et"
  },
  {
    "postId": 6,
    "id": 27,
    "name": "doloribus quibusdam molestiae amet illum",
    "email": "Francesco.Gleason@nella.us",
    "body": "nisi vel quas ut laborum ratione\nrerum magni eum\nunde et voluptatem saepe\nvoluptas corporis modi amet ipsam eos saepe porro"
  },
  {
    "postId": 6,
    "id": 28,
    "name": "quo voluptates voluptas nisi veritatis dignissimos dolores ut officiis",
    "email": "Ronny@rosina.org",
    "body": "voluptatem repellendus quo alias at laudantium\nmollitia quidem esse\ntemporibus consequuntur vitae rerum illum\nid corporis sit id"
  },
  {
    "postId": 6,
    "id": 29,
    "name": "eum distinctio amet dolor",
    "email": "Jennings_Pouros@erica.biz",
    "body": "tempora voluptatem est\nmagnam distinctio autem est dolorem\net ipsa molestiae odit rerum itaque corporis nihil nam\neaque rerum error"
  },
  {
    "postId": 6,
    "id": 30,
    "name": "quasi nulla ducimus facilis non voluptas aut",
    "email": "Lurline@marvin.biz",
    "body": "consequuntur quia voluptate assumenda et\nautem voluptatem reiciendis ipsum animi est provident\nearum aperiam sapiente ad vitae iste\naccusantium aperiam eius qui dolore voluptatem et"
  }
]
*
*
* */


