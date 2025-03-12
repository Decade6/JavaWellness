//shopping item adapter for recycler view
package com.example.myapplication;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShoppingItemAdapter extends RecyclerView.Adapter<ShoppingItemAdapter.ViewHolder>{
    Context context;
    ArrayList<ShoppingItem> itemList;
    public ShoppingItemAdapter (Context context, ArrayList<ShoppingItem> itemList)
    {
        this.context = context;
        this.itemList = itemList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_items, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = itemList.get(position).getName();
        String info = itemList.get(position).getInfo();
        int quantity = itemList.get(position).getQuantity();

        holder.name.setText(name);
        holder.info.setText(info);
        holder.quantity.setText(quantity+"");

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txt1 = new TextView(context);
                txt1.setTextColor(context.getResources().getColor(R.color.black));
                txt1.setTextSize(16);
                txt1.setText("Item:");
                TextView txt2 = new TextView(context);
                txt2.setTextColor(context.getResources().getColor(R.color.black));
                txt2.setTextSize(16);
                txt2.setText("Additional info: ");
                TextView txt3 = new TextView(context);
                txt3.setText("Quantity:");
                txt3.setTextSize(16);
                txt3.setTextColor(context.getResources().getColor(R.color.black));
                EditText name1 = new EditText(context);
                name1.setHint("ex. Milk");
                name1.setText(name);
                EditText info1 = new EditText(context);
                info1.setHint("ex. 1 Gallon (Can be left blank)");
                info1.setText(info);
                EditText quantity1 = new EditText(context);
                quantity1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                quantity1.setHint("ex. 1");
                quantity1.setText(quantity+"");

                LinearLayout myLayout = new LinearLayout(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(30,0,30,0);
                myLayout.setOrientation(LinearLayout.VERTICAL);
                myLayout.setPadding(30,20,30,10);
                myLayout.addView(txt1,layoutParams);
                myLayout.addView(name1, layoutParams);
                myLayout.addView(txt2,layoutParams);
                myLayout.addView(info1,layoutParams);
                myLayout.addView(txt3,layoutParams);
                myLayout.addView(quantity1,layoutParams);

                AlertDialog.Builder prompt = new AlertDialog.Builder(context);
                prompt.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String itemname = name1.getText().toString();
                        String additionalInfo = info1.getText().toString() + "";
                        int itemquantity = Integer.parseInt(quantity1.getText().toString());
                        itemList.set(holder.getAdapterPosition(),new ShoppingItem(itemname,additionalInfo,itemquantity));

                        notifyDataSetChanged();
                        ContentValues updateValues = new ContentValues();
                        updateValues.put("Name",itemname.trim());
                        updateValues.put("Info", additionalInfo.trim());
                        updateValues.put("Quantity",itemquantity);

                        String selectionClause = "Name = ? AND Info = ? AND Quantity = ?";
                        String[] selectionArgs = { name,info,quantity+""};

                        context.getContentResolver().update(ShoppingItemProvider.CONTENT_URI,updateValues,selectionClause,selectionArgs);
                    }
                });
                prompt.setNegativeButton("Cancel", null);
                prompt.setView(myLayout);
                prompt.show();
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are You Sure?").setMessage("This item from the list will be removed").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String itemname = itemList.get(holder.getAdapterPosition()).getName();
                        String additionalInfo = itemList.get(holder.getAdapterPosition()).getInfo();
                        int itemquantity = itemList.get(holder.getAdapterPosition()).getQuantity();
                        String[] selectionArgs = {itemname, additionalInfo, itemquantity+""};
                        itemList.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();

                        context.getContentResolver().delete(ShoppingItemProvider.CONTENT_URI,"Name = ? AND Info = ? AND Quantity = ?", selectionArgs);
                    }
                }).setNegativeButton("No", null).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        TextView name;
        TextView info;
        TextView quantity;
        Button edit;
        Button remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            info = itemView.findViewById(R.id.additionalInfo);
            quantity = itemView.findViewById(R.id.quantity);
            edit = itemView.findViewById(R.id.edit);
            remove = itemView.findViewById(R.id.remove);

        }

    }
}
