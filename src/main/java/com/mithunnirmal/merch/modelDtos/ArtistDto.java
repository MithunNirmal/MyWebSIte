package com.mithunnirmal.merch.modelDtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArtistDto {
    String id;
    String name;
    String dp;
}
