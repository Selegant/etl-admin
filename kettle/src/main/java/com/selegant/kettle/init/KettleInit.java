package com.selegant.kettle.init;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class KettleInit implements CommandLineRunner {

    @Value("${kettle.repository-name}")
    private String repositoryName;

    @Value("${kettle.access-type}")
    private String accessType;

    @Value("${kettle.database-type}")
    private String databaseType;

    @Value("${kettle.database-host}")
    private String databaseHost;

    @Value("${kettle.database-name}")
    private String databaseName;

    @Value("${kettle.database-port}")
    private String databasePort;

    @Value("${kettle.database-user}")
    private String databaseUser;

    @Value("${kettle.database-password}")
    private String databasePassword;

    @Value("${kettle.repository-username}")
    private String repositoryUsername;

    @Value("${kettle.repository-password}")
    private String repositoryPassword;

    @Value("${kettle.plugins-path}")
    private String pluginsPath;


    private Logger logger = LoggerFactory.getLogger(KettleInit.class);
    @Override
    public void run(String... args) throws Exception {

    }

    @Bean
    public KettleDatabaseRepository kettleDatabaseRepository() throws KettleException {
        logger.info(">>>>>>>>>>>系统启动初始化Kettle环境开始<<<<<<<<<<<");
        if(KettleEnvironment.isInitialized()){
            logger.info(">>>>>>>>>>>Kettle环境已经初始化进行重新初始化<<<<<<<<<<<");
            KettleEnvironment.shutdown();
        }
//        System.getProperties().put("KETTLE_HOME", "/Users/selegant/Downloads/data-integration");
        System.getProperties().put("KETTLE_PLUGIN_BASE_FOLDERS", pluginsPath);
//
//        StepPluginType stepPluginType = StepPluginType.getInstance();
//        PluginRegistry.addPluginType(stepPluginType);
//        for (PluginFolderInterface pluginFolder : stepPluginType.getPluginFolders()) {
//            logger.info("插件地址为"+pluginFolder.getFolder());
//        }

//        PluginRegistry.addPluginType(RowDistributionPluginType.getInstance());
//        PluginRegistry.addPluginType(LogTablePluginType.getInstance());
//        PluginRegistry.addPluginType(CartePluginType.getInstance());
//        PluginRegistry.addPluginType(CompressionPluginType.getInstance());
//        PluginRegistry.addPluginType(AuthenticationProviderPluginType.getInstance());
//        PluginRegistry.addPluginType(AuthenticationConsumerPluginType.getInstance());
//        PluginRegistry.addPluginType(PartitionerPluginType.getInstance());
//        PluginRegistry.addPluginType(JobEntryPluginType.getInstance());
//        PluginRegistry.addPluginType(RepositoryPluginType.getInstance());
//        PluginRegistry.addPluginType(DatabasePluginType.getInstance());
//        PluginRegistry.addPluginType(LifecyclePluginType.getInstance());
//        PluginRegistry.addPluginType(KettleLifecyclePluginType.getInstance());
//        PluginRegistry.addPluginType(ImportRulePluginType.getInstance());

//        PluginRegistry.init();
//
//        KettleVariablesList.init();

        KettleEnvironment.init();

        logger.info(">>>>>>>>>>>系统启动初始化Kettle环境成功<<<<<<<<<<<");
        KettleDatabaseRepository repository = new KettleDatabaseRepository();
        try {
            KettleDatabaseRepositoryMeta repositoryMeta = new KettleDatabaseRepositoryMeta();
            DatabaseMeta databaseMeta = new DatabaseMeta(repositoryName, databaseType, accessType, databaseHost, databaseName, databasePort, databaseUser, databasePassword);
            repositoryMeta.setConnection(databaseMeta);
            repository.init(repositoryMeta);
            repository.connect(repositoryUsername, repositoryPassword);
        } catch (KettleException e) {
            logger.info(">>>>>>>>>>>初始化Kettle环境失败<<<<<<<<<<<");
            logger.error(e.getMessage(),e);
        }
        return repository;
    }
}
