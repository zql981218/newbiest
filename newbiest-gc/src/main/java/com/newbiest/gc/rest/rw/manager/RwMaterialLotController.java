package com.newbiest.gc.rest.rw.manager;

import com.newbiest.base.exception.ClientException;
import com.newbiest.gc.service.GcService;
import com.newbiest.mms.model.MaterialLot;
import com.newbiest.msg.Request;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController("RwMaterialLotController")
@RequestMapping("/gc")
@Slf4j
@Api(value="/gc", tags="gc客制化接口", description = "GalaxyCore客制化接口")
public class RwMaterialLotController {

    @Autowired
    GcService gcService;

    @ApiOperation(value = "RwManager", notes = "RW管理")
    @ApiImplicitParam(name="request", value="request", required = true, dataType = "RwMaterialLotRequest")
    @RequestMapping(value = "/rwMaterialLotManager", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public RwMaterialLotResponse execute(@RequestBody RwMaterialLotRequest request) throws Exception {
        RwMaterialLotResponse response = new RwMaterialLotResponse();
        response.getHeader().setTransactionId(request.getHeader().getTransactionId());

        RwMaterialLotResponseBody responseBody = new RwMaterialLotResponseBody();
        RwMaterialLotRequestBody requestBody = request.getBody();

        String actionType = requestBody.getActionType();
        if (RwMaterialLotRequest.ACTION_QUERY_PRINT_PARAMETER.equals(actionType)) {
            List<Map<String, String>> parameterMapList = gcService.getRWIssueMaterialLotPrintParameter(requestBody.getMaterialLotList());
            responseBody.setParameterList(parameterMapList);
        } else if(RwMaterialLotRequest.ACTION_RECEIVE_PACKEDLOT.equals(actionType)) {
            List<Map<String, String>> parameterMapList = gcService.receiveRWFinishPackedLot(requestBody.getMesPackedLots(), requestBody.getPrintLabel());
            responseBody.setParameterList(parameterMapList);
        } else if(RwMaterialLotRequest.ACTION_PRINT_LOT_LABEL.equals(actionType)){
            Map<String, String> parameterMap = gcService.getRwReceiveLotLabelPrintParameter(requestBody.getMaterialLot());
            responseBody.setParameterMap(parameterMap);
        } else if(RwMaterialLotRequest.ACTION_AUTO_PICK.equals(actionType)){
            List<MaterialLot> materialLots = gcService.rwTagginggAutoPickMLot(requestBody.getMaterialLotList(), requestBody.getPickQty());
            responseBody.setMaterialLotList(materialLots);
        } else if(RwMaterialLotRequest.ACTION_STOCK_OUT_TAG.equals(actionType)) {
            gcService.rwMaterialLotStockOutTag(requestBody.getMaterialLotList(), requestBody.getCustomerName(), requestBody.getAbbreviation(), requestBody.getRemarks());
        } else {
            throw new ClientException(Request.NON_SUPPORT_ACTION_TYPE + requestBody.getActionType());
        }
        response.setBody(responseBody);
        return response;
    }
}
