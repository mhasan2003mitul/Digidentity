package eu.digidentity.exam;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;

import eu.digidentity.exam.model.CatalogItem;
import eu.digidentity.exam.view.adapter.CatalogItemAdapter;

/**
 * Created by Administrator on 14-Jul-16.
 */
public class DigidentityHomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home);

        ListView catalogItemListView = (ListView)findViewById(R.id.catalogItemList);
        catalogItemListView.setAdapter(new CatalogItemAdapter(DigidentityHomeActivity.this, new ArrayList<CatalogItem>()));
    }
}
