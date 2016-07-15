package ly.generalassemb.drewmahrt.shoppinglistwithdetailview;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView nameTextView = (TextView)findViewById(R.id.name_text_view);
        TextView descriptionTextView = (TextView)findViewById(R.id.description_text_view);
        TextView priceTextView = (TextView)findViewById(R.id.price_text_view);
        TextView typeTextView = (TextView)findViewById(R.id.type_text_view);

        /*
            We need to figure out which item the user clicked. If everything went according to plan,
            the Intent should contain an extra which is the primary key value of the selected item.
            Try to get that value from the Intent. Use -1 as a default value in case the extra was
            not added to the Intent as we expected.
         */
        int selectedId = getIntent().getIntExtra("dbIndex",-1);

        /*
            If selectedId has a value of -1, that means the primary key value of the selected item
            was NOT passed as an extra in the Intent. In that case, display an error message.
            Otherwise, if selectedId is not -1, then query the db for the selected item.
         */
        if(selectedId != -1) {
            // use the getItem() method to get the one item we want to show, and specify it's primary key value as an argument
            Cursor selectedItemCursor = ShoppingSQLiteOpenHelper.getInstance(DetailActivity.this).getItem(selectedId);
            selectedItemCursor.moveToFirst();

            // once we have the item, populate this activity's view with the item's details
            nameTextView.setText(selectedItemCursor.getString(selectedItemCursor.getColumnIndex(ShoppingSQLiteOpenHelper.COL_ITEM_NAME)));
            descriptionTextView.setText(selectedItemCursor.getString(selectedItemCursor.getColumnIndex(ShoppingSQLiteOpenHelper.COL_ITEM_DESCRIPTION)));
            priceTextView.setText(selectedItemCursor.getString(selectedItemCursor.getColumnIndex(ShoppingSQLiteOpenHelper.COL_ITEM_PRICE)));
            typeTextView.setText(selectedItemCursor.getString(selectedItemCursor.getColumnIndex(ShoppingSQLiteOpenHelper.COL_ITEM_TYPE)));

            // it's always good practice to close the cursor when you're done with it!
            selectedItemCursor.close();
        } else {
            nameTextView.setText("Error: The selected item was not found!");
        }
    }
}
