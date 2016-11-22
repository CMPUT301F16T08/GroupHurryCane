package hurrycaneblurryname.ryde.View;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import hurrycaneblurryname.ryde.ElasticSearchRequestController;
import hurrycaneblurryname.ryde.Model.Request.Request;
import hurrycaneblurryname.ryde.Model.User;
import hurrycaneblurryname.ryde.R;

/**
 * The type Search Requests activity.
 * Author: Chen
 * Storyboard by: Blaz
 */

public class SearchRequestsActivity extends AppCompatActivity {
    private Button searchButton;
    private Button refreshButton;
    private EditText SearchEditText;

    private ListView searchView;
    private ArrayAdapter<Request> searchViewAdapter;

    private ArrayList<Request> searchResult;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_requests);
        setTitle("Search");

        searchButton = (Button)findViewById(R.id.searchButton);
        refreshButton = (Button)findViewById(R.id.refreshButton);
        searchView = (ListView) findViewById(R.id.SearchResultListView);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mLastLocation = extras.getParcelable("currLocation");
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String lon = String.valueOf(mLastLocation.getLongitude());
                String lat= String.valueOf(mLastLocation.getLatitude());
                searchRequests(lat, lon);
            }
        });


        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                searchRequests("");
            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mLastLocation == null) {
                    Toast.makeText(SearchRequestsActivity.this, "Current location not found", Toast.LENGTH_SHORT).show();
                }
                String str = parent.getItemAtPosition(position).toString();

            }
        });
    }

    /**
     * Execute search for open requests
     * @param searchParam
     */
    public void searchRequests(String... searchParam) {
        ElasticSearchRequestController.GetOpenRequestsTask getRequestsTask = new ElasticSearchRequestController.GetOpenRequestsTask();
        getRequestsTask.execute(searchParam);
        ArrayList<Request> temp = new ArrayList<>();

        try {
            temp = getRequestsTask.get();

            if (temp.isEmpty()) {
                Toast.makeText(SearchRequestsActivity.this, "No results!", Toast.LENGTH_SHORT).show();
            } else {
                searchResult = temp;
                searchViewAdapter = new ArrayAdapter<Request>(SearchRequestsActivity.this, R.layout.list_item, searchResult);
                searchView.setAdapter(searchViewAdapter);
            }

        } catch (Exception e) {
            Toast.makeText(SearchRequestsActivity.this, "Could not communicate with server", Toast.LENGTH_SHORT).show();
            Log.i("ErrorGetUser", "Something went wrong when looking for requests");
            //e.printStackTrace();
        }

    }
}
