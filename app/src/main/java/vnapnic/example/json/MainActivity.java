package vnapnic.example.json;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

    private TextView mText;
    private ImageView mImg;
    private static final String URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetJSONOject().execute();
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetDataFirstArray().execute();
            }
        });

        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    list = GetdataXMl();
                    for (int i=0;i<list.size();i++){
                        System.out.println("XMLLLLLL: " + list.get(i).getCompany());
                    }
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private class GetJSONOject extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                publishProgress(jsonArray().getJSONObject(0).optString("email").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mText.setText(values[0]);
        }
    }


    private class GetDataFirstArray extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                publishProgress(getFirstArray("http://mobiadzone.com/campaign/hainam1421").getJSONObject(0).optString("name").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            mText.setText(values[0]);
        }
    }

    //Json Array first
    private JSONArray getFirstArray(String url) {
        JSONArray array = null;
        try {
            URL openCon = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) openCon.openConnection();
            urlConnection.setRequestProperty("charset", "UTF-8");
            InputStream is = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);
            StringBuffer buffer = new StringBuffer();
            String strJson;
            while ((strJson = br.readLine()) != null) {
                buffer.append(strJson + "\n");
            }
            array = new JSONArray(buffer.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return array;
    }


    // JSOn Object
    private JSONObject dataJson(String url) {
        try {
            URL link = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) link.openConnection();
//            connection.setDoOutput(true);
//            connection.setDoInput(true);
//            connection.setInstanceFollowRedirects(false);
//            connection.setRequestProperty("Content-Type", "application/json");
//                /* optional request header */
//            connection.setRequestProperty("Accept", "application/json");
//                /* for Get request */
//            connection.setRequestMethod("GET");
////            int statusCode = connection.getResponseCode();
////            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//
//            connection.setRequestProperty("charset", "utf-8");
//            connection.setUseCaches (false);

            InputStream is = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(is, Charset.forName("UTF-8"));

            BufferedReader br = new BufferedReader(reader);
            StringBuffer buffer = new StringBuffer();
            String str;
            while ((str = br.readLine()) != null) {
                buffer.append(str + "\n");
            }
            br.close();
            JSONObject jsonObject = new JSONObject(buffer.toString());
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONArray jsonArray() {
        JSONArray array = null;
        try {
            array = dataJson("http://api.androidhive.info/contacts/").getJSONArray("contacts");
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    //XML parse
    //Static XML data which we will parse
    final String XMLData = "<?xml version=\"1.0\"?>\n" +
            "                         <login>\n" +
            "                            <status>OK</status>\n" +
            "                            <jobs>\n" +
            "                              <job>\n" +
            "                                  <id>4</id>\n" +
            "                                  <companyid>4</companyid>\n" +
            "                                  <company>Android Example</company>\n" +
            "                                  <address>Parse XML Android</address>\n" +
            "                                  <city>Tokio</city>\n" +
            "                                  <state>Xml Parsing Tutorial</state>\n" +
            "                                  <zipcode>601301</zipcode>\n" +
            "                                  <country>Japan</country>\n" +
            "                                  <telephone>9287893558</telephone>\n" +
            "                                  <fax>1234567890</fax>\n" +
            "                                  <date>2012-03-15 12:00:00</date>\n" +
            "                               </job>\n" +
            "                               <job>\n" +
            "                                  <id>5</id>\n" +
            "                                  <companyid>6</companyid>\n" +
            "                                  <company>Xml Parsing In Java</company>\n" +
            "                                  <address>B-22</address>\n" +
            "                                  <city>Cantabill</city>\n" +
            "                                  <state>XML Parsing Basics</state>\n" +
            "                                  <zipcode>201301</zipcode>\n" +
            "                                  <country>America</country>\n" +
            "                                  <telephone>9287893558</telephone>\n" +
            "                                  <fax>1234567890</fax>\n" +
            "                                  <date>2012-05-18 13:00:00</date>\n" +
            "                                </job>\n" +
            "                             </jobs>\n" +
            "                          </login>";
    List<XmlValuesModel> list;

    private List<XmlValuesModel> GetdataXMl() throws ParserConfigurationException, SAXException, IOException {
        BufferedReader br = new BufferedReader(new StringReader(XMLData));
        InputSource is = new InputSource(br);
        XMLParser parser=new XMLParser();
        SAXParserFactory factory= SAXParserFactory.newInstance();
        SAXParser sp=factory.newSAXParser();
        XMLReader reader=sp.getXMLReader();
        reader.setContentHandler(parser);
        reader.parse(is);
        return parser.list;
    }


    private class XMLParser extends DefaultHandler{

        List<XmlValuesModel> list;
        XmlValuesModel model;
        StringBuffer buffer;


        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            list = new ArrayList<>();
        }

        private static final String TAG = "localName";

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            buffer = new StringBuffer();
            if(localName.equals("login")){
                Log.d(TAG,"login");
            }else if(localName.equals("status")){
                Log.d(TAG,"status");
            }else if (localName.equals("jobs")){
                Log.d(TAG,"jobs");
            }else if(localName.equals("job")){
                model = new XmlValuesModel();
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if(localName.equalsIgnoreCase("job")){
                list.add(model);
            }else if(localName.equalsIgnoreCase("id")){
                model.setId(Integer.valueOf(buffer.toString()));
            }else if(localName.equalsIgnoreCase("company")){
                model.setCompany(buffer.toString());
            }
        }

        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {

            /******  Read the characters and append them to the buffer  ******/
            String tempString=new String(ch, start, length);
            buffer.append(tempString);
        }
    }


    public class XmlValuesModel {

        private  int id;
        private  int companyid;
        private  String company = "";
        private  String date = "";
        private  String address="";
        private  String city="";
        private  String state="";
        private  String zipcode="";
        private  String country="";
        private  String telephone="";
        private  String forms="";

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCompanyid() {
            return companyid;
        }

        public void setCompanyid(int companyid) {
            this.companyid = companyid;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getForms() {
            return forms;
        }

        public void setForms(String forms) {
            this.forms = forms;
        }
    }




    private void init() {
        mText = (TextView) findViewById(R.id.txtView);
        mImg = (ImageView) findViewById(R.id.imgView);
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
