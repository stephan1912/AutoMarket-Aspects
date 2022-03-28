package com.awb.automarket.dto.advertDto;


import java.util.List;

public class AdvertListResponse {
    public int totalCount;
    public List<AdvertDTO> adverts;
	public AdvertListResponse(int totalCount, List<AdvertDTO> adverts) {
		super();
		this.totalCount = totalCount;
		this.adverts = adverts;
	}
	public AdvertListResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    
}
