package com.newbiest.gc.rest.erp.docLine;

import com.newbiest.msg.Request;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class GCErpDocLineMergeRequest extends Request {

    public static final String MESSAGE_NAME = "GCErpDocLineMergeManager";

    public static final String ACTION_TYPE_MERGE_DOC = "MergeDoc";

    private GCErpDocLineMergeRequestBody body;
}
