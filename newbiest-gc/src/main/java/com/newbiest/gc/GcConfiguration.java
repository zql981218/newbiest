package com.newbiest.gc;

import com.newbiest.base.factory.ModelFactory;
import com.newbiest.base.ui.model.NBTable;
import com.newbiest.gc.model.*;
import com.newbiest.main.YmlPropertyLoaderFactory;
import com.newbiest.mms.model.*;
import liquibase.integration.spring.SpringLiquibase;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 *
 */
@Configuration
@Data
@Slf4j
@ConfigurationProperties(prefix = "gc.liquibase")
@PropertySource(value = "classpath:gc.yml", factory = YmlPropertyLoaderFactory.class)
public class GcConfiguration {

    private String changeLog;

    private boolean enabled;

    private boolean dropFirst;

    @Bean("gcLiquibase")
    @ConditionalOnResource(resources = {"classpath:gc.yml"})
    public SpringLiquibase liquibase(DataSource dataSource) throws Exception{
        if (log.isInfoEnabled()) {
            log.info("Load GC Liquibase Configuration.");
        }
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(changeLog);
        liquibase.setShouldRun(enabled);
        liquibase.setDropFirst(dropFirst);
        return liquibase;
    }

    @PostConstruct
    public void init() {
        // 注册modelClassLoader
        ModelFactory.registerModelClassLoader(GCProductSubcode.class.getName(), GCProductSubcode.class.getClassLoader());
        ModelFactory.registerModelClassLoader(GCLcdCogDetail.class.getName(), GCLcdCogDetail.class.getClassLoader());
        ModelFactory.registerModelClassLoader(GCProductModelConversion.class.getName(), GCProductModelConversion.class.getClassLoader());
        ModelFactory.registerModelClassLoader(GCProductRelation.class.getName(), GCProductRelation.class.getClassLoader());
        ModelFactory.registerModelClassLoader(GCOutSourcePo.class.getName(), GCOutSourcePo.class.getClassLoader());
        ModelFactory.registerModelClassLoader(Supplier.class.getName(), Supplier.class.getClassLoader());
        ModelFactory.registerModelClassLoader(GCWorkorderRelation.class.getName(), GCWorkorderRelation.class.getClassLoader());
        ModelFactory.registerModelClassLoader(MLotDocRule.class.getName(), MLotDocRule.class.getClassLoader());
        ModelFactory.registerModelClassLoader(MLotDocRuleLine.class.getName(), MLotDocRuleLine.class.getClassLoader());
        ModelFactory.registerModelClassLoader(GCProductNumberRelation.class.getName(), GCProductNumberRelation.class.getClassLoader());
        ModelFactory.registerModelClassLoader(GCProductWeightRelation.class.getName(), GCProductWeightRelation.class.getClassLoader());

        // 注册历史
        ModelFactory.registerHistoryModelClassLoader(GCLcdCogDetail.class.getName(), GCLcdCogDetailHis.class.getClassLoader());
        ModelFactory.registerHistoryClassName(GCLcdCogDetail.class.getName(), GCLcdCogDetailHis.class.getName());

        ModelFactory.registerHistoryModelClassLoader(WaferIssueOrder.class.getName(), DocumentHistory.class.getClassLoader());
        ModelFactory.registerHistoryClassName(WaferIssueOrder.class.getName(), DocumentHistory.class.getName());

        ModelFactory.registerHistoryModelClassLoader(OtherShipOrder.class.getName(), DocumentHistory.class.getClassLoader());
        ModelFactory.registerHistoryClassName(OtherShipOrder.class.getName(), DocumentHistory.class.getName());

        ModelFactory.registerHistoryModelClassLoader(ReTestOrder.class.getName(), DocumentHistory.class.getClassLoader());
        ModelFactory.registerHistoryClassName(ReTestOrder.class.getName(), DocumentHistory.class.getName());

        ModelFactory.registerHistoryModelClassLoader(ReceiveOrder.class.getName(), DocumentHistory.class.getClassLoader());
        ModelFactory.registerHistoryClassName(ReceiveOrder.class.getName(), DocumentHistory.class.getName());

        ModelFactory.registerHistoryModelClassLoader(GCOutSourcePo.class.getName(), GCOutSourcePoHis.class.getClassLoader());
        ModelFactory.registerHistoryClassName(GCOutSourcePo.class.getName(), GCOutSourcePoHis.class.getName());

        ModelFactory.registerHistoryModelClassLoader(GCWorkorderRelation.class.getName(), GCWorkorderRelationHis.class.getClassLoader());
        ModelFactory.registerHistoryClassName(GCWorkorderRelation.class.getName(), GCWorkorderRelationHis.class.getName());

        ModelFactory.registerHistoryModelClassLoader(NBTable.class.getName(), NBTableHis.class.getClassLoader());
        ModelFactory.registerHistoryClassName(NBTable.class.getName(), NBTableHis.class.getName());

        ModelFactory.registerHistoryModelClassLoader(GCProductWeightRelation.class.getName(), GCProductWeightRelationHis.class.getClassLoader());
        ModelFactory.registerHistoryClassName(GCProductWeightRelation.class.getName(), GCProductWeightRelationHis.class.getName());

    }
}
