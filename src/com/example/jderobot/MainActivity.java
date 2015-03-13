package com.example.jderobot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class MainActivity extends ActionBarActivity 
{
	ListView listView ;
	SwipeRefreshLayout swipeLayout;
	ProgressDialog pd;
	final String URL="http://quiet-stone-2094.herokuapp.com/transactions.json";
	ArrayList<Product> listSKU=new ArrayList<Product>();
	ProgressBar ListProgressBar;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);	
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.material_toolbar);
		toolbar.setTitle("SKU");
		setSupportActionBar(toolbar);
		
		new HttpAsyncTask().execute(URL);

		
		listView = (ListView) findViewById(R.id.list);
		ListProgressBar=(ProgressBar)findViewById(R.id.List_progress_bar);
		
		 listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		 {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,int position,long id) 
	            {
	            	Product selectedSKU=listSKU.get(position);
	            	Gson gson=new Gson();
	            	String selectedSKUjson=gson.toJson(selectedSKU);
	            	
	            	Bundle bundle = new Bundle();
	            	bundle.putString("SelectedSKUjson",selectedSKUjson);
	            	
	            	Intent singleSKU=new Intent("com.example.jderobot.SingleSKU");
	            	singleSKU.putExtras(bundle);
	            	startActivity(singleSKU);
	            }
	        });
        
    }

    
    private static String convertInputStreamToString(InputStream inputStream) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }
    
    public static String GET(String url)
    {
        InputStream inputStream = null;
        String result = "";
        try {
 
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            
            HttpGet get = new HttpGet(url);
            
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(get);
 
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } 
        catch (Exception e) 
        {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        return result;
    }
    
	public void setlayout()
	{
		String skunamelist[]=getSKUList();
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
			        android.R.layout.simple_list_item_1,skunamelist);
		listView.setAdapter(adapter);
	}
	
	 private class HttpAsyncTask extends AsyncTask<String, Void,String> 
	    {
				@Override
		        protected String doInBackground(String... urls) 
		        {
		            return GET(urls[0]);
		        }
		        // onPostExecute displays the results of the AsyncTask.
		        @Override
		        protected void onPostExecute(String result) 
		        {
		             JSONArray jsonArr;
						try 
						{
							  jsonArr = new JSONArray(result);
							  for (int i = 0; i < jsonArr.length(); i++) 
					             {
									JSONObject jsonObj = jsonArr.getJSONObject(i);
									Gson gson=new Gson();
							        Product arr = gson.fromJson(jsonObj.toString(),Product.class);      
							        listSKU.add(arr);

					             }
						} 
						catch (JSONException e1) 
						{
							e1.printStackTrace();
						}

//						Log.d("result",listSKU.toString());
						setlayout();
						ListProgressBar.setVisibility(View.GONE);
		        }
	       }
	 
	 public String[] getSKUList()
	 {
		 ArrayList<String> skunamelist=new ArrayList<String>();
		 for(Product s:listSKU)
		 {
			 String singleSkuString=s.getSku();
			 skunamelist.add(singleSkuString);
		 }
		 String[] arr = skunamelist.toArray(new String[skunamelist.size()]);
		 return arr;
	 }
	 
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        int id = item.getItemId();
	        if (id == R.id.action_settings) {
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
}
