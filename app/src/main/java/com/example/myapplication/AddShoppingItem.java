/*
    Add shopping items to the shopping list dialog
    prompts user for input fields
 */
package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddShoppingItem extends DialogFragment {
    private AddShoppingItem.OnShoppingListCloseListener listener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddShoppingItem.OnShoppingListCloseListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnShoppingCloseListener");
        }
    }

    public interface OnShoppingListCloseListener{
        void onShoppingClose(String name, String info, int quantity);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        TextView txt1 = new TextView(getContext());
        txt1.setTextColor(getResources().getColor(R.color.black));
        txt1.setTextSize(16);
        txt1.setText("Item:");
        TextView txt2 = new TextView(getContext());
        txt2.setTextColor(getResources().getColor(R.color.black));
        txt2.setTextSize(16);
        txt2.setText("Additional info: ");
        TextView txt3 = new TextView(getContext());
        txt3.setText("Quantity:");
        txt3.setTextSize(16);
        txt3.setTextColor(getResources().getColor(R.color.black));
        EditText name = new EditText(getContext());
        name.setHint("ex. Milk");
        EditText info = new EditText(getContext());
        info.setHint("ex. 1 Gallon (Can be left blank)");
        EditText quantity = new EditText(getContext());
        quantity.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        quantity.setHint("ex. 1");

        LinearLayout myLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30,0,30,0);
        myLayout.setOrientation(LinearLayout.VERTICAL);
        myLayout.setPadding(30,20,30,10);
        myLayout.addView(txt1,layoutParams);
        myLayout.addView(name, layoutParams);
        myLayout.addView(txt2,layoutParams);
        myLayout.addView(info,layoutParams);
        myLayout.addView(txt3,layoutParams);
        myLayout.addView(quantity,layoutParams);

        AlertDialog.Builder prompt = new AlertDialog.Builder(getContext());
        prompt.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String itemname = name.getText().toString();
                String additionalInfo = info.getText().toString() + "";
                int itemquantity = 0;
                try{
                    itemquantity = Integer.parseInt(quantity.getText().toString());
                } catch (NumberFormatException e){
                    // catches error if string is entered
                }

                ContentValues mValues = new ContentValues();

                mValues.put("Name", itemname.trim());
                mValues.put("Info", additionalInfo.trim());
                mValues.put("Quantity", itemquantity);

                getContext().getContentResolver().insert(ShoppingItemProvider.CONTENT_URI, mValues);

                listener.onShoppingClose(itemname,additionalInfo,itemquantity);
            }
        });
        prompt.setNegativeButton("Cancel", null);
        prompt.setView(myLayout);

        return prompt.create();
    }
}
