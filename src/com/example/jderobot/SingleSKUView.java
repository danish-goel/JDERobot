package com.example.jderobot;

import com.google.gson.Gson;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class SingleSKUView extends ActionBarActivity  
{
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.singlesku);	
        
//        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
//		toolbar.setTitle("SKU");
//		setSupportActionBar(toolbar);
        
        Bundle bundle = getIntent().getExtras();
        String selectedSKUjson = bundle.getString("SelectedSKUjson");
        Gson gson=new Gson();
        Product selectedSKU = gson.fromJson(selectedSKUjson.toString(),Product.class);      
        
        TextView sku,amount,currency;
        
        sku=(TextView)findViewById(R.id.sku);
        amount=(TextView)findViewById(R.id.amount);
        currency=(TextView)findViewById(R.id.currency);
        
        sku.setText(selectedSKU.getSku());
        amount.setText(String.valueOf(selectedSKU.getAmount()));
        currency.setText(selectedSKU.getCurrency());
        
       
    }
	

	
	
}
