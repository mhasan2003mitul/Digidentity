package eu.digidentity.exam.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import eu.digidentity.exam.R;
import eu.digidentity.exam.model.CatalogItem;

/**
 * Created by Administrator on 14-Jul-16.
 */
public class CatalogItemAdapter extends BaseAdapter {

    private Context applicationContext;
    private List<CatalogItem> catalogItems;

    private CatalogItemAdapter() {
    }

    public CatalogItemAdapter(Context applicationContext,List<CatalogItem> catalogItems){
        this.applicationContext = applicationContext;
        this.catalogItems = catalogItems;
    }

    @Override
    public int getCount() {
//        return this.catalogItems.size();
        return 100;
    }

    @Override
    public CatalogItem getItem(int i) {
        return this.catalogItems.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(applicationContext).inflate(R.layout.catalog_item_view,null);
        }
        return view;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
