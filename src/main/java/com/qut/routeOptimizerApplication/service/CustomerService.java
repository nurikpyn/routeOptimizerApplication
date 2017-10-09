package com.qut.routeOptimizerApplication.service;

import java.util.ArrayList;
import java.util.List;

import com.qut.routeOptimizerApplication.Bean.Address;
import com.qut.routeOptimizerApplication.Bean.Customer;

public class CustomerService {
	public List<Customer> getCustomerList(List<Address> uploadInvoiceBean,int demand) {
		List<Customer> customerList=new ArrayList<Customer>();
		Customer customer;
		int locationLength = uploadInvoiceBean.size();
		for (int i = 0; i < locationLength; i++) {
			customer=new Customer();
			customer.setAddress(uploadInvoiceBean.get(i));
			customer.setDemand(demand);
			customerList.add(customer);
		}
		return customerList;
	}
}
