package com.sudipta.bookings.code_challenge.dao;


import com.sudipta.bookings.code_challenge.model.Department;
import com.sudipta.bookings.code_challenge.model.MarketingDepartment;
import com.sudipta.bookings.code_challenge.model.SalesDepartment;
import com.sudipta.bookings.code_challenge.model.SoftwareDepartment;

public class DepartmentFactory {

    public static Department createDepartment(String departmentType) {

        switch (departmentType){
            case "Marketing Department" :return new MarketingDepartment();
            case "Software Department": return new SoftwareDepartment();
            case "Sales Department": return new SalesDepartment();
            default : throw new RuntimeException("Please enter valid Department Type.");
        }
    }

}
