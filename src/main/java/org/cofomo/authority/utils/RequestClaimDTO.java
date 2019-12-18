package org.cofomo.authority.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestClaimDTO {
	private String vct;
	private String jwt;
}
