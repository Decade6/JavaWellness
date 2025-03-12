//shoppinglist fragment
package com.example.myapplication;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link shoppinglist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class shoppinglist extends Fragment implements AddShoppingItem.OnShoppingListCloseListener{
    private ArrayList<ShoppingItem> items;
    private ShoppingItemAdapter itemAdapter;
    private RecyclerView shoppinglist;
    private FloatingActionButton add;
    private FloatingActionButton clearAll;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public shoppinglist() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fitness.
     */
    // TODO: Rename and change types and number of parameters
    public static shoppinglist newInstance(String param1, String param2) {
        shoppinglist fragment = new shoppinglist();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_shoppinglist, container, false);
        shoppinglist = fragment.findViewById(R.id.lvItems);
        add = fragment.findViewById(R.id.addShopItem);
        clearAll = fragment.findViewById(R.id.clearAll);
        items = new ArrayList<ShoppingItem>();




        Cursor mCursor = getContext().getContentResolver().query(ShoppingItemProvider.CONTENT_URI,null,null,null,null);
        if(mCursor != null)
        {
            if(mCursor.getCount()>0){
                mCursor.moveToFirst();
                while(!mCursor.isAfterLast())
                {
                    items.add(new ShoppingItem(mCursor.getString(1), mCursor.getString(2),mCursor.getInt(3)));
                    mCursor.moveToNext();
                }
                mCursor.moveToFirst();
            }
        }

        setRecyclerView();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddShoppingItem addShoppingItem = new AddShoppingItem();
                addShoppingItem.show(getActivity().getSupportFragmentManager(), "add shopping items");
            }
        });
 
        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(items.isEmpty()){
                    Toast.makeText(getContext(),"No Items to Clear!", Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Are You Sure?").setMessage("All items in the shopping list will be removed").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            items.clear();
                            itemAdapter.notifyDataSetChanged();
                            getContext().getContentResolver().delete(ShoppingItemProvider.CONTENT_URI,null,null);
                        }
                    }).setNegativeButton("No", null).show();
                }
            }
        });


        return fragment;
    }
    private void setRecyclerView(){
        shoppinglist.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemAdapter = new ShoppingItemAdapter(getActivity(), items);
        shoppinglist.setAdapter(itemAdapter);
        shoppinglist.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onShoppingClose(String name, String info, int quantity) {
        items.add(new ShoppingItem(name,info,quantity));
        setRecyclerView();
    }
}