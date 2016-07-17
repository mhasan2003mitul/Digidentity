package eu.digidentity.exam.view.adapter;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import eu.digidentity.exam.R;
import eu.digidentity.exam.model.CatalogItem;
import eu.digidentity.exam.unit.ImageUtil;

/**
 * Created by Md. Mainul Hasan Patwary on 14-Jul-16.
 * Email: mhasan_mitul@yahoo.com
 * Skype: mhasan_mitul
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
        return this.catalogItems.size();
    }

    @Override
    public CatalogItem getItem(int i) {
        return this.getCatalogItems().get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(applicationContext).inflate(R.layout.catalog_item_view,null);
        }
        ((TextView)view.findViewById(R.id.catalogItemId)).setText(getItem(i).getmId());
        ((TextView)view.findViewById(R.id.catalogItemText)).setText(getItem(i).getmText());
        ((TextView)view.findViewById(R.id.catalogItemConfidence)).setText(getItem(i).getmConfidence());
        ((ImageView)view.findViewById(R.id.catalogItemImage)).setImageBitmap(ImageUtil.byteToBitmap(Base64.decode(getItem(i).getmImage(),Base64.DEFAULT)));
        return view;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public List<CatalogItem> getCatalogItems() {
        return catalogItems;
    }

    public void setCatalogItems(List<CatalogItem> catalogItems) {
        this.catalogItems = catalogItems;
    }
}
