package com.newbiest.gc.rest.receive.wafer;

import com.newbiest.mms.model.MaterialLot;
import com.newbiest.msg.ResponseBody;
import lombok.Data;

import java.util.List;

/**
 * Created by guoxunbo on 2017/9/29.
 */
@Data
public class WaferManagerResponseBody extends ResponseBody {

	private static final long serialVersionUID = 1L;

	private List<MaterialLot> materialLotList;

}
