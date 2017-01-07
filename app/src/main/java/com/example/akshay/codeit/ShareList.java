package com.example.akshay.codeit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ShareList extends Fragment {

    String TAG="Bla";
    View rootView;
    ArrayList<ListObject> arrayList;
    public ShareList(){}
    LinearLayoutManager linearLayoutManager;
    ShareListRecyclerAdapter sharelistRecyclerAdapter;
    RecyclerView recyclerView;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_share_list, container, false);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerView);

        String[] categories = getResources().getStringArray(R.array.sharelist);
        int[] imageIcons = {R.drawable.ic_favorite,R.drawable.ic_favorite,R.drawable.ic_favorite,R.drawable.ic_favorite,R.drawable.ic_favorite};

        arrayList = new ArrayList<>();

        for (int i = 0; i < categories.length; i++) {
//            arrayList.add(new ListObject(categories[i],imageIcons[i]));

        }

        linearLayoutManager = new LinearLayoutManager(getContext());
        sharelistRecyclerAdapter = new ShareListRecyclerAdapter(getContext(), arrayList);
        recyclerView.setAdapter(sharelistRecyclerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        new GetStocks().execute();
        return rootView;
    }

        private class GetStocks extends AsyncTask<Void, Void, Void> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
               // Toast.makeText(ShareList.this,"Json Data",Toast.LENGTH_LONG).show();

            }

            @Override
            protected Void doInBackground(Void... arg0) {
                HttpHandler sh = new HttpHandler();
                // Making a request to url and getting response
                String url = "https://www.quandl.com/api/v3/datasets.json?query=&database_code=NSE&page=1";
                String jsonStr = sh.makeServiceCall(url);

                Log.e(TAG, "Response from url: " + jsonStr);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        // Getting JSON Array node
                        JSONArray stocks = jsonObj.getJSONArray("datasets");

                        // looping through All stocks
                        for (int i = 0; i < stocks.length(); i++) {
                            JSONObject c = stocks.getJSONObject(i);

                            String name = c.getString("name");
                            String datasetcode = c.getString("dataset_code");
                                      // tmp hash map for single stock
                                                        // adding stock to stock list
                            //listview here
                            arrayList.add(new ListObject(datasetcode
                            ,R.drawable.ic_favorite));
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());


                    }

                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                                    }

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

            }
        }
        }





/*






































        protected Void doInBackground(Void... voids) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("https://www.quandl.com/api/v3/datasets.json?query=hdfc&database_code=NSE&page=1");
            JSONObject jsonObject= new JSONObject(httpget);
            try {

                JSONArray jsonArray= new JSONArray();
               for(int i=0;i<jsonArray.length();i++){
                   String array=jsonArray.getJSONArray(i).getString(i);
                   Log.d("dataset",httpget.toString());

               }



                //  arrayList.add(new ListObject("input", jsonobj.toString()));

//                Log.e("mainToPost", "mainToPost" + nameValuePairs.toString());

                // Use UrlEncodedFormEntity to send in proper format which we need
//                HttpEntity e = new StringEntity(jsonobj.toString());
//                httpget.setEntity(e);
//
//                httpget.addHeader("Content-Type","application/json");
//                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httpget);
                InputStream inputStream = response.getEntity().getContent();
                InputStreamToStringExample str = new InputStreamToStringExample();
                String responseServer = str.getStringFromInputStream(inputStream);
                responseServer="["+responseServer+"]";
                JSONArray arr = new JSONArray(responseServer);
                JSONObject jObj = arr.getJSONObject(0);


                //JSONObject read=jsonobj.getJSONObject(response.toString());
                //String time = read.getString("time");

                Log.e("response", "response -----" + responseServer);




            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            // Log.d("result before sending", Arrays.toString(result1));




            //input.setText(responseServer);
        }
    }

    public static class InputStreamToStringExample {

        public static void main(String[] args) throws IOException {

            // intilize an InputStream
            InputStream is =
                    new ByteArrayInputStream("file content..blah blah".getBytes());

            String result = getStringFromInputStream(is);

            System.out.println(result);
            System.out.println("Done");

        }

        // convert InputStream to String
        private static String getStringFromInputStream(InputStream is) {

            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            String line;
            try {

                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return sb.toString();
        }
    }



}
*/
