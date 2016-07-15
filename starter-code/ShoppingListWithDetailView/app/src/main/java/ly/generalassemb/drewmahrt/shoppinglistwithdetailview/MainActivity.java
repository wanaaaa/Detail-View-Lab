package ly.generalassemb.drewmahrt.shoppinglistwithdetailview;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import ly.generalassemb.drewmahrt.shoppinglistwithdetailview.setup.DBAssetHelper;

import static ly.generalassemb.drewmahrt.shoppinglistwithdetailview.ShoppingSQLiteOpenHelper.COL_ID;

public class MainActivity extends AppCompatActivity {
    private ListView mShoppingListView;
    private CursorAdapter mCursorAdapter, mCursorDetailAdapter;
    private ShoppingSQLiteOpenHelper mHelper;
    private static final String TAG = "asdfasdf";
    private Cursor mCursor;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Ignore the two lines below, they are for setup
        DBAssetHelper dbSetup = new DBAssetHelper(MainActivity.this);
        dbSetup.getReadableDatabase();

        mShoppingListView = (ListView) findViewById(R.id.shopping_list_view);
        mHelper = new ShoppingSQLiteOpenHelper(MainActivity.this);

//        final Cursor cursor = mHelper.getShoppingList();
        mCursor = mHelper.getShoppingList();

        mCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                mCursor, new String[]{ShoppingSQLiteOpenHelper.COL_ITEM_NAME},
                new int[]{android.R.id.text1}, 0);
        mShoppingListView.setAdapter(mCursorAdapter);

        handleIntent(getIntent());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.


        mShoppingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viw, int position, long id) {
                mCursor.moveToPosition(position);

                Intent intentToDetail = new Intent(MainActivity.this, MmDetailActivity.class);
                intentToDetail.putExtra("idItem",
                        mCursor.getInt(mCursor.getColumnIndex(COL_ID)));
                startActivity(intentToDetail);

                /////////////////////////////
                Toast.makeText(getApplicationContext(), "asdf", Toast.LENGTH_LONG).show();


                Log.e(TAG,"asdf");
            }
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            /////////////////////////////////////
//            Cursor cursor = mHelper.searchShoppingList(query);
            mCursor = mHelper.searchShoppingList(query);
            mCursorAdapter.changeCursor(mCursor);
            mCursorAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://ly.generalassemb.drewmahrt.shoppinglistwithdetailview/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://ly.generalassemb.drewmahrt.shoppinglistwithdetailview/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
