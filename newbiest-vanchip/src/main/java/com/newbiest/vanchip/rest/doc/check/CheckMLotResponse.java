package com.newbiest.vanchip.rest.doc.check;

import com.newbiest.base.msg.Response;
import lombok.Data;

@Data
public class CheckMLotResponse extends Response {
	
	private static final long serialVersionUID = 1L;
	
	private CheckMLotResponseBody body;
	
}
