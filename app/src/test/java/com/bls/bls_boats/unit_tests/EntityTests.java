package com.bls.bls_boats.unit_tests;

import android.content.Context;

import com.bls.bls_boats.main.DB.DBHelper;
import com.bls.bls_boats.main.Entity.Operator;

import org.junit.Before;
import org.junit.Test;

import androidx.test.core.app.ApplicationProvider;
import static org.junit.Assert.assertEquals;


public class EntityTests {
    private static String OPERATORNAME = "BLS";
    private DBHelper dbHelper;

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        dbHelper = new DBHelper(context);
    }


    private Operator createOperator() {
        Operator operator = new Operator();
        operator.setOperatorName(OPERATORNAME);

        return operator;
    }

    @Test
    public void insertOperator(){
        Operator operator = createOperator();
        dbHelper.insertOperator(operator);

        operator = dbHelper.getOperator(1);

        assertEquals(operator.getOperatorName(), OPERATORNAME);

    }


    @Test
    public void deleteOperator() {
        Operator operator = createOperator();


    }

}
