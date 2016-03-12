package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //String priceMessage = "Valor $" + calculatePrice();

        CheckBox checkWhipped = (CheckBox) findViewById(R.id.check_whipped);
        CheckBox checkChocolate = (CheckBox) findViewById(R.id.check_chocolate);
        Boolean WhippedCream = checkWhipped.isChecked();
        Boolean Chocolate = checkChocolate.isChecked();

        EditText nome = (EditText) findViewById(R.id.Edt_nome);
        String textoNome = nome.getText().toString();

        Log.v("MainActivity", "foi selecionado ? " + WhippedCream);

        String priceMessage = createOrderSummary(calculatePrice(WhippedCream, Chocolate), WhippedCream, Chocolate, textoNome);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java for: " + textoNome);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


       // displayMessage(priceMessage);
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(Boolean addWhippedCream, Boolean addChocolate) {
        int  basePrice = 5;

        if(addWhippedCream){
            basePrice = basePrice + 1;
        }
        if (addChocolate){
            basePrice = basePrice + 1;
        }

        int price = quantity * basePrice;
        return price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numero) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numero);
    }

//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

    public void increment(View view) {
        quantity++;
        displayQuantity(quantity);


    }

    public void decrement(View view) {
        if (quantity <= 0) {
            Toast.makeText(MainActivity.this, "Tá bom môfiuu!", Toast.LENGTH_SHORT).show();

        } else {
            quantity--;
            displayQuantity(quantity);
        }
    }

    public String createOrderSummary(int price, boolean cream, boolean chocolate, String nome) {


        return "Name: " + nome +
                "\nAdd whipped cream? " + cream +
                "\nAdd Chocolate? " + chocolate +
                "\nQuantity: " + quantity +
                "\nTotal: " + price +
                "\nThank you!";
    }
}