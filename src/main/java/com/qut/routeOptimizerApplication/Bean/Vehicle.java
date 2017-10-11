package com.qut.routeOptimizerApplication.Bean;

public class Vehicle {
	protected int capacity;
    protected Depot depot;
    protected Customer nextCustomer;
protected int id;
	public int getCapacity() {
		return capacity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}

	public Customer getNextCustomer() {
		return nextCustomer;
	}

	public void setNextCustomer(Customer nextCustomer) {
		this.nextCustomer = nextCustomer;
	}
    
}
