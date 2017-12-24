package com.tianmaying.utils;

public class Pager {

	private boolean hasPrevious;
	private boolean hasNext;
	private int page;
	private int size;
	private int total;
	
	public Pager(int page, int size, int total) {
		this.page = page;
		this.size = size;
		this.total = total;
		
		if(page > 1) {
			hasPrevious = true;
		}
		
		if(page * size < total) {
			hasNext = true;
		}
	}
	//getters and setters

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	
}
