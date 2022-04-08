package org.me.gcu.abedelkriem_hamid_s2027185;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String urlString = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    public static final String planningJourneyUrl = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    public static final String currentIncidentUrl  = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";

    private Button displayAllButton;

    private TextView channelTitle;
    private TextView channelDesc;

    private ExpandableListView listView;
    MyExpandableListAdapter listAdapter;

    private EditText roadWorkInput;
    private Button searchByKeyWordButton;

    private EditText roadWorkByDateInput;
    private Button searchByDateButton;

    private EditText planningJourneyDateInput;
    private Button planningJourneyDateButton;

    private Button currentIncidentButton;

    private Channel channel;
    private Channel planningJourneyChannel;

    private Channel currentIncidentChannel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //call initialise the Date
        init();

        //
        displayAllButton = (Button) findViewById(R.id.displayAllButton);
        displayAllButton.setOnClickListener(this::displayAll);

        //list adapter
        listAdapter = new MyExpandableListAdapter(this, new ArrayList<>());
        listView = (ExpandableListView) findViewById(R.id.expandableListView);
        listView.setAdapter(listAdapter);

        //channel details
        channelTitle = (TextView) findViewById(R.id.channelTitle);//title
        channelDesc = (TextView) findViewById(R.id.channelDesc);//desc


        roadWorkInput = (EditText) findViewById(R.id.roadWorkInput);
        searchByKeyWordButton = (Button) findViewById(R.id.searchByKeyWordButton);
        searchByKeyWordButton.setOnClickListener(this::handleFilterByKeyWord);

        roadWorkByDateInput = (EditText) findViewById(R.id.roadWorkByDateInput);
        searchByDateButton = (Button) findViewById(R.id.searchByDateButton);
        searchByDateButton.setOnClickListener(this::handleFilterByDate);


        planningJourneyDateInput = (EditText) findViewById(R.id.planningJourneyDateInput);
        planningJourneyDateButton = (Button) findViewById(R.id.planningJourneyDateButton);
        planningJourneyDateButton.setOnClickListener(this::planningJourneyByDate);

        currentIncidentButton = (Button) findViewById(R.id.currentIncidentButton);
        currentIncidentButton.setOnClickListener(this::displayCurrentIncident);
    }

    private void init() {
        //init stages
        channel = new Channel();
        planningJourneyChannel = new Channel();
        currentIncidentChannel = new Channel();
        //read the data from the server and parse
        try {
            showMessage("please wait while we reading data ..");
            new Thread(new GetData()).start();
            new Thread(new GetCurrentIncidentData()).start();
        } catch (Exception ex) {
            System.err.println("***Exception: " + ex.toString());
        }
    }

    private void clear() {
        //clear
        channelTitle.setText("");
        channelDesc.setText("");
    }

    public void displayAll(View v) {

        if (channel == null) {
            new Thread(new GetData()).start();
        }

        //clear
        clear();

        display(channel);

        // Extract the specified range of elements from the original list and store them into the sublist
        List<Item> subList = channel.getItems();//.subList(0, 5);//.stream().limit(5).collect(Collectors.toList());//

        updateItemList(subList);
    }

    /**
     * Search By Keywords
     */
    public void handleFilterByKeyWord(View v) {
        //new Thread(new filterDataByKeyword()).start();
        String userInput = roadWorkInput.getText().toString();
        List<Item> list = new ArrayList<>();

        for (Item item : channel.getItems()) {
            if (item.getTitle().equalsIgnoreCase(userInput)) {
                list.add(item);
            }
        }
        updateItemList(list);
    }

    /**
     * Search By Date
     */
    public void handleFilterByDate(View v) {
        //new Thread(new filterDataByKeyword()).start();
        List<Item> list = new ArrayList<>();
        String userInput = roadWorkByDateInput.getText().toString();

        for (Item item : channel.getItems()) {
            if (Utility.isEquals(Utility.getDate(item.getPubDate(), Utility.ITEM_PUB_DATE_FORMAT), Utility.getDate(userInput, Utility.DATE_FORMAT))) {
                list.add(item);
            }
        }
        updateItemList(list);

    }

    /**
     * find planning journey By Date
     */
    public void planningJourneyByDate(View v) {
        //new Thread(new filterDataByKeyword()).start();
        List<Item> list = new ArrayList<>();
        String userInput = planningJourneyDateInput.getText().toString();

//        Log.d("userInput", userInput);
//        Log.d("planning", "planningJourneyByDate called");
        for (Item item : planningJourneyChannel.getItems()) {
            if (Utility.isEquals(Utility.getDate(item.getPubDate(), Utility.ITEM_PUB_DATE_FORMAT), Utility.getDate(userInput, Utility.DATE_FORMAT))) {
                list.add(item);
            }
        }

        updateItemList(list);
    }

    public void displayCurrentIncident(View v) {
        if (currentIncidentChannel == null) {
            new Thread(new GetCurrentIncidentData()).start();
        }

        //clear
        clear();

        display(currentIncidentChannel);

        // Extract the specified range of elements from the original list and store them into the sublist
        List<Item> subList = currentIncidentChannel.getItems();

        updateItemList(subList);
    }

    private class GetCurrentIncidentData implements Runnable {

        @Override
        public void run() {

            try {
                RoadWorksParser parser = new RoadWorksParser();
                currentIncidentChannel = parser.parse(currentIncidentUrl);
            } catch (Exception e) {
                System.out.println("XML Pasing Exception = " + e);
                showMessage("*Error while Parsing Data: " + e.getMessage());
            }
        }
    }


    private void updateItemList(List<Item> list) {
        showMessage("Updating List, total = " + list.size());
        listAdapter = new MyExpandableListAdapter(this, list);
        listView.setAdapter(listAdapter);

        //listAdapter.notifyDataSetChanged();;
        //listAdapter.addAll( list );
    }

    private void updateUI(TextView textView, String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // This code will always run on the UI thread, therefore is safe to modify UI elements.
                textView.append(Html.fromHtml(text));
            }
        });
    }

    private void display(Channel channel) {
        if (channel == null) {
            return;

        }

        channelTitle.setText(channel.getTitle());
        channelDesc.setText(channel.getDescription());
    }


    private class GetData implements Runnable {

        @Override
        public void run() {

            try {
                RoadWorksParser parser = new RoadWorksParser();
                channel = parser.parse(urlString);

                planningJourneyChannel = parser.parse(planningJourneyUrl);

            } catch (Exception e) {
                System.out.println("XML Pasing Exception = " + e);
                showMessage("*Error while Parsing Data: " + e.getMessage());
            }
        }
    }

    private void showMessage(String text) {
        runOnUiThread(() -> Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG).show());
    }
}


