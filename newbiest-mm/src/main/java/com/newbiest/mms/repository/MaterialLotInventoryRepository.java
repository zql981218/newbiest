package com.newbiest.mms.repository;

import com.newbiest.base.exception.ClientException;
import com.newbiest.base.repository.custom.IRepository;
import com.newbiest.mms.model.MaterialLotInventory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by guoxunbo on 2019/2/20.
 */
@Repository
public interface MaterialLotInventoryRepository extends IRepository<MaterialLotInventory, Long> {

    /**
     * 一个仓库的库位上只能存在一个相同名称的物料批次
     */
    MaterialLotInventory findByMaterialLotRrnAndWarehouseRrnAndStorageRrn(long materialLotRrn, long warehouseRrn, long storageRrn) throws ClientException;

    /**
     * 根据物料批次或者物料批次对应的所有仓库
     */
    List<MaterialLotInventory> findByMaterialLotRrn(long materialLotRrn) throws ClientException;

    /**
     * 删除库存
     * @param materialLotRrn
     * @throws ClientException
     */
    @Modifying
    @Query("DELETE FROM MaterialLotInventory MaterialLotInventory WHERE MaterialLotInventory.materialLotRrn = :materialLotRrn")
    void deleteByMaterialLotRrn(@Param("materialLotRrn")Long materialLotRrn) throws ClientException;

    @Modifying
    @Query("DELETE FROM MaterialLotInventory m WHERE m.materialLotId in (:vboxIdList)")
    void deleteByMaterialLotIdIn(List<String> vboxIdList);
}
