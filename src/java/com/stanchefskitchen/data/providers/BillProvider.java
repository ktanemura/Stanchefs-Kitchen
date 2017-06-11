/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanchefskitchen.data.providers;

import com.stanchefskitchen.data.models.Bill;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kyle
 */
public class BillProvider {
    private static Connection connection = DatabaseProvider.getConnection();
    private static final String GET_BILL = "select * from bill where id = ?;";
    private static final String CREATE_BILL = "insert into bill(employeeId, total) values (?, ?);";
    private static final String PAY_BILL = "update bill set isPaid = TRUE where bill.id = ?;";
    public static Bill get_bill(int id) {
        Bill b = null;
        
        try {
            PreparedStatement s = connection.prepareStatement(GET_BILL);
            ResultSet r = s.executeQuery();
            
            if (r.next()) {
                b = new Bill(r.getInt(Bill.ID), r.getInt(Bill.EMPL_ID),
                        r.getDouble(Bill.PRICE), r.getDouble(Bill.TIP), r.getBoolean(Bill.PAID));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BillProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public static void create_bill(int employeeId, double total) {
        try {
            PreparedStatement s = connection.prepareStatement(CREATE_BILL);
            s.setInt(1, employeeId);
            s.setDouble(2, total);
            s.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BillProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void set_paid(int billId) {
        try {
            PreparedStatement s = connection.prepareStatement(PAY_BILL);
            s.setInt(1, billId);
            s.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BillProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
