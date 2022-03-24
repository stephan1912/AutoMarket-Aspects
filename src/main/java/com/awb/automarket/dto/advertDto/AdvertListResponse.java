package com.awb.automarket.dto.advertDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AdvertListResponse {
    public int totalCount;
    public List<AdvertDTO> adverts;
}
