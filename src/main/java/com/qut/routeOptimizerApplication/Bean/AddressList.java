package com.qut.routeOptimizerApplication.Bean;

import java.util.Iterator;
import java.util.List;

public class AddressList implements Iterable<Address> {
	private List<Address> addresses;

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	@Override
	public Iterator<Address> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
